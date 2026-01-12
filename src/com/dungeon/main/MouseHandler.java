package com.dungeon.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
    GamePanel gp;
    public MouseHandler(GamePanel gp) { this.gp = gp; }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        if (gp.gameState == gp.titleState) {
            for (int i = 0; i < 4; i++) {
                if (gp.ui.menuButtons[i].contains(x, y)) {
                    gp.ui.commandNum = i;
                    if (i == 0) { gp.gameState = gp.playState; gp.setDefaultValues(); }
                    else if (i == 1) gp.gameState = gp.helpState;
                    else if (i == 2) gp.gameState = gp.musicSelectState;
                    else if (i == 3) gp.gameState = gp.mapState;
                }
            }
        } else if (gp.gameState == gp.mapState) {
            for (int i = 0; i < 3; i++) {
                if (gp.ui.mapButtons[i].contains(x, y)) {
                    gp.ui.mapNum = i;
                    if(i == 0) { gp.currentLevel = 1; gp.gameState = gp.playState; gp.setDefaultValues(); }
                    else if(i == 1 && gp.level2Unlocked) { gp.currentLevel = 2; gp.gameState = gp.playState; gp.setDefaultValues(); }
                    else if(i == 2 && gp.level3Unlocked) { gp.currentLevel = 3; gp.gameState = gp.playState; gp.setDefaultValues(); }
                }
            }
        } else if (gp.gameState == gp.musicSelectState) {
            for (int i = 0; i < 5; i++) {
                if (gp.ui.musicButtons[i].contains(x, y)) {
                    gp.ui.musicNum = i; gp.changeBGM(i + 4);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        if (gp.gameState == gp.titleState) {
            for (int i = 0; i < 4; i++) if (gp.ui.menuButtons[i].contains(x, y)) gp.ui.commandNum = i;
        } else if (gp.gameState == gp.mapState) {
            for (int i = 0; i < 3; i++) if (gp.ui.mapButtons[i].contains(x, y)) gp.ui.mapNum = i;
        } else if (gp.gameState == gp.musicSelectState) {
            for (int i = 0; i < 5; i++) if (gp.ui.musicButtons[i].contains(x, y)) gp.ui.musicNum = i;
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
}