package e16;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PieChartExample extends Application {

  @Override
  public void start(Stage primaryStage) {
    ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
        new PieChart.Data("Brand A", 40),
        new PieChart.Data("Brand B", 25),
        new PieChart.Data("Brand C", 20),
        new PieChart.Data("Others", 15));

    PieChart pieChart = new PieChart(pieData);
    pieChart.setTitle("Market Share");

    BorderPane root = new BorderPane(pieChart);

    Scene scene = new Scene(root, 600, 400);
    primaryStage.setTitle("Pie Chart Example");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
