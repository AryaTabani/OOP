package com.example.projectgraphic;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class RestaurantSelect implements Initializable {
    List<String> RESTAURANT = new ArrayList<>();
    @FXML
    private ListView<String> Restaurantlist;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    @FXML
    private Button Proceed;
    @FXML
    private Button Createone;
    private void selectionChanged(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> selectedItems = Restaurantlist.getSelectionModel().getSelectedItems();
        String getSelectedItem = (selectedItems.isEmpty())?"No Selected Item":selectedItems.toString();
        CustomManager.resselect = Restaurantlist.getSelectionModel().getSelectedItem();
        System.out.println(CustomManager.resselect);
    }

    @FXML
    void accept(ActionEvent event) throws IOException {
        Proceed.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("mainform.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Foodi");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void create(ActionEvent event) throws IOException {
        Createone.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Rescreate.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Foodi");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = "SELECT ResName FROM restaurant WHERE ResOwner = ?";
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prepare.setString(1, DocumentController.user);
            System.out.println(DocumentController.user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet resultSet = null;
        try {
            resultSet = prepare.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        {
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String resname = null;
                try {
                    resname = resultSet.getString("ResName");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                RESTAURANT.add(resname);
            }
            Restaurantlist.getItems().addAll(RESTAURANT);
            Restaurantlist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            Restaurantlist.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);

            for (String resname : RESTAURANT) {
                System.out.println(resname);
            }
        }


    }

}
