package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends Element {
    private User user;
    private boolean dead = false;
    private String type;
    private int Score;

    Player(Image image,User user){
        super(image);
        this.user = user;
        this.type = "player";
        setTranslateX(0);
        setTranslateY(740);
        setFitHeight(50);
        setFitWidth(50);

    }

    public Player(User user) {
        super(new Image("player.png"));
        this.user = user;
        this.type = "player";
        setTranslateX(0);
        setTranslateY(740);
        setFitHeight(50);
        setFitWidth(50);

    }

    public Player(Image image, boolean dead, String type) {
        super(image);
        this.dead = dead;
        this.type = type;
    }

    public Player() {
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getType() {
        return type;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public int finalScore(int a){
        if (a <0){
            return -1;
        }else {
            int f = a * 3;
            this.Score = f;
            return f;
        }
    }

    public Metak shoot(){
        if (Score > 50  ){
            Metak m = new Metak((int)getTranslateX()-10,(int)getTranslateY(),1,type);
            m.setFitWidth(35);
            m.setFitHeight(55);
            return m;
        }
        else {
            Metak m = new Metak((int)getTranslateX(),(int)getTranslateY(),2,type);
            return m;
        }
    }

    void moveUp(){
        setTranslateY(getTranslateY()-5);
    }
    void moveDown(){
        setTranslateY(getTranslateY()+5);
    }
    public void dead(){
        setDead(true);
    }

    @Override
    public String toString() {
        return user.getUsername() + "(" + user.getHighScore()+")";
    }
}

