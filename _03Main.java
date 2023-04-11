package jfx_examples;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class _03Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    Button btn = new Button();
    btn.setText("Say 'Hello World'");
    btn.setOnAction(e -> System.out.println("Hello World!"));

    Button btn2 = new Button();
    btn2.setText("Do confirm");
    btn2.setOnAction(e -> {
      boolean result = ConfirmBox.display("Confirmation", "Are you sure you want to do this?");
      System.out.println(result);
    });

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(btn, btn2);
    Scene scene = new Scene(layout, 300, 250);

    primaryStage.setTitle("Hello World!");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}