package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class Monster_Skeleton extends Entity {
    int shotCounter = 0;

    public Monster_Skeleton(GamePanel gp) {
        super(gp);
        name = "Skeleton";
        speed = 1;
        maxLife = 3;
        life = maxLife;
        direction = "down";
        solidArea = new Rectangle(12, 12, 40, 40);
        getImage();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/skeleton_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/skeleton_down2.png"));
        } catch (Exception e) { System.out.println("Erro imagem Skeleton"); }
    }

    @Override
    public void update() {
        // IA: MOVIMENTO PERSISTENTE
        int diffX = gp.playerX - x;
        int diffY = gp.playerY - y;

        // Tenta mover-se no eixo onde a distância é maior
        if (Math.abs(diffX) > Math.abs(diffY)) {
            direction = (diffX > 0) ? "right" : "left";
        } else {
            direction = (diffY > 0) ? "down" : "up";
        }

        collisionOn = false;
        gp.cChecker.checkTile(this);

        // SE BATER NA PAREDE NO EIXO PRINCIPAL, TENTA O EIXO SECUNDÁRIO
        if(collisionOn) {
            if(direction.equals("up") || direction.equals("down")) {
                direction = (diffX > 0) ? "right" : "left";
            } else {
                direction = (diffY > 0) ? "down" : "up";
            }
            collisionOn = false;
            gp.cChecker.checkTile(this);
        }

        if (!collisionOn) {
            switch(direction) {
                case "up": y -= speed; break;
                case "down": y += speed; break;
                case "left": x -= speed; break;
                case "right": x += speed; break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 15) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        // ATAQUE DE OSSO (ARREMESSO)
        shotCounter++;
        if(shotCounter > 70) {
            Projectile bone = new Projectile_Bone(gp);
            bone.set(this.x, this.y, direction, true, this);
            gp.projectileList.add(bone);
            shotCounter = 0;
        }
    }
}