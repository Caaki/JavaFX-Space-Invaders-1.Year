package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Collections;

public class Options {

        static double volueme;

    public static void btnset(Button btn){
        btn.setMinWidth(100);
        btn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY,Insets.EMPTY)));
        btn.setTextFill(Color.WHITE);
        btn.setFont(Font.font(20));
        btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-border-color: white; -fx-border-width: 3px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;"));

    }



    public static void display(){



        Pane pane = new Pane();
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Options");
        Label l = new Label();

        l.setTextFill(Color.YELLOW);
        l.setFont(new Font("Arial",35));
        l.setLayoutX(20);
        l.setLayoutY(20);
        l.setVisible(true);
        pane.getChildren().add(l);

        l.setText(String.valueOf( Integer.valueOf((int) (MainMeni.mediaPlayer.getVolume() *10))));
        l.setLayoutY(225);
        l.setLayoutX(230);


        Button btn1 = new Button("Volume+");
        Button btn2 = new Button("Volume-");
        Button btn3 = new Button("Save");

        btnset(btn1);
        btnset(btn2);
        btnset(btn3);

        btn2.setLayoutX(60);
        btn2.setLayoutY(300);

        //==========================Menja jacinu zvuka u aplikaciji=========================

        btn1.setOnAction(e ->{

            if (MainMeni.mediaPlayer.getVolume()<0.91){
                MainMeni.mediaPlayer.setVolume(MainMeni.mediaPlayer.getVolume()+0.1);
            }
            l.setText(String.valueOf( Integer.valueOf((int) (MainMeni.mediaPlayer.getVolume() *10))));
            volueme = MainMeni.mediaPlayer.getVolume();
        });

        btn2.setOnAction(e ->{

            if (MainMeni.mediaPlayer.getVolume()>0.10){
                MainMeni.mediaPlayer.setVolume(MainMeni.mediaPlayer.getVolume()-0.1);
            }

            l.setText(String.valueOf( Integer.valueOf((int) (MainMeni.mediaPlayer.getVolume() *10))));
            volueme = MainMeni.mediaPlayer.getVolume();
        });

        btn3.setOnAction(e ->{
            window.close();
        });


        btn1.setLayoutX(200);
        btn1.setLayoutY(300);

        btn3.setLayoutX(340);
        btn3.setLayoutY(300);


        pane.getChildren().add(btn1);
        pane.getChildren().add(btn2);
        pane.getChildren().add(btn3);


        pane.setBackground(
                new Background(
                        Collections.singletonList(new BackgroundFill(
                                Color.WHITE,
                                new CornerRadii(500),
                                new Insets(10))),
                        Collections.singletonList(new BackgroundImage(
                                new Image("Login.jpg", 500, 400, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT))));

        Scene scene = new Scene(pane,500,400);
        window.setScene(scene);
        window.getIcons().add(new Image("PLayer.png"));
        window.showAndWait();

    }
}


