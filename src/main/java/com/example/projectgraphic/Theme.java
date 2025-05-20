package com.example.projectgraphic;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Theme {
    @FXML
    private Button Theme1;

    @FXML
    private Button Theme2;


    @FXML
    void Theme1(ActionEvent event) throws IOException {
        Foodi.Themetype = "";
        Theme1.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("MAin.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Foodi");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Theme2(ActionEvent event) throws IOException {
        Foodi.Themetype = "2";
        Theme2.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("MAin"+Foodi.Themetype+".fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Foodi");
        stage.setScene(scene);
        stage.show();
    }

}
