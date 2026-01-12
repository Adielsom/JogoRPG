package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class Monster_Slime extends Entity {
    public Monster_Slime(GamePanel gp) {
        super(gp);
        speed = 1;
        direction = "down";
        solidArea = new Rectangle(8, 8, 48, 48);
        getImage();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/slime1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/slime2.png"));
            up1 = down1; up2 = down2; left1 = down1; left2 = down2; right1 = down1; right2 = down2;
        } catch (Exception e) {}
    }

    @Override
    public void setAction() {
        // IA que segue o jogador contornando obstáculos
        int diffX = Math.abs(gp.playerX - x);
        int diffY = Math.abs(gp.playerY - y);

        if (diffX > diffY) {
            // Tenta se mover no eixo X primeiro
            direction = (gp.playerX > x) ? "right" : "left";
            checkCollision();
            if(collisionOn) {
                // Se bloqueado no X, tenta o eixo Y para desviar
                direction = (gp.playerY > y) ? "down" : "up";
            }
        } else {
            // Tenta se mover no eixo Y primeiro
            direction = (gp.playerY > y) ? "down" : "up";
            checkCollision();
            if(collisionOn) {
                // Se bloqueado no Y, tenta o eixo X para desviar
                direction = (gp.playerX > x) ? "right" : "left";
            }
        }
    }

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
    }
}