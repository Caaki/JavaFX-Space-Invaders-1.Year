package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy extends Element {

    public Enemy(Image image) {
        super(image, false, "enemy");

        setTranslateX(0);
        setTranslateY(0);
        setFitHeight(35);
        setFitWidth(35);
    }

    public Enemy() {
        super(new Image("enemy" + (int)(Math.random()*3 +1) + ".png"),false,"enemy");

        setTranslateX(0);
        setTranslateY(0);
        setFitHeight(35);
        setFitWidth(35);
    }
    public Enemy(int y) {
        super(new Image("enemy" + (int)(Math.random()*3 +1) + ".png"),false,"enemy");

        setTranslateX(y);
        setTranslateY(0);
        setFitHeight(35);
        setFitWidth(35);
    }
    void moveUp(){
        setTranslateY(getTranslateY()-5);
    }
    void moveDown2(){
        setTranslateY(getTranslateY()+1);
    }
    public void dead(){
        setDead(true);
    }
    public Metak shoot2() {

        Metak m = new Metak((int) getTranslateX(), (int) getTranslateY(), 3, getType());
        return m;

    }
}