package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Ballon extends Enemy {
    public Ballon(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
        life = 1;
        throughWall= false;
        rtg = new Rectangle(x, y, 1, 1);
    }
}
