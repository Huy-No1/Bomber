package uet.oop.bomberman.entities.Enemy;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.CurrentImage;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Entity {

    protected boolean damaged = false;
    protected double speed;
    protected int life;
    protected boolean throughWall;
    protected int direction = 1;
    protected CurrentImage currentImage = new CurrentImage();
    protected int deathCountDown = 30;

    public Enemy(int x, int y, Image img) {
        super(x, y, img);
        rtg = new Rectangle(x, y, 0.95, 0.95);
        damaged = false;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public boolean isDamaged() {
        return damaged;
    }

    @Override
    public boolean moveRight() {
        if (x - Math.floor(x) == 0) {
            if (!throughWall) {
                if (BombermanGame.map[(int) y].charAt((int) x + 1) != '*' &&
                        BombermanGame.map[(int) y].charAt((int) x + 1) != '#' &&
                        BombermanGame.map[(int) y].charAt((int) x + 1) != 'w' &&
                        BombermanGame.map[(int) y].charAt((int) x + 1) != 't') {
                    x = (double) Math.round((x + speed) * 1000) / 1000;
                    setLocation(x, y);
                    return true;
                }
                return false;
            } else {
                if (BombermanGame.map[(int) y].charAt((int) x + 1) != '#') {
                    x = (double) Math.round((x + speed) * 1000) / 1000;
                    setLocation(x, y);
                    return true;
                }
                return false;
            }
        } else {
            x = (double) Math.round((x + speed) * 1000) / 1000;
            setLocation(x, y);
            return true;
        }

    }

    public boolean moveLeft() {
        if (x - Math.floor(x) == 0) {
            if (!throughWall) {
                if (BombermanGame.map[(int) y].charAt((int) x - 1) != '*' &&
                        BombermanGame.map[(int) y].charAt((int) x - 1) != '#' &&
                        BombermanGame.map[(int) y].charAt((int) x - 1) != 'w' &&
                        BombermanGame.map[(int) y].charAt((int) x - 1) != 't') {
                    x = (double) Math.round((x - speed) * 1000) / 1000;
                    setLocation(x, y);
                    return true;
                }
                return false;
            } else {
                if (BombermanGame.map[(int) y].charAt((int) x - 1) != '#') {
                    x = (double) Math.round((x - speed) * 1000) / 1000;
                    setLocation(x, y);
                    return true;
                }
                return false;
            }
        } else {
            x = (double) Math.round((x - speed) * 1000) / 1000;
            setLocation(x, y);
            return true;
        }

    }

    public boolean moveUp() {
        if (y - Math.floor(y) == 0) {
            if (!throughWall) {
                if (BombermanGame.map[(int) y - 1].charAt((int) x) != '*' &&
                        BombermanGame.map[(int) y - 1].charAt((int) x) != '#' &&
                        BombermanGame.map[(int) y - 1].charAt((int) x) != 'w' &&
                        BombermanGame.map[(int) y - 1].charAt((int) x) != 't') {
                    y = (double) Math.round((y - speed) * 1000) / 1000;
                    setLocation(x, y);
                    return true;
                }
                return false;
            } else {
                if (BombermanGame.map[(int) y - 1].charAt((int) x) != '#') {
                    y = (double) Math.round((y - speed) * 1000) / 1000;
                    setLocation(x, y);
                    return true;
                }
                return false;
            }
        } else {
            y = (double) Math.round((y - speed) * 1000) / 1000;
            setLocation(x, y);
            return true;
        }
    }


    public boolean moveDown() {
        if (y - Math.floor(y) == 0) {
            if (!throughWall) {
                if (BombermanGame.map[(int) y + 1].charAt((int) x) != '*' &&
                        BombermanGame.map[(int) y + 1].charAt((int) x) != '#' &&
                        BombermanGame.map[(int) y + 1].charAt((int) x) != 'w' &&
                        BombermanGame.map[(int) y + 1].charAt((int) x) != 't') {
                    y = (double) Math.round((y + speed) * 1000) / 1000;
                    setLocation(x, y);
                    return true;
                }
                return false;
            } else {
                if (BombermanGame.map[(int) y + 1].charAt((int) x) != '#') {
                    y = (double) Math.round((y + speed) * 1000) / 1000;
                    setLocation(x, y);
                    return true;
                }
                return false;
            }
        } else {
            y = (double) Math.round((y + speed) * 1000) / 1000;
            setLocation(x, y);
            return true;
        }
    }

    public void dieImg() {
        if (deathCountDown == 0) {
            this.img = null;
        } else {
            this.img = Sprite
                    .bombExplodeSprite(Sprite.balloom_dead, Sprite.balloom_dead, Sprite.balloom_dead, deathCountDown)
                    .getFxImage();
            deathCountDown--;
        }
    }

    @Override
    public void update() {

        if (isDamaged() && life == 2) {
            setDamaged(false);
            life = 1;
        } else if (isDamaged() && life == 1) {
            setDamaged(false);
            life = 0;
        }

        if (life == 0) {
            dieImg();
        } else {

            if (direction == 1) {
                if (!moveLeft()) direction = (int) (Math.random() * 4 + 1);

            }
            if (direction == 2) {
                if (!moveRight()) direction = (int) (Math.random() * 4 + 1);
            }

            if (direction == 3) {
                if (!moveUp()) direction = (int) (Math.random() * 4 + 1);
            }

            if (direction == 4) {
                if (!moveDown()) direction = (int) (Math.random() * 4 + 1);
            }
        }
    }
}