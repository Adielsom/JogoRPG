package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Monster_Dragon extends Entity {
    int shotCounter = 0;

    public Monster_Dragon(GamePanel gp) {
        super(gp);
        this.gp = gp; 
        name = "Dragon";
        speed = 2;
        maxLife = 60;
        life = maxLife;
        direction = "down";
        solidArea = new Rectangle(20, 20, 80, 80);
        getImage();
    }

    public void getImage() {
        
        down1 = setup("/monster/dragon_down1.png");
        down2 = setup("/monster/dragon_down2.png");
        up1 = setup("/monster/dragon_up1.png");
        left1 = setup("/monster/dragon_left1.png");
        right1 = setup("/monster/dragon_right1.png");

        
        up2 = setup("/monster/dragon_up2.png");
        if(up2 == null) up2 = up1;

        left2 = setup("/monster/dragon_left2.png");
        if(left2 == null) left2 = left1;

        right2 = setup("/monster/dragon_right2.png");
        if(right2 == null) right2 = right1;
    }

    
    public BufferedImage setup(String imagePath) {
        try {
            java.io.InputStream is = getClass().getResourceAsStream(imagePath);
            if (is == null) return null;
            return ImageIO.read(is);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update() {
        
        int diffX = gp.playerX - x;
        int diffY = gp.playerY - y;

        if (Math.abs(diffX) > Math.abs(diffY)) {
            direction = (diffX > 0) ? "right" : "left";
            if (diffX > 30) x += speed; else if (diffX < -30) x -= speed;
        } else {
            direction = (diffY > 0) ? "down" : "up";
            if (diffY > 30) y += speed; else if (diffY < -30) y -= speed;
        }

        
        if(x < gp.tileSize) x = gp.tileSize;
        if(x > gp.screenWidth - (gp.tileSize * 3)) x = gp.screenWidth - (gp.tileSize * 3);
        if(y < gp.tileSize) y = gp.tileSize;
        if(y > gp.screenHeight - (gp.tileSize * 3)) y = gp.screenHeight - (gp.tileSize * 3);

        spriteCounter++;
        if(spriteCounter > 15) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        
        shotCounter++;
        if(shotCounter > 45) {
            fireTripleShot(direction);
            shotCounter = 0;
        }
    }

    private void fireTripleShot(String baseDir) {
        switch(baseDir) {
            case "down": fireShot("down"); fireShot("downleft"); fireShot("downright"); break;
            case "up": fireShot("up"); fireShot("upleft"); fireShot("upright"); break;
            case "left": fireShot("left"); fireShot("downleft"); fireShot("upleft"); break;
            case "right": fireShot("right"); fireShot("downright"); fireShot("upright"); break;
        }
    }

    private void fireShot(String dir) {
        Projectile fire = new Projectile_FireBlast(gp);
        fire.set(this.x + 48, this.y + 48, dir, true, this);
        gp.projectileList.add(fire);
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        
        switch(direction) {
            case "up": img = (spriteNum == 1) ? up1 : up2; break;
            case "down": img = (spriteNum == 1) ? down1 : down2; break;
            case "left": img = (spriteNum == 1) ? left1 : left2; break;
            case "right": img = (spriteNum == 1) ? right1 : right2; break;
        }

        if(img != null) {
            g2.drawImage(img, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        }
    }
}
