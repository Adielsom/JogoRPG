package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Projectile_Arrow extends Projectile {
    public Projectile_Arrow(GamePanel gp) {
        super(gp);
        speed = 6;
        maxLife = 100;
        life = maxLife;
        getImage();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/arrow.png"));
        } catch (Exception e) {
            
            System.out.println("Erro ao carregar imagem da flecha! Verifique res/monster/arrow.png");
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(down1 != null) {
            g2.drawImage(down1, x, y, 32, 32, null);
        }
    }
}
