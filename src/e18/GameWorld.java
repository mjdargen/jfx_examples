package e18;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

import java.util.*;

/**
 * The GameWorld class manages all game objects, user input,
 * physics updates, and collision detection.
 *
 * This version loads a Tiled TMX map and uses the layer
 * named "Platforms" as solid collision geometry.
 */
public class GameWorld extends Pane {

  private static final String TMX_PATH = "src/e18/maps/platformer.tmx";
  private static final double TILE_SCALE = 2.0;
  private static final String COLLISION_LAYER_NAME = "platforms";

  private Player player;
  private Set<KeyCode> keys = new HashSet<>();
  private List<Rectangle> platforms = new ArrayList<>();

  // layer name -> tiles
  private Map<String, List<ImageView>> tileLayers = new LinkedHashMap<>();

  /**
   * Initializes the world with a player and a tile map from Tiled.
   */
  public GameWorld(int width, int height) {
    setPrefSize(width, height);
    setStyle("-fx-background-color: black;");

    // load tiles first so the player is drawn on top
    loadTileMap();

    // Create player near the top so gravity acts immediately
    player = new Player(400, 50, 40, 50, Color.ORANGE);
    getChildren().add(player);
  }

  /**
   * Loads the TMX map, adds all tile layers for drawing,
   * and builds collision rectangles from the "Platforms" layer.
   */
  private void loadTileMap() {
    try {
      Map<String, List<ImageView>> loaded = TiledMapLoader.loadTileMap(TMX_PATH, TILE_SCALE);
      tileLayers.putAll(loaded);

      // draw layers in their insertion order
      for (List<ImageView> layerTiles : tileLayers.values()) {
        getChildren().addAll(layerTiles);
      }

      // create platform rectangles from the layer named "Platforms"
      List<ImageView> solidTiles = tileLayers.get(COLLISION_LAYER_NAME);
      if (solidTiles != null) {
        for (ImageView iv : solidTiles) {
          Rectangle r = new Rectangle(
              iv.getLayoutX(),
              iv.getLayoutY(),
              iv.getFitWidth(),
              iv.getFitHeight());
          r.setFill(Color.TRANSPARENT); // collision only
          platforms.add(r);
        }
      }
    } catch (Exception e) {
      // If loading fails, fall back to an empty map and just use the player
      e.printStackTrace();
    }
  }

  /**
   * Optional accessor if you ever want to inspect tiles by layer.
   */
  public Map<String, List<ImageView>> getTileLayers() {
    return tileLayers;
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
   * Called by the main loop in PlatformerDemo.
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
   * The player lands when moving downward and crossing a platform top surface.
   */
  private void checkPlatformCollisions(Bounds prevPlayerBounds) {
    boolean landed = false;

    for (Rectangle platform : platforms) {
      Bounds playerBounds = player.getBoundsInParent();
      Bounds platBounds = platform.getBoundsInParent();

      // Check if horizontally overlapping with the platform
      boolean horizontalOverlap = playerBounds.getMaxX() > platBounds.getMinX()
          && playerBounds.getMinX() < platBounds.getMaxX();

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
