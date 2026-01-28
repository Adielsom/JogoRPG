package com.dungeon.entity;

import com.dungeon.main.GamePanel;
import java.awt.Graphics2D;

public abstract class Projectile extends Entity {
    public Entity user;
    public boolean alive;
    public int life;
    public int maxLife;

    public Projectile(GamePanel gp) { super(gp); }

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
        collisionOn = false;
        gp.cChecker.checkTile(this);
        if(collisionOn) { alive = false; }

        // Lógica de movimento completa (8 direções)
        switch(direction) {
            case "up": y -= speed; break;
            case "down": y += speed; break;
            case "left": x -= speed; break;
            case "right": x += speed; break;
            case "downleft": x -= (speed-1); y += speed; break;
            case "downright": x += (speed-1); y += speed; break;
            case "upleft": x -= (speed-1); y -= speed; break;
            case "upright": x += (speed-1); y -= speed; break;
        }

        life--;
        if(life <= 0) alive = false;

        if(user != null) { // Tiro de Monstro atingindo Player
            if(Math.abs(x - gp.playerX) < 32 && Math.abs(y - gp.playerY) < 32) {
                if(!gp.invincible) { gp.life--; gp.invincible = true; gp.playSE(2); }
                alive = false;
            }
        } else { // Tiro do Player atingindo Monstro
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999) {
                Entity target = gp.monster[monsterIndex];
                if(target.name != null && (target.name.equals("Dragon") || target.name.equals("Skeleton"))) {
                    target.life--;
                    if(target.life <= 0) { gp.monster[monsterIndex] = null; gp.playSE(3); }
                } else {
                    gp.monster[monsterIndex] = null; gp.playSE(3);
                }
                alive = false;
            }
        }
    }
    public abstract void draw(Graphics2D g2);
}