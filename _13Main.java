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

public class _13Main extends Application {
  private static int interval = (int) (1.0 / 60.0 * 1_000_000_000);
  private static long previous = 0;
  private static ArrayList<KeyCode> keysPressed = new ArrayList<KeyCode>();

  @Override
  public void start(Stage primaryStage) {

    // initialize javafx stuff
    Group group = new Group();
    Scene scene = new Scene(group, 600, 600);
    scene.setFill(Color.BLACK);
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
    group.getChildren().add(platform1);
    group.getChildren().add(platform2);

    // initialize player
    _13Character player = new _13Character(300, 20, 20, 20, Color.LIMEGREEN);
    group.getChildren().add(player);

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
