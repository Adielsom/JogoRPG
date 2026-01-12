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
        tile = new Tile[2];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile(); tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
            tile[1] = new Tile(); tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;
        } catch (Exception e) {}
    }

    public void loadMap(int level) {
        int[][] maze;
        // MAPA FASE 1: Complexo e com caminhos sem saída
        maze = new int[][]{
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1}, // Início (1,1)
                {1,0,1,1,1,0,1,0,1,0,1,0,1,1,0,1},
                {1,0,1,0,1,0,0,0,1,0,0,0,1,0,0,1},
                {1,0,1,0,1,1,1,1,1,1,1,1,1,0,1,1},
                {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1},
                {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,0,1,1,1,1,1,1,1,0,1},
                {1,0,1,0,0,1,0,0,0,0,0,0,0,1,0,1}, // Chave no (14,10)
                {1,0,0,0,0,1,1,1,1,1,1,1,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        for(int c=0; c<gp.maxScreenCol; c++) for(int r=0; r<gp.maxScreenRow; r++) mapTileNum[c][r] = maze[r][c];
    }

    public void draw(Graphics2D g2) {
        for(int c=0; c<gp.maxScreenCol; c++)
            for(int r=0; r<gp.maxScreenRow; r++)
                g2.drawImage(tile[mapTileNum[c][r]].image, c*gp.tileSize, r*gp.tileSize, gp.tileSize, gp.tileSize, null);
    }
}