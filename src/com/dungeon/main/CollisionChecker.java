package com.dungeon.main;

import com.dungeon.entity.Entity;
import java.awt.Rectangle;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp) { this.gp = gp; }

    public void checkTile(Object target) {
        int x, y, speed; String dir; Rectangle solidArea;

        if(target instanceof GamePanel) {
            GamePanel p = (GamePanel)target;
            x = p.playerX; y = p.playerY; speed = p.playerSpeed; dir = p.direction; solidArea = p.playerSolidArea;
        } else {
            Entity e = (Entity)target;
            x = e.x; y = e.y; speed = e.speed; dir = e.direction; solidArea = e.solidArea;
        }

        int leftX = x + solidArea.x;
        int rightX = x + solidArea.x + solidArea.width;
        int topY = y + solidArea.y;
        int bottomY = y + solidArea.y + solidArea.height;

        int leftCol = leftX / gp.tileSize;
        int rightCol = rightX / gp.tileSize;
        int topRow = topY / gp.tileSize;
        int bottomRow = bottomY / gp.tileSize;

        int t1, t2;
        try {
            switch (dir) {
                case "up":
                    topRow = (topY - speed) / gp.tileSize;
                    t1 = gp.tileM.mapTileNum[leftCol][topRow];
                    t2 = gp.tileM.mapTileNum[rightCol][topRow];
                    if (gp.tileM.tile[t1].collision || gp.tileM.tile[t2].collision) setCol(target);
                    break;
                case "down":
                    bottomRow = (bottomY + speed) / gp.tileSize;
                    t1 = gp.tileM.mapTileNum[leftCol][bottomRow];
                    t2 = gp.tileM.mapTileNum[rightCol][bottomRow];
                    if (gp.tileM.tile[t1].collision || gp.tileM.tile[t2].collision) setCol(target);
                    break;
                case "left":
                    leftCol = (leftX - speed) / gp.tileSize;
                    t1 = gp.tileM.mapTileNum[leftCol][topRow];
                    t2 = gp.tileM.mapTileNum[leftCol][bottomRow];
                    if (gp.tileM.tile[t1].collision || gp.tileM.tile[t2].collision) setCol(target);
                    break;
                case "right":
                    rightCol = (rightX + speed) / gp.tileSize;
                    t1 = gp.tileM.mapTileNum[rightCol][topRow];
                    t2 = gp.tileM.mapTileNum[rightCol][bottomRow];
                    if (gp.tileM.tile[t1].collision || gp.tileM.tile[t2].collision) setCol(target);
                    break;
            }
        } catch (Exception e) {}
    }

    private void setCol(Object t) {
        if(t instanceof GamePanel) ((GamePanel)t).collisionOn = true;
        else ((Entity)t).collisionOn = true;
    }

    public int checkObject(GamePanel gp, boolean player) {
        int index = 999;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                Rectangle pArea = new Rectangle(gp.playerX + gp.playerSolidArea.x, gp.playerY + gp.playerSolidArea.y, gp.playerSolidArea.width, gp.playerSolidArea.height);
                Rectangle oArea = new Rectangle(gp.obj[i].worldX, gp.obj[i].worldY, gp.tileSize, gp.tileSize);
                if (pArea.intersects(oArea)) {
                    if (gp.obj[i].collision) gp.collisionOn = true;
                    if (player) index = i;
                }
            }
        }
        return index;
    }

    // NOVO: Detecta se um projétil atingiu um monstro
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        for(int i = 0; i < target.length; i++) {
            if(target[i] != null) {
                Rectangle entArea = new Rectangle(entity.x + entity.solidArea.x, entity.y + entity.solidArea.y, entity.solidArea.width, entity.solidArea.height);
                Rectangle tarArea = new Rectangle(target[i].x + target[i].solidArea.x, target[i].y + target[i].solidArea.y, target[i].solidArea.width, target[i].solidArea.height);

                if(entArea.intersects(tarArea)) {
                    index = i;
                }
            }
        }
        return index;
    }
}