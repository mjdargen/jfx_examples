package jfx_examples;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.scene.control.Label;

public class _05ConfirmBox {

  static boolean answer;

  public static boolean display(String title, String message) {
    Stage window = new Stage();

    // blocks other windows from being accessed
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);

    Label label = new Label();
    label.setText(message);

    Button yesButton = new Button("Yes");
    Button noButton = new Button("No");
    yesButton.setOnAction(e -> {
      answer = true;
      window.close();
    });
    noButton.setOnAction(e -> {
      answer = false;
      window.close();
    });

    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, yesButton, noButton);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait(); // waits until closed

    return answer;
  }

}
