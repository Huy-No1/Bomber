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
import uet.oop.bomberman.entities.Enemy.Doll;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Enemy.Oneal;
import uet.oop.bomberman.entities.Item.BombsItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.entities.Item.SpeedItem;
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

    public static GraphicsContext gc;
    private GraphicsContext gcentity;
    private Canvas canvas;
    private Canvas canvasentity;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> finalStaticObjects = new ArrayList<>(); // contains Grass and Walls
    private List<Entity> staticObjects = new ArrayList<>(); // contains Items and Bricks
    private List<Entity> flames = new ArrayList<>();
    public static List<Entity> damagedEntities = new ArrayList<>();

    // public static Entity[][] bricks = new Entity[32][14];

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
        entities.add(bomberman);
        render();


        //loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
                render();
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
                default: {
                }
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
                            flames.addAll(((Bomb) bomb).getFlames());
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
                            finalStaticObjects.add(object);
                            break;
                        }
                        case '*': {
                            finalStaticObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            object = new Brick(j, i, Sprite.brick.getFxImage());
                            staticObjects.add(object);
                            break;
                        }
                        case '1': {
                            finalStaticObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            Enemy enemy = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        case '2': {
                            finalStaticObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            Entity enemy = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        case '3': {
                            finalStaticObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            Entity enemy = new Doll(j, i, Sprite.doll_right1.getFxImage());
                            entities.add(enemy);
                            break;
                        }
                        case 'b': {
                            staticObjects.add(new Brick(j, i, Sprite.brick.getFxImage()
                                    , new BombsItem(j, i, Sprite.powerup_bombs.getFxImage())));
                            map[i] = map[i].replace('b', '*');
                            break;
                        }
                        case 's': {
                            staticObjects.add(new Brick(j, i, Sprite.brick.getFxImage()
                                    , new SpeedItem(j, i, Sprite.powerup_speed.getFxImage())));
                            map[i] = map[i].replace('s', '*');
                            break;
                        }
                        case 'f': {
                            staticObjects.add(new Brick(j, i, Sprite.brick.getFxImage()
                                    , new FlameItem(j, i, Sprite.powerup_flames.getFxImage())));
                            map[i] = map[i].replace('f', '*');
                            break;
                        }
                        default: {
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            finalStaticObjects.add(object);
                            break;
                        }
                    }
                }
            }
            return map;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void update() {
        updateDamagedObjects(); // enemies, bricks and flames
        entities.forEach(Entity::update);
        flames.forEach(Entity::update);
        updateItem();
    }

    public void render() {
        gcentity.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        finalStaticObjects.forEach(g -> g.render(gc));
        staticObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gcentity));
        damagedEntities.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gcentity));
    }


    public void updateStaticObjectsAndEnemies(Entity br) {
        if (br instanceof Brick) {
            Brick brick = (Brick) br;
            if (brick.isDone()) {
                // replace the tile with the grass or Item
                Entity entity = new Grass((int) br.getX(), (int) br.getY(), Sprite.grass.getFxImage());
                if (brick.getItem() != null) {

                    if (brick.getItem() instanceof BombsItem) {
                        entity = new BombsItem((int) br.getX(), (int) br.getY(), Sprite.powerup_bombs.getFxImage());
                    }
                    if (brick.getItem() instanceof SpeedItem) {
                        entity = new SpeedItem((int) br.getX(), (int) br.getY(), Sprite.powerup_speed.getFxImage());
                    }
                    if (brick.getItem() instanceof FlameItem) {
                        entity = new FlameItem((int) br.getX(), (int) br.getY(), Sprite.powerup_flamepass.getFxImage());
                    }
                    staticObjects.add(entity);
                } else {
                    finalStaticObjects.add(entity);
                }
                damagedEntities.remove(br);
                System.out.println(staticObjects.remove(br));


                // enable bomberman to go through the tile
                String newMap = map[(int) br.getY()];
                map[(int) br.getY()] = newMap.substring(0, (int) br.getX()) + " " +
                        newMap.substring((int) br.getX() + 1);

            } else {
                br.update();
            }
        } else if (br instanceof Enemy) {
            if (br.getImg() == null) entities.remove(br);
        }
    }

    public void checkForDamagedEntities(Entity o) {
        // remove bomb from the entites
        if (o instanceof Bomb) {
            if (((Bomb) o).isExploded()) {
                // check if the bomb damange any objects
                ((Bomb) o).handleFlameCollision(entities, staticObjects, damagedEntities);
                System.out.println(entities.remove(o));
            }
        }
    }

    public void updateDamagedObjects() {
        try {

            // update bricks and enemies
            entities.forEach(o -> {
                checkForDamagedEntities(o);
                damagedEntities.forEach(dam -> updateStaticObjectsAndEnemies(dam));

            });

            // get the explosion done (remove the flames)
            flames.forEach(o -> {
                if (o instanceof Flame) {
                    if (((Flame) o).isDone()) {
                        flames.remove(o);
                    }
                }
            });
        } catch (ConcurrentModificationException e) {
            // System.out.println("were no errors to happen");
        }

        // update bomberman
        if (bomberman instanceof Bomber) {
            Bomber bomber = (Bomber) bomberman;
            if (bomber.collision(entities)) {
                bomber.setLive(false);
            }
        }
    }

    public void updateItem() {
        int size= staticObjects.size();
        for (int i = 0; i < size; i++) {
            Entity o = staticObjects.get(i);
            if (o instanceof Item) {
                if (((Item) o).collision(bomberman)) {
                    if(o instanceof SpeedItem) {
                        ((Bomber) bomberman).speed+=0.01;
                    }
                    else if(o instanceof FlameItem) {
                        ((Bomber) bomberman).speed+=0.01;
                    }
                    else {
                        ((Bomber) bomberman).bombLimit++;
                    }
                    staticObjects.remove(i);
                    Entity grass = new Grass((int) o.getX(), (int) o.getY(), Sprite.grass.getFxImage());
                    finalStaticObjects.add(grass);
                    i--;
                    size--;
                }
            }
        }
    }
}
