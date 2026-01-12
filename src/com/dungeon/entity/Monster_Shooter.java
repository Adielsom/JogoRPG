package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class Monster_Shooter extends Entity {
    int shotCounter = 0;

    public Monster_Shooter(GamePanel gp) {
        super(gp);
        speed = 0; // Fixo guardando a chave
        direction = "left";
        solidArea = new Rectangle(4, 4, 56, 56);
        getImage();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/shooter1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/shooter2.png"));
        } catch (Exception e) {}
    }

    @Override
    public void update() {
        if(canSeePlayer()) {
            shotCounter++;
            if(shotCounter > 70) { // Cadência de tiro
                Projectile rock = new Projectile_Rock(gp);
                rock.set(this.x, this.y, direction, true, this);
                gp.projectileList.add(rock);
                shotCounter = 0;
            }
        } else {
            shotCounter = 0; // Reseta se você sair da visão
        }
    }

    public boolean canSeePlayer() {
        // Distância máxima de visão (6 blocos)
        int maxDist = gp.tileSize * 6;

        // Verifica se está na mesma linha (Eixo Y próximo)
        boolean sameRow = (Math.abs(y - gp.playerY) < gp.tileSize / 3);
        // Verifica se está na mesma coluna (Eixo X próximo)
        boolean sameCol = (Math.abs(x - gp.playerX) < gp.tileSize / 3);

        if ((sameRow || sameCol) && (Math.abs(x - gp.playerX) < maxDist && Math.abs(y - gp.playerY) < maxDist)) {
            // Vira na direção do jogador antes de atirar
            if(sameRow) direction = (gp.playerX < x) ? "left" : "right";
            if(sameCol) direction = (gp.playerY < y) ? "up" : "down";
            return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(down1, x, y, gp.tileSize, gp.tileSize, null);
    }
}