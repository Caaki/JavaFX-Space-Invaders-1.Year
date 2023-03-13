package sample;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScores {



    public static void display(){
        Button btn = new Button("Back");

        btn.setLayoutX(225);
        btn.setLayoutY(700);
        btn.setMinWidth(150);
        btn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY,Insets.EMPTY)));
        btn.setTextFill(Color.WHITE);
        btn.setFont(Font.font(20));
        btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-border-color: white; -fx-border-width: 3px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;"));

        Stage window = new Stage();
        Scene scene;
        Pane pane = new Pane();
        ListView lv = new ListView();
        pane.getChildren().add(btn);
        pane.setBackground(
                new Background(
                        Collections.singletonList(new BackgroundFill(
                                Color.WHITE,
                                new CornerRadii(500),
                                new Insets(10))),
                        Collections.singletonList(new BackgroundImage(
                                new Image("meni.jpg", 600, 800, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT))));

        //============================Uzima sve korisnike i prikazuje njihove scorove============================
        List<User> korisnici = new ArrayList<>();

        Runnable r = ()->{

            korisnici.addAll(Database.allUsers().getUsers());
            lv.getItems().addAll(korisnici);
        };

        Thread th = new Thread(r);
        th.run();



        lv.setLayoutY(350);
        lv.setLayoutX(150);

        lv.setPrefHeight(320);
        lv.setPrefWidth(300);

        pane.getChildren().add(lv);

        btn.setOnAction(e ->{
            window.close();
        });

        scene = new Scene(pane,600,800);
        window.setScene(scene);
        window.setTitle("High Scores");
        window.getIcons().add(new Image("PLayer.png"));
        window.showAndWait();

    }

}
//        korisnici.addAll(Database.allUsers().getUsers());
//        lv.getItems().addAll(korisnici);