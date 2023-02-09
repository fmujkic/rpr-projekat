package ba.unsa.etf.rpr.projekat;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;


public class Controller {

    ObservableList list = FXCollections.observableArrayList();

    @FXML
    BorderPane borderPane;

    @FXML
    ListView<String> weightsList = new ListView<>();


    @FXML
    public void handleChart(ActionEvent event){
        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setLabel("Time");

        NumberAxis categoryAxisY = new NumberAxis();
        categoryAxisY.setLabel("Weight");

        AreaChart areaChart = new AreaChart(categoryAxisX,categoryAxisY);

        XYChart.Series data = new XYChart.Series();
        data.setName("Progress");

        data.getData().add(new XYChart.Data("1.1.2023", 80));
        data.getData().add(new XYChart.Data("2.1.2023", 78));
        data.getData().add(new XYChart.Data("3.1.2023", 82));
        data.getData().add(new XYChart.Data("4.1.2023", 75));
        data.getData().add(new XYChart.Data("5.1.2023", 78));
        data.getData().add(new XYChart.Data("6.1.2023", 74.5));
        data.getData().add(new XYChart.Data("7.1.2023", 76.2));
        data.getData().add(new XYChart.Data("8.1.2023", 71));
        data.getData().add(new XYChart.Data("9.1.2023", 62));
        data.getData().add(new XYChart.Data("10.1.2023", 67));
        data.getData().add(new XYChart.Data("11.1.2023", 68));
        data.getData().add(new XYChart.Data("12.1.2023", 60));

        areaChart.getData().add(data);
        borderPane.setCenter(areaChart);
    }

    @FXML
    public void handleList(ActionEvent event){
        list.addAll("ttt", "rfds","fdvr");
        weightsList.getItems().addAll(list);

        borderPane.setCenter(weightsList);
    }

    @FXML
    public void handleAddWeight(ActionEvent event){
        list.addAll("ttt", "rfds","fdvr");
        weightsList.getItems().addAll(list);

        borderPane.setCenter(weightsList);
    }


}
