package e14;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import java.util.*;

/**
 * The GameWorld class manages all game objects, user input,
 * physics updates, and collision detection.
 */
public class GameWorld extends Pane {

  private Player player;
  private Set<KeyCode> keys = new HashSet<>();
  private List<Rectangle> platforms = new ArrayList<>();

  /**
   * Initializes the world with a player and several platforms.
   */
  public GameWorld(int width, int height) {
    setPrefSize(width, height);
    setStyle("-fx-background-color: black;");

    // Create player near the top so gravity acts immediately
    player = new Player(100, 50, 40, 50, Color.ORANGE);
    getChildren().add(player);

    // Create several rectangular platforms including the ground
    createPlatforms(width, height);
  }

  /**
   * Adds the ground and several floating platforms.
   */
  private void createPlatforms(int worldWidth, int worldHeight) {
    Rectangle ground = new Rectangle(0, worldHeight - 50, worldWidth, 50);
    ground.setFill(Color.DARKGREEN);

    Rectangle p1 = new Rectangle(150, 450, 150, 20);
    p1.setFill(Color.DARKGRAY);

    Rectangle p2 = new Rectangle(400, 350, 200, 20);
    p2.setFill(Color.DARKGRAY);

    Rectangle p3 = new Rectangle(650, 250, 100, 20);
    p3.setFill(Color.DARKGRAY);

    platforms.addAll(Arrays.asList(ground, p1, p2, p3));
    getChildren().addAll(platforms);
  }

  /** Records when a key is pressed so movement continues while held down. */
  public void handleKeyPressed(KeyCode code) {
    keys.add(code);
  }

  /** Removes a key when it is released. */
  public void handleKeyReleased(KeyCode code) {
    keys.remove(code);
  }

  /**
   * Updates the game state once per frame.
   * Called by the main loop in Main.java.
   */
  public void update(double deltaTime) {
    handleInput();

    // Save player's previous bounds for collision logic
    Bounds previousBounds = player.getPreviousBounds();

    // Update physics and position
    player.update(deltaTime);

    // Check for collisions with all platforms
    checkPlatformCollisions(previousBounds);
  }

  /**
   * Reads keyboard state and updates player motion.
   */
  private void handleInput() {
    if (keys.contains(KeyCode.LEFT)) {
      player.moveLeft();
    } else if (keys.contains(KeyCode.RIGHT)) {
      player.moveRight();
    } else {
      player.stop();
    }

    if (keys.contains(KeyCode.UP)) {
      player.jump();
    }
  }

  /**
   * Detects and handles vertical collisions between the player and platforms.
   * 
   * The player "lands" when moving downward and crossing a platform's top
   * surface.
   */
  private void checkPlatformCollisions(Bounds prevPlayerBounds) {
    boolean landed = false;

    for (Rectangle platform : platforms) {
      Bounds playerBounds = player.getBoundsInParent();
      Bounds platBounds = platform.getBoundsInParent();

      // Check if horizontally overlapping with the platform
      boolean horizontalOverlap = playerBounds.getMaxX() > platBounds.getMinX() &&
          playerBounds.getMinX() < platBounds.getMaxX();

      // Detect vertical crossing from above
      boolean wasAbove = prevPlayerBounds.getMaxY() <= platBounds.getMinY();
      boolean nowBelowOrTouching = playerBounds.getMaxY() >= platBounds.getMinY();

      // Only count as a landing if the player is falling
      if (horizontalOverlap && wasAbove && nowBelowOrTouching && player.getyVel() >= 0) {
        player.landOnPlatform(platBounds.getMinY());
        landed = true;
        break; // Stop after the first landing platform
      }
    }

    // If not standing on any platform, the player should fall
    if (!landed) {
      player.setOnGround(false);
    }
  }
}
