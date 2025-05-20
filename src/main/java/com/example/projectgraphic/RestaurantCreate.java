package com.example.projectgraphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantCreate {

    @FXML
    private TextField Resname;

    @FXML
    private TextField reslocation;

    @FXML
    private TextField restype;
    @FXML
    private Button Create;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    Alert alert;

    @FXML
    void CrRes(ActionEvent event) throws SQLException, IOException {
        connect=database.connectDB();
        String info =" INSERT INTO restaurant (ResName, ResOwner, ResLocation,ResType) VALUES(?,?,?,?)";
        prepare = connect.prepareStatement(info);
        prepare.setString(1, Resname.getText());
        prepare.setString(2, DocumentController.user);
        prepare.setString(3, reslocation.getText());
        prepare.setString(4, restype.getText());
        prepare.executeUpdate();
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulate!");
        alert.setHeaderText(null);
        alert.setContentText("Restaurant created successfuly!");
        DocumentController.restname = Resname.getText();
        CustomManager.resselect =Resname.getText();
        alert.showAndWait();
        Create.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("mainform"+Foodi.Themetype+".fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Foodi");
        stage.setScene(scene);
        stage.show();
    }

}
