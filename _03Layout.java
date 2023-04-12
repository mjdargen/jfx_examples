package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class _03Layout extends Application {

  @Override
  public void start(Stage primaryStage) {

    Label label = new Label();
    label.setText("To Be Done!");

    VBox vBox = new VBox(20);
    vBox.getChildren().addAll(label);
    vBox.setAlignment(Pos.CENTER);

    Scene scene = new Scene(vBox, 300, 250);

    primaryStage.setTitle("Layout Example");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
