package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class _04Color extends Application {
  public void start(Stage primaryStage) throws Exception {

    // creating colors
    double red = 1.0;
    double green = 0.8;
    double blue = 0.6;
    double alpha = 1.0;
    Color color = new Color(red, green, blue, alpha);
    Color color1 = Color.web("#ff00ff");
    Color color2 = Color.web("#ff00ff", 0.5);
    Color color3 = Color.rgb(255, 0, 255);
    Color color4 = Color.rgb(255, 0, 255, 0.5);
    Color color5 = Color.grayRgb(255);
    Color color6 = Color.grayRgb(255, 0.5);
    Color color7 = Color.hsb(270.0, 0.5, 0.8);
    Color color8 = Color.hsb(270.0, 0.5, 0.8, 0.5);
    Color color9 = Color.color(1.0, 0.0, 1.0);
    Color color10 = Color.color(1.0, 0.0, 1.0, 0.5);

    // create color picker
    ColorPicker colorPicker = new ColorPicker();
    Label rawValue = new Label();
    Label rgb = new Label();
    Label opacity = new Label();
    Label hsb = new Label();
    colorPicker.setOnAction(e -> {
      Color value = colorPicker.getValue();
      rawValue.setText("Raw value: " + value);
      rgb.setText("RGB: " + value.getRed() + ", " + value.getGreen() + ", " +
          value.getBlue());
      opacity.setText("Opacity: " + value.getOpacity());
      hsb.setText("HSB: " + value.getHue() + ", " +
          value.getSaturation() + ", " + value.getBrightness() + ")");
    });

    // create a rectangle shape and set the color
    Rectangle rectangle = new Rectangle();
    rectangle.setX(200);
    rectangle.setY(600);
    rectangle.setWidth(200);
    rectangle.setHeight(200);
    rectangle.setStroke(Color.TRANSPARENT);
    rectangle.setFill(Color.valueOf("#00ffff"));

    // create a layout
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(colorPicker, rawValue, rgb, opacity, hsb, rectangle);

    // set background with background fill
    Background background = new Background(new BackgroundFill(Color.DARKBLUE, null, null));
    layout.setBackground(background);

    // set text color
    rawValue.setTextFill(Color.WHITE);
    rgb.setTextFill(Color.WHITE);
    opacity.setTextFill(Color.WHITE);
    hsb.setTextFill(Color.WHITE);

    // set the scene / stage
    Scene scene = new Scene(layout, 400, 800);
    primaryStage.setTitle("Color Example");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }
}
