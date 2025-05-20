package com.example.projectgraphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class offer3 {

    @FXML
    private Button close3;

    @FXML
    private ImageView offer3;

    @FXML
    void quit3(ActionEvent event) {
        close3.getScene().getWindow().hide();
    }

}
