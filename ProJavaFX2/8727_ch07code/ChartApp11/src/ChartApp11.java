import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ChartApp11 extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(2011);
        xAxis.setUpperBound(2016);
        BubbleChart bubbleChart = new BubbleChart(xAxis, yAxis);
        bubbleChart.setData(getChartData());
        bubbleChart.setTitle("speculations");
        primaryStage.setTitle("BubbleChart example");

        StackPane root = new StackPane();
        root.getChildren().add(bubbleChart);
        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();
    }

    private ObservableList<XYChart.Series<Integer, Double>> getChartData() {
        double javaValue = 17.56;
        double cValue = 17.06;
        double cppValue = 8.25;
        ObservableList<XYChart.Series<Integer, Double>> answer = FXCollections.observableArrayList();
        Series<Integer, Double> java = new Series<Integer, Double>();
        Series<Integer, Double> c = new Series<Integer, Double>();
        Series<Integer, Double> cpp = new Series<Integer, Double>();
        java.setName("java");
        c.setName("C");
        cpp.setName("C++");
        for (int i = 2011; i < 2016; i++) {
            double diff = Math.random();
            java.getData().add(new XYChart.Data(i, javaValue, diff));
            javaValue = javaValue + 10*diff - 5;
            diff = Math.random();
            c.getData().add(new XYChart.Data(i, cValue, diff));
            cValue = cValue + 10*diff - 5;
            diff = Math.random();
            cpp.getData().add(new XYChart.Data(i, cppValue, diff));
            cppValue = cppValue + 10*diff - 5;
        }
        answer.addAll(java, c, cpp);
        return answer;
    }
}