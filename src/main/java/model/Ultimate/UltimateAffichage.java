package model.Ultimate;

import gui.Joueurs;
import gui.Player;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.*;

public class UltimateAffichage {

    public static Text fondUltA = new Text("  On cooldown  ");
    public static Text fondUltB = new Text("  On cooldown  ");
    public static ProgressBar progressBarA = new ProgressBar();
    public static ProgressBar progressBarB = new ProgressBar();

    public static void setUltimateAffichage() {
        progressBarA.setTranslateX(100);
        progressBarA.setTranslateY(550);
        progressBarA.setMinHeight(30);
        progressBarA.setMinWidth(200);
        progressBarB.setMinHeight(30);
        progressBarB.setMinWidth(200);

        fondUltA.setTranslateX(progressBarA.getTranslateX() + progressBarA.getMinHeight() / 2);
        fondUltA.setTranslateY(progressBarA.getTranslateY() - 10);
        progressBarB.setTranslateX(800);
        progressBarB.setTranslateY(550);
        fondUltB.setTranslateX(progressBarB.getTranslateX() + progressBarB.getMinHeight() / 2);
        fondUltB.setTranslateY(progressBarB.getTranslateY() - 10);

    }

    public static void resetfondUlt() {
        fondUltA.setText("  On cooldown ...  ");
        fondUltB.setText("  On cooldown ... ");

    }

    public static void resetProgressUlt() {
        progressBarA.setProgress(0);
        progressBarB.setProgress(0);

    }

    public static void resetUltimate() {
        resetProgressUlt();
        resetfondUlt();
        Player A = Joueurs.getA();
        Player B = Joueurs.getB();
        if (A.getCoupUltimate() != null) {
            A.getCoupUltimate().setChargement(0);
            A.getCoupUltimate().setOnUlt(false);
            A.getCoupUltimate().setcanBeLaunch(false);
            A.getCoupUltimate().isreadyToBounce = false;
            if(A.getCoupUltimate().getUltName()=="EarthWall") {
                for(int i=0; i<A.getCoupUltimate().getWalls().size(); i++) {
                    A.getCoupUltimate().getWallsToRemove().add(A.getCoupUltimate().getWalls().getLast());
                    A.getCoupUltimate().getWalls().removeLast();
                }
            }
        }
        if (B.getCoupUltimate() != null) {
            B.getCoupUltimate().setChargement(0);
            B.getCoupUltimate().setOnUlt(false);
            B.getCoupUltimate().setcanBeLaunch(false);
            A.getCoupUltimate().isreadyToBounce = false;
            if(B.getCoupUltimate().getUltName()=="EarthWall") {
                for(int i=0; i<B.getCoupUltimate().getWalls().size(); i++) {
                    B.getCoupUltimate().getWallsToRemove().add(B.getCoupUltimate().getWalls().getLast());
                    B.getCoupUltimate().getWalls().removeLast();
                }
            }
        }
    }
}
