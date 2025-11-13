package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

/**
 * Entry point for the platformer game.
 * 
 * Sets up the window, user interface (HUD), game world,
 * and a continuous game loop that updates the world.
 */
public class _13Main extends Application {

  private _13GameWorld gameWorld;
  private long previousTime = 0;

  @Override
  public void start(Stage stage) {
    // Root layout with HUD on top and the game world below
    BorderPane root = new BorderPane();

    // Simple Heads-Up Display showing static labels
    HBox hud = new HBox(20);
    Label scoreLabel = new Label("Score: 0");
    Label healthLabel = new Label("Health: 100");
    hud.getChildren().addAll(scoreLabel, healthLabel);

    // Create the main game world
    gameWorld = new _13GameWorld(800, 600);

    // Assemble the layout
    root.setTop(hud);
    root.setCenter(gameWorld);

    // Scene setup
    Scene scene = new Scene(root);

    // Forward keyboard input to the GameWorld
    scene.setOnKeyPressed(e -> gameWorld.handleKeyPressed(e.getCode()));
    scene.setOnKeyReleased(e -> gameWorld.handleKeyReleased(e.getCode()));

    // Configure and show window
    stage.setTitle("JavaFX Platformer Starter");
    stage.setScene(scene);
    stage.show();

    // Main game loop using AnimationTimer (~60 FPS)
    AnimationTimer loop = new AnimationTimer() {
      @Override
      public void handle(long now) {
        // Initialize previousTime on the first frame
        if (previousTime == 0) {
          previousTime = now;
          return;
        }

        // Compute elapsed time (in seconds) since last frame
        double delta = (now - previousTime) / 1_000_000_000.0;
        previousTime = now;

        // Update the game world using delta time
        gameWorld.update(delta);
      }
    };
    loop.start();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
