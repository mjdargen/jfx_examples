package e15;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class CSSDemo extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    // get all audio file names
    String path = "./src/audio/";
    // works but sorts in alphabetical order, not chronological
    // File audioDir = new File(path);
    // String[] fileNames = audioDir.list();
    String[] fileNames = { "12am_sunny.mp3", "1am_sunny.mp3", "2am_sunny.mp3", "3am_sunny.mp3", "4am_sunny.mp3",
        "5am_sunny.mp3", "6am_sunny.mp3", "7am_sunny.mp3", "8am_sunny.mp3", "9am_sunny.mp3", "10am_sunny.mp3",
        "11am_sunny.mp3", "12pm_sunny.mp3", "1pm_sunny.mp3", "2pm_sunny.mp3", "3pm_sunny.mp3", "4pm_sunny.mp3",
        "5pm_sunny.mp3", "6pm_sunny.mp3", "7pm_sunny.mp3", "8pm_sunny.mp3", "9pm_sunny.mp3", "10pm_sunny.mp3",
        "11pm_sunny.mp3" };

    // create media players and buttons for each sound
    MediaPlayer[] players = new MediaPlayer[fileNames.length];
    Button[] buttons = new Button[fileNames.length];
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = new Button(fileNames[i].split("_")[0]);
      Media sound = new Media(new File(path + fileNames[i]).toURI().toString());
      MediaPlayer player = new MediaPlayer(sound);
      players[i] = player;

    }

    // set action to stop all other tracks and play current track
    for (int i = 0; i < buttons.length; i++) {
      MediaPlayer player = players[i];
      buttons[i].setOnAction(e -> {
        for (int j = 0; j < players.length; j++)
          players[j].stop();
        player.play();
      });
    }

    // create grid pane
    GridPane gridPane = new GridPane();
    gridPane.getStyleClass().add("gridpane");
    final int cols = 4;
    final int rows = fileNames.length % 4 == 0 ? fileNames.length / 4 : fileNames.length / 4 + 1;
    for (int i = 0; i < buttons.length; i++) {
      gridPane.add(buttons[i], i % 4, i / 4, 1, 1);
    }

    Label title = new Label("Animal Crossing Hourly Music Player");

    VBox layout = new VBox(20);
    layout.getStyleClass().add("vbox");
    layout.getChildren().addAll(title, gridPane);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout, 100 * cols, 100 * rows + title.getHeight() + 40);
    scene.getStylesheets().add("style.css");
    primaryStage.setTitle("GridPane Experiment");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}
