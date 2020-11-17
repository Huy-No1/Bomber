package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    protected double x;
    protected double y;
    protected Image img;
    public Rectangle rtg;
    public Entity(double x, double y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView iv = new ImageView(img);
        Image base = iv.snapshot(params, null);

        gc.drawImage(base, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
    }

    public void moveLeft() {
    }

    ;

    public void moveRight() {
    }

    ;

    public void moveUp() {
    }

    ;

    public void moveDown() {
    }

    ;

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public abstract void update();
}
