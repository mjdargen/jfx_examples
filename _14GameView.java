package jfx_examples;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Pos;

public class _14GameView extends BorderPane {

  public _14GameView(_14SceneManager sceneManager) {
    // Top bar with a "Back to Menu" button
    Button backButton = new Button("Back to Menu");
    backButton.setOnAction(e -> sceneManager.showMainMenu());

    HBox topBar = new HBox(backButton);
    topBar.setAlignment(Pos.CENTER_LEFT);
    topBar.setSpacing(10);

    setTop(topBar);

    // Center content - placeholder
    Label gameLabel = new Label("Game Screen Placeholder");
    setCenter(gameLabel);
  }
}
