package com.dungeon.object;
import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject {
    public OBJ_Door() {
        name = "Door";
        collision = true; // Não deixa passar sem abrir
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}