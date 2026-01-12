package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import java.awt.Graphics2D;

public abstract class Projectile extends Entity {
    public Entity user;
    public boolean alive;
    public int life;
    public int maxLife;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int x, int y, String dir, boolean alive, Entity user) {
        this.x = x;
        this.y = y;
        this.direction = dir;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    @Override
    public void update() {
        // Colisão com parede mata o tiro
        collisionOn = false;
        gp.cChecker.checkTile(this);
        if(collisionOn) {
            alive = false;
        }

        switch(direction) {
            case "up": y -= speed; break;
            case "down": y += speed; break;
            case "left": x -= speed; break;
            case "right": x += speed; break;
        }

        life--;
        if(life <= 0) alive = false;

        // SE O USUÁRIO FOR O MONSTRO (Atira no Player)
        if(user instanceof Monster_Shooter || user instanceof Monster_Slime) {
            if(Math.abs(x - gp.playerX) < 32 && Math.abs(y - gp.playerY) < 32) {
                if(!gp.invincible) { gp.life--; gp.invincible = true; gp.playSE(2); }
                alive = false;
            }
        }
        // SE O USUÁRIO FOR O PLAYER (User é null no GamePanel)
        else {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999) {
                gp.monster[monsterIndex] = null; // Monstro morre
                gp.playSE(3);
                alive = false;
            }
        }
    }

    public abstract void draw(Graphics2D g2);
}