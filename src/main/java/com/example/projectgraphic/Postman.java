package com.example.projectgraphic;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Postman implements Initializable {

    @FXML
    private AnchorPane Cus_form;

    @FXML
    private Button logout_btn;

    @FXML
    private TextField username;

    @FXML
    private Button Availableorders;

    @FXML
    private Button CurrentDelivery;

    @FXML
    private Button ShowLocation;

    @FXML
    private Button ShowBalance;

    @FXML
    private AnchorPane Restaurantlist;

    @FXML
    private ListView<String> listView;

    private List<String> Orders = new ArrayList<>();
    private String selectedorder;

    @FXML
    private Label selection;
    Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private void selectionChanged(ObservableValue<? extends String> Observable, String oldVal, String newVal) {
        ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
        String getSelectedItem = (selectedItems.isEmpty()) ? "No Selected Item" : selectedItems.toString();
        selection.setText(getSelectedItem);
        selectedorder = listView.getSelectionModel().getSelectedItem();
        //System.out.println(resselect);
    }

    private String deliveryPrice = "";
    private String customerName = "";

    @FXML
    void Accept(ActionEvent event) throws SQLException, IOException {
        String input2 = selectedorder;
        String[] fields2 = input2.split("-->");
        deliveryPrice = fields2[2].substring(fields2[2].indexOf(":") + 2);
        System.out.println(deliveryPrice);
        String input = selectedorder;
        String[] fields = input.split("-->");
        customerName = fields[4].substring(fields[4].indexOf(":") + 2);
        System.out.println(customerName);
        double amountToAdd = Double.parseDouble(deliveryPrice);
        connect = database.connectDB();
        String sql = "UPDATE employee SET Balance = Balance + ? WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(2, DocumentController.user);
        preparedStatement.setString(3, DocumentController.pass);
        preparedStatement.setString(1, String.valueOf(amountToAdd));
        System.out.println(DocumentController.user + amountToAdd + DocumentController.pass);
        preparedStatement.executeUpdate();
        Orders.remove(selectedorder);
        String sql2 = "UPDATE customer SET IsDelivered = ? WHERE Customername = ?";
        PreparedStatement pstmt = connect.prepareStatement(sql2);
        pstmt.setBoolean(1, false);
        pstmt.setString(2, customerName);
        pstmt.executeUpdate();
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(amountToAdd + "$ Added to your balance!");
        alert.showAndWait();
        String sql3= "SELECT * FROM postman WHERE orper = ?";
//        connect = database.connectDB();
        PreparedStatement statement = connect.prepareStatement(sql3);
        statement.setString(1, customerName);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int mabda = Integer.parseInt(resultSet.getString("Src"));
            int maghsad = Integer.parseInt(resultSet.getString("dst"));
            System.out.println(resultSet.getString("dst"));
            System.out.println(resultSet.getString("Src"));
            new MapTest(mabda,maghsad);
        }
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                logout_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("MAin" + Foodi.Themetype + ".fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Foodi");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SHowbal(ActionEvent event) throws SQLException {
        String sql = "SELECT balance FROM employee WHERE username = ?";
        connect = database.connectDB();
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setString(1, DocumentController.user);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            double balance = resultSet.getDouble("balance");
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Balance");
            alert.setContentText("Balance:" + balance + "$");
            alert.showAndWait();
        } else {
            System.out.println("User not found.");
        }
    }

    @FXML
    void showLocation(ActionEvent event) throws SQLException {
        String input = selectedorder;
        String[] fields = input.split("-->");
        customerName = fields[4].substring(fields[4].indexOf(":") + 2);
        System.out.println(customerName);
        String sql = "SELECT * FROM postman WHERE orper = ?";
//        connect = database.connectDB();
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setString(1, customerName);

        ResultSet resultSet = statement.executeQuery();
//        System.out.println(resultSet.findColumn("dst"));
        while (resultSet.next()) {

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Infomation Message");
            alert.setHeaderText(null);
            System.out.println(resultSet.getString("dst"));
            alert.setContentText("Dst: " + resultSet.getInt("dst") + "->" + "Estimated Time: " + resultSet.getString("estimatedtime"));
            alert.showAndWait();
        }
    }
    @FXML
    void renew(ActionEvent event) {
        listView.getItems().clear();
        listView.getItems().addAll(Orders);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sql = "SELECT * FROM customer WHERE IsDelivered =?";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            prepare.setBoolean(1, true);
            result = prepare.executeQuery();

            productData prod;

            while (result.next()) {
                String or =
                        "Product Name: " + result.getString("prod_name") + "-->" +
                                "Product Type: " + result.getString("type") + "-->" +
                                "Delivery Price : " + result.getDouble("price") * 0.05 + "-->" +
                                "Restaurant Name: " + result.getString("em_username") + "-->" +
                                "Customer Name: " + result.getString("Customername") + "-->" +
                                "Request Date: " + result.getDate("date");
                Orders.add(or);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.getItems().addAll(Orders);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
//        String input = selectedorder;
//        String[] fields = input.split("-->");
//        String customerName = fields[4].substring(fields[4].indexOf(":") + 2);
//        System.out.println(customerName);
//        String input2 = selectedorder;
//        String[] fields2 = input2.split("-->");
//        String deliveryPrice = fields2[2].substring(fields2[2].indexOf(":") + 2);
//        System.out.println(deliveryPrice);

    }

}
