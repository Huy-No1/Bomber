package uet.oop.bomberman.entities.Enemy;

import com.sun.javafx.geom.Rectangle;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.entities.Entity;

public abstract class Enemy extends Entity {
    protected double speed = 10;
    protected int life = 1;
    protected boolean throughWall = false;

    public Enemy(int x, int y, Image img) {
        super( x, y, img);
    }


    @Override
    public void update() {

    }
}
