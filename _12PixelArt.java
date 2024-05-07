package jfx_examples;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseButton;

// Custom GridPane for pixel art creation
public class _12PixelArt extends GridPane {
  private Color activeColor; // Current active drawing color

  // Constructor for PixelArt
  public _12PixelArt() {
    super();
    // Set default active color to white
    activeColor = Color.WHITE;

    // Create buttons for each square representing a pixel
    Button[] buttons = new Button[8 * 8];
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = new Button();
      // Styling the button to represent a pixel
      buttons[i].setStyle(
          "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px; -fx-min-width: 80; -fx-max-width: 80; -fx-min-height: 80; -fx-max-height: 80;");

    }

    // Set action for each button to change its color on click
    for (int i = 0; i < buttons.length; i++) {
      int row = i / 8;
      int col = i % 8;
      buttons[i].setOnMouseClicked(e -> {
        // Left mouse button clicked
        if (e.getButton() == MouseButton.PRIMARY)
          // Call clearColor to set to white
          setColor(row, col);
        // Right mouse button clicked
        else if (e.getButton() == MouseButton.SECONDARY)
          // Call setColor method to change the color of the clicked pixel
          clearColor(row, col);
      });
    }

    // Add buttons to the grid pane
    this.getStyleClass().add("gridpane");
    for (int i = 0; i < buttons.length; i++) {
      // Add button to the grid pane at specified row and column
      this.add(buttons[i], i % 8, i / 8, 1, 1);
    }
  }

  // Getter method for activeColor
  public Color getActiveColor() {
    return activeColor;
  }

  // Setter method for activeColor
  public void setActiveColor(Color activeColor) {
    this.activeColor = activeColor;
  }

  // Method to set the color of a specific pixel
  public void setColor(int row, int col) {
    // Convert the color to hexadecimal format
    String hexCode = activeColor.toString();
    // Add '#' symbol to the hexadecimal color code
    hexCode = "#" + hexCode.substring(2);
    // Set the style of the pixel button to reflect the selected color
    this.getChildren().get(row * 8 + col).setStyle("-fx-background-color: " + hexCode
        + "; -fx-border-color: black; -fx-border-width: 1px; -fx-min-width: 80; -fx-max-width: 80; -fx-min-height: 80; -fx-max-height: 80;");
  }

  // Method to clear the color of a specific pixel
  public void clearColor(int row, int col) {
    // Set the style of the pixel button to white
    this.getChildren().get(row * 8 + col).setStyle(
        "-fx-background-color: #ffffff; -fx-border-color: black; -fx-border-width: 1px; -fx-min-width: 80; -fx-max-width: 80; -fx-min-height: 80; -fx-max-height: 80;");
  }

}
