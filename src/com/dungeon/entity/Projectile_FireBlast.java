package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Projectile_FireBlast extends Projectile {
    public Projectile_FireBlast(GamePanel gp) {
        super(gp);
        speed = 7;
        maxLife = 120;
        getImage();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/fire_blast.png"));
        } catch (Exception e) {}
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(down1, x, y, 48, 48, null);
    }
}