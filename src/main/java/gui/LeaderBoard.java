package gui;


import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Ultimate.UltimateAffichage;

import java.io.*;

public class LeaderBoard {

    // La variable suivante est là pour garder quelle seront les nouvelles choses
    // dans le fichier
    private String leaderBoardtoadd;
    // La variable suivante est là pour récupérer le texte dans le fichier
    private String leaderBoardPrevious;
    // Pour ne pas avoir d'erreur dans le nom du fichier, on l'écrit une fois
    public static String fileName= "config/LeaderBoard";
    public static int point = 0;
    public static Text txtPoint = new Text(String.valueOf(point));
    public static Text txtTime = new Text();
    public static Text txtLoose = new Text("Echec du challenge , retentez votre chance ! ");
    public static Text txtWin = new Text("Temps écoulé vous pouvez enregistrer votre score");
    public static StackPane pPointpane = new StackPane();

    // Méthode pour mettre le nombre de points à jour
    public static void marquePoint() {
        point += 1;
        LeaderBoardTab.point += 1;
        majScores();
    }

    public int getPoint() {
        return point;
    }

    // Méthode qui va lire le fichier et garder son contenue dans
    // leaderBoardPrevious
    public static String readLeaderBoard(String fileName) {
        String content = "";
        try {
            File doc = new File(fileName);

            BufferedReader obj = new BufferedReader(new FileReader(doc));

            String string;
            while ((string = obj.readLine()) != null) {
                content = string;
            }
            obj.close();
        } catch (IOException e) {
            System.out.println("Erreur dans le nom du fichier");
        }
        return content;
    }

    // Méthode pour metre jour la variable dans le fichier et nos variables
    public void refreshLeaderBoard() {
        if (!this.leaderBoardtoadd.equals("")) {
            writeLeaderBoard(fileName, this.leaderBoardtoadd);
            this.leaderBoardPrevious += this.leaderBoardtoadd;
        }
    }

    // Méthodes pour écrire dans le fichier
    public static void writeLeaderBoard(String fileName, String newContent) {
        try {

            String oldContent = readLeaderBoard(fileName);
            String content;
            if (oldContent.length() == 0) {
                content = newContent;
            } else {
                content = oldContent + newContent;
            }

            File file = new File(fileName);
            // créer le fichier s'il n'existe pas
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode qui créait le fichier si nécessaire
    public static boolean createLeaderBoard(String fileName) {
        try {
            File file = new File(fileName);
            // créer le fichier s'il n'existe pas
            if (!file.exists()) {
                file.createNewFile();
                return false;
            }
            return true;
        } catch (IOException e) {
            return true;
        }
    }

    // Creation de l'objet leaderBoard et qui va lire le fichier
    public LeaderBoard() {
        createLeaderBoard(fileName);
        this.setLeaderBoardPrevious(readLeaderBoard(fileName));
    }

    // On met en variable le résultat d'une partit que l'on veut sauvegarder
    public void setLeaderBoardtoadd(String nom) {
        this.leaderBoardtoadd = "|" + nom + "/" + point + '|';
    }

    public String getLeaderBoardtoadd() {
        return leaderBoardtoadd;
    }

    public void setLeaderBoardPrevious(String leaderBoardPrevious) {
        this.leaderBoardPrevious = leaderBoardPrevious;
    }

    public String getLeaderBoardPrevious() {
        return leaderBoardPrevious;
    }

    // Méthode qui créait le bouton de lancement de parti le place et créait l'objet
    // leaderBoard
    public static Button btnLB(Stage stage) {
        Button btnRes = new Button("");
        btnRes.setTranslateY(210);
        btnRes.setTranslateX(80);
        btnRes.setMinHeight(24);
        btnRes.setMinWidth(50);
        txtTime.setY(300);
        btnRes.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Parametre.modeDejeu = "ModeChallenge";
            if (Menu.userPosition.getLast().equals("ResumeQMOption")){
                Menu.userPosition.offer("MenuMultiQM");
                ChoixUltimate choixUltimate=new ChoixUltimate();
                choixUltimate.start(stage);
                Parametre.modeDejeu = "ModeChallenge";
            }else {
                Skin skin = new Skin();
                skin.start(stage);
                Menu.userPosition.offer("MenuMulti");
            }
            event.consume();
        });
        return btnRes;
    }

    // Méthode qui met à jour l'affichage des scores
    public static void majScores() {
        txtPoint.setText(String.valueOf(point));
    }

