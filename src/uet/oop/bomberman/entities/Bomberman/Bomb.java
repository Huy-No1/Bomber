package uet.oop.bomberman.entities.Bomberman;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NeutralObject.Brick;
import uet.oop.bomberman.entities.NeutralObject.Flame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {

    private int bombLevel = 3;
    private List<Entity> flames = new ArrayList<>();
    private boolean done = false;
    private boolean exploded = false;
    private int explosionCountDown = 30;
    private int tickingCountDown = 90;

    public List<Entity> getFlames() {
        return flames;
    }

    // set up flame cho phu hop


    public void setTickingCountDown(int tickingCountDown) {
        this.tickingCountDown = tickingCountDown;
    }

    public int getTickingCountDown() {
        return tickingCountDown;
    }

    public void setFlames() {
        String[] pos = {"left", "down", "right", "top", "left_most", "down_most", "right_most", "top_most", "center" };
        int[] iX = {-1, 0, 1, 0};
        int[] iY = {0, 1, 0, -1};

        flames.add(new Flame(x, y, null, "center"));
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= bombLevel; j++) {
                char flag = BombermanGame.map[(int) y + j * iY[i]].charAt((int) x + j * iX[i]);
                if (flag == '#' || flag == '*') break;
                if (j == bombLevel) {
                    flames.add(new Flame(x + iX[i] * bombLevel, y + iY[i] * bombLevel, null, pos[i + 4]));
                } else {
                    flames.add(new Flame(x + iX[i] * j, y + iY[i] * j, null, pos[i]));
                }
            }
        }
    }

    public void setBombLevel(int bombLevel) {
        this.bombLevel = bombLevel;
    }

    public int getBombLevel() {
        return bombLevel;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        setFlames();
    }

    public Bomb(int x, int y, Image img, int bombLevel) {
        super(x, y, img);
        setFlames();
        this.bombLevel = bombLevel;
    }

    public void tickingImg() {
        if (tickingCountDown == 0) {
            setExploded(true);
        } else {
            this.img = Sprite
                    .bombTickingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, tickingCountDown)
                    .getFxImage();
            tickingCountDown--;
        }
    }

    public void explodingImg() {
        if (explosionCountDown == 0) {
            setDone(true);
            this.img = null;
        } else {
            this.img = Sprite
                    .bombExplodeSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                            Sprite.bomb_exploded2, explosionCountDown)
                    .getFxImage();
            explosionCountDown--;
        }
    }

    @Override
    public void update() {
        if (!isExploded()) {
            tickingImg();
        } else {
            explodingImg();
        }
    }

    public void handleFlameCollision(List<Entity> entities, List<Entity> staticObjects,
                                     List<Entity> damagedEntities) {

        // damage bricks
        for (Entity o : staticObjects) {
            if (o instanceof Brick) {
                flames.forEach(flame -> {
                    int oX = (int) o.getX(), fX = (int) flame.getX(), x = (int) this.getX();
                    int oY = (int) o.getY(), fY = (int) flame.getY(), y = (int) this.getY();
                    String pos = ((Flame) flame).getPos();

                    if (!pos.equals("left_most") && !pos.equals("down_most") &&
                            !pos.equals("right_most") && !pos.equals("top_most")) {
                        if (oX == fX && x == oX && oY - fY == 1 ||
                                oX == fX && x == oX && oY - fY == -1 ||
                                oX - fX == -1 && oY == fY && y == oY ||
                                oX - fX == 1 && oY == fY && y == oY) {
                            ((Brick) o).setDamaged(true);
                            damagedEntities.add(o);
                        }
                    } else {
                        if (oX == fX && oY == fY) {
                            ((Brick) o).setDamaged(true);
                            damagedEntities.add(o);
                        }
                    }
                });
            }
        }

        // damage entities
        for (Entity x : entities) {
            if (x instanceof Bomber || x instanceof Enemy) {
                flames.forEach(flame -> {
                    if (flame.rtg.intersects(x.rtg.getLayoutBounds())) {
                        if (x instanceof Bomber) {
                            ((Bomber) x).setLive(false);
                        } else if (x instanceof Enemy) {
                            ((Enemy) x).setDamaged(true);
                        }
                        damagedEntities.add(x);

                    }
                });
            }
        }
    }
}


