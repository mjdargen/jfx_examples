package jfx_examples;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class _14MenuView extends VBox {

  public _14MenuView(_14SceneManager sceneManager) {
    // Basic layout setup
    setSpacing(10);
    setAlignment(Pos.CENTER);

    // Create UI controls
    Button startButton = new Button("Start Game");
    Button exitButton = new Button("Exit");

    // Hook up behavior
    startButton.setOnAction(e -> sceneManager.showGame());
    exitButton.setOnAction(e -> System.exit(0));

    // Add controls to this layout
    getChildren().addAll(startButton, exitButton);
  }
}
