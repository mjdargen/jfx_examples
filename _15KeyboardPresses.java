package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _15KeyboardPresses extends Application {

  // History of key presses (order matters)
  private final List<String> keyHistory = new ArrayList<>();
  // Keys currently held down (no duplicates, fast membership checks)
  private final Set<String> currentlyPressedKeys = new HashSet<>();
  // UI labels to show information
  private final Label historyLabel = new Label("Key history: ");
  private final Label pressedLabel = new Label("Currently pressed: ");
  // Limit for how many items to keep in the history list
  private static final int MAX_HISTORY_SIZE = 20;

  @Override
  public void start(Stage primaryStage) {
    VBox root = new VBox(10);
    root.getChildren().addAll(historyLabel, pressedLabel);

    Scene scene = new Scene(root, 500, 200);

    // Ensure the root can receive focus so it gets key events
    root.setFocusTraversable(true);
    root.requestFocus();

    // If focus is lost, clicking in the window will restore it
    scene.setOnMouseClicked(e -> root.requestFocus());

    // 1) Key pressed: update history and "currently pressed"
    scene.setOnKeyPressed(event -> {
      String keyName = event.getCode().toString();

      // Record in history
      keyHistory.add(keyName);
      if (keyHistory.size() > MAX_HISTORY_SIZE) {
        keyHistory.remove(0); // remove oldest
      }

      // Record in currently pressed set
      currentlyPressedKeys.add(keyName);

      updateLabels();
    });

    // 2) Key released: update "currently pressed"
    scene.setOnKeyReleased(event -> {
      String keyName = event.getCode().toString();

      // Remove from currently pressed set
      currentlyPressedKeys.remove(keyName);

      updateLabels();
    });

    primaryStage.setTitle("Keyboard Input Basics");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  // Update the on-screen labels based on our data structures
  private void updateLabels() {
    String historyText = String.join(", ", keyHistory);
    historyLabel.setText("Key history: " + historyText);

    String pressedText = String.join(", ", currentlyPressedKeys);
    pressedLabel.setText("Currently pressed: " + pressedText);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
