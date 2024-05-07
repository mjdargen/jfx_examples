package jfx_examples;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

// The main class for the pixel art application
public class _12Main extends Application {

  @Override
  // Starting point of the application
  public void start(Stage primaryStage) throws Exception {

    // Title label for the application
    Label title = new Label("Pixel Art");

    // Creating the pixel art board
    _12PixelArt board = new _12PixelArt();

    // Color picker to select the drawing color
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setOnAction(e -> {
      // Retriever new color picker item on event trigger
      Color value = colorPicker.getValue();
      // Set the selected color to the active color for drawing
      board.setActiveColor(value);
    });

    // Layout for arranging UI elements vertically
    VBox layout = new VBox(10);
    // Adding title, color picker, and board to the layout
    layout.getChildren().addAll(title, colorPicker, board);
    // Aligning the layout to the center
    layout.setAlignment(Pos.CENTER);

    // Creating the scene with specified dimensions
    Scene scene = new Scene(layout, 80 * 8, 80 * 8 + title.getHeight() + layout.getHeight() + 80);
    // Setting the title of the primary stage
    primaryStage.setTitle("Pixel Art");
    // Setting the scene to the primary stage
    primaryStage.setScene(scene);
    // Displaying the primary stage
    primaryStage.show();
  }

  // Main method to launch the application
  public static void main(String[] args) {
    Application.launch(args);
  }

}
