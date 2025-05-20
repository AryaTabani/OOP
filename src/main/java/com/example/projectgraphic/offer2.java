package com.example.projectgraphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class offer2 {

    @FXML
    private Button close2;

    @FXML
    private ImageView offer2;

    @FXML
    void quit2(ActionEvent event) {
        close2.getScene().getWindow().hide();
    }

}
