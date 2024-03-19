package model.Ultimate;
import javafx.scene.shape.*;
import java.util.LinkedList;

@SuppressWarnings("FieldMayBeFinal")
public class WallUlt extends Ultimate{

    private LinkedList<Rectangle> walls;
    private LinkedList<Rectangle> wallsToRemove;

    public WallUlt() {
        walls = new LinkedList<>();
        wallsToRemove = new LinkedList<>();
    }

    public LinkedList<Rectangle> getWalls() {
        return walls;
    }
    public LinkedList<Rectangle> getWallsToRemove() {
        return wallsToRemove;
    }

    @Override
    public String getUltName() {
        return "EarthWall";
    }

    public void setWall(double y, double racketSize, boolean isA) {
        if(isA) {
            Rectangle r = new Rectangle(40,y,10.0,racketSize);
            r.setStyle("-fx-effect: dropshadow(three-pass-box,rgba(152, 107, 14, 0.8),10, 0.7, 0, 0)");
            walls.add(r);
        } else {
            Rectangle r = new Rectangle(1050,y,10.0,racketSize);  
            r.setStyle("-fx-effect: dropshadow(three-pass-box,rgba(152, 107, 14, 0.8),10, 0.7, 0, 0)");
            walls.add(r);
        }
    }

    public void removeWall(Rectangle r) {
        wallsToRemove.add(r);
    }
}
