package uet.oop.bomberman.entities.NeutralObject;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {

    private int explosionCountDown = 140;
    private String pos;
    private boolean done = false;

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getPos() {
        return pos;
    }

    public Flame(double x, double y, Image img) {
        super(x, y, img);
        rtg = new Rectangle(x, y, 0.99, 0.99);
    }

    public Flame(double x, double y, Image img, String pos) {
        super(x, y, img);
        rtg = new Rectangle(x, y, 0.99, 0.99);
        this.pos = pos;
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

    public void explodingImg() {
        if (explosionCountDown == 0 || explosionCountDown >= 50) {
            this.img = null;
            if (explosionCountDown >= 50) {
                explosionCountDown--;
            } else {
                setDone(true);
            }
        } else {
            // System.out.println(pos);
            switch(pos) {
                case "left": {
                    // System.out.println(pos);
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                                    Sprite.explosion_horizontal2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "down": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                                    Sprite.explosion_vertical2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "right": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                                    Sprite.explosion_horizontal2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "top": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                                    Sprite.explosion_vertical2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "left_most": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1,
                                    Sprite.explosion_horizontal_left_last2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "down_most": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1,
                                    Sprite.explosion_vertical_down_last2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "right_most": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1,
                                    Sprite.explosion_horizontal_right_last2, explosionCountDown)
                            .getFxImage();
                    break;
                }
                case "top_most": {
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1,
                                    Sprite.explosion_vertical_top_last2, explosionCountDown)
                            .getFxImage();
                    break;
                }
            }
            explosionCountDown--;
        }
    }

    @Override
    public void update() {
        // horizontalRightLastExplodingImg();
        explodingImg();
    }
}
