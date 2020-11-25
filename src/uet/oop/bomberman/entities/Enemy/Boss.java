package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;

public class Boss extends Enemy {
    public Boss(int x, int y, Image img) {
        super(x, y, img);
        speed = 0.1;
        life = 10;
        throughWall = false;
    }

    @Override
    public boolean moveUp() {
        return super.moveUp();
    }

    @Override
    public boolean moveRight() {
        return super.moveRight();
    }

    @Override
    public boolean moveDown() {
        return super.moveDown();
    }

    @Override
    public boolean moveLeft() {
        return super.moveLeft();
    }

    @Override
    public void update() {

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
