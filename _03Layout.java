package jfx_examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Separator;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;

public class _03Layout extends Application {
  static int sceneNum = 0;

  @Override
  public void start(Stage primaryStage) {

    // define scenes and containers
    Scene[] scenes = new Scene[3];
    VBox[] sceneContainers = new VBox[scenes.length];
    Button[] changeSceneBtn = new Button[scenes.length];
    for (int i = 0; i < scenes.length; i++) {
      sceneContainers[i] = new VBox(20);
      sceneContainers[i].setAlignment(Pos.CENTER);
      scenes[i] = new Scene(sceneContainers[i], 300, 250);
      changeSceneBtn[i] = new Button();
      changeSceneBtn[i].setText("Change Scene");
      String scene = "" + i;
      changeSceneBtn[i].setOnAction(e -> {
        sceneNum = (sceneNum + 1) % scenes.length;
        primaryStage.setScene(scenes[sceneNum]);
        primaryStage.show();
        System.out.println("Changing to scene " + scene);
      });
    }

    // scene 0
    Label label1 = new Label();
    label1.setText("This is an example.");
    Label label2 = new Label();
    label2.setText("It's super exciting!");

    HBox hBox1 = new HBox(20);
    hBox1.getChildren().addAll(label1, label2);
    hBox1.setAlignment(Pos.CENTER);

    Label label3 = new Label();
    label3.setText("Label 1");
    Label label4 = new Label();
    label4.setText("Label 2");
    Label label5 = new Label();
    label5.setText("Label 3");

    HBox hBox2 = new HBox(20);
    hBox2.getChildren().addAll(label3, label4, label5);
    hBox2.setAlignment(Pos.CENTER);

    Separator sep = new Separator(Orientation.HORIZONTAL);

    VBox vBox = new VBox(20);
    vBox.getChildren().addAll(hBox1, hBox2, sep, changeSceneBtn[0]);
    vBox.setAlignment(Pos.CENTER);

    sceneContainers[0].getChildren().addAll(vBox);

    // scene 1
    final int cols = 4;
    final int rows = 4;
    Button[] buttons = new Button[cols * rows];
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = new Button("" + i);
      buttons[i].setMinSize(40, 40);
      buttons[i].setMaxSize(40, 40);
      buttons[i].setPrefSize(40, 40);
      String buttonNum = "" + i;
      buttons[i].setOnAction(e -> {
        System.out.println(buttonNum);
      });
    }

    GridPane gridPane = new GridPane();
    for (int i = 0; i < buttons.length; i++) {
      gridPane.add(buttons[i], i % 4, i / 4, 1, 1);
    }
    HBox gridContainer = new HBox(20);
    gridContainer.setAlignment(Pos.CENTER);
    gridContainer.getChildren().add(gridPane);
    sceneContainers[1].getChildren().addAll(gridContainer, changeSceneBtn[1]);

    // scene 2
    Button button1 = new Button("Button 1");
    Button button2 = new Button("Button Number 2");
    Button button3 = new Button("Button No 3");
    Button button4 = new Button("Button No 4");
    Button button5 = new Button("Button 5");
    Button button6 = new Button("Button Number 6");

    TilePane tilePane = new TilePane();
    tilePane.getChildren().addAll(button1, button2, button3, button4, button5, button6);
    tilePane.setHgap(10);
    tilePane.setVgap(10);
    tilePane.setTileAlignment(Pos.TOP_LEFT);

    sceneContainers[2].getChildren().addAll(tilePane, changeSceneBtn[2]);

    // show first scene
    primaryStage.setTitle("Layout Example");
    primaryStage.setScene(scenes[0]);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
