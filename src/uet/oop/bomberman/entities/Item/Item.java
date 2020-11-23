package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Entity;

import java.util.List;

public abstract class Item extends Entity {
    private boolean isCollision = false;

    public Item(int x, int y, Image img) {
        super(x, y, img);
        rtg= new Rectangle(x, y, 0.98, 0.98);
    }


    //Collision Bomber

    public boolean collision(Entity entities) {
        if (this.rtg.intersects(entities.rtg.getLayoutBounds())) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {

    }
}
