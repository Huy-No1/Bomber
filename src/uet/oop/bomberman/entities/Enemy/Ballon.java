package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;

public class Ballon extends Enemy {
    public Ballon(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
        life = 1;
        throughWall= false;
    }
}
