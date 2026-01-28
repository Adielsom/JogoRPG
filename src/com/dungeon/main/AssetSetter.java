package com.dungeon.main;

import com.dungeon.entity.*;
import com.dungeon.object.*;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) { this.gp = gp; }

    public void setObject(int level) {
        for(int i = 0; i < gp.obj.length; i++) gp.obj[i] = null;
        if(level == 1) {
            gp.obj[0] = new OBJ_Key(); gp.obj[0].worldX = gp.tileSize * 14; gp.obj[0].worldY = gp.tileSize * 10;
            gp.obj[1] = new OBJ_Door(); gp.obj[1].worldX = gp.tileSize * 14; gp.obj[1].worldY = gp.tileSize * 1;
        } else if(level == 2) {
            // Chave e Porta da Fase 2
            gp.obj[0] = new OBJ_Key(); gp.obj[0].worldX = gp.tileSize * 14; gp.obj[0].worldY = gp.tileSize * 9;
            gp.obj[1] = new OBJ_Door(); gp.obj[1].worldX = gp.tileSize * 1; gp.obj[1].worldY = gp.tileSize * 9;
        } else if(level == 3) {
            gp.obj[0] = new OBJ_Door(); gp.obj[0].worldX = gp.tileSize * 7; gp.obj[0].worldY = gp.tileSize * 1;
        }
    }

    public void setMonster(int level) {
        for(int i = 0; i < gp.monster.length; i++) gp.monster[i] = null;

        if(level == 1) {
            // Slimes da Fase 1
            int[][] sPos = { {2,2}, {5,5}, {9,1}, {11,3}, {14,5}, {1,7}, {4,9}, {8,7} };
            for(int i=0; i<sPos.length; i++) {
                gp.monster[i] = new Monster_Slime(gp);
                gp.monster[i].x = gp.tileSize * sPos[i][0]; gp.monster[i].y = gp.tileSize * sPos[i][1];
            }
        } else if(level == 2) {
            // --- EXÉRCITO DE ESQUELETOS (12 unidades) ---
            int[][] skPos = {
                    {3,1}, {8,1}, {13,1}, {4,5}, {10,5}, {1,10},
                    {8,10}, {14,2}, {2,8}, {6,6}, {11,8}, {13,4}
            };
            for(int i=0; i<skPos.length; i++) {
                gp.monster[i] = new Monster_Skeleton(gp);
                gp.monster[i].x = gp.tileSize * skPos[i][0];
                gp.monster[i].y = gp.tileSize * skPos[i][1];
            }

            // --- ARQUEIROS NOS CANTOS (5 unidades) ---
            // Posicionados para cobrir o labirinto com flechas
            int[][] shooterPos = { {1,1}, {14,1}, {1,10}, {14,10}, {7,5} };
            for(int i=0; i<shooterPos.length; i++) {
                int index = 12 + i; // Começa após os esqueletos
                gp.monster[index] = new Monster_Shooter(gp);
                gp.monster[index].x = gp.tileSize * shooterPos[i][0];
                gp.monster[index].y = gp.tileSize * shooterPos[i][1];
            }
        } else if(level == 3) {
            gp.monster[0] = new Monster_Dragon(gp);
            gp.monster[0].x = gp.tileSize * 7; gp.monster[0].y = gp.tileSize * 2;
        }
    }
}