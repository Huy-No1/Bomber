package uet.oop.bomberman.entities;

import com.sun.javafx.geom.Rectangle;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private int left = 0;
    private int right = 0;
    private int up = 0;
    private int down = 0;
    private double speed = 0.12;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public static int round(double x) {
        double x_ = Math.floor(x);
        if (x - x_ < 0.3) return (int) x_;
        if (x - x_ > 0.7) return (int) (x_ + 1);
        else return -1;

    }

    public void moveLeft() {
        img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, left).getFxImage();
        if (left == 8) {
            left = 0;
        } else {
            left++;
        }
        if (x - speed >= Math.floor(x)) {
            x -= speed;
        } else {
            int x1 = Bomber.round(x);
            int y1 = Bomber.round(y);
            System.out.println(x1 + " " + y1);
            if (x1 == -1 || y1 == -1) {
                System.out.println("loi");
                return;
            } else if (BombermanGame.map[y1].charAt(x1 - 1) != '#' && BombermanGame.map[y1].charAt(x1 - 1) != '*') {
                y = y1;
                x = x - speed;
                return;
            }

        }
    }

    public void moveRight() {
        img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, right).getFxImage();
        if (right == 8) {
            right = 0;
        } else {
            right++;
        }
        if (x + 0.6875 + speed <= Math.floor(x + 0.6875) + 1) {
            x += speed;
        } else {
            int x1 = Bomber.round(x + 0.6875);
            int y1 = Bomber.round(y);
            System.out.println(x1 + " " + y1);
            if (y1 == -1 || x1 == -1) {

                return;
            } else if (BombermanGame.map[y1].charAt(x1) != '#' && BombermanGame.map[y1].charAt(x1) != '*') {
                y = y1;
                System.out.println("run");
                x = x + speed;
                return;
            }

        }

    }

    public void moveUp() {
        img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, up).getFxImage();
        if (up == 8) {
            up = 0;
        } else {
            up++;
        }
        if (y - speed >= Math.floor(y)) {
            y -= speed;
        } else {
            int x1 = Bomber.round(x);
            int y1 = Bomber.round(y);
            System.out.println(x1 + " " + y1);
            if (x1 == -1 || y1 == -1) {
                System.out.println("loi");
                return;
            } else if (BombermanGame.map[y1 - 1].charAt(x1) != '#' && BombermanGame.map[y1 - 1].charAt(x1) != '*') {
                x = x1;
                y -= speed;
                return;
            }

        }

    }

    public void moveDown() {
        img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, down).getFxImage();
        if (down == 8) {
            down = 0;
        } else {
            down++;
        }
        if (y + 1 + speed <= Math.floor(y + 1) + 1) {
            y += speed;
        } else {
            int x1 = Bomber.round(x);
            int y1 = Bomber.round(y + 1);
            System.out.println(x1 + " " + y1);
            if (y1 == -1 || x1 == -1) {
                return;
            } else if (BombermanGame.map[y1].charAt(x1) != '#' && BombermanGame.map[y1].charAt(x1) != '*') {
                x = x1;
                System.out.println("run");
                y += speed;
                return;
            }

        }
    }

    @Override
    public void update() {

    }
}
