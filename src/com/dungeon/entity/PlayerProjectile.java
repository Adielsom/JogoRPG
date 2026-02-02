package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class PlayerProjectile extends Projectile {
    public PlayerProjectile(GamePanel gp) {
        super(gp);
        speed = 8;
        maxLife = 60;
        life = maxLife;
        getImage();
    }

    public void getImage() {
        try {
            
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/fireball.png"));
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem da BOLA DE FOGO!");
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(down1 != null) {
            g2.drawImage(down1, x, y, 40, 40, null);
        }
    }
}
