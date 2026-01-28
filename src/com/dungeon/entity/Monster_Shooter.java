package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class Monster_Shooter extends Entity {
    int shotCounter = 0;

    public Monster_Shooter(GamePanel gp) {
        super(gp);
        name = "Shooter";
        speed = 0; // TORRE FIXA
        direction = "down";
        solidArea = new Rectangle(10, 10, 44, 44);
        getImage();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/shooter_down.png"));
            down2 = down1;
        } catch (Exception e) { System.out.println("Erro imagem Shooter"); }
    }

    @Override
    public void update() {
        // ATIRA SEM PARAR
        shotCounter++;
        if(shotCounter > 60) {
            // Decide a direção do tiro baseado em onde o player está
            String fireDir = "down";
            if(Math.abs(gp.playerX - x) > Math.abs(gp.playerY - y)) {
                fireDir = (gp.playerX > x) ? "right" : "left";
            } else {
                fireDir = (gp.playerY > y) ? "down" : "up";
            }

            Projectile arrow = new Projectile_Arrow(gp);
            arrow.set(this.x, this.y, fireDir, true, this);
            gp.projectileList.add(arrow);
            shotCounter = 0;
        }
    }
}