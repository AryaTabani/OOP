package com.example.projectgraphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class Post implements Initializable {

    @FXML
    private TextField Src;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    @FXML
    private TextField Dst;

    @FXML
    private Button continuebtn;

    @FXML
    private Button continuebtn1;

    @FXML
    private TextField estimtime;

    @FXML
    private TextField shortestpath;



    @FXML
    void Continue(ActionEvent event) throws SQLException {
        String postcontent = " INSERT INTO postman (orper,Src, Dst, EstimatedTIme,Shortestpath) VALUES(?,?,?,?,?)";
        prepare = connect.prepareStatement(postcontent);
        prepare.setString(1, DocumentController.user);
        prepare.setString(2, Src.getText());
        prepare.setString(3, Dst.getText());
        prepare.setString(4, estimtime.getText());
        prepare.setString(5, shortestpath.getText());
        prepare.executeUpdate();
        continuebtn.getScene().getWindow().hide();
        CustomManager.farayand = true;
    }
    @FXML
    void calculate(ActionEvent event) {
        ShortestPathFinder spf = null;
        try {
            spf = new ShortestPathFinder("F:/Projects/Intellij IDEA/ProjectGraphic/src/main/java/com/example/projectgraphic/graph.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int m = Integer.parseInt(Src.getText());
        int n = Integer.parseInt(Dst.getText());
        int shortestDist = spf.findShortestPath(m-1, n-1);
        List<Integer> shortestPath = spf.getShortestPath(m-1, n-1);
        System.out.println("Shortest distance: " + shortestDist+" km");
        System.out.println("Shortest path: " + shortestPath.toString());
        Timer timer =new Timer(0,shortestDist*2,shortestDist*2);
        estimtime.setText((String) timer.initializeTimer());
        shortestpath.setText(shortestPath.toString());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int Location =0;
        String sql = "SELECT ResLocation FROM restaurant WHERE ResName = ?";
        connect = database.connectDB();
        PreparedStatement statement = null;
        try {
            statement = connect.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.setString(1, CustomManager.resselect);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (resultSet.next()) {
                 Location = Integer.parseInt(resultSet.getString("ResLocation"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Src.setText(String.valueOf(Location));
        System.out.println(Location);

    }
}