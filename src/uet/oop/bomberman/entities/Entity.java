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

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        if (this.x >= 0 && this.y >= 0) {
            gc.drawImage(img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
        }
    }

    public boolean moveLeft() {
        return true;
    }

    ;

    public boolean moveRight() {
        return true;
    }

    ;

    public boolean moveUp() {
        return true;
    }

    ;

    public boolean moveDown() {
        return true;
    }

    ;

    public void setLocation(double x, double y) {
        rtg.setX(x);
        rtg.setY(y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void update();
}
