package model.Court;

import java.util.Random;

import gui.ElementSize;
import gui.OptionSon;
import gui.Parametre;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import gui.GameView;
import gui.Joueurs;
import gui.Player;
import gui.LeaderBoard;
import model.Ultimate.BalleRapideUlt;
import model.Ultimate.BalleWave;
import model.Ultimate.ThunderBallUlt;
import model.Ultimate.UltimateAffichage;

@SuppressWarnings("FieldMayBeFinal")
public class Court {
    // instance parameters
    private final Player playerA, playerB;
    public final static double width = 1000; // m
    public final static double height = 600; // m
    private double racketASpeed = 50.0; // m/s
    private double racketBSpeed = 50.0; // m/s
    private double racketBotSpeed = 300.0; // m/s
    public static double racketSize = 100.0; // m
    public static double ballRadius = 10.0; // m
    // instance state
    private double racketA; // m
    private double racketB; // m
    private double ballX, ballY; // m
    public static double ballSpeedX, ballSpeedY; // m
    public static double trajectoireAleatoire;
    private static double cpt;

    private boolean isInAGame = true;

    public Court(Player playerA, Player playerB, double width, double height) {
        this.playerA = playerA;
        this.playerB = playerB;
        reset();
    }

    public void setBallX(double ballX) {
        this.ballX = ballX;
    }