    // Méthode qui permet d'afficher le chrono avec la bonne notation
    // Et qui met ajour les autres texts et la progressions bars
    public void majText(int i) {
        String s = String.valueOf(i);
        String r = "";
        if (s.length() == 1) {
            r = "0'00" + s;
        } else if (s.length() == 2) {
            r = "0'0" + s;
        } else if (s.length() == 3) {
            r = "0'" + s;
        } else if (s.length() == 4) {
            r = s.charAt(0) + "'" + s.substring(1);
        } else if (s.length() == 5) {
            r = s.charAt(0) + "" + s.charAt(1) + "'" + s.substring(2);
        }
        txtTime.setText(r);
        setTimeIndicator((i / 3) * 10e-5);
    }

    public static ProgressBar progressBarTime = new ProgressBar(0);

    // méthode qui rempli la barre de progression
    public static void setTimeIndicator(double i) {
        progressBarTime.setProgress(i);
    }

    // Méthode lancer apres la victoire (temps écoulé)
    // ET propose de rejouer
    public static Button win(Stage stage) {
        Button res = new Button("Rejouer");
        res.setMinWidth(60);
        res.setMinHeight(24);
        res.setTranslateX(550 - res.getMinWidth() / 2 - 28);
        res.setTranslateY(400);
        App game = new App();
        GameView.isWin = false;
        GameView.lbWin = false;
        point = 0;
        res.setOnAction(event -> {
            OptionSon.playSelectedButton();
            if(Joueurs.getA().getCoupUltimate() != null){
                Joueurs.getA().getCoupUltimate().isActivated = false;
            }
            if(Joueurs.getB().getCoupUltimate() != null){
                Joueurs.getB().getCoupUltimate().isActivated = false;
            }
            UltimateAffichage.resetUltimate();
            OptionSon.mediaPlayerMusique.stop();
            OptionSon.mediaPlayerMusique.play();
            game.start(stage);
            GameView.i = 0;
            event.consume();
        });
        return res;
    }

    // Revoit le bouton de sauvegarde des points et prend en argument si on a des
    // données à sauvegarder ou non
    public static Button saveStat(Stage stage, boolean isMaJable) {
        Button res = new Button("Statistique");
        LeaderBoard.txtWin.setY(300);
        LeaderBoard.txtWin.setWrappingWidth(750);
        LeaderBoard.txtWin.setX(200);
        res.setMinWidth(60);
        res.setMinHeight(24);
        res.setTranslateX(550 - res.getMinWidth() / 2 - 45);
        res.setTranslateY(460);
        res.setOnAction(event -> {
            OptionSon.playSelectedButton();
            LeaderBoardTab o = new LeaderBoardTab();
            OptionSon.mediaPlayerMusique = new AudioClip(new File(OptionSon.stringMusicMenuFile).toURI().toString());
            OptionSon.muteSound();
            OptionSon.mediaPlayerMusique.setCycleCount(MediaPlayer.INDEFINITE);
            OptionSon.mediaPlayerMusique.stop();
            OptionSon.mediaPlayerMusique.play();
            o.OpenLeaderBoard(stage, isMaJable);
            event.consume();
        });
        return res;
    }

    // --Mise en place des different compteurs et cadre sur la fenêtre--
    public static Rectangle fondScore = new Rectangle();
    // public static Rectangle fondTime = new Rectangle();

    public static void setFond() {
        fondScore.setHeight(90);
        fondScore.setWidth(110);
        fondScore.setY(5);
        fondScore.setX(495);
    }

    public static void setVisuel() {
        setFond();
        if (!pPointpane.getChildren().contains(txtPoint)) {
            pPointpane.getChildren().addAll(txtPoint);
        }
        pPointpane.setPrefHeight(90);
        pPointpane.setPrefWidth(110);
        pPointpane.setTranslateX(495);
        pPointpane.setTranslateY(5);
        progressBarTime.setMinHeight(30);
        progressBarTime.setMinWidth(200);
        progressBarTime.setTranslateX(450);
        progressBarTime.setTranslateY(100 + 8);
        txtTime.setX(progressBarTime.getTranslateX() + progressBarTime.getWidth() + 15);
        txtTime.setY(progressBarTime.getTranslateY() + (progressBarTime.getHeight() / 1.5));
        txtPoint.setY(65);
        LeaderBoard.txtLoose.setY(300);
        LeaderBoard.txtLoose.setX(245);
    }
    // --fin de la mise en place--
}