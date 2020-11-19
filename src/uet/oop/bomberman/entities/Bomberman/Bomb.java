package uet.oop.bomberman.entities.Bomberman;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NeutralObject.Brick;
import uet.oop.bomberman.entities.NeutralObject.Flame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Bomb extends Entity {

    private Entity rightFlame;
    private boolean done = false;
    private boolean exploded = false;
    private int explosionCountDown = 50;
    private int tickingCountDown = 90;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public Entity getRightFlame() {
        return rightFlame;
    }

    public void setRightFlame(Flame rightFlame) {
        this.rightFlame = rightFlame;
    }

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        rightFlame = new Flame(x+1, y, null);
    }

    public void tickingImg() {
        if (tickingCountDown == 0) {
            setExploded(true);
        } else {
            this.img = Sprite
                    .bombTickingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, tickingCountDown)
                    .getFxImage();
            tickingCountDown--;
        }
    }

    public void explodingImg() {
        if (explosionCountDown == 0) {
            setDone(true);
            this.img = null;
        } else {
            this.img = Sprite
                    .bombExplodeSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, explosionCountDown)
                    .getFxImage();
            explosionCountDown--;
        }
    }

    @Override
    public void update() {
        if (!isExploded()) {
            tickingImg();
        } else {
            explodingImg();
        }
    }

    public void flameCollision(List<Entity> entities, List<Entity> bricks) {
        for (Entity x : entities) {
            //System.out.println(x.rtg);
            if (x instanceof Brick) {
                if (rightFlame.rtg.intersects(x.rtg.getLayoutBounds())) {
                    ((Brick) x).setDamaged(true);
                    bricks.add(x);
                    // return true;
                }
            }
        }
        // return false;
    }
}
