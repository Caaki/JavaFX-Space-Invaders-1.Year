package sample;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Collections;

public class Chat extends Application {

    public class Client {
        private Socket socket;
        private BufferedWriter bufferedWriter;
        private BufferedReader bufferedReader;
        private String username;

        public Client(Socket socket, String username) {

            try {
                this.socket = socket;
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.username = username;
            } catch (IOException e) {
                zatvoriSve(socket, bufferedReader, bufferedWriter);
            }
        }

        public void posaljiPoruku() {
            try {
                    bufferedWriter.write(username + ": " +tf.getText().toString());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    chat.appendText(username +": " + tf.getText()+"\n");
                    tf.clear();
            } catch (IOException e) {
                zatvoriSve(socket, bufferedReader, bufferedWriter);
            }
        }

        public void listenForMessage() {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String poruka;
                    while (socket.isConnected()) {
                        try {
                            poruka = bufferedReader.readLine();
                            chat.appendText(poruka+"\n");
                        } catch (IOException e) {
                            zatvoriSve(socket, bufferedReader, bufferedWriter);
                        }
                    }
                }
            }).start();
        }

        public void zatvoriSve(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static TextField tf = new TextField();
    static TextArea chat = new TextArea();
    private User u;
    static Label username = new Label();


    public void setU(User u) {
        this.u = u;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Socket socket = new Socket("localhost",1234);
        Client client = new Client(socket,u.getUsername());
        client.bufferedWriter.write(client.username);
        client.bufferedWriter.newLine();
        client.bufferedWriter.flush();
        client.listenForMessage();



        Stage window = new Stage();
        Scene scene;
        Pane pane = new Pane();

        username.setText(u.getUsername());
        username.setTextFill(Color.YELLOW);
        username.setFont(new Font("Arial",25));
        username.setLayoutX(20);
        username.setLayoutY(20);
        username.setVisible(true);
        pane.getChildren().add(username);


        Button btn = new Button("Send");
        Button back = new Button("Back");

        chat.setLayoutY(110);
        chat.setLayoutX(220);
        chat.setPrefHeight(380);
        chat.setMaxWidth(350);
        tf.setMinWidth(350);
        tf.setLayoutX(220);
        tf.setPromptText("Type message");

        btnset(btn);
        btnset(back);

        tf.setLayoutY(500);
        btn.setLayoutY(550);
        btn.setLayoutX(150);
        back.setLayoutY(550);
        back.setLayoutX(500);

        pane.getChildren().add(chat);
        pane.getChildren().add(tf);
        pane.getChildren().add(back);
        pane.getChildren().add(btn);
        pane.setBackground(
                new Background(
                        Collections.singletonList(new BackgroundFill(
                                Color.WHITE,
                                new CornerRadii(500),
                                new Insets(10))),
                        Collections.singletonList(new BackgroundImage(
                                new Image("UserChat9001.png", 800, 630, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT))));

        scene = new Scene(pane, 800, 630);
        window.setScene(scene);
        window.getIcons().add(new Image("PLayer.png"));
        window.show();



        btn.setOnAction(e -> {
            client.posaljiPoruku();
        });

        back.setOnAction(e ->{
            try {
                this.stop();
                window.close();


            } catch (Exception exception) {
                client.zatvoriSve(socket, client.bufferedReader, client.bufferedWriter);
            }


        });

    }
    public static void main(String[] args) {
        launch(args);
    }

    public void btnset(Button btn){
        btn.setMinWidth(150);
        btn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY,Insets.EMPTY)));
        btn.setTextFill(Color.WHITE);
        btn.setFont(Font.font(20));
        btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-border-color: white; -fx-border-width: 3px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;"));

    }


}
