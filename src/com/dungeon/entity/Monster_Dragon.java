package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Monster_Dragon extends Entity {
    public int life = 10;
    public Monster_Dragon(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 2;
        getImage();
    }
    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/dragon1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/dragon2.png"));
            up1 = down1; up2 = down2; left1 = down1; left2 = down2; right1 = down1; right2 = down2;
        } catch (IOException e) { e.printStackTrace(); }
    }
    @Override
    public void setAction() {
        if (gp.playerX > this.x) direction = "right";
        if (gp.playerX < this.x) direction = "left";
        if (gp.playerY > this.y) direction = "down";
        if (gp.playerY < this.y) direction = "up";
    }
}