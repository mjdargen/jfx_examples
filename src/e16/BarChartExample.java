import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BarChartExample extends Application {

  @Override
  public void start(Stage primaryStage) {
    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("Fruit");

    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Quantity Sold");

    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
    barChart.setTitle("Fruit Sales");

    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Store 1");

    series.getData().add(new XYChart.Data<>("Apples", 50));
    series.getData().add(new XYChart.Data<>("Oranges", 80));
    series.getData().add(new XYChart.Data<>("Bananas", 30));

    barChart.getData().add(series);

    BorderPane root = new BorderPane(barChart);

    Scene scene = new Scene(root, 600, 400);
    primaryStage.setTitle("Bar Chart Example");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
