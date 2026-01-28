package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Projectile_Bone extends Projectile {
    public Projectile_Bone(GamePanel gp) {
        super(gp);
        speed = 5;
        maxLife = 80;
        getImage();
    }
    public void getImage() {
        try { down1 = ImageIO.read(getClass().getResourceAsStream("/monster/bone_projectile.png")); } catch (Exception e) {}
    }
    @Override
    public void draw(Graphics2D g2) { g2.drawImage(down1, x, y, 32, 32, null); }
}