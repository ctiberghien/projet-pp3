package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.util.LinkedList;

public class Menu extends Application {
    static int graphismes;
    static String current = "";
    static boolean toLaunchAgain = false;
    // indique ou ce situe l'utilisateur;
    public static LinkedList<String> userPosition = new LinkedList<String>();

    // On met un setter pour modifier si besoin
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Pong");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/iconBonk.png")));
        Pane root = new Pane();
        var scene = new Scene(root, 1100, 600);

        //Fonction appeler pour set tout les parameters provenant des fichiers
        ParametresSaved.initailiseTab();
        Parametre.readficParameters();

        OptionSon.mediaPlayerSelected.setVolume(OptionSon.nivSonBruit);
        OptionSon.mediaPlayerBonk.setVolume(OptionSon.nivSonBruit);

        if (OptionSon.mediaPlayerMusique == null) {
            OptionSon.mediaPlayerMusique = new AudioClip(new File(OptionSon.stringMusicMenuFile).toURI().toString());
            OptionSon.mediaPlayerMusique.setVolume(OptionSon.nivSonMusique);
            OptionSon.mediaPlayerMusique.play();
        }

        // --Code des boutons--

        Button btnJeu = new Button();
        Button btnOption = new Button();
        Button btnClassement = new Button();
        Button btnQuit = new Button();
        Button btnAide = new Button();

        // --Code des Régions--

        Region P = new Region();
        P.setMinSize(30, 30);
        P.setTranslateX(640);
        P.setTranslateY(310);

        Region C = new Region();
        C.setMinSize(30, 30);
        C.setTranslateX(745);
        C.setTranslateY(410);

        Region escape = new Region();
        escape.setMinSize(94, 30);
        escape.setTranslateX(640);
        escape.setTranslateY(530);

        Region O = new Region();
        O.setMinSize(30, 30);
        O.setTranslateX(985);
        O.setTranslateY(30);

        Region AQ = new Region();
        AQ.setMinSize(55, 30);
        AQ.setTranslateX(110);
        AQ.setTranslateY(30);

        // ----------------------Bouton Play--------------------
        btnJeu.setText("");
        btnJeu.setMinWidth(170);
        btnJeu.setMinHeight(55);
        btnJeu.setTranslateX(550 - btnJeu.getMinWidth() / 2);
        btnJeu.setTranslateY(300);

        // ----- Bouton Option----------------------------
        btnOption.setMinWidth(70);
        btnOption.setMinHeight(70);
        btnOption.setTranslateX(1100 - btnOption.getMinWidth() - 13);
        btnOption.setTranslateY(13);
        // Bouton quit
        btnQuit.setMinWidth(170);
        btnQuit.setMinHeight(55);
        btnQuit.setTranslateX(550 - btnQuit.getMinWidth() / 2);
        btnQuit.setTranslateY(520);
        btnQuit.setCancelButton(true);

        // Bouton classement
        btnClassement.setMinWidth(390);
        btnClassement.setMinHeight(55);
        btnClassement.setTranslateX(550 - btnClassement.getMinWidth() / 2);
        btnClassement.setTranslateY(400);

        // ----- Bouton Aide----------------------------
        btnAide.setMinWidth(70);
        btnAide.setMinHeight(70);
        btnAide.setTranslateX(30);
        btnAide.setTranslateY(13);



        // ----- Action des Button ------------------
        btnJeu.setOnAction(event -> {
            OptionSon.playSelectedButton();
            if (ParametresSaved.isSaved){
                if (ParametresSaved.tabParameters!=null){
                    ResumeGame r = new ResumeGame();
                    Menu.userPosition.offer("QuickMatch");
                    r.start(primaryStage);
                    ParametresSaved.setParametersToGame();
                }
            }else{
                MenuMulti m = new MenuMulti();
                Menu.userPosition.offer("MenuMulti");
                OptionSon.playSelectedButton();
                m.start(primaryStage);
            }
            event.consume();
        });
        btnOption.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Option o = new Option();
            o.OpenOption(primaryStage);
            OptionSon.playSelectedButton();
            Menu.userPosition.offer("Option");
            event.consume();
        });

        btnClassement.setOnAction(event -> {
            OptionSon.playSelectedButton();
            LeaderBoardTab lbt = new LeaderBoardTab();
            OptionSon.playSelectedButton();
            LeaderBoardTab.lb.setLeaderBoardPrevious(LeaderBoard.readLeaderBoard("./config/LeaderBoard"));
            lbt.OpenLeaderBoard(primaryStage, false);
            Menu.userPosition.offer("Classement");
            event.consume();
        });

        btnQuit.setOnAction(event -> {
            OptionSon.playSelectedButton();
            ParametresSaved.saveParameters();
            Parametre.writeficParametres();
            primaryStage.close();
            event.consume();
        });

        btnAide.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Aide pageAide = new Aide();
            pageAide.start(primaryStage, false);
            Menu.userPosition.offer("Aide");
            event.consume();
        });

        // --Fin du code des boutons--

        // --Image of the title--

        Region title = new Region();
        title.setMinSize(400, 130);
        title.setTranslateX(550 - title.getMinWidth() / 2);
        title.setTranslateY(120 - title.getMinHeight() / 2);

        // Easter egg du jeu

        String password = "bonk";
        current = "";
        Text FirstLetter = new Text("_");
        FirstLetter.setTranslateX(10);
        FirstLetter.setTranslateY(10);
        FirstLetter.setFont(new Font(10));
        Text SecondLetter = new Text("_");
        SecondLetter.setTranslateX(20);
        SecondLetter.setTranslateY(10);
        SecondLetter.setFont(new Font(10));
        Text ThirdLetter = new Text("_");
        ThirdLetter.setTranslateX(30);
        ThirdLetter.setTranslateY(10);
        ThirdLetter.setFont(new Font(10));
        Text FourthLetter = new Text("_");
        FourthLetter.setTranslateX(40);
        FourthLetter.setTranslateY(10);
        FourthLetter.setFont(new Font(10));
        scene.setOnKeyPressed(key -> {
            //Pour que l'on puisse activer le bouton start avec la touche s
            if(key.getCode() == KeyCode.P){
                OptionSon.playSelectedButton();
                btnJeu.fire();
            }
    
            //Pour que l'on puisse activer le bouton options avec la touche o
            else if(key.getCode() == KeyCode.O){
                if(current.length() != 1){
                    OptionSon.playSelectedButton();
                    Option o = new Option();
                    o.OpenOption(primaryStage);
                    OptionSon.playSelectedButton();
                    Menu.userPosition.offer("Option");
                }
            }
            else if(key.getCode() == KeyCode.Q || key.getCode() == KeyCode.A){
                OptionSon.playSelectedButton();
                Aide pageAide = new Aide();
                pageAide.start(primaryStage, false);
                Menu.userPosition.offer("Aide");
                key.consume();
            }
            else if(key.getCode() == KeyCode.C){
                OptionSon.playSelectedButton();
                LeaderBoardTab lbt = new LeaderBoardTab();
                OptionSon.playSelectedButton();
                LeaderBoardTab.lb.setLeaderBoardPrevious(LeaderBoard.readLeaderBoard("./config/LeaderBoard"));
                lbt.OpenLeaderBoard(primaryStage, false);
                Menu.userPosition.offer("Classement");
                key.consume();
            }
            if (key.getText().length() > 0) {
                if ((key.getText().charAt(0) >= 65 && key.getText().charAt(0) <= 90)
                        || (key.getText().charAt(0) >= 97 && key.getText().charAt(0) <= 122)) {
                    current += key.getText();
                    FirstLetter.setFill(Color.BLACK);
                    SecondLetter.setFill(Color.BLACK);
                    ThirdLetter.setFill(Color.BLACK);
                    FourthLetter.setFill(Color.BLACK);
                    if (current.length() == 1)
                        FirstLetter.setText("" + current.charAt(current.length() - 1));
                    if (current.length() == 2)
                        SecondLetter.setText("" + current.charAt(current.length() - 1));
                    if (current.length() == 3)
                        ThirdLetter.setText("" + current.charAt(current.length() - 1));
                    if (current.length() == 4)
                        FourthLetter.setText("" + current.charAt(current.length() - 1));
                    for (int i = 0; i < current.length(); i++) {
                        if (current.charAt(i) != password.charAt(i)) {
                            FirstLetter.setFill(Color.RED);
                            SecondLetter.setFill(Color.RED);
                            ThirdLetter.setFill(Color.RED);
                            FourthLetter.setFill(Color.RED);
                            FirstLetter.setText("_");
                            SecondLetter.setText("_");
                            ThirdLetter.setText("_");
                            FourthLetter.setText("_");
                            current = "";
                        }
                    }
                }
            }
        });

        // --Affichage-

        // ajout du css
        switch (Graphismes.getGraphismes()) {
            case 0:
                root.setId("root");
                graphismes = 0;
                Region img1 = new Region();
                img1.setId("img1");
                Region img2 = new Region();
                img2.setId("img2");
                Region img3 = new Region();
                img3.setId("img3");
                scene.getStylesheets()
                .addAll(getClass().getResource("stylesheet/loadingstylesheet.css").toExternalForm());
                root.getChildren().addAll(img1, img2, img3);
                break;
            case 1:
                root.setId("root1");
                graphismes = 1;
                break;
            case 2:
                root.setId("root2");
                graphismes = 2;
                break;
        }

        scene.getStylesheets().addAll(getClass().getResource("stylesheet/menustylesheet.css").toExternalForm());
        btnAide.getStyleClass().add("button");
        btnAide.setId("aide");
        btnClassement.getStyleClass().add("button");
        btnClassement.setId("classement");
        btnJeu.getStyleClass().add("button");
        btnJeu.setId("play");
        btnOption.getStyleClass().add("button");
        btnOption.setId("option");
        btnQuit.getStyleClass().add("button");
        btnQuit.setId("quit");
        title.setId("title");
        P.setId("p");
        C.setId("c");
        escape.setId("escape");
        O.setId("o");
        AQ.setId("aq");

        primaryStage.setOnCloseRequest((WindowEvent) ->{
            OptionSon.playSelectedButton();
            ParametresSaved.saveParameters();
            Parametre.writeficParametres();
            primaryStage.close();
            WindowEvent.consume();
        });

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        root.getChildren().addAll(btnJeu, btnOption, title, btnQuit, btnClassement, FirstLetter,
                SecondLetter, ThirdLetter, FourthLetter, btnAide,
                P,C,escape,O,AQ);
        root.requestFocus();
        primaryStage.show();
    }

    public static int getGraphismes() {
        return graphismes;
    }

    public static void setGraphismes(int graphismes) {
        Menu.graphismes = graphismes;
    }

    public static void main(String[] args) {
        launch();
    }
}
