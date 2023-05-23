package jfx_examples;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.Group;

public class _10Main extends Application {

  private static long previous = 0;
  private static ArrayList<_10Eye> eyes = new ArrayList<_10Eye>();

  @Override
  public void start(Stage primaryStage) {

    Group group = new Group();

    Scene scene = new Scene(group, 800, 800);
    scene.setFill(Color.BLACK);

    primaryStage.setTitle("Eyes Example");
    primaryStage.setScene(scene);
    primaryStage.show();

    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        // now provided in nanoseconds
        // repeat every 0.01 seconds
        if (now - previous > 10_000_000) {
          previous = now;

          // create 100 eyes, but stagger them to not all start at once
          if (eyes.size() < 100) {
            int x = (int) (Math.random() * 800);
            int y = (int) (Math.random() * 800);
            int width = (int) (Math.random() * 101) + 25;
            Color pupil = Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256),
                (int) (Math.random() * 256));
            eyes.add(new _10Eye(x, y, width, pupil));
          }

          group.getChildren().clear();

          for (int i = 0; i < eyes.size(); i++) {
            Group g = eyes.get(i).draw();
            group.getChildren().add(g);
          }
        }
      }
    };
    timer.start();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
