package model.Ultimate;
import java.util.LinkedList;

import javafx.scene.shape.Rectangle;

public class Ultimate {
    public double chargement; // represente le chargement du coups spécial /3
    public boolean canBeLaunch; // indique si le coups spécial peut etre activé (true si l'ult peut etre activé
                         // , false sinon)
                         public boolean onUlt; // indique si le coups spécial est activé par le joueur (true quand le joueurs
                   // clique sur sa touche pour activer l'ult ; false quand l'ult est fini )
                   public boolean isreadyToBounce; // indique si le coups spécial va etre lancer au prochain rebond de la balle sur
                             // sa raquette(devient true quand onUlt==true ; devient false quand la balle
                             // touche la raquette du joueur)
    public boolean isActivated; // indique si le coups spécial est déclenché (devient true en touchant la
                         // raquette du joueur)

    Ultimate() {
        this.chargement = 0;
        this.canBeLaunch = false;
        onUlt = false;
        isActivated = false;
    }

    // getter / setter
    public double getChargement() {
        return chargement;
    }

    public void setChargement(double chargement) {
        this.chargement = chargement;
    }

    public boolean getcanBeLaunch() {
        return this.canBeLaunch;
    }

    public void setcanBeLaunch(boolean canBeLaunch) {
        this.canBeLaunch = canBeLaunch;
    }

    public boolean getOnult() {
        return onUlt;
    }

    public void setOnUlt(boolean onUlt) {
        this.onUlt = onUlt;
    }

    public String getUltName() {
        return null;
    }

    // méthode :
    public void incrementeChargement() {
        this.chargement+=0.4;
    }

    public boolean isUltReady() {
        return this.chargement >= 1;
    }

    public void setWall(double y, double racketSize, boolean isA) {}
    public void removeWall(Rectangle r){}

    public LinkedList<Rectangle> getWalls() {
        return null;
    }

    public LinkedList<Rectangle> getWallsToRemove() {
         return null;
    }

    public boolean getIsActivated() {
        return isActivated;
    }

}
