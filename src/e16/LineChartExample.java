import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LineChartExample extends Application {

  @Override
  public void start(Stage primaryStage) {
    // X axis: categories (quarters)
    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("Quarter");

    // Y axis: numeric values (sales)
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Sales (units)");

    // Create the chart
    LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    lineChart.setTitle("Sales Over Time");

    // One series of data
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Product A");

    series.getData().add(new XYChart.Data<>("Q1", 120));
    series.getData().add(new XYChart.Data<>("Q2", 150));
    series.getData().add(new XYChart.Data<>("Q3", 90));
    series.getData().add(new XYChart.Data<>("Q4", 180));

    // Add series to chart
    lineChart.getData().add(series);

    BorderPane root = new BorderPane(lineChart);

    Scene scene = new Scene(root, 600, 400);
    primaryStage.setTitle("Line Chart Example");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
