package jfx_examples;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class _02Input extends Application {
  public void start(Stage primaryStage) throws Exception {

    primaryStage.setTitle("Input/Control Examples");

    // create button
    Button btn = new Button();
    btn.setText("Say 'Hello, World!'");
    btn.setOnAction(e -> System.out.println("Hello, World!"));

    // create menu button
    MenuItem menuItem1 = new MenuItem("Option 1");
    MenuItem menuItem2 = new MenuItem("Option 2");
    MenuItem menuItem3 = new MenuItem("Option 3");
    menuItem1.setOnAction(e -> System.out.println("Option 1 selected!"));
    menuItem2.setOnAction(e -> System.out.println("Option 2 selected!"));
    menuItem3.setOnAction(e -> System.out.println("Option 3 selected!"));
    MenuButton menuButton = new MenuButton("Options", null, menuItem1, menuItem2, menuItem3);

    // create combo box
    ComboBox<String> comboBox = new ComboBox<String>();
    comboBox.getItems().addAll("Choice 1", "Choice 2", "Choice 3");
    comboBox.getItems().add("Choice 4");
    comboBox.setEditable(true); // allow user to enter custom response
    comboBox.setOnAction(e -> System.out.println(comboBox.getValue()));
    String response = comboBox.getValue(); // to be used elsewhere in code

    // create radio buttons
    RadioButton radioButton1 = new RadioButton("Left");
    RadioButton radioButton2 = new RadioButton("Right");
    RadioButton radioButton3 = new RadioButton("Up");
    RadioButton radioButton4 = new RadioButton("Down");
    ToggleGroup radioGroup = new ToggleGroup();
    radioButton1.setToggleGroup(radioGroup);
    radioButton2.setToggleGroup(radioGroup);
    radioButton3.setToggleGroup(radioGroup);
    radioButton4.setToggleGroup(radioGroup);
    HBox hbox = new HBox(radioButton1, radioButton2, radioButton3, radioButton4);
    hbox.setAlignment(Pos.CENTER);
    RadioButton selected = (RadioButton) radioGroup.getSelectedToggle(); // to be used elsewhere in code

    // create check box
    CheckBox checkBox1 = new CheckBox("Valid");
    // check whether selected or not
    boolean isSelected = checkBox1.isSelected();
    // allows to check whether or not user has interacted with check box
    checkBox1.setAllowIndeterminate(true);
    // check whether indeterminate or not
    boolean isIndeterminate = checkBox1.isIndeterminate();

    // create date picker
    DatePicker datePicker = new DatePicker();
    datePicker.setOnAction(e -> {
      LocalDate value = datePicker.getValue();
      System.out.println(value.getMonth() + " " + value.getDayOfMonth() + ", " + value.getYear());
    });

    // create color picker
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setOnAction(e -> {
      Color value = colorPicker.getValue();
      System.out.println(value);
      System.out.println("rgbo(" + value.getRed() + ", " + value.getGreen() + ", " +
          value.getBlue() + ", " + value.getOpacity() + ")");
      System.out.println("hsb(" + value.getHue() + ", " + value.getSaturation() +
          ", " + value.getBrightness() + ")");
    });

    // create slider
    Slider slider = new Slider(0, 100, 0);
    double value = slider.getValue(); // read value
    slider.setMajorTickUnit(5.0); // sets how many units the value changes by
    slider.setMinorTickCount(4); // how many minor ticks between two major ticks
    slider.setSnapToTicks(true); // snaps to ticks, no intermediate values
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setOnMouseReleased(e -> System.out.println(slider.getValue()));

    // create text field
    Label username = new Label();
    username.setText("Username: ");
    TextField userField = new TextField();
    userField.setOnAction(action -> {
      System.out.println(userField.getText());
    });
    HBox hboxUser = new HBox(username, userField);
    hboxUser.setAlignment(Pos.CENTER);

    // create password field
    Label password = new Label();
    password.setText("Password: ");
    PasswordField passwordField = new PasswordField();
    passwordField.setOnAction(action -> {
      System.out.println(passwordField.getText());
    });
    HBox hboxPassword = new HBox(password, passwordField);
    hboxPassword.setAlignment(Pos.CENTER);

    // create a text area
    TextArea textArea = new TextArea();
    Button button = new Button("Click to get text");
    button.setMinWidth(50);
    button.setOnAction(action -> {
      System.out.println(textArea.getText());
      textArea.setText("Clicked!");
    });
    VBox textEntry = new VBox(textArea, button);
    textEntry.setAlignment(Pos.CENTER);

    // create a layout
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(btn, menuButton, comboBox, hbox, checkBox1, datePicker, colorPicker, slider,
        hboxUser, hboxPassword, textEntry);

    // set the scene / stage
    Scene scene = new Scene(layout, 400, 800);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
