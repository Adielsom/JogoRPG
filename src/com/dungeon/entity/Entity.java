package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public GamePanel gp;
    public int x, y, speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "down";
    public int spriteNum = 1, spriteCounter = 0;
    public Rectangle solidArea = new Rectangle(8, 8, 48, 48);
    public boolean collisionOn = false;
    public boolean alive = true;

    // Atributos de Combate
    public int maxLife;
    public int life;
    public String name;

    public Entity(GamePanel gp) { this.gp = gp; }

    public void setAction() {}

    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        if(!collisionOn) {
            switch(direction) {
                case "up": y -= speed; break;
                case "down": y += speed; break;
                case "left": x -= speed; break;
                case "right": x += speed; break;
            }
        }
        spriteCounter++;
        if(spriteCounter > 12) { spriteNum = (spriteNum == 1) ? 2 : 1; spriteCounter = 0; }
    }

    public void draw(Graphics2D g2) {
        BufferedImage img = (spriteNum == 1) ? down1 : down2;
        if(img != null) g2.drawImage(img, x, y, gp.tileSize, gp.tileSize, null);
    }
}