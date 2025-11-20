package e07;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageDemo extends Application {

  @Override
  public void start(Stage primaryStage) throws FileNotFoundException {

    Label infoLabel = new Label("JavaFX Image demo");

    // Load from a file path on disk
    // Make sure the path is correct relative to where you run the program
    String path = "./src/images/parrot.png";
    FileInputStream inputFile = new FileInputStream(path);
    Image image = new Image(inputFile);
    ImageView imageView = new ImageView(image);

    // Set appropriate size and allow uniform scaling
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(300); // scale down if needed

    // Save initial transform values so we can reset
    double initialScaleX = imageView.getScaleX();
    double initialScaleY = imageView.getScaleY();
    double initialRotate = imageView.getRotate();

    // Buttons
    Button flipButton = new Button("Flip Horizontal");
    Button zoomInButton = new Button("Zoom In");
    Button zoomOutButton = new Button("Zoom Out");
    Button resetButton = new Button("Reset");

    // Flip horizontally by negating scaleX
    flipButton.setOnAction(e -> {
      imageView.setScaleX(imageView.getScaleX() * -1);
    });

    // Zoom in by increasing both scales
    zoomInButton.setOnAction(e -> {
      imageView.setScaleX(imageView.getScaleX() * 1.1);
      imageView.setScaleY(imageView.getScaleY() * 1.1);
    });

    // Zoom out by decreasing both scales
    zoomOutButton.setOnAction(e -> {
      imageView.setScaleX(imageView.getScaleX() * 0.9);
      imageView.setScaleY(imageView.getScaleY() * 0.9);
    });

    // Reset transforms
    resetButton.setOnAction(e -> {
      imageView.setScaleX(initialScaleX);
      imageView.setScaleY(initialScaleY);
      imageView.setRotate(initialRotate);
    });

    HBox controls = new HBox(10, flipButton, zoomInButton, zoomOutButton, resetButton);
    controls.setAlignment(Pos.CENTER);
    controls.setPadding(new Insets(10));

    BorderPane root = new BorderPane();
    root.setTop(infoLabel);
    root.setCenter(imageView);
    root.setBottom(controls);

    Scene scene = new Scene(root, 600, 600);
    primaryStage.setTitle("JavaFX Image Basics");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
