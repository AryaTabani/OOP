package com.example.projectgraphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Foodi extends Application {

    public static List<String> items = new ArrayList<>();
    public static ArrayList<String> serching =new ArrayList<>();


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    public static String Themetype = "";

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Foodi.class.getResource("Theme.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Foodi");
        stage.setScene(scene);
        stage.show();
        String query = "SELECT ResName FROM restaurant";
        connect = database.connectDB();
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        {
            int rowCount = 0;
            while (resultSet.next()) {
                String resname = resultSet.getString("ResName");
                items.add(resname);
                serching.add(resname);
            }

            for (String resname : items) {
                System.out.println(resname);
            }

        }
    }


    public static void main(String[] args) {
        launch();
    }
}

