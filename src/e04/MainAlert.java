package e04;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Pos;

public class MainAlert extends Application {

  @Override
  public void start(Stage primaryStage) {

    Button btn = new Button();
    btn.setText("Say 'Hello World'");
    btn.setOnAction(e -> System.out.println("Hello World!"));

    Button btn2 = new Button();
    btn2.setText("Show alert");
    btn2.setOnAction(e -> AlertBox.display("My Title", "alert!"));

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(btn, btn2);
    Scene scene = new Scene(layout, 300, 250);

    primaryStage.setTitle("Alert Box Example");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