    public void setBallY(double ballY) {
        this.ballY = ballY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRacketSize() {
        return racketSize;
    }

    public double getRacketA() {
        return racketA;
    }

    public double getRacketB() {
        return racketB;
    }

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public void setRacketA(double a) {
        this.racketA = a;
    }

    public void setRacketB(double b) {
        this.racketB = b;
    }

    public void setRacketASpeed(double a) {
        this.racketASpeed = a;
    }

    public void setRacketBSpeed(double b) {
        this.racketBSpeed = b;
    }

    public void update(double deltaT, Circle ball) {
        if (!playerA.getIsBot()) {
            this.controlPlayerA(deltaT);
        } else {
            // Le bot sera influencer selon la position de la balle sur le terrain
            switch (Parametre.botLvlA) {
                case 1:
                    this.botAstrategieLv1(deltaT);
                    break;
                case 2:
                    this.botAstrategieLv2(deltaT);
                    break;
            }
        }
        if (!playerB.getIsBot()) {

            this.controlPlayerB(deltaT);
        } else {
            switch (Parametre.botLvlB) {
                case 1:
                    this.botBstrategieLv1(deltaT);
                    break;
                case 2:
                    this.botBstrategieLv2(deltaT);
                    break;
            }
        }
        this.uptadeUlt(deltaT, ball);
        if (updateBall(deltaT, ball)) {
            reset();
        }
    }

    /**
     * @return true if a player lost
     */
    private boolean updateBall(double deltaT, Circle ball) {
        double nextBallX;
        double nextBallY;
        Random rand = new Random();       
        boolean changeTrajectory = true;
        // first, compute possible next position if nothing stands in the way
        if (playerA.getCoupUltimate() != null || playerB.getCoupUltimate() != null) {

            Player p;
            if (playerA.getCoupUltimate() != null) {
                if (playerB.getCoupUltimate() != null) {
                    // les deux joueurs possède un coup spécial
                    // En théorie seul 1 coup spécial peut avoir isActivated == true a la fois
                    p = (playerA.getCoupUltimate().isActivated) ? playerA : playerB;
                } else {
                    p = playerA;
                }
            } else {
                p = playerB;
            }
            if (p.getCoupUltimate().isActivated) {
                switch (p.getCoupUltimate().getUltName()) {
                    case "BalleRapideUlt":
                        nextBallX = BalleRapideUlt.nextBallX(deltaT, ballX, ballSpeedX);
                        nextBallY = BalleRapideUlt.nextBallY(deltaT, ballY, ballSpeedY);
                        break;
                    case "BalleWave":
                        nextBallX = BalleWave.nextBallX(deltaT, ballX, ballSpeedX);
                        nextBallY = BalleWave.nextBallY(deltaT, ballY, ballSpeedY);
                        break;
                    case "ThunderBall":
                        nextBallX = ballX + deltaT * ballSpeedX;
                        nextBallY = ballY + deltaT * ballSpeedY * 2;
                        if (ThunderBallUlt.verif())
                            ballSpeedY = -ballSpeedY;
                        break;
                    default:
                        nextBallX = ballX + deltaT * ballSpeedX;
                        nextBallY = ballY + deltaT * ballSpeedY;
                        break;
                }
            } else {
                nextBallX = ballX + deltaT * ballSpeedX;
                nextBallY = ballY + deltaT * ballSpeedY;
            }
            if (playerA.getCoupUltimate() != null) {
                if (playerA.getCoupUltimate().getUltName() == "EarthWall") {
                    if (playerA.getCoupUltimate().isActivated) {
                        boolean isA = true;
                        playerA.getCoupUltimate().setWall(racketA, racketSize, isA);
                    }
                }
            }
            if (playerB.getCoupUltimate() != null) {
                if (playerB.getCoupUltimate().getUltName() == "EarthWall") {
                    if (playerB.getCoupUltimate().isActivated) {
                        boolean isA = false;
                        playerB.getCoupUltimate().setWall(racketB, racketSize, isA);
                    }
                }
            }
        } else {
            nextBallX = ballX + deltaT * ballSpeedX;
            nextBallY = ballY + deltaT * ballSpeedY;
        }
        // next, see if the ball would meet some obstacle
        if ((nextBallY < 0 || nextBallY > height) || (ElementSize.collisions_centrees.isSelected() && (nextBallY < 0+ballRadius || nextBallY > height-ballRadius))) {
            ballSpeedY = -ballSpeedY;
            if(playerA.getCoupUltimate()!=null) if(playerA.getCoupUltimate().isActivated) changeTrajectory=false;
            if(playerB.getCoupUltimate()!=null) if(playerB.getCoupUltimate().isActivated) changeTrajectory=false;
            int aleatoire = rand.nextInt(125) - rand.nextInt(125);
            if(changeTrajectory) {
                ballSpeedY += (ballSpeedY>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                trajectoireAleatoire = aleatoire;
                ballSpeedY += (ballSpeedY>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                nextBallY = ballY + deltaT * ballSpeedY;
            }
        }
        if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize) || (ElementSize.collisions_centrees.isSelected() && (nextBallX < 0+ballRadius && nextBallY > racketA && nextBallY < racketA + racketSize))) {
            ballSpeedX = -ballSpeedX;
            if(playerA.getCoupUltimate()!=null) if(playerA.getCoupUltimate().isActivated) changeTrajectory=false;
            if(playerB.getCoupUltimate()!=null) if(playerB.getCoupUltimate().isActivated) changeTrajectory=false;
            int aleatoire = rand.nextInt(125) - rand.nextInt(125);
            if(changeTrajectory) {
                ballSpeedY += (ballSpeedY>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                trajectoireAleatoire = aleatoire;
                ballSpeedY += (ballSpeedY>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                nextBallY = ballY + deltaT * ballSpeedY;
            }
            if (ballSpeedX < 0) {
                if (ballSpeedX > -600) {
                    ballSpeedX -= 10;
                }
            } else {
                if (ballSpeedX < 600) {
                    ballSpeedX += 10;
                }
            }
            if (ballSpeedY < 0) {
                if (ballSpeedY > -600) {
                    ballSpeedY -= 10;
                }
            } else {
                if (ballSpeedY < 600) {
                    ballSpeedY += 10;
                }
            }
            OptionSon.playRebound();
            nextBallX = ballX + deltaT * ballSpeedX;
            // a chaque fois que la balle touche la raquette son chargement augmente;
            if (playerA.getCoupUltimate() != null) {
                if (playerA.getCoupUltimate().canBeLaunch == false) {
                    playerA.getCoupUltimate().incrementeChargement();
                    UltimateAffichage.progressBarA.setProgress(playerA.getCoupUltimate().chargement);
                    if (playerA.getCoupUltimate().isUltReady()) {
                        playerA.getCoupUltimate().setcanBeLaunch(true);
                        OptionSon.playChargedUlt();
                        if (!playerA.getIsBot()) {
                            UltimateAffichage.fondUltA.setText("Press " + playerA.getUlt() + " to ult");
                        } else {
                            UltimateAffichage.fondUltA.setText("  Ready ");
                        }
                    }
                }
            }
        } else if (playerA.getCoupUltimate() != null || playerB.getCoupUltimate() != null) {
            if (playerA.getCoupUltimate() != null) {
                if (playerA.getCoupUltimate().getUltName() == "EarthWall") {
                    for (int i = 0; i < playerA.getCoupUltimate().getWalls().size(); i++) {
                        if (nextBallX <= 0
                                && nextBallY < playerA.getCoupUltimate().getWalls().get(i).getY() + racketSize
                                && nextBallY > playerA.getCoupUltimate().getWalls().get(i).getY()) {
                            ballSpeedX = -ballSpeedX;
                            nextBallX = ballX + deltaT * ballSpeedX;
                            playerA.getCoupUltimate().getWallsToRemove()
                                    .add(playerA.getCoupUltimate().getWalls().get(i));
                        }
                    }
                }
            }
            if (playerB.getCoupUltimate() != null) {
                if (playerB.getCoupUltimate().getUltName() == "EarthWall") {
                    for (int i = 0; i < playerB.getCoupUltimate().getWalls().size(); i++) {
                        if (nextBallX > 1000
                                && nextBallY < playerB.getCoupUltimate().getWalls().get(i).getY() + racketSize
                                && nextBallY > playerB.getCoupUltimate().getWalls().get(i).getY()) {
                            ballSpeedX = -ballSpeedX;
                            nextBallX = ballX + deltaT * ballSpeedX;
                            playerB.getCoupUltimate().getWallsToRemove()
                                    .add(playerB.getCoupUltimate().getWalls().get(i));
                        }
                    }
                }
            }
        }

        if ((nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSize) || (ElementSize.collisions_centrees.isSelected() && (nextBallX > width-ballRadius && nextBallY > racketB && nextBallY < racketB + racketSize))) {
            ballSpeedX = -ballSpeedX;
            if(playerA.getCoupUltimate()!=null) if(playerA.getCoupUltimate().isActivated) changeTrajectory=false;
            if(playerB.getCoupUltimate()!=null) if(playerB.getCoupUltimate().isActivated) changeTrajectory=false;
            int aleatoire = rand.nextInt(125) - rand.nextInt(125);
            if(changeTrajectory) {
                ballSpeedY += (ballSpeedY>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                trajectoireAleatoire = aleatoire;
                ballSpeedY += (ballSpeedY>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                nextBallY = ballY + deltaT * ballSpeedY;
            }
            if (ballSpeedX < 0){
                if (ballSpeedX > -600){
                    ballSpeedX-=10;
                }
            } else {
                if (ballSpeedX < 600) {
                    ballSpeedX += 10;
                }
            }
            if (ballSpeedY < 0) {
                if (ballSpeedY > -600) {
                    ballSpeedY -= 10;
                }
            } else {
                if (ballSpeedY < 600) {
                    ballSpeedY += 10;
                }
            }

            OptionSon.playRebound();
            nextBallX = ballX + deltaT * ballSpeedX;
            if (playerB.getCoupUltimate() != null) {
                if (playerB.getCoupUltimate().canBeLaunch == false) {
                    playerB.getCoupUltimate().incrementeChargement();
                    UltimateAffichage.progressBarB.setProgress(playerB.getCoupUltimate().chargement);
                    if (playerB.getCoupUltimate().isUltReady()) {
                        playerB.getCoupUltimate().setcanBeLaunch(true);
                        OptionSon.playChargedUlt();
                        if (!playerB.getIsBot()) {
                            UltimateAffichage.fondUltB.setText("Press " + playerB.getUlt() + " to ult");
                        } else {
                            UltimateAffichage.fondUltB.setText("  Ready  ");
                        }
                    }
                }
            }
        } else if (nextBallX <= 0) {
            // Méthode qui compte les points du joueur de face
            ScoreBoard.playerBScore();
            if (playerB.getCoupUltimate() != null) {
                playerB.getCoupUltimate().setOnUlt(false);
                playerB.getCoupUltimate().isActivated = false;
                ball.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(256,256,256,100),10, 0, 0, 0)");
            }
            return true;
        } else if (nextBallX >= width) {
            // Méthode qui compte les points du joueur de face
            ScoreBoard.playerAScore();
            if (playerA.getCoupUltimate() != null) {
                playerA.getCoupUltimate().setOnUlt(false);
                playerA.getCoupUltimate().isActivated = false;
                ball.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(256,256,256,100),10, 0, 0, 0)");
            }
            return true;
        }
        // Met à jour le text pour que les affichages
        ScoreBoard.majScores();
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }

    public double getBallRadius() {
        return ballRadius;
    }

    /** reset la position de la balle et la prochaine direction (random) */
    void reset() {

        // on rend aléatoire la direction (gauche/droite || haut/bas de la balle au
        // moment de reset).
        Random rand = new Random();

        this.ballX = (width / 2);
        this.ballY = (height / 2);

        trajectoireAleatoire = 0;
    }

    public void updatelb(double deltaT, Circle ball) {

        this.controlPlayerA(deltaT);
        this.botBstrategieLv1(deltaT);

        this.uptadeUlt(deltaT, ball);
        if (updateBallLb(deltaT, ball)) {
            reset();
        }
    }

    private boolean updateBallLb(double deltaT, Circle ball) {
        double nextBallX;
        double nextBallY;
        Random rand = new Random();
        boolean changeTrajectory = true;
        // first, compute possible next position if nothing stands in the way
        if (playerA.getCoupUltimate() != null || playerB.getCoupUltimate() != null) {

            Player p;
            if (playerA.getCoupUltimate() != null) {
                if (playerB.getCoupUltimate() != null) {
                    // les deux joueurs possède un coups spécial
                    // En théorie seul 1 coups spécial peut avoir isActivated == true a la fois
                    p = (playerA.getCoupUltimate().isActivated) ? playerA : playerB;
                } else {
                    p = playerA;
                }
            } else {
                p = playerB;
            }
            if (p.getCoupUltimate().isActivated) {
                switch (p.getCoupUltimate().getUltName()) {
                    case "BalleRapideUlt":
                        nextBallX = BalleRapideUlt.nextBallX(deltaT, ballX, ballSpeedX);
                        nextBallY = BalleRapideUlt.nextBallY(deltaT, ballY, ballSpeedY);
                        break;
                    case "BalleWave":
                        nextBallX = BalleWave.nextBallX(deltaT, ballX, ballSpeedX);
                        nextBallY = BalleWave.nextBallY(deltaT, ballY, ballSpeedY);
                        break;
                    default:
                        nextBallX = ballX + deltaT * ballSpeedX;
                        nextBallY = ballY + deltaT * ballSpeedY;
                        break;
                }
            } else {
                nextBallX = ballX + deltaT * ballSpeedX;
                nextBallY = ballY + deltaT * ballSpeedY;
            }
        } else {
            nextBallX = ballX + deltaT * ballSpeedX;
            nextBallY = ballY + deltaT * ballSpeedY;
        }
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height) {
            ballSpeedY = -ballSpeedY;
            if(playerA.getCoupUltimate()!=null) if(playerA.getCoupUltimate().isActivated) changeTrajectory=false;
            if(playerB.getCoupUltimate()!=null) if(playerB.getCoupUltimate().isActivated) changeTrajectory=false;
            int aleatoire = rand.nextInt(125) - rand.nextInt(125);
            if(changeTrajectory) {
                ballSpeedY += (ballSpeedY>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                trajectoireAleatoire = aleatoire;
                ballSpeedY += (ballSpeedY>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                nextBallY = ballY + deltaT * ballSpeedY;
            }
        }
        if (nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize) {
            ballSpeedX = -ballSpeedX;
            if(playerA.getCoupUltimate()!=null) if(playerA.getCoupUltimate().isActivated) changeTrajectory=false;
            if(playerB.getCoupUltimate()!=null) if(playerB.getCoupUltimate().isActivated) changeTrajectory=false;
            int aleatoire = rand.nextInt(125) - rand.nextInt(125);
            if(changeTrajectory) {
                ballSpeedY += (ballSpeedY>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                trajectoireAleatoire = aleatoire;
                ballSpeedY += (ballSpeedY>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                nextBallY = ballY + deltaT * ballSpeedY;
            }
            if (ballSpeedX < 0){
                if (ballSpeedX > -600){
                    ballSpeedX-=10;
                }
            } else {
                if (ballSpeedX < 600) {
                    ballSpeedX += 10;
                }
            }
            if (ballSpeedY < 0) {
                if (ballSpeedY > -600) {
                    ballSpeedY -= 10;
                }
            } else {
                if (ballSpeedY < 600) {
                    ballSpeedY += 10;
                }
            }
            OptionSon.playRebound();
            nextBallX = ballX + deltaT * ballSpeedX;
            // a chaque fois que la balle touche la raquette son chargement augmente;
            if (playerA.getCoupUltimate() != null) {
                if (playerA.getCoupUltimate().canBeLaunch == false) {
                    playerA.getCoupUltimate().incrementeChargement();
                    // UltimateAffichage.fondUltA.setText(playerA.getCoupUltimate().chargement + "/3
                    // ult is charging");
                    UltimateAffichage.progressBarA.setProgress(playerA.getCoupUltimate().chargement);
                    if (playerA.getCoupUltimate().isUltReady()) {
                        playerA.getCoupUltimate().setcanBeLaunch(true);
                        OptionSon.playChargedUlt();
                        if (!playerA.getIsBot()) {
                            UltimateAffichage.fondUltA.setText("Press " + playerA.getUlt() + " to ult");
                        }
                    }
                }
            }
        }

        if (nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSize) {
            ballSpeedX = -ballSpeedX;
            if(playerA.getCoupUltimate()!=null) if(playerA.getCoupUltimate().isActivated) changeTrajectory=false;
            if(playerB.getCoupUltimate()!=null) if(playerB.getCoupUltimate().isActivated) changeTrajectory=false;
            int aleatoire = rand.nextInt(125) - rand.nextInt(125);
            if(changeTrajectory) {
                ballSpeedY += (ballSpeedY>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                trajectoireAleatoire = aleatoire;
                ballSpeedY += (ballSpeedY>0) ? trajectoireAleatoire : -trajectoireAleatoire;
                ballSpeedX += (ballSpeedX>0) ? -trajectoireAleatoire : trajectoireAleatoire;
                nextBallY = ballY + deltaT * ballSpeedY;
            }
            if (ballSpeedX < 0){
                if (ballSpeedX > -600){
                    ballSpeedX-=10;
                }
            } else {
                if (ballSpeedX < 600) {
                    ballSpeedX += 10;
                }
            }
            if (ballSpeedY < 0) {
                if (ballSpeedY > -600) {
                    ballSpeedY -= 10;
                }
            } else {
                if (ballSpeedY < 600) {
                    ballSpeedY += 10;
                }
            }

            OptionSon.playRebound();
            nextBallX = ballX + deltaT * ballSpeedX;

        } else if (nextBallX < 0) {
            // Méthode qui compte les points du joueur de face
            ScoreBoard.playerBScore();
            GameView.lbLoss = true;
            return true;
        } else if (nextBallX > width) {
            // Méthode qui compte les points du joueur de face
            ScoreBoard.playerAScore();
            if (playerA.getCoupUltimate() != null) {
                playerA.getCoupUltimate().setOnUlt(false);
                playerA.getCoupUltimate().isActivated = false;
                ball.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(256,256,256,100),10, 0, 0, 0)");
            }
            LeaderBoard.marquePoint();
            return true;
        }
        // Met à jour le text pour que les affichages
        ScoreBoard.majScores();
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }

    public void uptadeUlt(double deltaT, Circle ball) {
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;

        if (playerA.getCoupUltimate() != null) {
            // si playerA appuye sur son coups spécial
            if (playerA.getStateUlt()) {
                // si playerA possède un coups spécial et qu'il est chargé alors :
                if (playerA.getCoupUltimate() != null && playerA.getCoupUltimate().canBeLaunch) {
                    playerA.getCoupUltimate().onUlt = true;
                    playerA.getCoupUltimate().isreadyToBounce = true;
                    UltimateAffichage.fondUltA.setText("  Activated");
                }
            }
            if (playerA.getCoupUltimate().getUltName() == "EarthWall") {
                if (playerA.getCoupUltimate().canBeLaunch && playerA.getStateUlt()) {
                    // activation
                    playerA.getCoupUltimate().setcanBeLaunch(false);
                    playerA.getCoupUltimate().isreadyToBounce = false;
                    playerA.getCoupUltimate().isActivated = true;
                    playerA.getCoupUltimate().setChargement(-0.4);
                    UltimateAffichage.fondUltA.setText("  On cooldown");
                    MediaPlayer ultSound = OptionSon.playUlt(playerA.getCoupUltimate().getUltName());
                    ultSound.play();
                    // arret
                    Joueurs.getA().getCoupUltimate().setOnUlt(false);
                }
            }
            // activation du coups spécial
            if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize
                    && playerA.getCoupUltimate().isreadyToBounce) 
                    || (ElementSize.collisions_centrees.isSelected() && nextBallX < 0+ballRadius && nextBallY > racketA && nextBallY < racketA + racketSize
                    && playerA.getCoupUltimate().isreadyToBounce)) {
                playerA.getCoupUltimate().setcanBeLaunch(false);
                playerA.getCoupUltimate().isreadyToBounce = false;
                playerA.getCoupUltimate().isActivated = true;
                playerA.getCoupUltimate().setChargement(-0.4);
                UltimateAffichage.fondUltA.setText("  On cooldown ... ");
                MediaPlayer ultSound = OptionSon.playUlt(playerA.getCoupUltimate().getUltName());
                ultSound.play();
                switch (playerA.getCoupUltimate().getUltName()) {
                    case "BalleRapideUlt":
                        ballSpeedX = BalleRapideUlt.launchUlt(ballSpeedX);
                        ball.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(256,0,0,100),10, 0.7, 0, 0)");
                        break;
                    case "BalleWave":
                        ball.setStyle("-fx-effect: dropshadow(three-pass-box,rgba(10,192,255,0.8),10, 0.7, 0, 0)");
                        break;
                    case "ThunderBall":
                        ball.setStyle("-fx-effect: dropshadow(three-pass-box,rgba(221, 255, 0, 1),10, 0.7, 0, 0)");
                        break;
                }
            }

            // condition d'arret du coups spécial A
            if (playerA.getCoupUltimate() != null && playerA.getCoupUltimate().onUlt) {
                if (nextBallX > width || (ElementSize.collisions_centrees.isSelected() && nextBallX > width-ballRadius)) {
                    // && nextBallY > racketB && nextBallY <= racketB + racketSize
                    if (playerA.getCoupUltimate().isreadyToBounce == false) {
                        Joueurs.getA().getCoupUltimate().setOnUlt(false);
                        playerA.getCoupUltimate().isActivated = false;
                        switch (playerA.getCoupUltimate().getUltName()) {
                            case "BalleRapideUlt":
                                ballSpeedX = BalleRapideUlt.removeUlt(ballSpeedX);
                                ball.setStyle(
                                        "-fx-effect: dropshadow(three-pass-box, rgba(256,256,256,100),10, 0, 0, 0)");
                                break;
                            case "BalleWave":
                            case "ThunderBall":
                                ball.setStyle(
                                        "-fx-effect: dropshadow(three-pass-box, rgba(256,256,256,100),10, 0, 0, 0)");
                                break;
                        }
                    }
                }
            }
        }
        if (playerB.getCoupUltimate() != null) {
            if (playerB.getStateUlt()) {
                // si playerB possède un coups spécial et qu'il est chargé alors :
                if (playerB.getCoupUltimate() != null && playerB.getCoupUltimate().canBeLaunch) {
                    playerB.getCoupUltimate().onUlt = true;
                    playerB.getCoupUltimate().isreadyToBounce = true;
                    UltimateAffichage.fondUltB.setText("  Activated");//
                }
            }
            if (playerB.getCoupUltimate().getUltName() == "EarthWall") {
                if (playerB.getCoupUltimate().canBeLaunch && playerB.getStateUlt()) {
                    // activation
                    playerB.getCoupUltimate().setcanBeLaunch(false);
                    playerB.getCoupUltimate().isreadyToBounce = false;
                    playerB.getCoupUltimate().isActivated = true;
                    playerB.getCoupUltimate().setChargement(-0.4);
                    UltimateAffichage.fondUltB.setText("  On cooldown");
                    MediaPlayer ultSound = OptionSon.playUlt(playerB.getCoupUltimate().getUltName());
                    ultSound.play();
                    // arret
                    Joueurs.getB().getCoupUltimate().setOnUlt(false);
                }
            }
            if (nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSize
                    && playerB.getCoupUltimate().isreadyToBounce
                    || (ElementSize.collisions_centrees.isSelected() && nextBallX > width-ballRadius && nextBallY > racketB && nextBallY < racketB + racketSize
                    && playerB.getCoupUltimate().isreadyToBounce)) {
                playerB.getCoupUltimate().setcanBeLaunch(false);

                playerB.getCoupUltimate().isreadyToBounce = false;
                playerB.getCoupUltimate().isActivated = true;
                playerB.getCoupUltimate().setChargement(-0.4);
                UltimateAffichage.fondUltB.setText("  On cooldown ... ");
                MediaPlayer ultSound = OptionSon.playUlt(playerB.getCoupUltimate().getUltName());
                ultSound.play();
                switch (playerB.getCoupUltimate().getUltName()) {
                    case "BalleRapideUlt":
                        ballSpeedX = BalleRapideUlt.launchUlt(ballSpeedX);
                        ball.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(256,0,0,100),10, 0.7, 0, 0)");
                        break;
                    case "BalleWave":
                        ball.setStyle("-fx-effect: dropshadow(three-pass-box,rgba(10,192,255,0.8),10, 0.7, 0, 0)");
                        break;
                    case "ThunderBall":
                        ball.setStyle("-fx-effect: dropshadow(three-pass-box,rgba(221, 255, 0, 1),10, 0.7, 0, 0)");
                        break;
                }
            }

            // condition d'arret du coups spécial B
            if (playerB.getCoupUltimate() != null && playerB.getCoupUltimate().onUlt) {
                if (nextBallX < 0 || (ElementSize.collisions_centrees.isSelected() && nextBallX<0+ballRadius)) {
                    // && nextBallY > racketA && nextBallY < racketA + racketSize
                    if (playerB.getCoupUltimate().isreadyToBounce == false) {
                        playerB.getCoupUltimate().setOnUlt(false);
                        playerB.getCoupUltimate().isActivated = false;
                        switch (playerB.getCoupUltimate().getUltName()) {
                            case "BalleRapideUlt":
                                ballSpeedX = BalleRapideUlt.removeUlt(ballSpeedX);
                                ball.setStyle(
                                        "-fx-effect: dropshadow(three-pass-box, rgba(256,256,256,100),10, 0, 0, 0)");
                                break;
                            case "BalleWave":
                            case "ThunderBall":
                                ball.setStyle(
                                        "-fx-effect: dropshadow(three-pass-box, rgba(256,256,256,100),10, 0, 0, 0)");
                                break;
                        }
                    }
                }
            }
        }
    }

    public void botAstrategieLv2(double deltaT) {
        double ralentissement;
        if (ballX < width * 1.7 / 10) {
            ralentissement = 1.2;
        } else if (ballX > width * 8.3 / 10) {
            ralentissement = 4;
        } else if (ballX < width * 2.2 / 10) {
            ralentissement = 2;
        } else {
            ralentissement = 10000;
        }

        if (racketA + racketSize / 2 > ballY) {
            racketA -= racketBotSpeed * deltaT / ralentissement;
        }
        if (racketA + racketSize / 2 < ballY) {
            racketA += racketBotSpeed * deltaT / ralentissement;
        }
        // botA use Ult
        if (playerA.getCoupUltimate() != null) {
            if (playerA.getCoupUltimate().canBeLaunch && ballX < (width / 2) + 50 && ballX > (width / 2) - 50) {
                playerA.stateUlt = ((new Random()).nextInt(100) == 1);
            } else {
                playerA.stateUlt = false;
            }
        }
        // on empêche la raquette bot de sortir hors de l'écran
        if (racketA + racketSize > height) {
            racketA = height - racketSize;
        }
        if (racketA < 0) {
            racketA = 0;
        }
    }

    public void botAstrategieLv1(double deltaT) {
        double ralentissement=1.3;

        if (racketA + racketSize / 2 > ballY) {
            racketA -= racketBotSpeed * deltaT / ralentissement;
        }
        if (racketA + racketSize / 2 < ballY) {
            racketA += racketBotSpeed * deltaT / ralentissement;
        }
        // botA use Ult
        if (playerA.getCoupUltimate() != null) {
            if (playerA.getCoupUltimate().canBeLaunch && ballX < (width / 2) + 50 && ballX > (width / 2) - 50) {
                playerA.stateUlt = ((new Random()).nextInt(100) == 1);
            } else {
                playerA.stateUlt = false;
            }
        }
        // on empêche la raquette bot de sortir hors de l'écran
        if (racketA + racketSize > height) {
            racketA = height - racketSize;
        }
        if (racketA < 0) {
            racketA = 0;
        }

    }

    public void botBstrategieLv1(double deltaT) {
        // Le bot sera influencer selon la position de la balle sur le terrain
        double ralentissement = 1.3;

        if (racketB + racketSize / 2 > ballY) {
            racketB -= racketBotSpeed * deltaT / ralentissement;
        }
        if (racketB + racketSize / 2 < ballY) {
            racketB += racketBotSpeed * deltaT / ralentissement;
        }

        // botB use Ult
        if (playerB.getCoupUltimate() != null) {
            if (playerB.getCoupUltimate().canBeLaunch && ballX < (width / 2) + 50 && ballX > (width / 2) - 50) {
                playerB.stateUlt = ((new Random()).nextInt(100) == 1);
            } else {
                playerB.stateUlt = false;
            }
        }
        // on empêche la raquette bot de sortir hors de l'écran
        if (racketB + racketSize > height) {
            racketB = height - racketSize;
        }
        if (racketB < 0) {
            racketB = 0;
        }
    }

    public void botBstrategieLv2(double deltaT) {
        // Le bot sera influencer selon la position de la balle sur le terrain
        double ralentissement;
        if (ballX > width * 8.3 / 10) {
            ralentissement = 1.2;
        } else if (ballX > width * 7.8 / 10) {
            ralentissement = 2;
        } else if (ballX < width * 3 / 10) {
            ralentissement = 4;
        } else {
            ralentissement = 10000;
        }

        if (racketB + racketSize / 2 > ballY) {
            racketB -= racketBotSpeed * deltaT / ralentissement;
        }
        if (racketB + racketSize / 2 < ballY) {
            racketB += racketBotSpeed * deltaT / ralentissement;
        }

        // botB use Ult
        if (playerB.getCoupUltimate() != null) {
            if (playerB.getCoupUltimate().canBeLaunch && ballX < (width / 2) + 50 && ballX > (width / 2) - 50) {
                playerB.stateUlt = ((new Random()).nextInt(100) == 1);
            } else {
                playerB.stateUlt = false;
            }
        }
        // on empêche la raquette bot de sortir hors de l'écran
        if (racketB + racketSize > height) {
            racketB = height - racketSize;
        }
        if (racketB < 0) {
            racketB = 0;
        }
    }

    public void controlPlayerA(double deltaT) {
        switch (playerA.getState()) {
            case GOING_UP:
                racketA -= racketASpeed * deltaT;
                racketASpeed += 10;
                if (racketA <= 0.0) {
                    racketA = 0.0;
                    racketASpeed = 0;
                }
                break;
            case IDLE:
                if (racketASpeed > 50) {
                    racketASpeed -= 80;
                }
                if (racketASpeed < 50) {
                    racketASpeed = 50;
                }
                break;
            case GOING_DOWN:
                racketA += racketASpeed * deltaT;
                racketASpeed += 10;
                if (racketA + racketSize >= height) {
                    racketA = height - racketSize;
                    racketASpeed = 0;
                }
                break;
        }
    }

    public void controlPlayerB(double deltaT) {
        switch (playerB.getState()) {
            case GOING_UP:
                racketB -= racketBSpeed * deltaT;
                racketBSpeed += 10;
                if (racketB <= 0.0) {
                    racketB = 0.0;
                    racketBSpeed = 0;
                }
                break;
            case IDLE:
                if (racketBSpeed > 50) {
                    racketBSpeed -= 80;
                }
                if (racketBSpeed < 50) {
                    racketBSpeed = 50;
                }
                break;
            case GOING_DOWN:
                racketB += racketBSpeed * deltaT;
                racketBSpeed += 10;
                if (racketB + racketSize >= height) {
                    racketB = height - racketSize;
                    racketBSpeed = 0;
                }
                break;
        }
    }
}
