package ba.unsa.etf.rpr.projekat;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class Controller {

    public TextField textWeight;
    public DatePicker textDate;
    public Button buttonSubmitWeight;
    DAO dao = new DAO();

    ObservableList list = FXCollections.observableArrayList();
    Stage primaryStage = new Stage();
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

        User user = dao.getUser(1);
        List<Weight> weights = user.getWeights();
        for(Weight weight : weights){
            data.getData().add(new XYChart.Data(weight.getDate(), weight.getWeight()));
        }

/*        data.getData().add(new XYChart.Data("1.1.2023", 80));
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
*/
        areaChart.getData().add(data);
        borderPane.setCenter(areaChart);
    }

    @FXML
    public void handleList(ActionEvent event){

        User user = dao.getUser(1);
        ArrayList<String> orderedWeights = new ArrayList<>();
        List<Weight> weights = user.getWeights();
        for(Weight weight : weights){
            orderedWeights.add(weight.toString());
        }


        list.addAll(orderedWeights);
        weightsList.getItems().addAll(list);
        borderPane.setCenter(weightsList);
    }

    @FXML
    public void handleAddWeight(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/addWeight.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }

    public void handleSubmitWeight(ActionEvent event) throws IOException {
        String weight = textWeight.getText();
        try{
            int number = Integer.parseInt(weight);
            System.out.println(number); // output = 25
        }
        catch (NumberFormatException ex){
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Please enter valid number!", "Warning", JOptionPane.ERROR_MESSAGE);
            textWeight.getScene().getWindow().hide();
        }
        System.out.println(textWeight.getText());
        if(textDate.getValue() != null)
        System.out.println(textDate.getValue().toString());

    }


}
