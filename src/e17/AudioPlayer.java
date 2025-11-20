package e17;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;

public class AudioPlayer extends Application {

  private MediaPlayer player;
  private Label statusLabel = new Label("Status: Ready");
  private Label volumeLabel = new Label("Volume: 100%");

  @Override
  public void start(Stage primaryStage) {
    // Base folder where your sound files live
    String basePath = "./audio/";
    String fileName = "5pm_sunny.mp3"; // make sure this file exists

    String fullPath = basePath + fileName;

    // Create Media and MediaPlayer from a file path
    Media media = new Media(new File(fullPath).toURI().toString());
    player = new MediaPlayer(media);

    // Set initial volume
    player.setVolume(1.0);

    // Update status when playback finishes
    player.setOnEndOfMedia(() -> statusLabel.setText("Status: Finished"));

    // Buttons
    Button playButton = new Button("Play");
    Button pauseButton = new Button("Pause");
    Button stopButton = new Button("Stop");
    Button volumeDownButton = new Button("Vol -");
    Button volumeUpButton = new Button("Vol +");

    // Play: restart from beginning if needed
    playButton.setOnAction(e -> {
      statusLabel.setText("Status: Playing");
      player.play();
    });

    pauseButton.setOnAction(e -> {
      statusLabel.setText("Status: Paused");
      player.pause();
    });

    stopButton.setOnAction(e -> {
      statusLabel.setText("Status: Stopped");
      player.stop(); // resets to start
    });

    volumeDownButton.setOnAction(e -> {
      double v = player.getVolume();
      v = Math.max(0.0, v - 0.1);
      player.setVolume(v);
      updateVolumeLabel();
    });

    volumeUpButton.setOnAction(e -> {
      double v = player.getVolume();
      v = Math.min(1.0, v + 0.1);
      player.setVolume(v);
      updateVolumeLabel();
    });

    HBox controls = new HBox(10, playButton, pauseButton, stopButton);
    controls.setAlignment(Pos.CENTER);

    HBox volumeControls = new HBox(10, volumeDownButton, volumeUpButton);
    volumeControls.setAlignment(Pos.CENTER);

    Label pathWarning = new Label("Audio file path: " + fullPath);

    VBox root = new VBox(10,
        pathWarning,
        controls,
        volumeControls,
        statusLabel,
        volumeLabel);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(10));

    Scene scene = new Scene(root, 400, 200);
    primaryStage.setTitle("Audio Player");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void updateVolumeLabel() {
    int percent = (int) Math.round(player.getVolume() * 100);
    volumeLabel.setText("Volume: " + percent + "%");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
