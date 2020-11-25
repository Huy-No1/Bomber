package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Boss extends Enemy {

    public Boss(int x, int y, Image img) {
        super(x, y, img);
        speed = 0.025;
        life = 2;
        throughWall = false;
    }

    public boolean changemob = false;
    public int changedmodcountdown = 50;
    public int generateCreepsCountDown = 200;

    @Override
    public boolean moveUp() {

        if (life == 2) {
            img = Sprite
                    .movingSprite(Sprite.boss1_left1, Sprite.boss1_left2, Sprite.boss1_left3, currentImage.left)
                    .getFxImage();
        } else {
            img = Sprite
                    .movingSprite(Sprite.boss2_left1, Sprite.boss2_left2, Sprite.boss2_left3, currentImage.left)
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
    public boolean moveRight() {
        if (life == 2) {
            img = Sprite
                    .movingSprite(Sprite.boss1_right1, Sprite.boss1_right2, Sprite.boss1_right3, currentImage.right)
                    .getFxImage();
        } else {
            img = Sprite
                    .movingSprite(Sprite.boss2_right1, Sprite.boss2_right2, Sprite.boss2_right3, currentImage.right)
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
    public boolean moveDown() {
        if (life == 2) {
            img = Sprite
                    .movingSprite(Sprite.boss1_right1, Sprite.boss1_right2, Sprite.boss1_right3, currentImage.right)
                    .getFxImage();

        } else {
            img = Sprite
                    .movingSprite(Sprite.boss2_right1, Sprite.boss2_right2, Sprite.boss2_right3, currentImage.right)
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
    public boolean moveLeft() {
        if (life == 2) {
            img = Sprite
                    .movingSprite(Sprite.boss1_left1, Sprite.boss1_left2, Sprite.boss1_left3, currentImage.left)
                    .getFxImage();
        } else {
            img = Sprite
                    .movingSprite(Sprite.boss2_left1, Sprite.boss2_left2, Sprite.boss2_left3, currentImage.left)
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
    public void dieImg() {
        if (deathCountDown == 0) {
            changemob = true;
            this.img = null;
        } else {
            this.img = Sprite
                    .dieSprite(Sprite.mob_dead2, Sprite.mob_dead1, Sprite.boss2_dead, deathCountDown)
                    .getFxImage();
            deathCountDown--;
        }
    }

    public void changemobImg() {
        if (changedmodcountdown == 0) {
            changemob = true;
        } else {
            this.img = Sprite
                    .dieBossSprite(Sprite.boss1_dead, Sprite.boss2_dead, Sprite.boss1_dead, changedmodcountdown)
                    .getFxImage();
            changedmodcountdown--;
        }
    }

    public void generateCreeps() {

        int types[] = new int[]{1, 2, 3};
        int type = types[(int) (Math.random() * 3 + 0)];
        switch(type) {
            case 1: {
                for (int i = 0; i < 3; i++) {
                    BombermanGame.entities.add(new Balloon((int) x, (int) y, Sprite.balloom_left1.getFxImage()));
                }
                break;
            }

            case 2: {
                for (int i = 0; i < 3; i++) {
                    BombermanGame.entities.add(new Oneal((int) x, (int) y, Sprite.oneal_left1.getFxImage()));
                }
                break;
            }
            case 3: {
                for (int i = 0; i < 3; i++) {
                    BombermanGame.entities.add(new Doll((int) x, (int) y, Sprite.doll_left1.getFxImage()));
                }
                break;
            }
            default: { break; }
        }
    }
    @Override
    public void update() {

        if (life == 2 && !changemob && isDamaged()) {
            changemobImg();
        } else {
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

            // super
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
                if (generateCreepsCountDown != 0) {
                    generateCreepsCountDown--;
                } else {
                    if (BombermanGame.entities.size() < 15) {
                        generateCreeps();
                        generateCreepsCountDown = 200;
                    }
                }

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
}
