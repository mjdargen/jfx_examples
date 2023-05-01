package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.animation.AnimationTimer;

public class _09Marquee extends Application {

  private static long previous = 0;

  @Override
  public void start(Stage primaryStage) {

    Label l1 = new Label("Isn't this program great?");
    l1.setFont(new Font(64));
    l1.setTextFill(Color.WHITE);
    Group group = new Group();
    group.getChildren().addAll(l1);

    Scene scene = new Scene(group, 800, 600, Color.BLACK);
    primaryStage.setTitle("Marquee Example");
    primaryStage.setScene(scene);
    primaryStage.show();

    l1.setLayoutX(scene.getWidth() / 2 - l1.getWidth() / 2);
    l1.setLayoutY(scene.getHeight() / 2 - l1.getHeight() / 2);

    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        // now provided in nanoseconds
        // repeat every 0.025 seconds
        if (now - previous > 25_000_000) {
          previous = now;
          l1.setLayoutX(l1.getLayoutX() - 5);
          if (l1.getLayoutX() + l1.getWidth() < 0)
            l1.setLayoutX(scene.getWidth());
        }
      }
    };
    timer.start();

  }

  public static void main(String[] args) {
    launch(args);
  }
}