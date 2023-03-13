package sample;

import javafx.scene.image.Image;

public class Metak extends Element{
    private int lvl;


    public Metak(int x, int y,int lvl,String type) {
        super(new Image("beams" + lvl + ".png"),false,type+"bullet");

        setTranslateX(x+15);
        setTranslateY(y);
        setFitHeight(25);
        setFitWidth(15);
        this.lvl = lvl;
    }

    public Metak() {
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    void moveUp(){
        setTranslateY(getTranslateY()-3);
    }
    void moveDown(){
        setTranslateY(getTranslateY()+3);
    }
    public void dead(){
        setDead(true);
    }

}