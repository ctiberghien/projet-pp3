package gui;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Court.Court;
import javafx.stage.Stage;
import model.Court.ScoreBoard;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
import model.Ultimate.UltimateAffichage;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.input.KeyEvent;

import static gui.OptionSon.*;

@SuppressWarnings("FieldCanBeLocal")
public class GameView {
    // class parameters
    public final Court court;
    public final Pane gameRoot; // main node of the game
    private final double scale;
    private final double racketThickness = 10.0; // pixels
    private final double xMargin = 50.0;
    private static boolean isPaused = false;


    // children of the game main node
    private final Rectangle racketA, racketB;
    private final Circle ball;

    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout
     *              ce qu'il y a dessus).
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera
     *              affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre
     *              de pixels correspondants dans la vue
     */
    public GameView(Court court, Pane root, double scale) {
        this.court = court;
        this.gameRoot = root;
        this.scale = scale;

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);

        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Joueurs.couleurRacketA);
        racketA.setX(xMargin - racketThickness);
        racketA.setY(court.getRacketA() * scale);

        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(racketThickness);
        racketB.setFill(Joueurs.couleurRacketB);
        racketB.setX(court.getWidth() * scale + xMargin);
        racketB.setY(court.getRacketB() * scale);

        ball = new Circle();
        ball.setRadius(court.getBallRadius());

        ball.setCenterX(court.getBallX() * scale + xMargin);
        ball.setCenterY(court.getBallY() * scale);
        ball.setFill(Joueurs.couleurBalle);
        // ball.setStyle("-fx-effect: drop shadow(three-pass-box,
        // rgba(256,256,256,100),10, 0, 0, 0)");
        court.setRacketA(court.getHeight()/2);
        court.setRacketB(court.getHeight()/2);

        UltimateAffichage.setUltimateAffichage();
        // on ajoute les affichages des chargements de l'ult sur la scene
        if (Joueurs.getA().getCoupUltimate() != null) {

            gameRoot.getChildren().addAll(UltimateAffichage.progressBarA, UltimateAffichage.fondUltA);
        }
        if (Joueurs.getB().getCoupUltimate() != null) {
            gameRoot.getChildren().addAll(UltimateAffichage.progressBarB, UltimateAffichage.fondUltB);
        }
        UltimateAffichage.resetProgressUlt();

        // Appel des deux méthodes qui mette en place les affichages des points
        ScoreBoard.setFond();
        ScoreBoard.setFontScore();

        switch (Parametre.modeDejeu) {
            case "ModePoint":
            case "ModeInfini":
                gameRoot.getChildren().addAll(ScoreBoard.fondScore, ScoreBoard.pApane, ScoreBoard.pBpane,
                        ScoreBoard.separation);
                break;
            case "ModeChallenge":
                LeaderBoard.setVisuel();
                gameRoot.getChildren().addAll(LeaderBoard.fondScore,
                        LeaderBoard.txtPoint, LeaderBoard.txtTime, LeaderBoard.progressBarTime, LeaderBoard.pPointpane);
                break;
        }

        gameRoot.getChildren().addAll(racketA, racketB, ball);

        // ajout du css
        racketA.getStyleClass().addAll("racketball");
        racketB.getStyleClass().addAll("racketball");
        ball.getStyleClass().addAll("racketball");
        LeaderBoard.txtWin.getStyleClass().addAll("texte");
        LeaderBoard.txtLoose.getStyleClass().addAll("texte");
        ScoreBoard.txtWin.getStyleClass().addAll("texte");

        if (Joueurs.getA().getCoupUltimate() != null) {
            switch (Joueurs.getA().getCoupUltimate().getUltName()) {
                // on met dans case le nom de l'ult // vous pouvez changer BalleRapideUlt si
                // voulez un truc + coherent
                case "BalleRapideUlt":
                    UltimateAffichage.progressBarA.setId("fire-progress-bar");
                    break;
                case "ThunderBall":
                    UltimateAffichage.progressBarA.setId("electro-progress-bar");
                    break;
                case "BalleWave":
                    UltimateAffichage.progressBarA.setId("water-progress-bar");
                    break;
                case "EarthWall":
                    UltimateAffichage.progressBarA.setId("ground-progress-bar");
                    break;
            }
        }

        if (Joueurs.getB().getCoupUltimate() != null) {
            switch (Joueurs.getB().getCoupUltimate().getUltName()) {
                // on met dans case le nom de l'ult // vous pouvez changer BalleRapideUlt si
                // voulez un truc + coherent
                case "BalleRapideUlt":
                    UltimateAffichage.progressBarB.setId("fire-progress-bar");
                    break;
                case "ThunderBall":
                    UltimateAffichage.progressBarB.setId("electro-progress-bar");
                    break;
                case "BalleWave":
                    UltimateAffichage.progressBarB.setId("water-progress-bar");
                    break;
                case "EarthWall":
                    UltimateAffichage.progressBarB.setId("ground-progress-bar");
                    break;
            }
        }
        LeaderBoard.progressBarTime.setId("timer-progress-bar");

        UltimateAffichage.fondUltA.getStyleClass().addAll("textUlt");
        UltimateAffichage.fondUltB.getStyleClass().addAll("textUlt");
        ScoreBoard.fondScore.getStyleClass().add("scorebackground");
        ScoreBoard.separation.getStyleClass().add("scoreseparation");
        ScoreBoard.txtPointA.getStyleClass().addAll("scoretexte");
        ScoreBoard.txtPointB.getStyleClass().addAll("scoretexte");
        LeaderBoard.fondScore.getStyleClass().addAll("scorebackground");
        LeaderBoard.txtPoint.getStyleClass().addAll("scoretexte");
        LeaderBoard.txtTime.setId("txttime");

    }

    // on crée une variable pour effectuer la méthode moins de fois possible
    public static boolean isWin = false;
    public static long last = 0;
    static int cpt = 0;
    static Timeline tm;
    static long now = 0;

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;
    private Button Pause;


    private static Button quit = new Button("Quitter(ESC)");
    {quit.setCancelButton(true);
    quit.setFocusTraversable(false);
    quit.setTranslateX(0);
    quit.setTranslateY(0);}
    
    public void animate(Stage primaryStage) {
        last = 0;

        final Duration oneFrameAmt = Duration.millis(1000 / 120);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
            new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    musicPause.stop();
                    now+=1000/120;
                    if (last == 0) { // ignore the first tick, just compute the first deltaT
                        last = now;
                        return;
                    }
                    court.update((now - last) * 1.0e-3, ball); // convert nanoseconds to seconds
    
    
                    if (!isWin) {
                        // On vérifie quel joueur à gagne et ensuite on efface les affichages et affiche
                        // le gagnant
                        Button rematch;
                        if (ScoreBoard.playerBGagne()) {
                            isWin = true;
                            tm.stop();
                            if (Joueurs.getB().getIsBot()) {
                                rematch = ScoreBoard.win(primaryStage, "CPU B");
                            } else {
                                rematch = ScoreBoard.win(primaryStage, "droite");
                            }
    
                            rematch.setMinWidth(60);
                            rematch.setMinHeight(24);
                            rematch.setTranslateX(550 - rematch.getMinWidth() / 2 - 20);
                            rematch.setTranslateY(450);
                            gameRoot.getChildren().removeAll(ball, racketA, racketB, Pause);
    
                            // on retire les affichages des chargements d'ulti
                            if (Joueurs.getA().getCoupUltimate() != null) {
                                gameRoot.getChildren().removeAll(UltimateAffichage.progressBarA,
                                        UltimateAffichage.fondUltA);
                            }
                            if (Joueurs.getB().getCoupUltimate() != null) {
                                gameRoot.getChildren().removeAll(UltimateAffichage.progressBarB,
                                        UltimateAffichage.fondUltB);
                            }
                            // ajout css
                            quit.setTranslateX(610-quit.getWidth());
                            quit.setTranslateY(550);
                            rematch.getStyleClass().addAll("spbtn");
                            Rectangle fog = new Rectangle(1100, 600);
                            fog.setFill(Color.GRAY);
                            fog.setOpacity(0.75);
                            gameRoot.getChildren().remove(quit);
                            gameRoot.getChildren().addAll(fog , quit,  ScoreBoard.txtWin, rematch );
                        }
    
                        if (ScoreBoard.playerAGagne()) {
                            isWin = true;
                            if (Joueurs.getA().getIsBot()) {
                                rematch = ScoreBoard.win(primaryStage, "CPU A");
                            } else {
                                rematch = ScoreBoard.win(primaryStage, "gauche");
                            }
                            tm.stop();
                            rematch.setMinWidth(60);
                            rematch.setMinHeight(24);
                            rematch.setTranslateX(550 - rematch.getMinWidth() / 2 - 20);
                            rematch.setTranslateY(450);
                            gameRoot.getChildren().removeAll(ball, racketA, racketB, Pause);
                            // on retire les affichages des chargements d'ulti
                            if (Joueurs.getA().getCoupUltimate() != null) {
                                gameRoot.getChildren().removeAll(UltimateAffichage.progressBarA,
                                        UltimateAffichage.fondUltA);
                            }
                            if (Joueurs.getB().getCoupUltimate() != null) {
                                gameRoot.getChildren().removeAll(UltimateAffichage.progressBarB,
                                        UltimateAffichage.fondUltB);
                            }
                            // ajout css
                            rematch.getStyleClass().addAll("spbtn");
                            Rectangle fog = new Rectangle(1100, 600);
                            fog.setFill(Color.GRAY);
                            fog.setOpacity(0.75);
                            gameRoot.getChildren().remove(quit);
                            gameRoot.getChildren().addAll(fog, quit, ScoreBoard.txtWin, rematch );
                        }
                    }
    
                    if (Joueurs.getA().getCoupUltimate() != null) {
                        if (Joueurs.getA().getCoupUltimate().getUltName() == "EarthWall") {
                            if (Joueurs.getA().getCoupUltimate().isActivated) {
                                if (Joueurs.getA().getCoupUltimate().getWalls().size() > 0) {
                                    gameRoot.getChildren().addAll(Joueurs.getA().getCoupUltimate().getWalls().getLast());
                                    Joueurs.getA().getCoupUltimate().isActivated = false;
                                }
                            }
                            if(Joueurs.getA().getCoupUltimate().getWallsToRemove().size()>0) {
                                gameRoot.getChildren().remove(Joueurs.getA().getCoupUltimate().getWallsToRemove().getLast());
                                Joueurs.getA().getCoupUltimate().getWalls().remove(Joueurs.getA().getCoupUltimate().getWallsToRemove().getLast());
                                OptionSon.playRebound();
                                Joueurs.getA().getCoupUltimate().getWallsToRemove().removeLast();
                            }
                        }
                    }
                    if (Joueurs.getB().getCoupUltimate() != null) {
                        if (Joueurs.getB().getCoupUltimate().getUltName() == "EarthWall") {
                            if (Joueurs.getB().getCoupUltimate().isActivated) {
                                if (Joueurs.getB().getCoupUltimate().getWalls().size() > 0) {
                                    gameRoot.getChildren().addAll(Joueurs.getB().getCoupUltimate().getWalls().getLast());
                                    Joueurs.getB().getCoupUltimate().isActivated = false;
                                }
                            }
                            if(Joueurs.getB().getCoupUltimate().getWallsToRemove().size()>0) {
                                gameRoot.getChildren().remove(Joueurs.getB().getCoupUltimate().getWallsToRemove().getLast());
                                Joueurs.getB().getCoupUltimate().getWalls().remove(Joueurs.getB().getCoupUltimate().getWallsToRemove().getLast());
                                Joueurs.getB().getCoupUltimate().getWallsToRemove().removeLast();
                                OptionSon.playRebound();
                            }
                        }
                    }
    
                    last = now;
                    racketA.setY(court.getRacketA() * scale);
                    racketB.setY(court.getRacketB() * scale);
    
                    ball.setCenterX(court.getBallX() * scale + xMargin);
                    ball.setCenterY(court.getBallY() * scale);
    
                    long oldFrameTime = frameTimes[frameTimeIndex] ;
                    frameTimes[frameTimeIndex] = now ;
                    frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
                    if (frameTimeIndex == 0) {
                        arrayFilled = true ;
                    }
                    if (arrayFilled) {
                        long elapsedNanos = now - oldFrameTime ;
                        long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                        double frameRate = 1_000.0 / elapsedNanosPerFrame ;
                    }

                }
            }); // oneFrame

            // sets the game world's game loop (Timeline)

            tm = new Timeline(120,oneFrame);
            tm.setCycleCount(Animation.INDEFINITE);
            tm.play();


        Pause = this.pause(false,tm , primaryStage);
        // --Démarre le animation timer--

        tm.play();

        // --Code du bouton quitter--

        quit.setOnAction(event -> {
            isPaused=false;
            OptionSon.playSelectedButton();
            // Si bouton quit appuyé le jeu s'arrête
            OptionSon.playSelectedButton();
            tm.stop();
            OptionSon.mediaPlayerMusique.stop();
            OptionSon.musicMedia = (new File(OptionSon.stringMusicMenuFile).toURI().toString());
            OptionSon.mediaPlayerMusique = new AudioClip(OptionSon.musicMedia);
            OptionSon.mediaPlayerMusique.setCycleCount(MediaPlayer.INDEFINITE);
            OptionSon.muteSound();
            OptionSon.mediaPlayerMusique.play();

            // reset les scores
            ScoreBoard.resetScore();
            // reset les ult
            Joueurs.getA().setCoupUltimate(null);
            Joueurs.getB().setCoupUltimate(null);
            UltimateAffichage.resetUltimate();
            Parametre.modeDejeu = "";
            // Lance le menu
            Menu m = new Menu();
            m.start(primaryStage);
            event.consume();
        });


        primaryStage.getScene().setOnKeyTyped((KeyEvent event) -> {
            if (!(ScoreBoard.playerBGagne() || ScoreBoard.playerAGagne())) {
                if (event.getCharacter().toUpperCase().equals(Joueurs.getA().getPause().getName())) {
                    Pause.fire();
                }
            }
        });

        quit.getStyleClass().addAll("spbtn", "quit");
        Pause.getStyleClass().addAll("spbtn", "quit");
        gameRoot.getChildren().addAll(quit, Pause);
    }

    // On créait plusieurs variables static pour gérer la partie
    // le timer
    static Timer time;
    static TimerTask task;
    // Le nombres de points marqué
    public static int i;
    // un boolean vérifiant si on a gagné les partis
    public static boolean lbLoss = false;
    // un boolean vérifiant si on a perdu les partis
    public static boolean lbWin = false;

    // Une nouvelle méthode animate qui la partie de manière different
    public void animatelb(Stage primaryStage) {
        LeaderBoard.point = 0;
        boolean lbGame = Parametre.modeDejeu.equals("ModeChallenge");

        final Duration oneFrameAmt = Duration.millis(1000 / 120);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        now+=1000/120;
                        if (last == 0) { // ignore the first tick, just compute the first deltaT
                            last = now;
                            return;
                        }
                        if (!lbGame) {
                            court.update((now - last) * 1.0e-3, ball); // convert nanoseconds to seconds
                        } else {
                            // on appelle donc la bonne méthode et met en place les choses à afficher
                            LeaderBoard.majScores();
                            court.updatelb((now - last) * 1.0e-3, ball);
                            LeaderBoard.setVisuel();
                        }
        
                        if (!isWin) {
                            Button rematch = new Button();
                            Button saveStat = new Button();
                            // On vérifie quel joueur à gagne et ensuite on efface les affichages et affiche
                            if (lbWin) {
                                rematch = LeaderBoard.win(primaryStage);
                                LeaderBoardTab.lb = new LeaderBoard();
                                saveStat = LeaderBoard.saveStat(primaryStage, true);
                                LeaderBoard.txtTime.setText("30'000");
                                gameRoot.getChildren().removeAll(ball, racketA, racketB);
                                isWin = false;
        
                                // on retire les affichages des chargements d'ulti
                                if (Joueurs.getA().getCoupUltimate() != null) {
                                    gameRoot.getChildren().removeAll(UltimateAffichage.progressBarA,
                                            UltimateAffichage.fondUltA);
                                }
                                if (Joueurs.getB().getCoupUltimate() != null) {
                                    gameRoot.getChildren().removeAll(UltimateAffichage.progressBarB,
                                            UltimateAffichage.fondUltB);
                                }
        
                                rematch.getStyleClass().addAll("spbtn");
                                saveStat.getStyleClass().addAll("spbtn");
                                gameRoot.getChildren().addAll(rematch, saveStat, LeaderBoard.txtWin);
                                tm.stop();
                                last = 0;
                            }
                            // L'éventualité ou le joueur se prend un point et perd donc les partis
                            if (lbLoss) {
                                LeaderBoardTab.lb = new LeaderBoard();
                                rematch = LeaderBoard.win(primaryStage);
                                saveStat = LeaderBoard.saveStat(primaryStage, false);
                                gameRoot.getChildren().removeAll(ball, racketA, racketB);
        
                                // on retire les affichages des chargement d'ulti
                                if (Joueurs.getA().getCoupUltimate() != null) {
                                    gameRoot.getChildren().removeAll(UltimateAffichage.progressBarA,
                                            UltimateAffichage.fondUltA);
                                }
                                if (Joueurs.getB().getCoupUltimate() != null) {
                                    gameRoot.getChildren().removeAll(UltimateAffichage.progressBarB,
                                            UltimateAffichage.fondUltB);
                                }
                                isWin = false;
                                lbLoss = false;
                                i = 0;
                                time.cancel();
                                rematch.getStyleClass().addAll("spbtn");
                                saveStat.getStyleClass().addAll("spbtn");
                                quit.setTranslateX(610-quit.getWidth());
                                quit.setTranslateY(550);
                                gameRoot.getChildren().addAll(rematch, saveStat, LeaderBoard.txtLoose);
                                tm.stop();
                    
                        }
                        last = now;
                        racketA.setY(court.getRacketA() * scale);
                        racketB.setY(court.getRacketB() * scale);
                        ball.setCenterX(court.getBallX() * scale + xMargin);
                        ball.setCenterY(court.getBallY() * scale);
                    }
                }
            }); // oneFrame

            // sets the game world's game loop (Timeline)

            tm = new Timeline(120,oneFrame);
            tm.setCycleCount(Animation.INDEFINITE);
            tm.play();


        Button Pause = pause(true , tm , primaryStage);


        // --Démarre le animation timer--

        tm.play();

        // --Code du bouton quitter--
        quit.setOnAction(event -> {
            isPaused=false;
            // Si bouton quit appuyé le jeu s'arrête
            OptionSon.playSelectedButton();
            tm.stop();

            OptionSon.mediaPlayerMusique.stop();
            OptionSon.musicMedia = (new File(OptionSon.stringMusicMenuFile).toURI().toString());
            OptionSon.mediaPlayerMusique = new AudioClip(OptionSon.musicMedia);
            OptionSon.muteSound();
            OptionSon.mediaPlayerMusique.setCycleCount(MediaPlayer.INDEFINITE);
            OptionSon.mediaPlayerMusique.play();

            // reset les scores
            ScoreBoard.resetScore();
            UltimateAffichage.resetUltimate();
            Parametre.modeDejeu = "";
            // Lance le menu
            Menu m = new Menu();


//            m.setPoint(5);
            m.start(primaryStage);

            time.cancel();
            event.consume();
        });
        // --Fin du code du bouton quitter--

        primaryStage.getScene().setOnKeyTyped((KeyEvent event) -> {
            if (event.getCharacter().toUpperCase().equals(Joueurs.getA().getPause().getName())) {
                if (!lbWin && !lbLoss) {
                    Pause.fire();
                }
            }
        });

        // --Gestion du timer--
        if (Parametre.modeDejeu.equals("ModeChallenge")) {
            time = new Timer();
            i = 0;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    i++;
                    // On limite le nombre de rafraichissements pour optimiser et ne pas sur charger
                    // le nombres d'appel
                    if (i % 42 == 0) {
                        if (LeaderBoardTab.lb == null) {
                            LeaderBoardTab.lb = new LeaderBoard();
                        }
                        LeaderBoardTab.lb.majText(i);
                    }
                    // Attention 30 secondes = 30 * 1000 i
                    if (i == 30 * 1000) {
                        lbWin = true;
                        lbLoss = false;
                        time.cancel();
                    }
                }
            };
            // On met un délai pour le timer pour laisser le temps au joueur de se
            // concentrer
            time.schedule(task, 500, 1);
        }
        quit.getStyleClass().addAll("spbtn", "quit");
        Pause.getStyleClass().addAll("spbtn", "quit");
        quit.setTranslateX(610-quit.getWidth());
        quit.setTranslateY(550);
        gameRoot.getChildren().addAll(quit, Pause);
    }

    private AudioClip musicPause = new AudioClip(musicMedia);

    public Button pause(Boolean isLb, Timeline tm, Stage stage) {
        Button popUpSon = new Button("Son");
        popUpSon.getStyleClass().addAll("spbtn","quit");
        popUpSon.setOnAction(ev -> {
            OptionSon.playSelectedButton();
            OptionSonPopUp e = new OptionSonPopUp();
            e.start(stage);
            ev.consume();
        });
        popUpSon.setTranslateY(350);
        popUpSon.setMinWidth(80);
        popUpSon.setTranslateX((1100 - popUpSon.getMinWidth()) / 2);

        Button popUpAide = new Button("Aide");
        popUpAide.getStyleClass().addAll("spbtn","quit");
        popUpAide.setOnAction(ev -> {
            OptionSon.playSelectedButton();
            Aide e = new Aide();
            e.start(stage, true);
            ev.consume();
        });
        popUpAide.setTranslateY(400);
        popUpAide.setMinWidth(80);
        popUpAide.setTranslateX((1100 - popUpAide.getMinWidth()) / 2);

        Media mediaPause = new Media(new File(stringMusicMenuFile).toURI().toString());

        Button Pause = new Button("Pause(" + Joueurs.getA().getPause() +")");
        Pause.setFocusTraversable(false);
        Pause.setMinWidth(100);
        Pause.setTranslateX(1100 - Pause.getMinWidth());
        Rectangle fog = new Rectangle(1100, 600);
        fog.setFill(Color.GRAY);
        fog.setOpacity(0.75);
        muteBruit = OptionSon.btnMuteBruitGenerator(gameRoot);
        Pause.setOnAction(ev -> {
            musicPause.stop();
            OptionSon.playSelectedButton();
            if ((!lbWin && !lbLoss) || (!ScoreBoard.playerAGagne() && !ScoreBoard.playerBGagne())) {
                double ballX = ball.getCenterX();
                double ballY = ball.getCenterY();
                if (isPaused) {
                    quit.setCancelButton(true);
                    quit.setTranslateX(0);
                    quit.setTranslateY(0);
                    Pause.setFocusTraversable(false);
                    Pause.setTranslateY(0);
                    Pause.setMinWidth(100);
                    Pause.setTranslateX(1100 - Pause.getMinWidth());
                    Pause.setText("Pause(" + Joueurs.getA().getPause() +")");
                    this.gameRoot.getChildren().removeAll(fog, popUpSon, popUpAide);
                    last = 0;
                    court.setBallX(ballX - xMargin);
                    court.setBallY(ballY);
                    ball.setCenterX(ballX);
                    ball.setCenterY(ballY);
                    OptionSon.mediaPlayerMusique.play();
                    if (isLb) {
                        time = new Timer();
                        task = new TimerTask() {
                            public void run() {
                                i++;
                                // On limite le nombre de rafraichissements pour optimiser et ne pas sur charger
                                // le nombres d'appel
                                if (i % 42 == 0) {
                                    if (LeaderBoardTab.lb == null) {
                                        LeaderBoardTab.lb = new LeaderBoard();
                                    }
                                    LeaderBoardTab.lb.majText(i);
                                }
                                // Attention 30 secondes = 30 * 1000 i
                                if (i == 30 * 1000) {
                                    lbWin = true;
                                    lbLoss = false;
                                    time.cancel();
                                }
                            }
                        };
                        time.schedule(task, 0, 1);
                    }
                    tm.play();
                    isPaused = false;
                } else {
                    quit.setTranslateX(610-quit.getWidth());
                    quit.setTranslateY(550);
                    Pause.setTranslateY(250);
                    Pause.setTranslateX((1100 - Pause.getMinWidth()) / 2);
                    Pause.setText("Retour ("+Joueurs.getA().getPause().getName()+")");
                    Pause.setFocusTraversable(false);
                    OptionSon.mediaPlayerMusique.stop();
                    musicPause.setCycleCount(MediaPlayer.INDEFINITE);
                    musicPause.setVolume(OptionSon.nivSonMusique);
                    musicPause.play();
                    this.gameRoot.getChildren().removeAll(Pause , quit);
                    this.gameRoot.getChildren().addAll( fog, Pause, popUpSon , popUpAide, quit);
                    if (isLb) {
                        time.cancel();
                    }
                    tm.stop();
                    isPaused = true;
                }
            }
            ev.consume();
        });
        return Pause;
    }
}
