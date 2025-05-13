package jfx_examples;

import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class _13Main extends Application {
  private static int interval = (int) (1.0 / 60.0 * 1_000_000_000);
  private static long previous = 0;
  private static ArrayList<KeyCode> keysPressed = new ArrayList<KeyCode>();

  @Override
  public void start(Stage primaryStage) {

    // Root layout with vertical spacing
    VBox verticalContainer = new VBox(20);
    verticalContainer.setAlignment(Pos.CENTER);

    // Horizontal layout for player stats
    HBox playersContainer = new HBox(200);
    playersContainer.setAlignment(Pos.CENTER);

    // Black background definition
    BackgroundFill blackBG = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);

    // Player 1 stats
    VBox p1Container = new VBox(20);
    Label p1Label = new Label("Player 1 Stats: ");
    Label p1HealthLabel = new Label("Health: ");
    ProgressBar p1HealthBar = new ProgressBar(80 / 100.0);
    p1Container.getChildren().addAll(p1Label, p1HealthLabel, p1HealthBar);

    // Player 2 stats
    VBox p2Container = new VBox(20);
    Label p2Label = new Label("Player 2 Stats: ");
    Label p2HealthLabel = new Label("Health: ");
    ProgressBar p2HealthBar = new ProgressBar(90 / 100.0);
    p2Container.getChildren().addAll(p2Label, p2HealthLabel, p2HealthBar);

    // Add both players to HBox
    playersContainer.getChildren().addAll(p1Container, p2Container);

    // Game area pane
    Pane pane = new Pane();
    pane.setPrefWidth(600);
    pane.setPrefHeight(600);
    pane.setBackground(new Background(blackBG));

    // Assemble scene layout
    verticalContainer.getChildren().addAll(pane, playersContainer);
    Scene scene = new Scene(verticalContainer);
    scene.setFill(Color.BLACK);

    // Set up and show stage
    primaryStage.setTitle("Game Example");
    primaryStage.setScene(scene);
    primaryStage.show();

    // initialize playforms
    ArrayList<Rectangle> platforms = new ArrayList<Rectangle>();
    Rectangle platform1 = new Rectangle(0, 580, 350, 20);
    platform1.setFill(Color.WHITE);
    Rectangle platform2 = new Rectangle(400, 580, 200, 20);
    platform2.setFill(Color.WHITE);
    platforms.add(platform1);
    platforms.add(platform2);
    pane.getChildren().add(platform1);
    pane.getChildren().add(platform2);

    // initialize player
    _13Character player = new _13Character(300, 20, 20, 20, Color.LIMEGREEN);
    pane.getChildren().add(player);

    // keeps track of which buttons are pressed to check in animation loop
    // keyboard button pressed event handler
    scene.setOnKeyPressed(e -> {
      // add button to pressed list
      keysPressed.add(e.getCode());
      // set jump velocity
      if (e.getCode() == KeyCode.UP)
        player.setyVel(-10);
    });
    // keyboard button release event handler
    scene.setOnKeyReleased(e -> {
      // remove button from pressed list
      while (keysPressed.contains(e.getCode()))
        keysPressed.remove(e.getCode());
    });

    // triggered 60 fps to update game state
    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        // now provided in nanoseconds
        // repeat every 0.01 seconds
        if (now - previous > interval) {
          previous = now;

          // handle player left movement
          if (keysPressed.contains(KeyCode.LEFT)) {
            player.setX(player.getX() - player.getxVel());
          }
          // handle player right movement
          else if (keysPressed.contains(KeyCode.RIGHT)) {
            player.setX(player.getX() + player.getxVel());
          }

          // handle gravity
          player.setY(player.getY() + player.getyVel());
          player.setyVel(player.getyVel() + 1);
          // if the movement caused a collision, move position back
          if (checkCollisionIndex(platforms, player) != -1) {
            // get object that player collided with
            Rectangle platform = platforms.get(checkCollisionIndex(platforms, player));
            // moving down - hit the ground
            if (player.getyVel() >= 0) {
              // move player up to no collision position
              player.setY(platform.getY() - platform.getHeight() / 2 - player.getHeight() / 2);
            }
            // moving up - bumped their head
            else {
              // move player down to no collision position
              player.setY(platform.getY() + platform.getHeight() / 2 + player.getHeight() / 2);
            }
            // reset velocity
            player.setyVel(0);
          }
        }
      }
    };
    timer.start();
  }

  // Check for collision between player and platforms
  public static boolean checkCollision(ArrayList<Rectangle> platforms, Rectangle player) {
    for (Rectangle platform : platforms) {
      if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
        return true;
      }
    }
    return false;
  }

  // Get index of the platform that player collides with
  public static int checkCollisionIndex(ArrayList<Rectangle> platforms, Rectangle player) {
    for (int i = 0; i < platforms.size(); i++) {
      if (player.getBoundsInParent().intersects(platforms.get(i).getBoundsInParent())) {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
