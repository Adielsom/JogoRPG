package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class PlayerProjectile extends Projectile {
    public PlayerProjectile(GamePanel gp) {
        super(gp);
        speed = 10; // Rápido
        maxLife = 60; // 1 segundo de vida
        getImage();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/fireball.png"));
        } catch (Exception e) {
            System.out.println("Erro: fireball.png não encontrado em res/player/");
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(down1 != null) {
            g2.drawImage(down1, x, y, 32, 32, null);
        }
    }
}