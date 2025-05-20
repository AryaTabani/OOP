package com.example.projectgraphic;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class CustomManager implements Initializable {
    @FXML
    private Button orderHis;
    @FXML
    private Button chargebtn;
    public static boolean farayand = false;
    @FXML
    private TextField Baladd;

    @FXML
    private AnchorPane Buy_form;

    @FXML
    private AnchorPane Cus_form;

    @FXML
    private TextField Ressearch;


    @FXML
    private Button logout_btn;

    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<?, ?> menu_col_price;

    @FXML
    private TableColumn<?, ?> menu_col_productName;

    @FXML
    private TableColumn<?, ?> menu_col_quantity;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<productData> menu_tableView;

    @FXML
    private Label menu_total;

    @FXML
    private TextField username;
    @FXML
    protected static AnchorPane resfoods;

    private Alert alert;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;
    private double amount;
    private double change;
    private int getid;
    private int cID;
    private double totalP;
    private String[] typeList = {"Meals", "Drinks", "Fast Food", "Iranian"};
    private String[] statusList = {"Available", "Unavailable"};

    public ObservableList<productData> menuGetOrder() {
        customerID();
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id = " + cID;

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;

            while (result.next()) {
                prod = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<productData> menuOrderListData;

    private ObservableList<productData> cardListData = FXCollections.observableArrayList();

    @FXML
    void chargeaccount(ActionEvent event) throws SQLException {
        double amountToAdd = Double.parseDouble(Baladd.getText());
        connect = database.connectDB();
        String sql = "UPDATE employee SET Balance = Balance + ? WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(2, DocumentController.user);
        preparedStatement.setString(3, DocumentController.pass);
        preparedStatement.setString(1, String.valueOf(amountToAdd));
        System.out.println(DocumentController.user+amountToAdd+DocumentController.pass);
       preparedStatement.executeUpdate();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText(amountToAdd+"$ Added to your balance");
            alert.showAndWait();
            Baladd.setText("");
    }

    @FXML
    void ShowB(ActionEvent event) throws SQLException {


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
                alert.setContentText("Balance:"+balance+"$");
                alert.showAndWait();
            } else {
                System.out.println("User not found.");
            }
    }
    @FXML
    private Button ShowB;
    @FXML
    public void logout() {

        try {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {


                logout_btn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("MAin"+Foodi.Themetype+".fxml"));

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
    public void menuAmount() {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(menu_amount.getText());
            if (amount < totalP) {
                menu_amount.setText("");
            } else {
                change = (amount - totalP);
                menu_change.setText("$" + change);
            }
        }
    }

    public  void menuShowOrderData() {
        menuOrderListData = menuGetOrder();

        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(menuOrderListData);
    }

    @FXML
    public void menuPayBtn() {

        if (totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username,Resname) "
                    + "VALUES(?,?,?,?,?)";

            connect = database.connectDB();

            try {

                if (amount == 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Messaged");
                    alert.setHeaderText(null);
                    alert.setContentText("Something wrong :3");
                    alert.showAndWait();
                } else {
                    Parent root = FXMLLoader.load(getClass().getResource("Post2.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setTitle("Foodi");
                    stage.setScene(scene);
                    stage.showAndWait();
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, DocumentController.user);
                        prepare.setString(5, resselect);
                        prepare.executeUpdate();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful.");
                        alert.showAndWait();

                        menuShowOrderData();

                    } else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled.");
                        alert.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("$0.0");
        menu_amount.setText("");
        menu_change.setText("$0.0");
    }

    @FXML
    public void menuReceiptBtn() {

        if (totalP == 0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            HashMap map = new HashMap();
            map.put("getReceipt", (cID - 1));

            try {

                JasperDesign jDesign = JRXmlLoader.load(
                        "F:\\Projects\\Intellij IDEA\\ProjectGraphic\\src\\main\\resources\\com\\example\\projectgraphic\\report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

                JasperViewer.viewReport(jPrint, false);

                menuRestart();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public ObservableList<customersData> customersDataList() {
        ObservableList<customersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt ";
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
         //   prepare.setString(1, DocumentController.restname);
            result = prepare.executeQuery();
            customersData cData;

            while (result.next()) {
                cData = new customersData(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"),
                        result.getString("Resname"));


                listData.add(cData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<customersData> customersListData;


    @FXML
    public void menuRemoveBtn() {

        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to remove");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = database.connectDB();
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void menuSelectOrder() {
        productData prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();

    }


    private ObservableList<productData> inventoryListData;

    public ObservableList<productData> inventoryDataList() {

        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prodData;

            while (result.next()) {

                prodData = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prodData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }


    public ObservableList<productData> menuGetData() {
        connect = database.connectDB();
        String sql = "SELECT * FROM product WHERE Resname = ? ";


        ObservableList<productData> listData = FXCollections.observableArrayList();

        try {
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,resselect);
            System.out.println(resselect);
            result = prepare.executeQuery();

            productData prod;

            while (result.next()) {
                prod = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }
    @FXML
    private Button menu_btn;
    @FXML
    private AnchorPane customers_form;
    @FXML
    private Button customers_btn;
    @FXML
    private TableColumn<?, ?> customers_col_customerID;
    @FXML
    private TableColumn<?, ?> customers_col_total;
    @FXML
    private TableColumn<?, ?> customers_col_date;
    @FXML
    private TableColumn<?, ?> customers_col_cashier;
    @FXML
    private TableView<customersData> customers_tableView;
    @FXML
    private AnchorPane menu_form;
    @FXML
    private ListView<String> listView;
    @FXML
    private AnchorPane Restaurantlist;
    @FXML
    private Label selection;
    @FXML
    private TableColumn<?, ?> Rescol;

    public   static String  resselect;
    private List<String> searchList(String searchWords, List<String> listOfStrings) {

        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }
    @FXML
    void search(ActionEvent event) {
        listView.getItems().clear();
        listView.getItems().addAll(searchList(Ressearch.getText(), Foodi.serching));
    }
    private void selectionChanged(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
        String getSelectedItem = (selectedItems.isEmpty())?"No Selected Item":selectedItems.toString();
        selection.setText(getSelectedItem);
        resselect = listView.getSelectionModel().getSelectedItem();
        //System.out.println(resselect);
    }

    @FXML
    public void switchForm(ActionEvent event) {

         if (event.getSource() == menu_btn) {
             Restaurantlist.setVisible(true);
             customers_form.setVisible(false);
            // listView.getItems().clear();


             if(resselect != null){
                 menuDisplayCard();
                 menuDisplayTotal();
                 menuShowOrderData();
                 Restaurantlist.setVisible(false);
                 System.out.println(resselect);
             }

        } else if (event.getSource() == customers_btn) {
            System.out.println("customers_btn");
             Restaurantlist.setVisible(false);
             customers_form.setVisible(true);
             customersShowData();
         }

    }
    public void customersShowData() {
        customersListData = customersDataList();

        //customers_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customers_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Rescol.setCellValueFactory(new PropertyValueFactory<>("Resname"));


        customers_tableView.setItems(customersListData);
    }
    @FXML
    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();


        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(mainformController.class.getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                com.example.projectgraphic.cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void customerID() {

        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("$" + totalP);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listView.getItems().addAll(Foodi.serching);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);

    }


}