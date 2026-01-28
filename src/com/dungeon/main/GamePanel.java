package com.dungeon.main;

import com.dungeon.entity.*;
import com.dungeon.object.SuperObject;
import com.dungeon.tile.TileManager;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    // CONFIGURAÇÕES DE TELA
    public final int tileSize = 64;
    public final int maxScreenCol = 16, maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol, screenHeight = tileSize * maxScreenRow;

    // SISTEMA
    Thread gameThread;
    public KeyHandler keyH = new KeyHandler(this);
    public MouseHandler mouseH = new MouseHandler(this);
    public Sound music = new Sound(), se = new Sound();
    public UI ui = new UI(this);
    public TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    // ESTADOS DO JOGO
    public int gameState;
    public final int titleState = 0, playState = 1, mapState = 2, gameOverState = 3, winState = 4, pauseState = 5, helpState = 6, musicSelectState = 7;

    // ATRIBUTOS DO JOGADOR
    public int playerX, playerY, playerSpeed = 4, hasKey = 0;
    public String direction = "down";
    public int maxLife = 6, life = maxLife;
    public boolean invincible = false, attacking = false, collisionOn = false;
    public int invincibleCounter = 0, attackCounter = 0;
    public Rectangle playerSolidArea = new Rectangle(12, 12, 40, 40);

    // NÍVEL E ENTIDADES
    public int currentLevel = 1;
    public boolean level2Unlocked = false, level3Unlocked = false;
    public int currentBGM = 4;
    public Entity monster[] = new Entity[30];
    public SuperObject obj[] = new SuperObject[10];
    public ArrayList<Projectile> projectileList = new ArrayList<>();
    public int shotAvailableCounter = 30;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, atkUp, atkDown, atkLeft, atkRight;
    int spriteNum = 1, spriteCounter = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.setFocusable(true);
        loadImages();
        gameState = titleState;
    }

    public void setDefaultValues() {
        if(currentLevel == 3) { playerX = tileSize * 7; playerY = tileSize * 10; }
        else { playerX = tileSize; playerY = tileSize; }
        life = maxLife; hasKey = 0; shotAvailableCounter = 30;
        projectileList.clear();
        tileM.loadMap(currentLevel);
        aSetter.setObject(currentLevel);
        aSetter.setMonster(currentLevel);
    }

    public void retry() {
        setDefaultValues();
        gameState = playState;
        stopMusic(); playMusic(currentBGM);
    }

    public void changeBGM(int i) {
        stopMusic(); currentBGM = i; playMusic(i);
    }

    public void loadImages() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
            atkUp = ImageIO.read(getClass().getResourceAsStream("/player/attack_up.png"));
            atkDown = ImageIO.read(getClass().getResourceAsStream("/player/attack_down.png"));
            atkLeft = ImageIO.read(getClass().getResourceAsStream("/player/attack_left.png"));
            atkRight = ImageIO.read(getClass().getResourceAsStream("/player/attack_right.png"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void playMusic(int i) { music.setFile(i); music.play(); music.loop(); }
    public void stopMusic() { music.stop(); }
    public void playSE(int i) { se.setFile(i); se.play(); }

    public void startGameThread() { gameThread = new Thread(this); gameThread.start(); }

    @Override
    public void run() {
        double drawInterval = 1000000000/60;
        double delta = 0;
        long lastTime = System.nanoTime();
        playMusic(currentBGM);
        while(gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1) { update(); repaint(); delta--; }
        }
    }

    public void update() {
        if (gameState == playState) {
            if(shotAvailableCounter < 30) shotAvailableCounter++;
            if(keyH.lPressed && shotAvailableCounter >= 30) {
                PlayerProjectile fireball = new PlayerProjectile(this);
                fireball.set(playerX, playerY, direction, true, null);
                projectileList.add(fireball);
                shotAvailableCounter = 0;
                playSE(1);
            }
            if(keyH.enterPressed) attacking = true;
            if(attacking) attack();
            else updatePlayer();
            if(invincible) { invincibleCounter++; if(invincibleCounter > 60) { invincible = false; invincibleCounter = 0; } }
            if(life <= 0) gameState = gameOverState;
            for(int i=0; i<monster.length; i++) if(monster[i] != null) monster[i].update();
            for(int i = 0; i < projectileList.size(); i++) {
                Projectile p = projectileList.get(i);
                if(p.alive) p.update();
                else { projectileList.remove(i); i--; }
            }
        }
    }

    // --- LOCAL DA LÓGICA DE MOVIMENTO ---
    public void updatePlayer() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            // 1. Resetamos a colisão ANTES de checar a porta
            collisionOn = false;

            // 2. Checamos se o bloco da frente é sólido
            cChecker.checkTile(this);

            // 3. Checamos se há um objeto (porta) no caminho
            int objIdx = cChecker.checkObject(this, true);

            // 4. Se houver objeto, processamos a colisão/interação aqui
            pickUpObject(objIdx);

            checkMonsterCollision();

            // 5. Só movemos se a colisão na direção atual for falsa
            if (!collisionOn) {
                if (direction.equals("up")) playerY -= playerSpeed;
                if (direction.equals("down")) playerY += playerSpeed;
                if (direction.equals("left")) playerX -= playerSpeed;
                if (direction.equals("right")) playerX += playerSpeed;
            }
            spriteCounter++;
            if (spriteCounter > 12) { spriteNum = (spriteNum == 1) ? 2 : 1; spriteCounter = 0; }
        }
    }

    public void attack() {
        attackCounter++;
        if(attackCounter <= 5) playSE(1);
        if(attackCounter > 5 && attackCounter <= 25) {
            for(int i=0; i<monster.length; i++) {
                if(monster[i] != null && Math.abs(playerX - monster[i].x) < tileSize && Math.abs(playerY - monster[i].y) < tileSize) {
                    if(monster[i].name != null && (monster[i].name.equals("Skeleton") || monster[i].name.equals("Dragon"))) {
                        monster[i].life--;
                        if(monster[i].life <= 0) { monster[i] = null; playSE(3); }
                        attacking = false; attackCounter = 0; break;
                    } else { monster[i] = null; playSE(3); }
                }
            }
        }
        if(attackCounter > 25) { attackCounter = 0; attacking = false; }
    }

    public void checkMonsterCollision() {
        for(Entity m : monster) {
            if(m != null && Math.abs(playerX - m.x) < tileSize/2 && Math.abs(playerY - m.y) < tileSize/2) {
                if(!invincible) { life -= 1; invincible = true; playSE(2); }
            }
        }
    }

    // --- LOCAL DA LÓGICA DA PORTA ---
    public void pickUpObject(int i) {
        if(i != 999 && obj[i] != null) {
            if(obj[i].name.equals("Key")) {
                hasKey++; obj[i] = null; playSE(0);
            }
            else if(obj[i].name.equals("Door")) {
                if(currentLevel == 3) {
                    if(monster[0] == null && gameState != winState) {
                        gameState = winState;
                        stopMusic(); playSE(4); attacking = false;
                        ui.messageOn = false;
                    }
                    else if(monster[0] != null) {
                        collisionOn = true;
                        ui.showMessage("Derrote o Dragão primeiro!");
                    }
                }
                else if(hasKey > 0) {
                    hasKey--; obj[i] = null; playSE(1);
                    if(currentLevel == 1) { level2Unlocked = true; gameState = mapState; }
                    else if(currentLevel == 2) { level3Unlocked = true; gameState = mapState; }
                } else {
                    // Se estiver sem chave, a colisão trava a direção atual
                    collisionOn = true;
                    ui.showMessage("Você precisa de uma chave!");
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (gameState == playState || gameState == pauseState) {
            tileM.draw(g2);
            for(SuperObject o : obj) if(o != null) o.draw(g2, this);
            for(Entity m : monster) if(m != null) m.draw(g2);
            for(int i = 0; i < projectileList.size(); i++) {
                Projectile p = projectileList.get(i);
                if(p != null && p.alive) p.draw(g2);
            }
            if(currentLevel == 3 && monster[0] != null && monster[0].name.equals("Dragon")) {
                int barW = 400; int barX = screenWidth/2 - barW/2;
                g2.setColor(Color.BLACK); g2.fillRect(barX-2, 40, barW+4, 24);
                g2.setColor(Color.RED); g2.fillRect(barX, 42, (int)(barW*((double)monster[0].life/monster[0].maxLife)), 20);
                g2.setColor(Color.WHITE); g2.drawString("DRAGÃO REI", barX, 35);
            }
            BufferedImage img = (spriteNum == 1) ? down1 : down2;
            if(attacking) {
                if(direction.equals("up")) img = atkUp; if(direction.equals("down")) img = atkDown;
                if(direction.equals("left")) img = atkLeft; if(direction.equals("right")) img = atkRight;
            } else {
                if(direction.equals("up")) img = (spriteNum==1?up1:up2);
                if(direction.equals("down")) img = (spriteNum==1?down1:down2);
                if(direction.equals("left")) img = (spriteNum==1?left1:left2);
                if(direction.equals("right")) img = (spriteNum==1?right1:right2);
            }
            if(invincible) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2.drawImage(img, playerX, playerY, tileSize, tileSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            ui.draw(g2);
        } else ui.draw(g2);
        g2.dispose();
    }
}