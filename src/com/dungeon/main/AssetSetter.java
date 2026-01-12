package com.dungeon.main;

import com.dungeon.entity.Monster_Slime;
import com.dungeon.entity.Monster_Shooter;
import com.dungeon.object.OBJ_Key;
import com.dungeon.object.OBJ_Door;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) { this.gp = gp; }

    public void setObject(int level) {
        if(level == 1) {
            gp.obj[0] = new OBJ_Key();
            gp.obj[0].worldX = gp.tileSize * 14; gp.obj[0].worldY = gp.tileSize * 10;

            gp.obj[1] = new OBJ_Door();
            gp.obj[1].worldX = gp.tileSize * 14; gp.obj[1].worldY = gp.tileSize * 1;
        }
    }

    public void setMonster(int level) {
        if(level == 1) {
            // 10 Slimes caçadores espalhados
            int[][] sPos = { {2,2}, {5,1}, {5,5}, {9,1}, {11,3}, {14,5}, {1,7}, {4,9}, {8,7}, {10,10} };
            for(int i=0; i<sPos.length; i++) {
                gp.monster[i] = new Monster_Slime(gp);
                gp.monster[i].x = gp.tileSize * sPos[i][0];
                gp.monster[i].y = gp.tileSize * sPos[i][1];
            }
            // O guarda da chave
            gp.monster[10] = new Monster_Shooter(gp);
            gp.monster[10].x = gp.tileSize * 12;
            gp.monster[10].y = gp.tileSize * 10;
        }
    }
}