package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class _03Color extends Application {
  public void start(Stage primaryStage) throws Exception {

    primaryStage.setTitle("Color Example");

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

    // create a layout
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(colorPicker, rawValue, rgb, opacity, hsb);

    // set the scene / stage
    Scene scene = new Scene(layout, 400, 800);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
