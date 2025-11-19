import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.animation.AnimationTimer;

/**
 * Entry point for the platformer game.
 * 
 * Sets up the window, user interface (HUD), game world,
 * and a continuous game loop that updates the world.
 */
public class PlatformerDemo extends Application {

  private GameWorld gameWorld;
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
    gameWorld = new GameWorld(800, 600);

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

    // inside your Game or PlatformerDemo initialization
    TiledMap map;
    // gather collision shapes from a named object layer
    List<Shape> collisionShapes;
    try {
      map = TiledMapLoader.load("e18/maps/platformer.tmx", 1.0); // adjust path and scale
      // gather collision shapes from a named object layer
      collisionShapes = map.objectLayers.getOrDefault("Collisions", Collections.emptyList());
    } catch (Exception e) {
      System.err.println(e);
      System.exit(1);
    }
    Canvas canvas = new Canvas(800, 600);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    // draw tile layers each frame or once if static
    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // draw every tile layer in order
        for (Map.Entry<String, List<TileSprite>> entry : map.tileLayers.entrySet()) {
          List<TileSprite> sprites = entry.getValue();
          for (TileSprite s : sprites) {
            // handle flips and diagonal flips here
            // simple draw with width/height. For flips, use save/restore and scale
            // transforms
            gc.save();
            if (s.flipH || s.flipV || s.flipD) {
              // translate to tile center for correct flip/rotate then draw
              double cx = s.x + s.width / 2.0;
              double cy = s.y + s.height / 2.0;
              gc.translate(cx, cy);
              double sx = s.flipH ? -1.0 : 1.0;
              double sy = s.flipV ? -1.0 : 1.0;
              if (s.flipD) {
                // diagonal flip: rotate 90 degrees then handle flips
                gc.rotate(90);
              }
              gc.scale(sx, sy);
              gc.drawImage(s.image, -s.width / 2.0, -s.height / 2.0, s.width, s.height);
            } else {
              gc.drawImage(s.image, s.x, s.y, s.width, s.height);
            }
            gc.restore();
          }
        }

        // example: draw collision rectangles for debug
        gc.setLineWidth(1);
        for (Shape sh : collisionShapes) {
          if (sh instanceof Rectangle) {
            Rectangle r = (Rectangle) sh;
            gc.strokeRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
          } else if (sh instanceof Polygon) {
            Polygon p = (Polygon) sh;
            ObservableList<Double> pts = p.getPoints();
            for (int i = 0; i < pts.size(); i += 2) {
              double x1 = pts.get(i);
              double y1 = pts.get(i + 1);
              double x2 = pts.get((i + 2) % pts.size());
              double y2 = pts.get((i + 3) % pts.size());
              gc.strokeLine(x1, y1, x2, y2);
            }
          }
        }

        // // collision check example: player rectangle
        // Player player = gameWorld.getPlayer();
        // Rectangle playerBounds = new Rectangle(player.getX(), player.getY(),
        // player.get, player.height);
        // for (Shape sh : collisionShapes) {
        // if (sh.getBoundsInParent().intersects(playerBounds.getBoundsInParent())) {
        // // collision detected. respond accordingly
        // }
        // }
      }
    };
    timer.start();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
