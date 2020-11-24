package uet.oop.bomberman.entities.NeutralObject;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.graphics.Sprite;


public class Brick extends Entity {
    private Item item = null;
    private boolean damaged = false;
    private boolean done = false;
    private int deathCountDown = 90;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        rtg = new Rectangle(x, y, 0.99, 0.99);
    }

    public Brick(int x, int y, Image img, Item item_) {
        super(x, y, img);
        item = item_;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void collapsingImg() {
        if (deathCountDown == 0) {
            this.img = null;
            setDone(true);
        } else {
            this.img = Sprite
                    .bombExplodeSprite(Sprite.brick_exploded2, Sprite.brick_exploded1, Sprite.brick_exploded, deathCountDown)
                    .getFxImage();
            deathCountDown--;
        }
    }

    @Override
    public void update() {
        if (this.isDamaged()) {
            // System.out.println("oke");
            collapsingImg();
        }
    }
}
