package com.dungeon.main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        // Efeitos Sonoros (0-3)
        soundURL[0] = getClass().getResource("/sound/coin.wav");
        soundURL[1] = getClass().getResource("/sound/sword.wav");
        soundURL[2] = getClass().getResource("/sound/receive_damage.wav");
        soundURL[3] = getClass().getResource("/sound/monster_die.wav");

        // Músicas de Fundo (BGM) - Começam no índice 4
        soundURL[4] = getClass().getResource("/sound/bgm1.wav");
        soundURL[5] = getClass().getResource("/sound/bgm2.wav");
        soundURL[6] = getClass().getResource("/sound/bgm3.wav");
        soundURL[7] = getClass().getResource("/sound/bgm4.wav");
        soundURL[8] = getClass().getResource("/sound/bgm5.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Erro: Arquivo de som " + i + " não encontrado ou formato inválido.");
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close(); // Importante para liberar a memória e permitir nova música
        }
    }
}