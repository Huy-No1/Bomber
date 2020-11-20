package uet.oop.bomberman.entities.Bomberman;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NeutralObject.Brick;
import uet.oop.bomberman.entities.NeutralObject.Flame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {

    private int bombLevel = 2;
    private List<Entity> flames = new ArrayList<>();
    private Entity rightFlame;
    private boolean done = false;
    private boolean exploded = false;
    private int explosionCountDown = 50;
    private int tickingCountDown = 90;

    public List<Entity> getFlames() {
        return flames;
    }

    // set up flame cho phu hop
    public void setFlames() {
        String[] pos = {"left", "down", "right", "top", "left_most", "down_most", "right_most", "top_most" };
        int[] iX = {-1, 0, 1, 0};
        int[] iY = {0, 1, 0, -1};
        if (bombLevel == 1) {
            for (int i = 0; i < 4; i++) {
                if (BombermanGame.map[(int) y + iY[i]].charAt((int) x +iX[i]) != '#') {
                    flames.add(new Flame(x + iX[i], y + iY[i], null, pos[i + 4]));
                    System.out.println(i + 4);
                }
            }
        } else if (bombLevel == 2) {
            for (int i = 0; i < 4; i++) {
                if (BombermanGame.map[(int) y + iY[i]].charAt((int) x +iX[i]) != '#') {
                    Flame newFlame = new Flame(x + iX[i], y + iY[i], null, pos[i]);
                    flames.add(newFlame);
                }
                if ((int) y + iY[i] * 2 >= 0 && (int) y + iY[i] * 2 < BombermanGame.HEIGHT &&
                        (int) x + iX[i] * 2 >= 0 && (int) x + iX[i] * 2 < BombermanGame.WIDTH &&
                        BombermanGame.map[(int) y + iY[i] * 2].charAt((int) x +iX[i] * 2) != '#' &&
                        BombermanGame.map[(int) y + iY[i]].charAt((int) x +iX[i]) != '#' &&
                        BombermanGame.map[(int) y + iY[i]].charAt((int) x +iX[i]) != '*')
                {
                    flames.add(new Flame(x + iX[i] * 2, y + iY[i] * 2, null, pos[i + 4]));
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

    public Entity getRightFlame() {
        return rightFlame;
    }

    public void setRightFlame(Flame rightFlame) {
        this.rightFlame = rightFlame;
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
                    .bombExplodeSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, explosionCountDown)
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

    public void flameCollision(List<Entity> staticEntities, Entity bomberman, List<Entity> damagedEntities) {
        for (Entity x : staticEntities) {
            //System.out.println(x.rtg);
            if (x instanceof Brick || x instanceof Enemy) {
                flames.forEach(flame -> {
                    if (flame.rtg.intersects(x.rtg.getLayoutBounds())) {
                        if (x instanceof Brick) {
                            ((Brick) x).setDamaged(true);
                        } else {
                            ((Enemy) x).setDamaged(true);
                        }
                        damagedEntities.add(x);
                    }
                });
            }
        }
        if (bomberman instanceof Bomber) {
            flames.forEach(flame -> {
                if (flame.rtg.intersects(bomberman.rtg.getLayoutBounds())) {
                    ((Bomber) bomberman).setLive(false);
                }
            });
        }

        // return false;
    }


    // Unused method and helpless
    public boolean staticObjectsFlameCollision(Entity e) {
        if (this.getX() == e.getX()) {
            if(this.getY() - e.getY() <= this.bombLevel && e.getY() - this.getY() <= this.bombLevel) {
                for (int y = (int) this.getY() - this.bombLevel + 1; y < this.getY() + this.bombLevel - 1; y++) {
                    if (y < 0 || y > BombermanGame.WIDTH) continue;
                    char tmp = BombermanGame.map[y].charAt((int) this.getX());
                    if (tmp == '#' || tmp == '*') return false;
                }
                return true;
            }
        } else if (this.getY() == e.getY()) {
            if(this.getX() - e.getX() <= this.bombLevel && e.getX() - this.getX() <= this.bombLevel) {
                for (int x = (int) this.getX() - this.bombLevel + 1; x < this.getX() + this.bombLevel - 1; x++) {
                    if (x < 0 || y > BombermanGame.HEIGHT) continue;
                    char tmp = BombermanGame.map[(int) this.getY()].charAt(x);
                    if (tmp == '#' || tmp == '*') return false;
                }
                return true;
            }
        } else {
            return false;
        }
        return false;
    }
}


