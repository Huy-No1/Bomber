package uet.oop.bomberman.entities.Bomberman;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.SoundEffect;
import uet.oop.bomberman.entities.CurrentImage;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.KeyInput;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {

    // ability
    public List<Bomb> bombList = new ArrayList<Bomb>();
    private int bombRange = 5;
    public int bombLimit = 2;
    public double speed = 0.08;
    private int deathCountDown = 15;
    private int live = 3;
    private boolean playsound = false;

    /*
    For rendering
     */
    private CurrentImage currentImage = new CurrentImage();
    private KeyInput keyInput = BombermanGame.keyInput;


    public Bomber(double x, double y, Image img) {
        super(x, y, img);
        rtg = new Rectangle(x, y, 0.6875, 0.9);
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public void setDeathCountDown(int deathCountDown) {
        this.deathCountDown = deathCountDown;
    }

    public int getDeathCountDown() {
        return deathCountDown;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public void setBombLimit(int bombLimit) {
        this.bombLimit = bombLimit;
    }

    public int getBombRange() {
        return bombRange;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public void setLocation(double x, double y) {
        rtg.setX(x);
        rtg.setY(y);
    }

    public static int round(double x) {
        double x_ = Math.floor(x);
        if (x - x_ < 0.32) return (int) x_;
        if (x - x_ > 0.7) return (int) (x_ + 1);
        else return -1;

    }

    /*
    MOVEMENTS AND BOMB PLACING.
     */
    @Override
    public boolean moveLeft() {
        img = Sprite
                .movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, currentImage.left)
                .getFxImage();
        if (currentImage.left == 8) {
            currentImage.left = 0;
        } else {
            currentImage.left++;
        }
        if (x - speed >= Math.floor(x)) {
            x -= speed;
            setLocation(x, y);
            return true;
        } else {
            int x1 = Bomber.round(x);
            int y1 = Bomber.round(y);
            if (x1 == -1 || y1 == -1) {
                return false;
            } else if (BombermanGame.map[y1].charAt(x1 - 1) != '#' &&
                    BombermanGame.map[y1].charAt(x1 - 1) != '*' &&
                    BombermanGame.map[y1].charAt(x1 - 1) != 't') {
                y = y1;
                x = x - speed;
                setLocation(x, y);
                return true;
            }

        }
        return true;
    }

    public boolean moveRight() {
        img = Sprite
                .movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, currentImage.right)
                .getFxImage();
        if (currentImage.right == 8) {
            currentImage.right = 0;
        } else {
            currentImage.right++;
        }
        if (x + 0.6875 + speed <= Math.floor(x + 0.6875) + 1) {
            x += speed;
            setLocation(x, y);
            return true;
        } else {
            int x1 = Bomber.round(x + 0.6875);
            int y1 = Bomber.round(y);
            if (y1 == -1 || x1 == -1) {
                return false;
            } else if (BombermanGame.map[y1].charAt(x1) != '#' &&
                    BombermanGame.map[y1].charAt(x1) != '*' &&
                    BombermanGame.map[y1].charAt(x1) != 't') {
                y = y1;
                x = x + speed;
                setLocation(x, y);
                return true;
            }

        }
        return true;
    }

    public boolean moveUp() {
        img = Sprite
                .movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, currentImage.up)
                .getFxImage();
        if (currentImage.up == 8) {
            currentImage.up = 0;
        } else {
            currentImage.up++;
        }
        if (y - speed >= Math.floor(y)) {
            y -= speed;
            setLocation(x, y);
            return true;
        } else {
            int x1 = Bomber.round(x);
            int y1 = Bomber.round(y);
            if (x1 == -1 || y1 == -1) {
                return false;
            } else if (BombermanGame.map[y1 - 1].charAt(x1) != '#' &&
                    BombermanGame.map[y1 - 1].charAt(x1) != '*' &&
                    BombermanGame.map[y1 - 1].charAt(x1) != 't') {
                double x_ = x - Math.floor(x);
                if (x_ <= 0.3 && x_ >= 0.1) x = x1 + 0.24;
                else if ((x_ >= 0.7)) x = x1;
                y -= speed;
                setLocation(x, y);
                return true;
            }

        }
        return true;
    }

    @Override
    public boolean moveDown() {
        img = Sprite
                .movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, currentImage.down)
                .getFxImage();
        if (currentImage.down == 8) {
            currentImage.down = 0;
        } else {
            currentImage.down++;
        }
        if (y + 1 + speed <= Math.ceil(y + 1)) {
            y += speed;
            setLocation(x, y);
            return true;
        } else {

            int x1 = Bomber.round(x);
            int y1 = Bomber.round(y + 1);
            if (y1 == -1 || x1 == -1) {
                return false;
            } else if (BombermanGame.map[y1].charAt(x1) != '#' &&
                    BombermanGame.map[y1].charAt(x1) != '*' &&
                    BombermanGame.map[y1].charAt(x1) != 't') {
                double x_ = x - Math.floor(x);
                if (x_ <= 0.3 && x_ >= 0.1) x = x1 + 0.24;
                else if ((x_ >= 0.7)) x = x1;
                y += speed;
                setLocation(x, y);
                return true;
            }
        }
        return true;
    }

    public Entity placeBomb() {
        Entity bom = new Bomb((int) Math.round(this.getX()), (int) Math.round(this.getY()),
                Sprite.bomb.getFxImage(), this.bombRange);
        this.bombList.add((Bomb) bom);
        return bom;
    }

    public int bombCounter() {
        return bombList.size();
    }

    public void dieImg() {
        if (!playsound) {
            SoundEffect.mediaPlayerCollisionEnemy.stop();
            SoundEffect.sound(SoundEffect.mediaPlayerCollisionEnemy);
            playsound = true;
        }
        if (deathCountDown == 0) {
            this.img = null;
        } else {
            this.img = Sprite
                    .bombExplodeSprite(Sprite.player_dead3, Sprite.player_dead2, Sprite.player_dead1, deathCountDown)
                    .getFxImage();
            deathCountDown--;
        }
    }

    @Override
    public void update() {
        if (live > 0) {
            if (keyInput.left && !keyInput.up && !keyInput.down) moveLeft();
            if (keyInput.right && !keyInput.up && !keyInput.down) moveRight();
            if (keyInput.up && !keyInput.right && !keyInput.left) moveUp();
            if (keyInput.down && !keyInput.right && !keyInput.left) moveDown();
            if (!bombList.isEmpty()) {
                if (bombList.get(bombList.size() - 1).isExploded()) {
                    bombList.remove(bombList.size() - 1);
                }
            }
        } else {

            dieImg();
        }
    }

    // Collision Enemy
    public boolean collision(List<Entity> entities) {

        for (Entity x : entities) {
            if (x instanceof Enemy) {
                if (this.rtg.intersects(x.rtg.getLayoutBounds())) {
                    return true;
                }
            }
        }
        return false;
    }
}
