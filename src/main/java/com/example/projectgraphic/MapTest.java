package com.example.projectgraphic;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class MapTest {

    private Pane pane;

    public static int i = 28;


    public MapTest(int D, int S) throws IOException {
        start(S, D);
    }

    public void start(int S, int D) throws IOException {
        Stage stage = new Stage();
        stage.setMaximized(true);
        pane = FXMLLoader.load(getClass().getResource("/com/example/projectgraphic/mapTest.fxml"));
        Paint paint = new ImagePattern(new Image(getClass().getResource("/com/example/projectgraphic/map.PNG").toExternalForm()));
        BackgroundFill backgroundFill = new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY);
        pane.setBackground(new Background(backgroundFill));
        pane.setPickOnBounds(true);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(10), e -> {
            ArrayList<Integer> newbie = Map.dijkstraPrinter(S, D);
            Random random = new Random();
            for (int i = 0; i < newbie.size() - 1; i++) {
                Line line = new Line(Map.getMapMatrixX()[newbie.get(i) - 1], Map.getMapMatrixY()[newbie.get(i) - 1],
                        Map.getMapMatrixX()[newbie.get(i + 1) - 1], Map.getMapMatrixY()[newbie.get(i + 1) - 1]);
                int colorIndex = random.nextInt(3);
                switch (colorIndex) {
                    case 0:
                        line.setStroke(Color.ORANGE);
                        break;
                    case 1:
                        line.setStroke(Color.RED);
                        break;
                    case 2:
                        line.setStroke(Color.BLUE);
                        break;
                }
                line.setStrokeWidth(7);
                pane.getChildren().addAll(line);
                ImageView startImageView = new ImageView();
                startImageView.setImage(new Image("F:/Projects/Intellij IDEA/ProjectGraphic/src/main/java/com/example/projectgraphic/RESTAURANT.png"));
                startImageView.setX(Map.getMapMatrixX()[newbie.get(0) - 1] - startImageView.getFitWidth() / 2);
                startImageView.setY(Map.getMapMatrixY()[newbie.get(0) - 1] - startImageView.getFitHeight() / 2);
                pane.getChildren().add(startImageView);
                ImageView endImageView = new ImageView();
                endImageView.setImage(new Image("F:/Projects/Intellij IDEA/ProjectGraphic/src/main/java/com/example/projectgraphic/CUSTOMER.png"));
                endImageView.setX(Map.getMapMatrixX()[newbie.get(newbie.size() - 1) - 1] - endImageView.getFitWidth() / 2);
                endImageView.setY(Map.getMapMatrixY()[newbie.get(newbie.size() - 1) - 1] - endImageView.getFitHeight() / 2);
                pane.getChildren().add(endImageView);
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        if (stage.getScene() == null) {
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        } else stage.getScene().setRoot(pane);
        stage.show();
    }

    @FXML
    public void initialize() throws Exception {
        Circle circle = new Circle();
        circle.setCenterX(50);
        circle.setCenterY(50);
        circle.setRadius(5);
    }
}
