package gui;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

// Class qui store les données des joueurs

public class Joueurs {
    public static Color couleurBalle = Color.BLACK;
    public static Color couleurRacketA = Color.BLACK;
    public static Color couleurRacketB = Color.BLACK;
    public static Player playerA = new Player(KeyCode.valueOf("CONTROL"),KeyCode.valueOf("ALT"),KeyCode.valueOf("W"), KeyCode.getKeyCode("P"));
    public static Player playerB = new Player(KeyCode.valueOf("UP"),KeyCode.valueOf("DOWN"),KeyCode.valueOf("M"), KeyCode.getKeyCode("P"));

    public static Player getA() {
        return playerA;
    }

    public static Player getB() {
        return playerB;
    }
}