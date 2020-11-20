package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Bomberman.Bomb;
import uet.oop.bomberman.entities.Bomberman.Bomber;
import uet.oop.bomberman.entities.Enemy.Balloon;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Enemy.Oneal;
import uet.oop.bomberman.entities.NeutralObject.Brick;
import uet.oop.bomberman.entities.NeutralObject.Flame;
import uet.oop.bomberman.entities.NeutralObject.Grass;
import uet.oop.bomberman.entities.NeutralObject.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Scanner;
/*
    0. Tao canvas
    1. Khoi tao map:
        - Them cac entities (enemies)
        - Them cac stillObjects (tinh vat, quang canh bleh bleh)
        - render map truoc (stillObject objects)
        - Them bomberman vao entities.
    2. Loop game
        -
 */
public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private GraphicsContext gcentity;
    private Canvas canvas;
    private Canvas canvasentity;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> damagedEntities = new ArrayList<>();

    public static String[] map;
    public static KeyInput keyInput = new KeyInput();
    public static Scene scene;
    public static Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
    public static boolean live = true;

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


        entities.add(bomberman);
        //loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        timer.start();
        // nhan key tu ban phim
        scene.setOnKeyPressed(keyEvent -> {
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

                }
                default: {}
            }
        });
        scene.setOnKeyReleased(keyEvent -> {
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
                    if (bomberman instanceof Bomber) {
                        Bomber bomber = (Bomber) bomberman;
                        if (bomber.bombCounter() < bomber.bombLimit) {
                            Entity bomb = bomber.placeBomb();
                            entities.add(bomb);

                            if (bomb instanceof Bomb) {
                                Bomb newbomb = (Bomb) bomb;
                                List<Entity> flames = newbomb.getFlames();
                                entities.addAll(flames);
                            }
                            // System.out.println(bomb instanceof Bomb);

                        }
                    }
                    break;
                }
            }
        });
    }

    //ham khoi tao map
    public String[] createMap() {
        try {
            Scanner scf = new Scanner(new BufferedReader(new FileReader("res/levels/Level1.txt")));

            int level = scf.nextInt();
            int row = scf.nextInt();

            String[] map = new String[row];
            int col = scf.nextInt();
            String s = scf.nextLine();

            // Xu ly thong tin trong file
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
                            // System.out.println("(" + i + ", " + j + ")");
                            break;
                        }
                        case '1': {
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            Enemy enemy = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        case '2': {
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            Entity enemy = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
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
        try {
            entities.forEach(o -> {
                getTheExplosionDoneAndCheckForDamagedEntities(o);
                damagedEntities.forEach(br -> damagingObjectsImg(br));
            });

        } catch (ConcurrentModificationException e) {
            // System.out.println("were no errors to happen");
        }

        // bomberman collides enemies
        if (bomberman instanceof Bomber) {
            Bomber bomber = (Bomber) bomberman;
            if (bomber.collision(entities)) {
                bomber.setLive(false);
            }
//            if (!bomber.isLive() && bomber.getDeathCountDown() == 0) {
//                bomber.setLive(true);
//                bomber.setDeathCountDown(30);
//                bomber.setLocation(1, 1);
//            }
        }
        entities.forEach(Entity::update);
        gcentity.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(g -> g.render(gcentity));

    }

    public void render() {
        stillObjects.forEach(g -> g.render(gc));
    }

    public void damagingObjectsImg(Entity br) {
        if (br instanceof Brick) {
            if(((Brick) br).isDone()) {

                // replace the tile with the grass
                damagedEntities.remove(br);
                stillObjects.remove(br);
                Entity grass = new Grass((int) br.getX(), (int) br.getY(), Sprite.grass.getFxImage());
                stillObjects.add(grass);
                grass.render(gc);

                // enable bomberman to go through the tile
                String newMap = map[(int) br.getY()];
                map[(int) br.getY()] = newMap.substring(0, (int) br.getX()) + " " +
                        newMap.substring((int) br.getX() + 1);

            } else {
                // exploding animation of bricks
                br.update();
                br.render(gc);
            }
        }
    }

    public void getTheExplosionDoneAndCheckForDamagedEntities(Entity o) {
        // remove bomb from the entites
        if (o instanceof Bomb) {
            if (((Bomb) o).isDone()) {
                // check if the bomb damange any objects
                ((Bomb) o).flameCollision(stillObjects, bomberman, damagedEntities);
                entities.remove(o);
            }
        }

        // after the explosion is done, check if the it damage objects
        if (o instanceof Flame) {
            if (((Flame) o).isDone()) {
                entities.remove(o);
            }
        }
    }




}
