package com.example.projectgraphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PaymentController {

    @FXML
    private TextField cardnumtxt;

    @FXML
    private TextField cardholdertxt;

    @FXML
    private TextField monthtxt;

    @FXML
    private TextField yeartxt;

    @FXML
    private PasswordField cvvtxt;

    @FXML
    private Label amounttxt;

    @FXML
    private Label warning;
    Alert alert;

    @FXML
    void confirmPayment(ActionEvent event) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Infomation Message");
        alert.setHeaderText(null);
        alert.setContentText("Successful.");
        alert.showAndWait();

    }

}
