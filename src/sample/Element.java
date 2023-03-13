package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Element extends ImageView {

    private boolean dead = false;
    private String type;

    Element(Image image){
        super(image);

        this.type = "Element";
        setTranslateX(0);
        setTranslateY(740);
        setFitHeight(50);
        setFitWidth(50);

    }

    public Element(Image image, boolean dead, String type) {
        super(image);
        this.dead = dead;
        this.type = type;
    }

    public Element() {
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

    public void setType(String type) {
        this.type = type;
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
}

