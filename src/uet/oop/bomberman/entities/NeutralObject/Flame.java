package uet.oop.bomberman.entities.NeutralObject;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {

    private int explosionCountDown = 140;
    private boolean done = false;

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public Flame(double x, double y, Image img) {
        super(x, y, img);
        rtg = new Rectangle(x, y, 0.99, 0.99);
    }

    public void horizontalRightLastExplodingImg() {
        if (explosionCountDown == 0 || explosionCountDown >= 50) {
            this.img = null;
            if (explosionCountDown >= 50) {
                explosionCountDown--;
            } else {
                setDone(true);
            }
        } else {
            this.img = Sprite
                    .bombExplodeSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1,
                            Sprite.explosion_horizontal_right_last2, explosionCountDown)
                    .getFxImage();
            explosionCountDown--;
        }
    }

    @Override
    public void update() {
        horizontalRightLastExplodingImg();
    }
}
