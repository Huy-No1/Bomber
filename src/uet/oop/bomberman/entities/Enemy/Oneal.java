package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;

public class Oneal extends Enemy{
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        speed= 1;
        life= 1;
        throughWall= false;
    }
}
