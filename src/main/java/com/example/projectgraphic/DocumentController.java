package com.example.projectgraphic;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
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
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
//import java.sql.Date;
import java.util.*;
import java.util.Date;

public class DocumentController implements Initializable {

    @FXML
    private TextField fp_answer;
    @FXML
    private ComboBox<?> Typeselect;
    @FXML
    private ComboBox<?> Typeselect1;
    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private TextField fp_username;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;
    private String[] questionList = new String[]{"What is your favorite Color?", "What is your favorite food?",
            "what is your birth date?"};
    private String[] Type = new String[]{"Restaurant Owner", "Customer", "Postman"
    };
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    public static String user;
    public static String restname;
    public static String pass;

    @FXML
    public void regLquestionList() {
        List<String> listQ = new ArrayList();
        String[] var2 = this.questionList;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        this.su_question.setItems(listData);
    }

    public void Typelist() {
        List<String> listA = new ArrayList();
        String[] var2 = this.Type;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            listA.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listA);
        this.Typeselect.setItems(listData);
    }

    public void Typelist1() {
        List<String> listB = new ArrayList();
        String[] var2;
        var2 = this.Type;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            listB.add(data);
        }


    }

    public void backToLoginForm() {
        si_loginForm.setVisible(true);
        Typelist1();
        fp_questionForm.setVisible(false);
    }

    @FXML
    public void backToQuestionForm() {
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
        Typelist1();
    }

    @FXML
    public void changePassBtn() {

        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            if (np_newPassword.getText().equals(np_confirmPassword.getText())) {
                String getDate = "SELECT date FROM employee WHERE username = '"
                        + fp_username.getText() + "'";

                connect = database.connectDB();

                try {

                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();

                    String date = "";
                    if (result.next()) {
                        date = result.getString("date");
                    }

                    String updatePass = "UPDATE employee SET password = '"
                            + np_newPassword.getText() + "', question = '"
                            + fp_question.getSelectionModel().getSelectedItem() + "', answer = '"
                            + fp_answer.getText() + "', date = '"
                            + date + "' WHERE username = '"
                            + fp_username.getText() + "'";

                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully changed Password!");
                    alert.showAndWait();

                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);

                    // TO CLEAR FIELDS
                    np_confirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.setText("");
                    fp_username.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Not match");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void loginBtn() throws SQLException, IOException {

        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        } else {
            connect = database.connectDB();
            String selctData = "SELECT username, password  FROM employee WHERE username = ? and password = ? ";
            String selectData2 = "SELECT Type FROM employee WHERE username = ? and password = ?";
            String selectData3 = "SELECT Resname FROM restaurant WHERE Resowner = ?";
            PreparedStatement preparedStatement2 = connect.prepareStatement(selectData3);
            preparedStatement2.setString(1, si_username.getText());
            PreparedStatement preparedStatement = connect.prepareStatement(selectData2);
            preparedStatement.setString(1, si_username.getText());
            preparedStatement.setString(2, si_password.getText());
            user = si_username.getText();
            pass = si_password.getText();
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            String userType = "";
            //restname ="";
            if (resultSet.next()) {
                userType = resultSet.getString("Type");
                System.out.println(userType);
            }
            if (!resultSet2.next() && userType.equals("Restaurant Owner")) {
                //restname =resultSet2.getString("Resname");
                System.out.println("new");
                si_loginBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("Rescreate.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Foodi");
                stage.setScene(scene);
                stage.show();

            } else if (userType.equals("Restaurant Owner") && resultSet2.next()) {
                System.out.println("many res");
                si_loginBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("Restaurantselect.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Foodi");
                stage.setScene(scene);
                stage.show();
            } else {
                try {

                    prepare = connect.prepareStatement(selctData);
                    prepare.setString(1, si_username.getText());
                    prepare.setString(2, si_password.getText());
                    prepare.setString(1, si_username.getText());
                    prepare.setString(2, si_password.getText());
                    result = prepare.executeQuery();
                    if (result.next()) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Login!");
                        alert.showAndWait();
                        if (userType.equals("Restaurant Owner")) {
                            //Parent root = FXMLLoader.load(getClass().getResource("mainform" + Foodi.Themetype + ".fxml"));
                            Parent root = FXMLLoader.load(getClass().getResource("Restaurantselect.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setTitle("Foodi");
                            stage.setScene(scene);
                            stage.show();
                        } else if (userType.equals("Customer")) {

                            Parent root = FXMLLoader.load(getClass().getResource("Custom" + Foodi.Themetype + ".fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setTitle("Foodi");
                            stage.setMinWidth(1100);
                            stage.setMinHeight(600);
                            stage.setScene(scene);
                            stage.show();
                            String s= String.valueOf(getRandomChar("123"));
                            Parent root2 = FXMLLoader.load(getClass().getResource("offer"+s+".fxml"));
                            Stage stage2 = new Stage();
                            Scene scene2 = new Scene(root2);
                            stage2.setTitle("Specialoffer");
                            stage2.setScene(scene2);
                            stage2.show();
                        }else{
                            Parent root = FXMLLoader.load(getClass().getResource("Postman" + Foodi.Themetype + ".fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setTitle("Foodi");
                            stage.setMinWidth(1100);
                            stage.setMinHeight(600);
                            stage.setScene(scene);
                            stage.show();
                        }
                        si_loginBtn.getScene().getWindow().hide();

                    } else { // IF NOT, THEN THE ERROR MESSAGE WILL APPEAR
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Incorrect Username/Password");
                        alert.showAndWait();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static char getRandomChar(String s) {
        Random random = new Random();
        int length = s.length();
        int index = random.nextInt(length);
        return s.charAt(index);
    }
    @FXML
    public void proceedBtn() {

        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String selectData = "SELECT username, question, answer FROM employee WHERE username = ? AND question = ? AND answer = ?";
            connect = database.connectDB();

            try {

                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    public void regBtn() {

        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all blank fields");
            alert.showAndWait();
        } else {

            String regData = "INSERT INTO employee (username, password, question, answer, date,Type) VALUES(?,?,?,?,?,?)";
            connect = database.connectDB();
            try {
                String checkUsername = "SELECT username FROM employee WHERE username = '"
                        + su_username.getText() + "'";

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " is already taken");
                    alert.showAndWait();
                } else if (su_password.getText().length() < 8) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password, atleast 8 characters are needed");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());
                    prepare.setString(6, (String) Typeselect.getSelectionModel().getSelectedItem());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully registered Account!");
                    alert.showAndWait();

                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));

                    slider.setOnFinished((ActionEvent e) -> {
                        side_alreadyHave.setVisible(false);
                        side_CreateBtn.setVisible(true);
                    });

                    slider.play();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void forgotPassQuestionList() {

        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);

    }

    @FXML
    public void switchForgotPass() {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);

        forgotPassQuestionList();
    }

    @FXML
    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

                regLquestionList();
                Typelist();
                Typelist1();
            });

            slider.play();
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
                Typelist1();
            });

            slider.play();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
