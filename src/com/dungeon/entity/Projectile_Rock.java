package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Projectile_Rock extends Projectile {
    public Projectile_Rock(GamePanel gp) {
        super(gp);
        speed = 5;
        maxLife = 100;
        getImage();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/rock.png"));
        } catch (Exception e) {}
    }

    @Override
    public void draw(Graphics2D g2) {
        if(down1 != null) g2.drawImage(down1, x, y, 24, 24, null);
    }
}