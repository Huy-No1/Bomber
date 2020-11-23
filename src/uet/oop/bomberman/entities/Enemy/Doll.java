package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
    public Doll(int x, int y, Image img) {
        super(x, y, img);
        speed = 0.025;
        life = 2;
        throughWall = false;
    }

    @Override
    public boolean moveLeft() {
        if (throughWall) {
            img = Sprite
                    .movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, currentImage.left)
                    .getFxImage();
        } else {
            img = Sprite
                    .movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, currentImage.left)
                    .getFxImage();
        }
        if (currentImage.left == 8) {
            currentImage.left = 0;
        } else {
            currentImage.left++;
        }

        return super.moveLeft();

    }

    @Override
    public boolean moveDown() {
        if (throughWall) {
            img = Sprite
                    .movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, currentImage.right)
                    .getFxImage();

        } else {
            img = Sprite
                    .movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, currentImage.right)
                    .getFxImage();
        }
        if (currentImage.down == 8) {
            currentImage.down = 0;
        } else {
            currentImage.down++;
        }

        return super.moveDown();
    }

    @Override
    public boolean moveRight() {
        if (throughWall) {
            img = Sprite
                    .movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, currentImage.right)
                    .getFxImage();
        } else {
            img = Sprite
                    .movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, currentImage.right)
                    .getFxImage();
        }
        if (currentImage.right == 8) {
            currentImage.right = 0;
        } else {
            currentImage.right++;
        }

        return super.moveRight();
    }

    @Override
    public boolean moveUp() {
        if (throughWall) {
            img = Sprite
                    .movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, currentImage.left)
                    .getFxImage();
        } else {
            img = Sprite
                    .movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, currentImage.left)
                    .getFxImage();
        }
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
                    .dieSprite(Sprite.doll_dead, Sprite.doll_dead, Sprite.doll_dead, deathCountDown)
                    .getFxImage();
            deathCountDown--;
        }
    }

    @Override
    public void update() {
        if (life == 1) {
            throughWall = true;
            speed = 0.025;
        }


        int a[] = new int[3];
        if (x - Math.floor(x) == 0 && y - Math.floor(y) == 0 && ((int) x) % 2 != 0 && ((int) y) % 2 != 0) {
            switch (direction) {
                case 1:
                    a = new int[]{1, 3, 4};
                    break;
                case 2:
                    a = new int[]{2, 3, 4};
                    break;
                case 3:
                    a = new int[]{1, 2, 3};
                    break;
                case 4:
                    a = new int[]{1, 2, 4};
                    break;
            }
            direction = a[(int) (Math.random() * 3 + 0)];
        }
        super.update();
    }
}
