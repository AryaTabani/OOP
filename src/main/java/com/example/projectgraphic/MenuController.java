//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.projectgraphic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController implements Initializable {
    public MenuModel menuModel = new MenuModel();
    @FXML
    private Label custLabel;
    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable, String> MenuIdCol;
    @FXML
    private TableColumn<ModelTable, String> MenuNameCol;
    @FXML
    private TableColumn<ModelTable, String> PriceCol;
    @FXML
    private TableColumn<ModelTable, String> QuantityCol;
    @FXML
    private Label totalAmount;
    @FXML
    private PasswordField oldpasstxt;
    @FXML
    private PasswordField newpasstxt;
    @FXML
    private TextField statetxt;
    @FXML
    private TextField citytxt;
    @FXML
    private TextField pincodetxt;
    @FXML
    private TextArea landtxt;
    @FXML
    private TableView<ModelTable1> table1;
    @FXML
    private TableColumn<ModelTable1, String> OrderidCol1;
    @FXML
    private TableColumn<ModelTable1, String> MenuNameCol1;
    @FXML
    private TableColumn<ModelTable1, String> QuantityCol1;
    @FXML
    private TableColumn<ModelTable1, String> OrderStatusCol1;
    //Connection con = SqlConnection.Connector();
    public static int i;
    boolean type;
    ObservableList<ModelTable> obList = FXCollections.observableArrayList();
    ObservableList<ModelTable1> obList1 = FXCollections.observableArrayList();

    public MenuController() {
    }

    public void initialize(URL url, ResourceBundle rb) {

}}
