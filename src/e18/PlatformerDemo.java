package e18;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;

/**
 * Entry point for the platformer game.
 *
 * Sets up the window, user interface, TMX based game world,
 * and a continuous game loop that updates the world.
 */
public class PlatformerDemo extends Application {

  private GameWorld gameWorld;

  @Override
  public void start(Stage primaryStage) {
    int width = 1080;
    int height = 720;

    // Create the game world and load the TMX map
    // Adjust the TMX path and collision layer name to match your Tiled project
    gameWorld = new GameWorld(width, height);

    // Simple HUD at the top

    BorderPane root = new BorderPane();
    root.setCenter(gameWorld);

    Scene scene = new Scene(root, width, height);

    // Wire key events to the game world
    scene.setOnKeyPressed(e -> gameWorld.handleKeyPressed(e.getCode()));
    scene.setOnKeyReleased(e -> gameWorld.handleKeyReleased(e.getCode()));

    primaryStage.setScene(scene);
    primaryStage.setTitle("JavaFX Platformer with Tiled TMX");
    primaryStage.show();

    // Animation loop
    AnimationTimer loop = new AnimationTimer() {
      private long previousTime = 0;

      @Override
      public void handle(long now) {
        if (previousTime == 0) {
          previousTime = now;
          return;
        }

        double delta = (now - previousTime) / 1_000_000_000.0;
        previousTime = now;

        gameWorld.update(delta);
      }
    };
    loop.start();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
