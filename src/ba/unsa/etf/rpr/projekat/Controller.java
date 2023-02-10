package ba.unsa.etf.rpr.projekat;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Controller {

    public TextField textWeight;
    public DatePicker textDate;
    public Button buttonSubmitWeight;
    public TextField userName;
    public TextField password;
    private DAO dao;
    private User user;
    private int userID = 1;

    /***
     * Default controller, initializes dao instance and user,
     */
    public Controller() {
        dao = DAO.getInstance();
        user = dao.getUser(userID);
    }

    ObservableList list = FXCollections.observableArrayList();
    Stage primaryStage = new Stage();
    @FXML
    BorderPane borderPane;

    @FXML
    ListView<String> weightsList = new ListView<>();


    /***
     * handles chart controls.
     * @param event fxml event
     */
    @FXML
    public void handleChart(ActionEvent event){
        user = dao.getUser(userID);
        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setLabel("Time");
        NumberAxis categoryAxisY = new NumberAxis();
        categoryAxisY.setLabel("Weight");
        var areaChart = new AreaChart(categoryAxisX,categoryAxisY);
        var data = new XYChart.Series();
        data.setName("Progress");

        List<Weight> weights = user.getWeights();
        for(Weight weight : weights){
            data.getData().add(new XYChart.Data(weight.getDate(), weight.getWeight()));
        }

        areaChart.getData().add(data);
        borderPane.setCenter(areaChart);
    }

    /***
     * handles list of weights.
     * @param event  fxml event
     */
    @FXML
    public void handleList(ActionEvent event){
        user = dao.getUser(userID);

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
        primaryStage.setTitle("Add weight");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }

    public void handleSubmitWeight(ActionEvent event) {
        Weight weight = new Weight();
        LocalDate localDate;
        try{
            weight.setWeight(Double.parseDouble(textWeight.getText()));
            localDate = textDate.getValue();
            weight.setDate(localDate.getDayOfMonth() + "." + localDate.getMonthValue() + "." + localDate.getYear() + ".");
            System.out.println(weight.getWeight() + " " +  weight.getDate());
            dao.addWeightForUser(user.getID(), weight);
        }
        catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Please enter valid values!", "Warning", JOptionPane.ERROR_MESSAGE);
        }
        textWeight.getScene().getWindow().hide();
        user = dao.getUser(userID);
    }


    public void login(ActionEvent actionEvent) throws IOException {
        userID = dao.loginRegisterUser(userName.getText(), password.getText());
        user = dao.getUser(userID);
        userName.getScene().getWindow().hide();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chart.fxml"));
        primaryStage.setTitle("Weighter");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }
}
