package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.io.File;
import java.io.FileInputStream;

public class _11SlideShow extends Application {
  private static int sceneNum = 0;

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("SlideShow");

    // get all image file names
    String path = "./jfx_examples/images/";
    File imgDir = new File(path);
    String[] fileNames = imgDir.list();

    // create a scene for each image
    Scene[] scenes = new Scene[fileNames.length];
    for (int i = 0; i < scenes.length; i++) {
      FileInputStream input = new FileInputStream("./jfx_examples/images/" + fileNames[i]);
      Image image = new Image(input);
      ImageView imageView = new ImageView(image);

      Button leftBtn = new Button("<");
      leftBtn.setOnAction(e -> {
        sceneNum = (sceneNum - 1 < 0) ? scenes.length - 1 : sceneNum - 1;
        primaryStage.setScene(scenes[sceneNum]);
        primaryStage.show();
      });
      Button rightBtn = new Button(">");
      rightBtn.setOnAction(e -> {
        sceneNum = (sceneNum + 1) % scenes.length;
        primaryStage.setScene(scenes[sceneNum]);
        primaryStage.show();
      });

      HBox buttonGroup = new HBox(leftBtn, rightBtn);
      buttonGroup.setAlignment(Pos.CENTER);

      VBox layout = new VBox(imageView, buttonGroup);
      layout.setAlignment(Pos.CENTER);
      scenes[i] = new Scene(layout, 400, 400);
    }

    primaryStage.setScene(scenes[sceneNum]);
    primaryStage.show();

  }

  public static void main(String[] args) {
    Application.launch(args);
  }

}
