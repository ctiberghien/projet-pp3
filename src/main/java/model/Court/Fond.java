package model.Court;

import javafx.scene.shape.Line;

public class Fond {

    public static Line h1 = new Line();
    public static Line h2 = new Line();
    public static Line h3 = new Line();

    public static Line v1 = new Line();
    public static Line v2 = new Line();
    public static Line v3 = new Line();
    public static Line v4 = new Line();
    public static Line v5 = new Line();
   
    public static void setTerrain() {
        
        h1.setStartX(80);
        h1.setStartY(110);
        h1.setEndX(1020);
        h1.setEndY(110);
        
        h2.setStartX(270);
        h2.setStartY(300);
        h2.setEndX(830);
        h2.setEndY(300);

        h3.setStartX(80);
        h3.setStartY(490);
        h3.setEndX(1020);
        h3.setEndY(490);

        v1.setStartX(80);
        v1.setStartY(0);
        v1.setEndX(80);
        v1.setEndY(600);

        v2.setStartX(270);
        v2.setStartY(110);
        v2.setEndX(270);
        v2.setEndY(490);

        v3.setStartX(550);
        v3.setStartY(0);
        v3.setEndX(550);
        v3.setEndY(600);

        v4.setStartX(830);
        v4.setStartY(110);
        v4.setEndX(830);
        v4.setEndY(490);

        v5.setStartX(1020);
        v5.setStartY(0);
        v5.setEndX(1020);
        v5.setEndY(600);
        
    }
}
