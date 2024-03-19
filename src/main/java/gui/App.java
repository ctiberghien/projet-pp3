package gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Court.Court;
import model.RacketController;

@SuppressWarnings("FieldCanBeLocal")
public class App extends Application {
    //On pose deux variables que l'on récupèrera pour commencer les parties
    private int nbPoint=-1;
    private boolean isBot=false;
    //Et leur accesseur
    public void setNbPoint(int nbPoint) {this.nbPoint = nbPoint;}

    public int getNbPoint() {return nbPoint;}
    public boolean isBot() {return isBot;}
    public void setBot(boolean bot) {isBot = bot;}

    //Mise en place d'un argument qui permet de lancer la partie avec les bonnes options
    private LeaderBoard lb;
    //On garde en argument un objet LeaderBoard pour gérer la partie
    private boolean lbGame=false;

    //Les accesseurs qui vont avec
    public void setLbGame(boolean lbGame) {this.lbGame = lbGame;}
    public void setLb(LeaderBoard lb) {this.lb = lb;}
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene gameScene = new Scene(root);
        Court court = new Court(Joueurs.getA(), Joueurs.getB(), 1000, 600);
        GameView gameView = new GameView(court, root, 1.0);
        gameScene.setOnKeyPressed(ev -> {
            if (ev.getCode() == Joueurs.getA().getUp())
                Joueurs.getA().state = RacketController.State.GOING_UP;
            if (ev.getCode() == Joueurs.getA().getDown())
                Joueurs.getA().state = RacketController.State.GOING_DOWN;
            if (ev.getCode() == Joueurs.getB().getUp())
                Joueurs.getB().state = RacketController.State.GOING_UP;
            if (ev.getCode() == Joueurs.getB().getDown())
                Joueurs.getB().state = RacketController.State.GOING_DOWN;
            if (ev.getCode() == Joueurs.getA().getUlt() && !Joueurs.getA().getIsBot()) {
                Joueurs.getA().stateUlt = true;
            }
            if (ev.getCode() == Joueurs.getB().getUlt() && !Joueurs.getB().getIsBot()) {
                Joueurs.getB().stateUlt = true;
            }
        });

        gameScene.setOnKeyReleased(ev -> {
                    if (ev.getCode() == Joueurs.getA().getUp()) {
                        if (Joueurs.getA().state == RacketController.State.GOING_UP)
                            Joueurs.getA().state = RacketController.State.IDLE;
                        court.setRacketASpeed(0);
                    }
                    if (ev.getCode() == Joueurs.getA().getDown()) {
                        if (Joueurs.getA().state == RacketController.State.GOING_DOWN)
                            Joueurs.getA().state = RacketController.State.IDLE;
                        court.setRacketASpeed(0);
                    }
                    if (ev.getCode() == Joueurs.getB().getUp()) {
                        if (Joueurs.getB().state == RacketController.State.GOING_UP)
                            Joueurs.getB().state = RacketController.State.IDLE;
                        court.setRacketBSpeed(0);
                    }
                    if (ev.getCode() == Joueurs.getB().getDown()) {
                        if (Joueurs.getB().state == RacketController.State.GOING_DOWN)
                            Joueurs.getB().state = RacketController.State.IDLE;
                        court.setRacketBSpeed(0);
                    }
                    if (ev.getCode() == Joueurs.getA().getUlt()) {
                        if (Joueurs.getA().stateUlt) {
                            Joueurs.getA().stateUlt = false;
                        }
                    }
                    if (ev.getCode() == Joueurs.getB().getUlt()) {
                        if (Joueurs.getB().stateUlt) {
                            Joueurs.getB().stateUlt = false;
                        }
                    }
                });

        // ajout de la feuille de css
        root.setId(Parametre.getActualMap());
        root.getStyleClass().addAll("root");
        gameScene.getStylesheets().addAll(getClass().getResource("stylesheet/gameviewstylesheet.css").toExternalForm());
        gameScene.getStylesheets().addAll(getClass().getResource("stylesheet/ultimatestylesheet.css").toExternalForm());

        primaryStage.setScene(gameScene);
        primaryStage.show();
        switch (Parametre.modeDejeu) {
            case "ModePoint":
            case"ModeInfini":
                gameView.animate(primaryStage);
                break;
            case "ModeChallenge":
                gameView.animatelb(primaryStage);
                break;
        }
    }
}

