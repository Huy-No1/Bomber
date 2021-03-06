package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        speed = 0.05;
        life = 1;
        throughWall = false;

    }

    @Override
    public boolean moveLeft() {
        img = Sprite
                .movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, currentImage.left)
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
                .movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, currentImage.down)
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
                .movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, currentImage.right)
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
                .movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, currentImage.up)
                .getFxImage();
        if (currentImage.up == 8) {
            currentImage.up = 0;
        } else {
            currentImage.up++;
        }
        return super.moveUp();
    }

    @Override
    public void dieImg() {
        if (deathCountDown == 0) {
            this.img = null;
        } else {
            this.img = Sprite
                    .dieSprite(Sprite.mob_dead2, Sprite.mob_dead1, Sprite.oneal_dead, deathCountDown)
                    .getFxImage();
            deathCountDown--;
        }
    }
}
