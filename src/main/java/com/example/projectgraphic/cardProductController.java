package com.example.projectgraphic;

import java.net.URL;
import java.sql.*;
//import java.sql.Date;
import java.util.*;
import java.util.Date;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class cardProductController implements Initializable {
    @FXML
    private AnchorPane card_form;
    @FXML
    private AnchorPane Commentpart;
    @FXML
    private Button Commetbtn;
    @FXML
    private Label prod_name;
    @FXML
    private TextField Score1;
    @FXML
    private TextField Score;

    @FXML
    private Label prod_price;
    @FXML
    private ImageView prod_imageView;
    @FXML
    private Spinner<Integer> prod_spinner;
    @FXML
    private Button prod_addBtn;
    private productData prodData;
    private Image image;
    private String prodID;
    private String type;
    private String prod_date;
    private String prod_image;
    private SpinnerValueFactory<Integer> spin;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    private int qty;
    private double totalP;
    private double pr;
    @FXML
    private ListView<String> Listview1;
    @FXML
    private Button Send;
    @FXML
    private Button exit;
    @FXML
    private TextField Comfield;
    public static List<String> comments = new ArrayList<>();

    //public static Set<String> newitems = new HashSet<>(comments);
    // public static List<String> newcomments = new ArrayList<>(newitems);
    @FXML
    void exiting(ActionEvent event) {
        Commentpart.setVisible(false);
    }

    @FXML
    void Sending(ActionEvent event) throws SQLException {
        score();
        String comment = " INSERT INTO comment (Comm, response, Food,Score) VALUES(?,?,?,?)";
        prepare = connect.prepareStatement(comment);
        prepare.setString(1, Comfield.getText());
        prepare.setString(2, "");
        prepare.setString(3, prod_name.getText());
        prepare.setString(4, Score1.getText());
        prepare.executeUpdate();
        Listview1.getItems().clear();
        Listview1.getItems().addAll(comments);
        Listview1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Listview1.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        Comfield.setText("");
        comments.clear();
    }

    void score() throws SQLException {
        int S = 0;
        int count = 0;
        String query = "SELECT Score FROM comment WHERE Food = ?";
        connect = database.connectDB();
        PreparedStatement statement = connect.prepareStatement(query);

        // Set the value for the "Food" parameter
        statement.setString(1, prod_name.getText());

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int score = resultSet.getInt("Score");
            S += score;
            count++;
        }
        if (count == 0)
            count++;

        double res = 0.0;
        if (count >= 0) {
            res = (double) S / count;
        }

        System.out.println(res);
        Score.setText(String.valueOf(res));
    }


    @FXML
    void addcomment(ActionEvent event) throws SQLException {
        comments.clear();
        score();
        Commentpart.setVisible(true);
        String query = "SELECT Comm FROM comment WHERE Food = ?";
        connect = database.connectDB();
        PreparedStatement statement = connect.prepareStatement(query);

        // Set the value for the "Food" parameter
        statement.setString(1, prod_name.getText());
        System.out.println(prod_name.getText());

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String resname = resultSet.getString("Comm");
            comments.add(resname);
        }

        for (String resname : comments) {
            System.out.println(resname);
        }
        Listview1.getItems().clear();
        Listview1.getItems().addAll(comments);
        Listview1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Listview1.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        Comfield.setText("");

    }

    private void selectionChanged(ObservableValue<? extends String> Observable, String oldVal, String newVal) {
        ObservableList<String> selectedItems = Listview1.getSelectionModel().getSelectedItems();
        String getSelectedItem = (selectedItems.isEmpty()) ? "No Selected Item" : selectedItems.toString();
        //selection.setText(getSelectedItem);
        // resselect = Listview1.getSelectionModel().getSelectedItem();
        //System.out.println(resselect);
    }

    public cardProductController() {
    }

    public void setData(productData prodData) {
        this.prodData = prodData;
        this.prod_image = prodData.getImage();
        this.prod_date = String.valueOf(prodData.getDate());
        this.type = prodData.getType();
        this.prodID = prodData.getProductId();
        this.prod_name.setText(prodData.getProductName());
        this.prod_price.setText("$" + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        this.image = new Image(path, 190.0, 94.0, false, true);
        this.prod_imageView.setImage(this.image);
        this.pr = prodData.getPrice();
    }

    public void setQuantity() {
        this.spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        this.prod_spinner.setValueFactory(this.spin);
    }

    public void addBtn() {
        mainformController mForm = new mainformController();
        mForm.customerID();
        this.qty = (Integer) this.prod_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE prod_id = '" + this.prodID + "'";
        this.connect = database.connectDB();

        try {
            int checkStck = 0;
            String checkStock = "SELECT stock FROM product WHERE prod_id = '" + this.prodID + "'";
            this.prepare = this.connect.prepareStatement(checkStock);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                checkStck = this.result.getInt("stock");
            }

            String insertData;
            if (checkStck == 0) {
                insertData = "UPDATE product SET prod_name = '" + this.prod_name.getText() + "', type = '" + this.type + "', stock = 0, price = " + this.pr + ", status = 'Unavailable', image = '" + this.prod_image + "', date = '" + this.prod_date + "' WHERE prod_id = '" + this.prodID + "'";
                this.prepare = this.connect.prepareStatement(insertData);
                this.prepare.executeUpdate();
            }

            this.prepare = this.connect.prepareStatement(checkAvailable);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                check = this.result.getString("status");
            }

            if (check.equals("Available") && this.qty != 0) {
                if (checkStck < this.qty) {
                    this.alert = new Alert(AlertType.ERROR);
                    this.alert.setTitle("Error Message");
                    this.alert.setHeaderText((String) null);
                    this.alert.setContentText("Invalid. This product is Out of stock");
                    this.alert.showAndWait();
                } else {
                    this.prod_image = this.prod_image.replace("\\", "\\\\");
                    insertData = "INSERT INTO customer (customer_id, prod_id, prod_name, type, quantity, price, date, image, em_username,Customername) VALUES(?,?,?,?,?,?,?,?,?,?)";
                    this.prepare = this.connect.prepareStatement(insertData);
                    this.prepare.setString(1, String.valueOf(data.cID));
                    this.prepare.setString(2, this.prodID);
                    this.prepare.setString(3, this.prod_name.getText());
                    this.prepare.setString(4, this.type);
                    this.prepare.setString(5, String.valueOf(this.qty));
                    this.totalP = (double) this.qty * this.pr;
                    this.prepare.setString(6, String.valueOf(this.totalP));
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    this.prepare.setString(7, String.valueOf(sqlDate));
                    this.prepare.setString(8, this.prod_image);
                    this.prepare.setString(9, CustomManager.resselect);
                    this.prepare.setString(10, DocumentController.user);
                    this.prepare.executeUpdate();
                    int upStock = checkStck - this.qty;
                    System.out.println("Date: " + this.prod_date);
                    System.out.println("Image: " + this.prod_image);
                    String updateStock = "UPDATE product SET prod_name = '" + this.prod_name.getText() + "', type = '" + this.type + "', stock = " + upStock + ", price = " + this.pr + ", status = '" + check + "', image = '" + this.prod_image + "', date = '" + this.prod_date + "' WHERE prod_id = '" + this.prodID + "'";
                    this.prepare = this.connect.prepareStatement(updateStock);
                    this.prepare.executeUpdate();
                    this.alert = new Alert(AlertType.INFORMATION);
                    this.alert.setTitle("Information Message");
                    this.alert.setHeaderText((String) null);
                    this.alert.setContentText("Successfully Added!");
                    this.alert.showAndWait();
                    mForm.menuGetTotal();
                }
            } else {
                this.alert = new Alert(AlertType.ERROR);
                this.alert.setTitle("Error Message");
                this.alert.setHeaderText((String) null);
                this.alert.setContentText("Something Wrong :3");
                this.alert.showAndWait();
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

    }

@Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setQuantity();
        try {
            score();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
