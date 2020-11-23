package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        speed = 0.025;
        life = 1;
        throughWall = false;
        // rtg = new Rectangle(x, y, 0.98, 0.98);
    }

    @Override
    public boolean moveLeft() {
        img = Sprite
                .movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, currentImage.left)
                .getFxImage();
        if (currentImage.left == 8) {
            currentImage.left = 0;
        } else {
            currentImage.left++;
        }
        return super.moveLeft();

    }

    @Override
    public boolean moveDown() {
        img = Sprite
                .movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, currentImage.down)
                .getFxImage();
        if (currentImage.down == 8) {
            currentImage.down = 0;
        } else {
            currentImage.down++;
        }
        return super.moveDown();
    }

    @Override
    public boolean moveRight() {
        img = Sprite
                .movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, currentImage.right)
                .getFxImage();
        if (currentImage.right == 8) {
            currentImage.right = 0;
        } else {
            currentImage.right++;
        }
        return super.moveRight();
    }

    @Override
    public boolean moveUp() {
        img = Sprite
                .movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, currentImage.up)
                .getFxImage();
        if (currentImage.up == 8) {
            currentImage.up = 0;
        } else {
            currentImage.up++;
        }
        return super.moveUp();
    }
}
