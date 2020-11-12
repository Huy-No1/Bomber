package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Enemy.Ballon;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private GraphicsContext gcentity;
    private Canvas canvas;
    private Canvas canvasentity;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    public static String[] map;
    public static KeyInput keyInput = new KeyInput();
    public static Scene scene;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        canvasentity = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gcentity = canvasentity.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(canvasentity);
        // Tao scene
        scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        //khoi tao map
        map = createMap();
        render();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());


        entities.add(bomberman);
        //loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                update();
            }
        };
        timer.start();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case LEFT: {
                        keyInput.left = true;
                        break;
                    }
                    case RIGHT: {
                        keyInput.right = true;
                        break;
                    }
                    case UP: {
                        keyInput.up = true;
                        break;
                    }
                    case DOWN: {
                        keyInput.down = true;
                        break;
                    }
                    case SPACE: {
                        Entity bom = new Bomb((int) Math.round(bomberman.getX()), (int) Math.round(bomberman.getY()), Sprite.bomb.getFxImage());
                        bom.render(gc);
                        //bomberman.render(gcentity);

                    }
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case LEFT: {
                        keyInput.left = false;
                        break;
                    }
                    case RIGHT: {
                        keyInput.right = false;
                        break;
                    }
                    case UP: {
                        keyInput.up = false;
                        break;
                    }
                    case DOWN: {
                        keyInput.down = false;
                        break;
                    }
                    case SPACE: {
                        Entity bom = new Bomb((int) Math.round(bomberman.getX()), (int) Math.round(bomberman.getY()), Sprite.bomb.getFxImage());
                        bom.render(gc);
                        //bomberman.render(gcentity);

                    }
                }
            }
        });
    }


    public String[] createMap() {
        try {
            Scanner scf = new Scanner(new BufferedReader(new FileReader("res/levels/Level1.txt")));
            int row = scf.nextInt();
            row = scf.nextInt();
            String[] map = new String[row];
            int col = scf.nextInt();
            String s = scf.nextLine();
            for (int i = 0; i < row; i++) {
                map[i] = scf.nextLine();
                for (int j = 0; j < map[i].length(); j++) {
                    Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                    char key = map[i].charAt(j);
                    switch (key) {
                        case '#': {
                            object = new Wall(j, i, Sprite.wall.getFxImage());
                            break;
                        }
                        case '*': {
                            object = new Brick(j, i, Sprite.brick.getFxImage());
                            break;
                        }
                        case '1': {
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            Enemy enemy = new Ballon(j, i, Sprite.balloom_left1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        case '2': {
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            Entity enemy = new Ballon(j, i, Sprite.oneal_left1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        default: {
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                    }

                    stillObjects.add(object);
                }
            }
            return map;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        gcentity.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(g -> g.render(gcentity));
    }

    public void render() {
        stillObjects.forEach(g -> g.render(gc));
    }


}
