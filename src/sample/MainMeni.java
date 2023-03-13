package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Scanner;

public class MainMeni extends Application {

    static MediaPlayer mediaPlayer;

    Stage window;
    Scene scene;
    Pane pane = new Pane();
    GridPane gp = new GridPane();
    Button btn = new Button("Login");
    Button btn2 = new Button("Register");
    Button btn3 = new Button("High Scores");
    Button btn4 = new Button("Exit");
    Button btn5 = new Button("Single Player");
    Button btn6 = new Button("Chat");
    Button btn7 = new Button("Options");
    User player1;
    Label povezaniUser = new Label();
    boolean povezan = false;

    public static void main(String[] args) {
        launch(args);
    }

    //========================Metoda za setovanje dugmica=================================
    public static void btnset(Button btn){
        btn.setMinWidth(150);
        btn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY,Insets.EMPTY)));
        btn.setTextFill(Color.WHITE);
        btn.setFont(Font.font(20));
        btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-border-color: white; -fx-border-width: 3px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;"));

    }
    //========================Music player=================================
    static public void music(String s){
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setVolume(0.51);
        mediaPlayer.play();
    }

    @Override
    public void start(Stage primaryStage) {


        String s = "MusicMain.mp3";
        music(s);
        btnset(btn);
        btnset(btn2);
        btnset(btn3);
        btnset(btn4);
        btnset(btn5);
        btnset(btn6);
        btnset(btn7);
        gp.setHgap(30);
        gp.setVgap(30);

        gp.setAlignment(Pos.CENTER);

        gp.setTranslateX(220);
        gp.setTranslateY(400);
        gp.add(btn,0,0);
        gp.add(btn2,0,1);
        gp.add(btn3,0,2);
        gp.add(btn7,0,3);
        gp.add(btn4,0,4);
        pane.getChildren().add(gp);
        window = primaryStage;
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

//    //========================Log in forma vraca korisnika=================================
        btn.setOnMouseClicked(e -> {
            window.hide();

            player1 = LogIn.display();

            if (player1!=null) {
                System.out.println("evo ga "+player1.getUsername() +" u mainu");
                povezaniUser.setText(player1.getUsername());
                povezaniUser.setTextFill(Color.YELLOW);
                povezaniUser.setFont(new Font("Arial",25));
                povezaniUser.setLayoutX(20);
                povezaniUser.setLayoutY(20);
                povezaniUser.setVisible(true);
                pane.getChildren().add(povezaniUser);

                povezan = true;
                System.out.println(povezan);

                gp.getChildren().remove(btn);
                gp.getChildren().remove(btn2);

                gp.add(btn5,0,0);
                gp.add(btn6,0,1);
            }
            window.show();
        });

//==============================Igrica==========================================
        btn5.setOnAction(e ->{
            window.hide();
            player1.setScore(0);
            if (povezan) {
               player1.setScore(SinglePlayer.display(player1));
            }
            Database.update(player1);
            GameOver.display(player1);
            player1.setScore(0);
            window.show();
            mediaPlayer.stop();
            music(s);
            mediaPlayer.setVolume(Options.volueme);
        });
//====================Pokrece klijenta a cet=======================
        btn6.setOnAction(e ->{
          Chat c = new Chat();
          c.setU(player1);
            try {
                c.start(new Stage());
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        });
//==================Registracija korisnika================================
        btn2.setOnAction(e ->{
            window.hide();
            Register.display();
            window.show();
        });

//============Labela sa imenom korisnika koja se isto koristi za logout===========================
        povezaniUser.setOnMouseClicked(e ->{
            player1 = null;
            povezaniUser.setText("");
            povezaniUser.setVisible(false);
            pane.getChildren().remove(povezaniUser);
            gp.getChildren().remove(btn5);
            gp.getChildren().remove(btn6);

            gp.add(btn,0,0);
            gp.add(btn2,0,1);
            btn.setVisible(true);
            povezan = false;
            System.out.println(povezan);

        });
//=====================Prikaz hight scorova=================================
        btn3.setOnAction(e ->{
            window.hide();
            HighScores.display();
            window.show();
        });

//==================Ovde se podesava jacina zvuka==========================
        btn7.setOnAction(e ->{
            Options.display();
        });
//======================Zatvara aplikaciju=============================
        btn4.setOnAction(e ->{

            window.close();
            System.exit(0);
        });

        scene = new Scene(pane,600,800);
        window.setScene(scene);
        window.setTitle("v6");
        window.show();
        window.getIcons().add(new Image("PLayer.png"));

    }
}
