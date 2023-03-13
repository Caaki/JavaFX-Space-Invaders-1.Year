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

public class GameOver {
    private static User player;

    public static User display(User user){
        String s = "GameOver.mp3";
        MainMeni.music(s);
        MainMeni.mediaPlayer.setVolume(Options.volueme);


        Pane pane = new Pane();
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Over");

        Label label = new Label("Score: " +(int) user.getScore());

        Button btn = new Button("Back");

        btn.setLayoutY(335);
        btn.setLayoutX(175);
        btn.setMinWidth(150);
        btn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY,Insets.EMPTY)));
        btn.setTextFill(Color.WHITE);
        btn.setFont(Font.font(20));
        btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-border-color: white; -fx-border-width: 3px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #181717; -fx-border-width: 3px; -fx-border-color: transparent;"));


        label.setLayoutX(190);
        label.setLayoutY(290);
        label.setTextFill(Color.YELLOW);
        label.setFont(new Font("Arial",30));


        pane.getChildren().add(label);
        pane.getChildren().add(btn);



        btn.setOnAction(e ->{
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
                                new Image("GameOver.jpg", 500, 400, false, true),
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
