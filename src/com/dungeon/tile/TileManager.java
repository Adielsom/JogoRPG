package com.dungeon.tile;

import com.dungeon.main.GamePanel;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[4];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
    }

    public void getTileImage() {
        try {
            // TILES FASE 1
            tile[0] = new Tile(); tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
            tile[1] = new Tile(); tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;

            // TILES FASE 2 e 3 (ESTÉTICA DE PEDRA)
            tile[2] = new Tile(); tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone_floor.png"));
            tile[3] = new Tile(); tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone_wall.png"));
            tile[3].collision = true;
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void loadMap(int level) {
        int floor = (level == 1) ? 0 : 2; // Se level 1 usa terra, se não usa pedra
        int wall = (level == 1) ? 1 : 3;

        // Desenha o mapa usando as variáveis de estilo acima
        int[][] maze;
        if(level == 1) {
            maze = new int[][]{
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
                    {1,0,1,1,1,0,1,0,1,0,1,0,1,1,0,1},
                    {1,0,1,0,1,0,0,0,1,0,0,0,1,0,0,1},
                    {1,0,1,0,1,1,1,1,1,1,1,1,1,0,1,1},
                    {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1},
                    {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,1,0,1,1,1,1,1,1,1,0,1},
                    {1,0,1,0,0,1,0,0,0,0,0,0,0,1,0,1},
                    {1,0,0,0,0,1,1,1,1,1,1,1,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            };
        } else if (level == 2) {
            maze = new int[][]{
                    {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
                    {3,2,3,2,2,2,3,2,2,2,2,2,2,2,2,3},
                    {3,2,3,2,3,2,3,2,3,3,3,3,3,3,2,3},
                    {3,2,2,2,3,2,2,2,2,2,3,2,2,2,2,3},
                    {3,3,3,2,3,3,3,3,3,2,3,2,3,3,3,3},
                    {3,2,2,2,2,2,3,2,2,2,3,2,2,2,2,3},
                    {3,2,3,3,3,2,3,2,3,3,3,3,3,3,2,3},
                    {3,2,3,2,2,2,2,2,2,2,2,2,2,3,2,3},
                    {3,2,3,2,3,3,3,3,3,3,3,3,2,3,2,3},
                    {3,2,3,2,3,2,2,2,2,2,2,3,2,2,2,3},
                    {3,2,2,2,3,2,2,2,2,2,2,3,2,2,2,3},
                    {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}
            };
        } else {
            // Fase 3 (Arena de Pedra)
            maze = new int[12][16];
            for(int r=0; r<12; r++) for(int c=0; c<16; c++) {
                if(r==0 || r==11 || c==0 || c==15) maze[r][c] = 3; else maze[r][c] = 2;
            }
        }

        for(int c=0; c<gp.maxScreenCol; c++) for(int r=0; r<gp.maxScreenRow; r++) mapTileNum[c][r] = maze[r][c];
    }

    public void draw(Graphics2D g2) {
        for(int c=0; c<gp.maxScreenCol; c++) {
            for(int r=0; r<gp.maxScreenRow; r++) {
                g2.drawImage(tile[mapTileNum[c][r]].image, c*gp.tileSize, r*gp.tileSize, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
