package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class _00HelloWorld extends Application {

  @Override
  public void start(Stage primaryStage) {
    Button btn = new Button();
    btn.setText("Say 'Hello, World!'");
    btn.setOnAction(e -> System.out.println("Hello, World!"));

    VBox vBox = new VBox(btn);

    Scene scene = new Scene(vBox, 300, 250);

    primaryStage.setTitle("Hello, World!");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
