package uet.oop.bomberman.entities.StillObject;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.Item;


public class Brick extends Entity {
    Item item = null;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    public Brick(int x, int y, Image img, Item item_) {
        super(x, y, img);
        item = item_;
    }

    @Override
    public void update() {

    }
}
