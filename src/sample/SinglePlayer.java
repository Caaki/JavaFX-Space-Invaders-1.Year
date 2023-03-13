package sample;

import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SinglePlayer {

    private static User user;
    private static Player p;
    private static Pane pane;
    private static Stage window;
    private static Scene scene;
    private static double t;
    private static double t2;
    static Label povezaniUser;
    static Label povezaniUserscore;
    static AnimationTimer timer;
    static Thread th;

    public static int display(User user1) {

        //==========================Menja se pesma za igru=========================
        MainMeni.mediaPlayer.stop();
        String s = "InGameMusic.mp3";
        MainMeni.music(s);
        MainMeni.mediaPlayer.setVolume(Options.volueme);


        window = new Stage();
        user = user1;
        p = new Player(user);
        p.setScore(0);

        pane = new Pane();
        List<Enemy> enemies = new ArrayList<>();
        scene = new Scene(pane, 600, 800);
        povezaniUser = new Label();
        povezaniUser.setText(user.getUsername());
        povezaniUser.setTextFill(Color.YELLOW);
        povezaniUser.setFont(new Font("Arial",25));
        povezaniUser.setLayoutX(20);
        povezaniUser.setLayoutY(20);
        povezaniUser.setVisible(true);
        pane.getChildren().add(povezaniUser);

        povezaniUserscore = new Label();
        povezaniUserscore.setText("Kills:  " + String.valueOf((int)   user.getScore()));
        povezaniUserscore.setTextFill(Color.YELLOW);
        povezaniUserscore.setFont(new Font("Arial",25));
        povezaniUserscore.setLayoutX(450);
        povezaniUserscore.setLayoutY(20);
        povezaniUserscore.setVisible(true);
        pane.getChildren().add(povezaniUserscore);

        //==========================Konstantan update sadrzaja=========================
        Runnable r = () -> {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();
                }
            };
            timer.start();

            scene.setOnMouseMoved(e -> {
                if (!p.isDead()) {
                    p.setTranslateX(e.getX() - 25);
                }
            }); 
            scene.setOnKeyReleased(   e -> {
                if (e.getCode()== KeyCode.SPACE) {
                    if (!p.isDead()) {
                        pane.getChildren().add(p.shoot());
                    }
                }
            });


        };

        th = new Thread(r);
        th.start();

        pane.setBackground(
                new Background(
                        Collections.singletonList(new BackgroundFill(
                                Color.WHITE,
                                new CornerRadii(500),
                                new Insets(10))),
                        Collections.singletonList(new BackgroundImage(
                                new Image("univ.jpeg", 600, 800, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT))));
        pane.getChildren().add(p);



        window.setTitle("V6");
        window.setScene(scene);
        window.getIcons().add(new Image("PLayer.png"));
        window.showAndWait();

        //==========================Vraca score=========================
        return (int) user.getScore();
        }

    //==========================Uzima sve Objekte odredjenog tipa=========================

    private static List<Enemy> enemies() {
        List<Object> prob = pane.getChildren().stream().map(n -> (Object) n).collect(Collectors.toList());
        List<Enemy> nep = new ArrayList<>();
        for (Object p : prob) {
            if (p instanceof Enemy) {
                nep.add((Enemy) p);
            }
        }
        return nep;
    }
    private static List<Metak> meci() {
        List<Object> prob = pane.getChildren().stream().map(n -> (Object) n).collect(Collectors.toList());
        List<Metak> nep = new ArrayList<>();
        for (Object p : prob) {
            if (p instanceof Metak) {
                nep.add((Metak) p);
            }
        }
        return nep;
    }

    //==========================Update koji obnavlja sadrzaj igre tako sto menja sadrzaj=========================
    public static void update() {
        t2 = 1;
        t += 0.016;
        if (p.getScore() < 40) {
            if (enemies().size() < 10) {
                pane.getChildren().add(new Enemy((20 + (int) (Math.random() * 550))));
            }
        }
        else if (p.getScore() < 90 && p.getScore() >= 40) {
            if (enemies().size() < 15) {
                pane.getChildren().add(new Enemy((20 + (int) (Math.random() * 550))));
            }
        }
        else  if (p.getScore() < 150 && p.getScore() >= 90){
            if (enemies().size() < 20) {
                pane.getChildren().add(new Enemy((20 + (int) (Math.random() * 550))));
            }
        }else{
            if (enemies().size() < 25) {
                pane.getChildren().add(new Enemy((20 + (int) (Math.random() * 550))));
            }
        }

        if (p.isDead()) {
            p.setDead(false);
            user.setScore(p.finalScore(p.getScore()));
            p.setTranslateX(-100);
            pane.getChildren().remove(p);
            timer.stop();
            th.stop();
            window.close();


        }

        enemies().forEach(s -> {
            if (t >=  2) {
                if (Math.random() < 0.3) {
                    pane.getChildren().add(((Enemy) s).shoot2());
                }
            }
            if (t2 > 0) {
                if (Math.random() < 1) {
                     s.setTranslateY(s.getTranslateY() + 1);
                }
            }
            if (s.getBoundsInParent().intersects(p.getBoundsInParent())) {
                p.dead();
                s.dead();
            }
            if (s.getTranslateY() > 800) {
                s.dead();
            }
        });


        meci().forEach(s -> {

            if (s instanceof Metak) {
                if (s.getType().equals("playerbullet")) {
                    s.moveUp();
                    enemies().stream().filter(e -> e.getType().equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead();
                            s.dead();
                            p.setScore(p.getScore()+1);
                            user.setScore(p.getScore());
                            povezaniUserscore.setText("Kills:  " + ((int)   user.getScore()));
                        }
                    });
                } else if (s instanceof Metak) {
                    if (s.getType().equals("enemybullet")) {
                        s.moveDown();
                        if (s.getBoundsInParent().intersects(p.getBoundsInParent())) {
                            p.dead();
                            s.dead();
                        }
                    }
                }
            }
            if (s.getTranslateY() > 800 || s.getTranslateY() < 0) {
                s.dead();
            }
        });

        pane.getChildren().removeIf(n -> {
            if (n instanceof Element) {
                Element s = (Element) n;
                return s.isDead();
            } return false;
        });

        enemies().forEach(enemy -> {
            if (enemy.isDead()) {
                enemies().remove(enemy);
            }
        });
        if (t > 2) {
            t = 0;
        }

    }


}
