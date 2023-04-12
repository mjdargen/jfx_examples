package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class _01Basics extends Application {

  @Override
  public void start(Stage primaryStage) {

    Label greeting = new Label();
    greeting.setText("What's up?");

    Button btn = new Button();
    btn.setText("Say 'Hello, World!'");
    btn.setOnAction(e -> System.out.println("Hello, World!"));

    VBox vBox = new VBox(20);
    vBox.getChildren().addAll(greeting, btn);
    vBox.setAlignment(Pos.CENTER);

    Scene scene = new Scene(vBox, 300, 250);

    primaryStage.setTitle("Basic Example");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
