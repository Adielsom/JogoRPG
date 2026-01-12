package com.dungeon.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font font_title, font_menu, font_small;

    // Imagens da Interface
    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage menuBG, musicBG, helpBG, scrollImg, mapBG;
    BufferedImage castleIcon, lockIcon, princessImg;

    // Variáveis de Navegação
    public int commandNum = 0;
    public int mapNum = 0;
    public int musicNum = 0;

    // Áreas de Clique para o Mouse (Hitboxes)
    public Rectangle[] menuButtons = new Rectangle[4];
    public Rectangle[] musicButtons = new Rectangle[5];
    public Rectangle[] mapButtons = new Rectangle[3];

    public UI(GamePanel gp) {
        this.gp = gp;

        // DEFINIÇÃO DE FONTES PROPORCIONAIS
        font_title = new Font("Arial", Font.BOLD, 52); // Título grande
        font_menu = new Font("Arial", Font.PLAIN, 28);  // Botões e Músicas (Equilibrado)
        font_small = new Font("Arial", Font.PLAIN, 18); // Rodapés e avisos

        loadImages();
    }

    private void loadImages() {
        heart_full = setup("/ui/heart_full.png");
        heart_half = setup("/ui/heart_half.png");
        heart_blank = setup("/ui/heart_blank.png");
        menuBG = setup("/ui/menu_bg.png");
        musicBG = setup("/ui/music_bg.png");
        helpBG = setup("/ui/help_bg.png");
        scrollImg = setup("/ui/scroll.png");
        mapBG = setup("/ui/map_bg.png");
        castleIcon = setup("/ui/castle.png");
        lockIcon = setup("/ui/lock.png");
        princessImg = setup("/entity/princess.png");
    }

    public BufferedImage setup(String imagePath) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            System.out.println("Aviso: Imagem não encontrada em " + imagePath);
            return null;
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (gp.gameState == gp.titleState) drawTitleScreen();
        else if (gp.gameState == gp.helpState) drawHelpScreen();
        else if (gp.gameState == gp.musicSelectState) drawMusicScreen();
        else if (gp.gameState == gp.mapState) drawMapScreen();
        else if (gp.gameState == gp.playState) drawHUD();
        else if (gp.gameState == gp.pauseState) { drawHUD(); drawPauseScreen(); }
        else if (gp.gameState == gp.gameOverState) drawGameOverScreen();
        else if (gp.gameState == gp.winState) drawWinScreen();
    }

    // --- MENU PRINCIPAL (DESIGN AJUSTADO) ---
    public void drawTitleScreen() {
        if(menuBG != null) g2.drawImage(menuBG, 0, 0, gp.screenWidth, gp.screenHeight, null);

        g2.setFont(font_title);
        String text = "DUNGEON SURVIVOR";
        int x = getCenteredX(text);
        int y = gp.tileSize * 3;

        g2.setColor(Color.BLACK); g2.drawString(text, x+4, y+4); // Sombra
        g2.setColor(Color.WHITE); g2.drawString(text, x, y);

        String[] options = {"JOGAR AGORA", "INSTRUÇÕES", "ESCOLHER MÚSICA", "MAPA"};
        g2.setFont(font_menu);

        for(int i=0; i<options.length; i++) {
            int bw = 320; // Largura aumentada para o texto caber
            int bh = 60;  // Altura proporcional
            int bx = gp.screenWidth/2 - bw/2;
            int by = (gp.tileSize * 5) + (i * 80); // Espaçamento entre botões

            menuButtons[i] = new Rectangle(bx, by, bw, bh);

            if(commandNum == i) {
                g2.setColor(new Color(255, 255, 255, 130));
                g2.fillRoundRect(bx, by, bw, bh, 15, 15);
                g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(bx, by, bw, bh, 15, 15);
            } else {
                g2.setColor(new Color(0, 0, 0, 160));
                g2.fillRoundRect(bx, by, bw, bh, 15, 15);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(bx, by, bw, bh, 15, 15);
            }

            g2.setColor(Color.WHITE);
            // Centralização exata do texto dentro do botão
            int textWidth = (int)g2.getFontMetrics().getStringBounds(options[i], g2).getWidth();
            g2.drawString(options[i], bx + (bw/2) - (textWidth/2), by + 40);
        }
    }

    // --- ESCOLHA DE MÚSICA (TEXTO AUMENTADO) ---
    public void drawMusicScreen() {
        if(musicBG != null) g2.drawImage(musicBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        else { g2.setColor(new Color(20, 20, 45)); g2.fillRect(0,0, gp.screenWidth, gp.screenHeight); }

        String[] tracks = {"Música 1: Aventura", "Música 2: Sombria", "Música 3: Perseguição", "Música 4: Realeza", "Música 5: Épica"};
        g2.setFont(font_menu);

        for(int i=0; i<5; i++) {
            int bw = 400;
            int bh = 60;
            int bx = gp.screenWidth/2 - bw/2;
            int by = 120 + (i * 90);

            musicButtons[i] = new Rectangle(bx, by, bw, bh);

            if(musicNum == i) {
                g2.setColor(new Color(255, 255, 255, 160));
                g2.fillRoundRect(bx, by, bw, bh, 15, 15);
                g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(bx, by, bw, bh, 15, 15);
            } else {
                g2.setColor(new Color(0, 0, 0, 180));
                g2.fillRoundRect(bx, by, bw, bh, 15, 15);
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(bx, by, bw, bh, 15, 15);
            }
            g2.setColor(Color.WHITE);
            int textWidth = (int)g2.getFontMetrics().getStringBounds(tracks[i], g2).getWidth();
            g2.drawString(tracks[i], bx + (bw/2) - (textWidth/2), by + 40);
        }
        g2.setFont(font_small);
        g2.drawString("[ESC] Voltar ao Menu", 30, gp.screenHeight - 30);
    }

    // --- TELA DE MAPA (ESTILO MANTIDO) ---
    public void drawMapScreen() {
        if(mapBG != null) g2.drawImage(mapBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        String title = "SELECIONE SEU DESTINO";
        g2.setFont(font_title.deriveFont(32f));
        g2.setColor(Color.YELLOW); g2.drawString(title, getCenteredX(title), 60);

        int size = 180;
        int[][] pos = { {100, 300}, {400, 140}, {720, 400} };

        for(int i = 0; i < 3; i++) {
            int x = pos[i][0]; int y = pos[i][1];
            mapButtons[i] = new Rectangle(x, y, size, size);
            if(mapNum == i) {
                g2.setColor(new Color(255, 255, 255, 120)); g2.fillOval(x-10, y+size-40, size+20, 60);
                g2.setColor(Color.WHITE); g2.setStroke(new BasicStroke(4)); g2.drawRoundRect(x-5, y-5, size+10, size+10, 25, 25);
            }
            if(castleIcon != null) g2.drawImage(castleIcon, x, y, size, size, null);
            if((i == 1 && !gp.level2Unlocked) || (i == 2 && !gp.level3Unlocked)) {
                g2.setColor(new Color(0, 0, 0, 160)); g2.fillRoundRect(x, y, size, size, 20, 20);
                if(lockIcon != null) g2.drawImage(lockIcon, x + 50, y + 50, 80, 80, null);
            }
            g2.setFont(font_menu.deriveFont(Font.BOLD, 22f)); g2.setColor(Color.WHITE);
            String name = "FASE " + (i+1);
            g2.drawString(name, x + (size/2) - 40, y + size + 40);
        }
    }

    // --- AJUDA, HUD E GAME OVER ---
    public void drawHelpScreen() {
        if(helpBG != null) g2.drawImage(helpBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        int sw = gp.screenWidth - 120, sh = gp.screenHeight - 60;
        int x = gp.screenWidth/2 - sw/2, y = gp.screenHeight/2 - sh/2;
        if (scrollImg != null) g2.drawImage(scrollImg, x, y, sw, sh, null);
        g2.setColor(new Color(60, 30, 10)); g2.setFont(font_title.deriveFont(38f));
        g2.drawString("GUIA DO CAVALEIRO", getCenteredX("GUIA DO CAVALEIRO"), y + 100);
        g2.setFont(font_menu.deriveFont(22f));
        g2.drawString("● MOVIMENTAÇÃO: W, A, S, D ou MOUSE", x + 130, y + 180);
        g2.drawString("● COMBATE: [ ENTER ] para atacar / [ ESPAÇO ] atira", x + 130, y + 270);
        g2.drawString("● OBJETIVO: Chave -> Porta -> Princesa", x + 130, y + 360);
    }

    public void drawHUD() {
        int x = 20, y = 20, i = 0;
        while(i < gp.maxLife/2) { if(heart_blank != null) g2.drawImage(heart_blank, x, y, 40, 40, null); i++; x += 45; }
        x = 20; i = 0;
        while(i < gp.life) {
            if(heart_half != null) g2.drawImage(heart_half, x, y, 40, 40, null); i++;
            if(i < gp.life) if(heart_full != null) g2.drawImage(heart_full, x, y, 40, 40, null); i++;
            x += 45;
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,220)); g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
        g2.setFont(font_title); g2.setColor(Color.RED);
        g2.drawString("GAME OVER", getCenteredX("GAME OVER"), 200);
        g2.setFont(font_menu); g2.setColor(Color.WHITE);
        g2.drawString("ENTER para Tentar Novamente", getCenteredX("ENTER para Tentar Novamente"), 400);
        g2.drawString("ESC para Menu", getCenteredX("ESC para Menu"), 480);
    }

    public void drawPauseScreen() {
        g2.setColor(new Color(0,0,0,150)); g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
        g2.setFont(font_title); g2.setColor(Color.WHITE);
        g2.drawString("PAUSADO", getCenteredX("PAUSADO"), gp.screenHeight/2);
    }

    public void drawWinScreen() {
        g2.setColor(new Color(50, 150, 50)); g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
        g2.setFont(font_menu); g2.setColor(Color.WHITE);
        g2.drawString("VOCÊ SALVOU A PRINCESA!", getCenteredX("VOCÊ SALVOU A PRINCESA!"), 200);
    }

    public int getCenteredX(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}