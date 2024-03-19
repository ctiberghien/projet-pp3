package model.Court;


import gui.App;
import gui.GameView;
import gui.OptionSon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Ultimate.UltimateAffichage;

public class ScoreBoard {
    // On créait une classe qui permettra la gestion des points et des futures
    // methods associer
    private static int pointA = 0;
    private static int pointB = 0;
    public static Rectangle fondScore = new Rectangle();
    public static Line separation = new Line();
    public static Text txtPointA = new Text();
    public static Text txtPointB = new Text();
    public static Text txtWin = new Text();
    public static int nbPoint;
    public static StackPane pApane = new StackPane();
    public static StackPane pBpane = new StackPane();

    // Remet les scores à 0.
    public static void resetScore() {
        pointA = 0;
        pointB = 0;
    }

    // Les deux méthodes qui augmentent les points des joueurs
    public static void playerAScore() {
        pointA += 1;
    }

    public static void playerBScore() {
        pointB += 1;
    }

    // --Deux méthodes qui verifies si les joueurs ont gagné
    public static boolean playerBGagne() {
        if (nbPoint == -1) {
            return false;
        } else {
            return pointB >= nbPoint;
        }
    }

    public static boolean playerAGagne() {
        if (nbPoint == -1) {
            return false;
        } else {
            return pointA >= nbPoint;
        }
    }
    // --Fin des deux méthodes

    // Méthode qui met en places les paramètre du fond du tableau des scores
    public static void setFond() {
        fondScore.setHeight(90);
        fondScore.setWidth(221);
        fondScore.setFill(Color.WHITE);
        fondScore.setStroke(Color.DARKBLUE);
        fondScore.setStrokeWidth(7);
        fondScore.setY(5);
        fondScore.setX(439);
        separation.setStartX(550);
        separation.setEndX(550);
        separation.setStartY(5);
        separation.setEndY(95);
        // separation.setStroke(Color.BLUE);
    }

    public static void setFontScore() {
        if (!pApane.getChildren().contains(txtPointA)) {
            pApane.getChildren().addAll(txtPointA);
        }
        pApane.setTranslateX(439);
        pApane.setTranslateY(5);
        pApane.setPrefSize(110, 90);
        if (!pBpane.getChildren().contains(txtPointB)) {
            pBpane.getChildren().addAll(txtPointB);
        }
        pBpane.setTranslateX(551);
        pBpane.setTranslateY(5);
        pBpane.setPrefSize(110, 90);

    }

    // Met à jour les scores à chaque appel
    public static void majScores() {
        txtPointA.setText(String.valueOf(pointA));
        txtPointB.setText(String.valueOf(pointB));
    }

    // Méthode qui met en place le text qui affiche le vainqueur
    public static void setTxtWin(String playerName) {
        txtWin.setText("Le joueur " + playerName + " à gagné");
        txtWin.setFont(Font.font(45));
        txtWin.setY(300);
        txtWin.setWrappingWidth(750);
        txtWin.setTextAlignment(TextAlignment.CENTER);
        txtWin.setX(550 - txtWin.getWrappingWidth() / 2);
    }

    // Méthode qui met en place la victoire
    public static Button win(Stage stage, String playerName) {
            ScoreBoard.majScores();
            GameView.isWin = false;
            ScoreBoard.setTxtWin(playerName);
            Button res = new Button("Rejouer");
            App game = new App();
            res.setOnAction(event -> {
                OptionSon.playSelectedButton();
                UltimateAffichage.resetUltimate();
                game.start(stage);
                ScoreBoard.resetScore();
                event.consume();
            });
            return res;
    }

}
