package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    public static boolean live = true;
    private CurrentImage currentImage = new CurrentImage();
    private double speed = 0.08;
    private KeyInput keyInput = BombermanGame.keyInput;
    private int death = 0;

    public Bomber(double x, double y, Image img) {
        super(x, y, img);
        rtg = new Rectangle(x, y, 0.6875, 1);
    }

    public void setLocation(double x_, double y_) {
        rtg.setX(x_);
        rtg.setY(y_);
    }

    public static int round(double x) {
        double x_ = Math.floor(x);
        if (x - x_ < 0.32) return (int) x_;
        if (x - x_ > 0.7) return (int) (x_ + 1);
        else return -1;

    }

    public void moveLeft() {
        img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, currentImage.left).getFxImage();
        if (currentImage.left == 8) {
            currentImage.left = 0;
        } else {
            currentImage.left++;
        }
        if (x - speed >= Math.floor(x)) {
            x -= speed;
            setLocation(x, y);
        } else {
            int x1 = Bomber.round(x);
            int y1 = Bomber.round(y);
            if (x1 == -1 || y1 == -1) {
                return;
            } else if (BombermanGame.map[y1].charAt(x1 - 1) != '#' && BombermanGame.map[y1].charAt(x1 - 1) != '*') {
                y = y1;
                x = x - speed;
                setLocation(x, y);
                return;
            }

        }

    }

    public void moveRight() {
        img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, currentImage.right).getFxImage();
        if (currentImage.right == 8) {
            currentImage.right = 0;
        } else {
            currentImage.right++;
        }
        if (x + 0.6875 + speed <= Math.floor(x + 0.6875) + 1) {
            x += speed;
            setLocation(x, y);
        } else {
            int x1 = Bomber.round(x + 0.6875);
            int y1 = Bomber.round(y);
            if (y1 == -1 || x1 == -1) {
                return;
            } else if (BombermanGame.map[y1].charAt(x1) != '#' && BombermanGame.map[y1].charAt(x1) != '*') {
                y = y1;
                x = x + speed;
                setLocation(x, y);
                return;
            }

        }

    }

    public void moveUp() {
        img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, currentImage.up).getFxImage();
        if (currentImage.up == 8) {
            currentImage.up = 0;
        } else {
            currentImage.up++;
        }
        if (y - speed >= Math.floor(y)) {
            y -= speed;
            setLocation(x, y);
        } else {
            int x1 = Bomber.round(x);
            int y1 = Bomber.round(y);
            if (x1 == -1 || y1 == -1) {
                return;
            } else if (BombermanGame.map[y1 - 1].charAt(x1) != '#' && BombermanGame.map[y1 - 1].charAt(x1) != '*') {
                double x_ = x - Math.floor(x);
                if (x_ <= 0.3 && x_ >= 0.1) x = x1 + 0.24;
                else if ((x_ >= 0.7)) x = x1;
                y -= speed;
                setLocation(x, y);
                return;
            }

        }

    }

    public void moveDown() {
        img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, currentImage.down).getFxImage();
        if (currentImage.down == 8) {
            currentImage.down = 0;
        } else {
            currentImage.down++;
        }
        if (y + 1 + speed <= Math.ceil(y + 1)) {
            y += speed;
            setLocation(x, y);
        } else {

            int x1 = Bomber.round(x);
            int y1 = Bomber.round(y + 1);
            if (y1 == -1 || x1 == -1) {
                return;
            } else if (BombermanGame.map[y1].charAt(x1) != '#' && BombermanGame.map[y1].charAt(x1) != '*') {
                double x_ = x - Math.floor(x);
                if (x_ <= 0.3 && x_ >= 0.1) x = x1 + 0.24;
                else if ((x_ >= 0.7)) x = x1;
                y += speed;
                setLocation(x, y);
                return;
            }

        }
    }

    public void dieImg() {
        if (death == 30) {
            this.img = null;
        } else {
            this.img = Sprite.dieSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, death).getFxImage();
            death++;
        }
    }

    @Override
    public void update() {
        if (live) {
            if (keyInput.left && !keyInput.up && !keyInput.down) moveLeft();
            if (keyInput.right && !keyInput.up && !keyInput.down) moveRight();
            if (keyInput.up && !keyInput.right && !keyInput.left) moveUp();
            if (keyInput.down && !keyInput.right && !keyInput.left) moveDown();
        } else {
                dieImg();
        }
    }
}
