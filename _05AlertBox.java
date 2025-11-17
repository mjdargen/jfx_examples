package jfx_examples;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.stage.Modality;

public class _05AlertBox {

  public static void display(String title, String message) {
    Stage window = new Stage();

    // blocks other windows from being accessed
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);

    Label label = new Label();
    label.setText(message);
    Button closeButton = new Button("Close the window");
    closeButton.setOnAction(e -> window.close());

    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, closeButton);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait(); // waits until closed
  }

}
