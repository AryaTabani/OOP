package com.example.projectgraphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class offer1 {

    @FXML
    private Button close;

    @FXML
    private ImageView offer1;

    @FXML
    void quit(ActionEvent event) {
        close.getScene().getWindow().hide();
    }

}
