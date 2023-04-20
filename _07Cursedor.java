package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.robot.Robot;

public class _07Cursedor extends Application {

  public static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
  public static int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
  private static long previousMouse = 0;

  @Override
  public void start(Stage primaryStage) {

    Label l1 = new Label("Isn't this program great?");
    l1.setFont(new Font(64));
    l1.setTextFill(Color.WHITE);
    Label l2 = new Label("Type \"q\" to end the program.");
    l2.setTextFill(Color.WHITE);
    VBox root = new VBox(20);
    root.getChildren().addAll(l1, l2);
    root.setAlignment(Pos.CENTER);
    root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    primaryStage.setTitle("Cursed(or) Example");
    primaryStage.setScene(scene);
    primaryStage.show();

    // setup event handler to exit program when user types a "q"
    scene.setOnKeyPressed(e -> {
      if (e.getCode().toString().equals("Q")) {
        System.exit(0);
      }
    });

    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        // now provided in nanoseconds
        // repeat every 0.1 seconds
        if (now - previousMouse > 100_000_000) {
          previousMouse = now;
          int x = (int) (Math.random() * screenWidth);
          int y = (int) (Math.random() * screenHeight);
          moveCursor(x, y);
        }
      }
    };
    timer.start();

  }

  public void moveCursor(int screenX, int screenY) {
    Robot robot = new Robot();
    robot.mouseMove(screenX, screenY);
  }

  // public void mirrorCursor() {
  // Robot robot = new Robot();
  // double x = robot.getMouseX();
  // double y = robot.getMouseY();
  // robot.mouseMove(screenWidth - x, screenHeight - y);
  // }

  public static void main(String[] args) {
    launch(args);
  }
}