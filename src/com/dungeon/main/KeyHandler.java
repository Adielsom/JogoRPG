package com.dungeon.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, lPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp) { this.gp = gp; }

    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // CONTROLES DE MENU
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) { gp.ui.commandNum--; if (gp.ui.commandNum < 0) gp.ui.commandNum = 3; }
            if (code == KeyEvent.VK_S) { gp.ui.commandNum++; if (gp.ui.commandNum > 3) gp.ui.commandNum = 0; }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) { gp.gameState = gp.playState; gp.setDefaultValues(); }
                else if (gp.ui.commandNum == 1) gp.gameState = gp.helpState;
                else if (gp.ui.commandNum == 2) gp.gameState = gp.musicSelectState;
                else if (gp.ui.commandNum == 3) gp.gameState = gp.mapState;
            }
        }
        else if (gp.gameState == gp.gameOverState) {
            if (code == KeyEvent.VK_ENTER) gp.retry();
            if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.titleState;
        }
        else if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) upPressed = true;
            if (code == KeyEvent.VK_S) downPressed = true;
            if (code == KeyEvent.VK_A) leftPressed = true;
            if (code == KeyEvent.VK_D) rightPressed = true;
            if (code == KeyEvent.VK_ENTER) enterPressed = true;
            if (code == KeyEvent.VK_L) lPressed = true; // MUDADO PARA 'L'
            if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.titleState;
        }
        else if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.titleState;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false;
        if (code == KeyEvent.VK_ENTER) enterPressed = false;
        if (code == KeyEvent.VK_L) lPressed = false;
    }
}