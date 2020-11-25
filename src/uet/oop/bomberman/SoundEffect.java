package uet.oop.bomberman;



import java.io.File;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SoundEffect extends Application{
    //backsound
    public static Media mediabacksound = new Media(new File("E:\\GIT\\Newfolder\\Sound\\backSound.mp3")
            .toURI().toString());
    public static MediaPlayer mediaPlayerbacksound = new MediaPlayer(mediabacksound);

    //EatItem
    public static Media mediaEatItem = new Media(new File("E:\\Bomberman SFX (6).wav")
            .toURI().toString());
    public static MediaPlayer mediaPlayerEatItem = new MediaPlayer(mediaEatItem);

    //collisionEnemy.mp3
    public static Media mediaCollisionEnemy = new Media(new File("D:\\Downloads\\02_Stage-Start.mp3")
            .toURI().toString());
    public static MediaPlayer mediaPlayerCollisionEnemy = new MediaPlayer(mediaCollisionEnemy);
    public static void main(String[] args) {
        launch(args);
    }

    public static void sound(MediaPlayer mp) {
        mp.play();
        System.out.println("play");
    }

    @Override
    public void start(Stage arg0) throws Exception {
        //sound();
    }
}
