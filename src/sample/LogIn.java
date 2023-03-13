package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Objects;

public class LogIn {
    private static User player;
    private static ConObjekat user;
    private String username;
    private String password;

    public static User display(){


        TextField username = new TextField();
        PasswordField password = new PasswordField();



        username.setPromptText("Username");
        password.setPromptText("Password");

        Pane pane = new Pane();
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login");
        Label label = new Label("Username");
        Label label2 = new Label("Password");

        Button btn1 = new Button("Login");
        Button btn2 = new Button("Back");

//        gp.add(btn1,0,0);

        label.setLayoutX(110);
        label.setLayoutY(240);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("Arial",20));

        label2.setLayoutX(110);
        label2.setLayoutY(290);
        label2.setTextFill(Color.WHITE);
        label2.setFont(new Font("Arial",20));

        username.setLayoutX(225);
        username.setLayoutY(240);
        username.setPrefWidth(150);

        password.setLayoutX(225);
        password.setLayoutY(290);
        password.setPrefWidth(150);

        btn1.setLayoutX(225);
        btn1.setLayoutY(335);
        btn1.setPrefWidth(70);

        btn2.setLayoutX(305);
        btn2.setLayoutY(335);
        btn2.setPrefWidth(70);

        pane.getChildren().add(label);
        pane.getChildren().add(label2);
        pane.getChildren().add(username);
        pane.getChildren().add(password);
        pane.getChildren().add(btn1);
        pane.getChildren().add(btn2);


        //==========================Login korisnika sa proverama=========================
        btn1.setOnAction(e ->{

            if (username.getText().trim().isEmpty()|| password.getText().trim().isEmpty()){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Los unos");
                a.setHeaderText("Popunite oba polja");
                a.showAndWait();
            }else if (username.getText().length() < 2|| password.getText().length() < 4){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Los unos");
                a.setHeaderText("password mora imati bar 4 karaktera");
                a.showAndWait();
            }else{
                Thread th;
                Runnable r = ()-> {
                    try {

                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(password.getText().getBytes(),0, password.getText().length());
                        String md5 = new BigInteger(1,md.digest()).toString(16);
                        user = Database.getPlayer(username.getText(),  md5);

                    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        noSuchAlgorithmException.printStackTrace();
                    }

                };
                th = new Thread(r);
                th.run();
                if (user.getUser() == null && user.getException() == null){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Los unos");
                    a.setHeaderText("Pogresna sifra ili username");
                    a.showAndWait();
                }else if (user.getUser() !=null){

                    System.out.println("Dobrodosli "+ user.getUser().getUsername());
                    player = user.getUser();
                    window.close();


                }else {

                    System.out.println("GRESKA");
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Greska sa mrezom");
                    a.setHeaderText("Problem pri povezivanju pokusajte ponovo");
                    a.showAndWait();
                    System.out.println(user.getException());
                }
                th.stop();
            }

        });
//==========================zatvara prozor=========================
        btn2.setOnAction(e ->{
            player = null;
            window.close();
        });


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


        return player;
    }
}
