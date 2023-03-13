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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Objects;

public class Register {
    private static User player;
    private static ConObjekat user;
    private String username;
    private String password;




    public static void display(){


        TextField username = new TextField();
        PasswordField password = new PasswordField();
        PasswordField coifirmpassword = new PasswordField();

        username.setPromptText("Username");
        password.setPromptText("Password");
        coifirmpassword.setPromptText("Confirm password");

        Pane pane = new Pane();
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Log in");
        Label label = new Label("Username");
        Label label2 = new Label("Password");
        Label label3 = new Label("Confirm password");

        Button btn1 = new Button("Register");
        Button btn2 = new Button("Back");



        label.setLayoutX(50);
        label.setLayoutY(290);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("Arial",20));

        label2.setLayoutX(50);
        label2.setLayoutY(340);
        label2.setTextFill(Color.WHITE);
        label2.setFont(new Font("Arial",20));

        label3.setLayoutX(50);
        label3.setLayoutY(390);
        label3.setTextFill(Color.WHITE);
        label3.setFont(new Font("Arial",20));

        username.setLayoutX(225);
        username.setLayoutY(290);
        username.setPrefWidth(150);

        password.setLayoutX(225);
        password.setLayoutY(340);
        password.setPrefWidth(150);

        coifirmpassword.setLayoutX(225);
        coifirmpassword.setLayoutY(390);
        coifirmpassword.setPrefWidth(150);

        btn1.setLayoutX(225);
        btn1.setLayoutY(440);
        btn1.setPrefWidth(70);

        btn2.setLayoutX(305);
        btn2.setLayoutY(440);
        btn2.setPrefWidth(70);

        pane.getChildren().add(label);
        pane.getChildren().add(label2);
        pane.getChildren().add(label3);
        pane.getChildren().add(coifirmpassword);
        pane.getChildren().add(username);
        pane.getChildren().add(password);
        pane.getChildren().add(btn1);
        pane.getChildren().add(btn2);


        btn1.setOnAction(e ->{
            if (username.getText().trim().isEmpty() || password.getText().trim().isEmpty() || coifirmpassword.getText().trim().isEmpty()){

                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Los unos");
                a.setHeaderText("Popunite sva polja!");
                a.showAndWait();

            }else if (username.getText().length() < 2){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Los unos");
                a.setHeaderText("Username mora da ima bar 2 karaktera");
                a.showAndWait();
            }else if (!password.getText().equals(coifirmpassword.getText())){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Los unos");
                a.setHeaderText("Los unos, nisu iste sifre");
                a.showAndWait();
            }else if(password.getText().length()< 4){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Los unos");
                a.setHeaderText("Sifra mora da ima bar 4 karaktera");
                a.showAndWait();
            }else if(username.getText().contains(" ")){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Los unos");
                a.setHeaderText("Username ne moze da sadrzi razmak");
                a.showAndWait();
            }else {

                try {

                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getText().getBytes(),0, password.getText().length());
                    String md5 = new BigInteger(1,md.digest()).toString(16);


                    User player = new User(username.getText(),md5);
                    boolean provera = Database.addUser(player.getUsername(),player.getPassword());
                    if (provera){
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setTitle("Registracija");
                        a.setHeaderText("Uspesno ste se registrovali");
                        a.showAndWait();
                        window.close();
                    }else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Registracija");
                        a.setHeaderText("Nije uspela registracija, pokusajte ponovo");
                        a.showAndWait();
                    }

                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }

//                User player = new User(username.getText(),password.getText());
//                boolean provera = Database.addUser(player.getUsername(),player.getPassword());
//                if (provera){
//                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
//                    a.setTitle("Registracija");
//                    a.setHeaderText("Uspesno ste se registrovali");
//                    a.showAndWait();
//                    window.close();
//                }else {
//                    Alert a = new Alert(Alert.AlertType.ERROR);
//                    a.setTitle("Registracija");
//                    a.setHeaderText("Nije uspela registracija, pokusajte ponovo");
//                    a.showAndWait();
//                }
            }

        });

        btn2.setOnAction(e ->{
            window.close();
        });

        pane.setBackground(
                new Background(
                        Collections.singletonList(new BackgroundFill(
                                Color.WHITE,
                                new CornerRadii(500),
                                new Insets(10))),
                        Collections.singletonList(new BackgroundImage(
                                new Image("Login.jpg", 500, 500, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT))));

        Scene scene = new Scene(pane,500,500);
        window.setScene(scene);
        window.setTitle("Register");
        window.getIcons().add(new Image("PLayer.png"));
        window.showAndWait();

    }
}
