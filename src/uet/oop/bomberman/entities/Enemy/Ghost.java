package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;

public class Ghost extends Enemy {
    public Ghost(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
        life = 2;
        throughWall = false;
    }
}
