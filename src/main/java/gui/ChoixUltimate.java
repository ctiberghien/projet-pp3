package gui;

import static gui.ParametresSaved.btnMajTab;
import static gui.ParametresSaved.hasToSave;
import static gui.ParametresSaved.isSaved;
import static gui.ParametresSaved.majTab;
import static gui.ParametresSaved.setParametersToGame;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Ultimate.BalleRapideUlt;
import model.Ultimate.BalleWave;
import model.Court.Court;
import model.Court.ScoreBoard;
import model.Ultimate.ThunderBallUlt;
import model.Ultimate.WallUlt;

public class ChoixUltimate {
    String modeDejeu = "";

    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.requestFocus();
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);

        // ------------Affichage ---------------------------------------------
        Region r = new Region();
        r.setMaxSize(880, 480);
        r.setTranslateY(20);

        // Déclaration Text

        Text modePlayerA = new Text("Joueurs A activé");
        Text modePlayerB = new Text("Joueurs B activé");
        Text indication = new Text("Préparez-vous au combat , configurez vos joueurs et vos sort !");
        Text ultPlayerAinfo = new Text("Vous n'avez selectionné aucun sort");
        Text ultPlayerBinfo = new Text("Vous n'avez selectionné aucun sort");
        Text nbPoint = new Text();
        Text AQ = new Text("[A-Q]");
        Text B = new Text("[B]");
        Text one = new Text("[1-&]");
        Text two = new Text("[2-é]");
        Text S = new Text("[S]");
        Text V = new Text("[V]");
        Text N = new Text("[N]");
        Text D = new Text("[D]");
        Text F = new Text("[F]");
        Text G = new Text("[G]");
        Text H = new Text("[H]");
        Text M = new Text("[M]");
        Text L = new Text("[L]");
        Text K = new Text("[K]");
        Text J = new Text("[J]");
        Text escape = new Text("[ESCAPE]");
        Text enter  = new Text("[ENTER]");

        // Font des Text

        modePlayerA.setFont(new Font(10));
        modePlayerB.setFont(new Font(10));
        indication.setFont(new Font(20));
        ultPlayerAinfo.setFont(new Font(10));
        ultPlayerBinfo.setFont(new Font(10));
        nbPoint.setFont(new Font(15));

        AQ.setFill(Color.WHITE);
        B.setFill(Color.WHITE);
        one.setFill(Color.WHITE);
        two.setFill(Color.WHITE);
        S.setFill(Color.WHITE);
        V.setFill(Color.WHITE);
        N.setFill(Color.WHITE);
        D.setFill(Color.WHITE);
        F.setFill(Color.WHITE);
        G.setFill(Color.WHITE);
        H.setFill(Color.WHITE);
        M.setFill(Color.WHITE);
        L.setFill(Color.WHITE);
        K.setFill(Color.WHITE);
        J.setFill(Color.WHITE);
        escape.setFill(Color.WHITE);
        enter.setFill(Color.WHITE);

        // Emplacement des Text

        modePlayerA.setTranslateX(-340);
        modePlayerA.setTranslateY(-280);

        modePlayerB.setTranslateX(+340);
        modePlayerB.setTranslateY(-280);

        indication.setTranslateX(0);
        indication.setTranslateY(220);

        ultPlayerAinfo.setTranslateX(-300);
        ultPlayerAinfo.setTranslateY(-100);

        ultPlayerBinfo.setTranslateX(300);
        ultPlayerBinfo.setTranslateY(-100);

        nbPoint.setTranslateY(-265);
        nbPoint.setTranslateX(-20);

        enter.setTranslateX(465);
        enter.setTranslateY(-285);

        escape.setTranslateY(-285);
        escape.setTranslateX(-465);

        S.setTranslateX(25);
        S.setTranslateY(190);

        V.setTranslateY(230);
        V.setTranslateX(-490);

        D.setTranslateX(-535);
        D.setTranslateY(-130);

        F.setTranslateX(-535);
        F.setTranslateY(-60);

        G.setTranslateX(-535);
        G.setTranslateY(10);

        H.setTranslateX(-535);
        H.setTranslateY(80);

        N.setTranslateX(490);
        N.setTranslateY(230);

        M.setTranslateX(535);
        M.setTranslateY(-130);

        L.setTranslateX(535);
        L.setTranslateY(-60);

        K.setTranslateX(535);
        K.setTranslateY(10);

        J.setTranslateX(535);
        J.setTranslateY(80);

        AQ.setTranslateX(-270);
        AQ.setTranslateY(-250);

        B.setTranslateX(270);
        B.setTranslateY(-250);

        two.setTranslateX(460);
        two.setTranslateY(-250);

        one.setTranslateX(-460);
        one.setTranslateY(-250);

        // Déclaration et emplacement des 3 preview

        Rectangle racketA_preview = new Rectangle();
        racketA_preview.setFill(Joueurs.couleurRacketA);
        racketA_preview.setHeight(Court.racketSize);
        racketA_preview.setWidth(10);
        racketA_preview.setTranslateX(-400);

        Rectangle racketB_preview = new Rectangle();
        racketB_preview.setFill(Joueurs.couleurRacketB);
        racketB_preview.setHeight(Court.racketSize);
        racketB_preview.setWidth(10);
        racketB_preview.setTranslateX(400);

        Circle ball_preview = new Circle();
        ball_preview.setFill(Joueurs.couleurBalle);
        ball_preview.setRadius(Court.ballRadius);

        // --------------fin Affichage ----------------------------------

        // Bouton des coups spéciaux :

        Button rapidBallA = new Button();
        rapidBallA.setFont(new Font(25));
        rapidBallA.setMinSize(60, 40);
        rapidBallA.setTranslateX(-490);
        rapidBallA.setTranslateY(-130);
        rapidBallA.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getA().setCoupUltimate(new BalleRapideUlt());
            ultPlayerAinfo.setText("Vous avez selectionné : Fire ball ");
            event.consume();
        });
        Button waveBallA = new Button();
        waveBallA.setFont(new Font(25));
        waveBallA.setMinSize(60, 40);
        waveBallA.setTranslateX(-490);
        waveBallA.setTranslateY(-60);
        waveBallA.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getA().setCoupUltimate(new BalleWave());
            ultPlayerAinfo.setText("Vous avez selectionné : Wave ball ");
            event.consume();
        });
        Button earthWallA = new Button();
        earthWallA.setFont(new Font(25));
        earthWallA.setMinSize(60, 40);
        earthWallA.setTranslateX(-490);
        earthWallA.setTranslateY(10);
        earthWallA.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getA().setCoupUltimate(new WallUlt());
            ultPlayerAinfo.setText("Vous avez selectionné : Earth Wall ");
            event.consume();
        });
        Button thunderBallA = new Button();
        thunderBallA.setFont(new Font(25));
        thunderBallA.setMinSize(60, 40);
        thunderBallA.setTranslateX(-490);
        thunderBallA.setTranslateY(80);
        thunderBallA.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getA().setCoupUltimate(new ThunderBallUlt());
            ultPlayerAinfo.setText("Vous avez selectionné : Thunder Ball ");
            event.consume();
        });
        Button rapidBallB = new Button();
        rapidBallB.setFont(new Font(25));
        rapidBallB.setMinSize(60, 40);
        rapidBallB.setTranslateX(490);
        rapidBallB.setTranslateY(-130);
        rapidBallB.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getB().setCoupUltimate(new BalleRapideUlt());
            ultPlayerBinfo.setText("Vous avez selectionné : Fire ball ");
            event.consume();
        });
        Button waveBallB = new Button();
        waveBallB.setFont(new Font(25));
        waveBallB.setMinSize(60, 40);
        waveBallB.setTranslateX(490);
        waveBallB.setTranslateY(-60);
        waveBallB.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getB().setCoupUltimate(new BalleWave());
            ultPlayerBinfo.setText("Vous avez selectionné : Wave ball ");
            event.consume();
        });
        Button earthWallB = new Button();
        earthWallB.setFont(new Font(25));
        earthWallB.setMinSize(60, 40);
        earthWallB.setTranslateX(490);
        earthWallB.setTranslateY(10);
        earthWallB.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getB().setCoupUltimate(new WallUlt());
            ultPlayerBinfo.setText("Vous avez selectionné : Earth Wall ");
            event.consume();
        });
        Button thunderBallB = new Button();
        thunderBallB.setFont(new Font(25));
        thunderBallB.setMinSize(60, 40);
        thunderBallB.setTranslateX(490);
        thunderBallB.setTranslateY(80);
        thunderBallB.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getB().setCoupUltimate(new ThunderBallUlt());
            ultPlayerBinfo.setText("Vous avez selectionné : Thunder Ball ");
            event.consume();
        });

        Button resetUltA = new Button();
        resetUltA.setMinSize(30, 30);
        resetUltA.setTranslateX(-490);
        resetUltA.setTranslateY(200);
        resetUltA.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getA().setCoupUltimate(null);
            ultPlayerAinfo.setText("Vous n'avez selectionné aucun sort");
            event.consume();
        });
        Button resetUltB = new Button();
        resetUltB.setMinSize(30, 30);
        resetUltB.setTranslateX(490);
        resetUltB.setTranslateY(200);
        resetUltB.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.getB().setCoupUltimate(null);
            ultPlayerBinfo.setText("Vous n'avez selectionné aucun sort");
            event.consume();
        });

        // -----------Fin des bouton coups spéciaux
        // on met par default les matchs en 5 pts
        if (Parametre.modeDejeu.equals("ModePoint")) {
            ScoreBoard.nbPoint = 5;
        } else {
            ScoreBoard.nbPoint = -1;
        }
        nbPoint.setText("Match en " + ScoreBoard.nbPoint + " points gagnant !");

        Button increase = new Button();
        increase.setMinHeight(5);
        increase.setMinWidth(25);
        increase.setTranslateY(-280);
        increase.setTranslateX(105);
        Button decrease = new Button();
        decrease.setMinHeight(5);
        decrease.setMinWidth(25);
        decrease.setTranslateY(-246);
        decrease.setTranslateX(105);

        increase.setOnAction((ActionEvent) -> {
            OptionSon.playSelectedButton();
            setNextValue(nbPoint);
            ActionEvent.consume();
        });
        decrease.setOnAction((ActionEvent) -> {
            OptionSon.playSelectedButton();
            setPreviousValue(nbPoint);
            ActionEvent.consume();
        });

        Button btnSetPlayerAlvl = new Button();
        btnSetPlayerAlvl.setText("Lvl" + Parametre.botLvlA);
        btnSetPlayerAlvl.setTranslateX(-340 - 70);
        btnSetPlayerAlvl.setTranslateY(-250);
        btnSetPlayerAlvl.setOnAction((event) -> {
            OptionSon.playSelectedButton();
            switch (Parametre.botLvlA) {
                case 1:
                    Parametre.botLvlA = 2;
                    break;
                case 2:
                    Parametre.botLvlA = 1;
                    break;
            }
            btnSetPlayerAlvl.setText("Lvl" + Parametre.botLvlA);
            event.consume();
        });

        Button btnmodePlayerA = new Button();
        btnmodePlayerA.setText("Player A");
        btnmodePlayerA.setTranslateX(-340);
        btnmodePlayerA.setTranslateY(-250);
        btnmodePlayerA.setOnAction(event -> {
            OptionSon.playSelectedButton();
            if (btnmodePlayerA.getText().equals("Player A")) {
                btnmodePlayerA.setText("CPU A");
                Joueurs.getA().setIsbot(true);
                modePlayerA.setText("CPU A activé : Choisissez un niveau de CPU");
                root.getChildren().addAll(btnSetPlayerAlvl, one);
            } else {
                btnmodePlayerA.setText("Player A");
                Joueurs.getA().setIsbot(false);
                modePlayerA.setText("Joueurs A activé");
                root.getChildren().removeAll(btnSetPlayerAlvl, one);
            }
            event.consume();
        });

        Button btnSetPlayerBlvl = new Button();
        btnSetPlayerBlvl.setText("Lvl" + Parametre.botLvlA);
        btnSetPlayerBlvl.setTranslateX(340+ 70);
        btnSetPlayerBlvl.setTranslateY(-250);
        btnSetPlayerBlvl.setOnAction((event) -> {
            OptionSon.playSelectedButton();
            switch (Parametre.botLvlB) {
                case 1:
                    Parametre.botLvlB = 2;
                    break;
                case 2:
                    Parametre.botLvlB = 1;
                    break;
            }
            btnSetPlayerBlvl.setText("Lvl" + Parametre.botLvlB);
            event.consume();
        });


        Button btnmodePlayerB = new Button();
        btnmodePlayerB.setText("Player B");
        btnmodePlayerB.setTranslateX(340);
        btnmodePlayerB.setTranslateY(-250);
        btnmodePlayerB.setOnAction(event -> {
            OptionSon.playSelectedButton();
            if (btnmodePlayerB.getText().equals("Player B")) {
                btnmodePlayerB.setText("CPU B ");
                Joueurs.getB().setIsbot(true);
                modePlayerB.setText("CPU B activé : Choisissez un niveau de CPU");
                root.getChildren().addAll(btnSetPlayerBlvl, two);
            } else {
                btnmodePlayerB.setText("Player B");
                Joueurs.getB().setIsbot(false);
                modePlayerB.setText("Joueurs B activé");
                root.getChildren().removeAll(btnSetPlayerBlvl, two);
            }
            event.consume();
        });

        // button retour
        Button retour = new Button();
        retour.setTranslateX(-523);
        retour.setTranslateY(-275);
        retour.setMinSize(50, 50);
        retour.setCancelButton(true);

        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
            switch (Menu.userPosition.getLast()) {
                case "Skin":
                    Skin sk = new Skin();
                    Menu.userPosition.removeLast();
                    sk.start(stage);
                    event.consume();
                    break;
                case "ElementSizeQM":
                case "ElementSize":
                    ElementSize e = new ElementSize();
                    Menu.userPosition.removeLast();
                    e.start(stage);
                    event.consume();
                    break;
                case "MenuMultiQM":
                    MenuMulti m = new MenuMulti();
                    Menu.userPosition.removeLast();
                    m.start(stage);
                    event.consume();
                    break;
            }
            event.consume();
        });

        // configuration boutton jouer
        Button suivant = new Button();
        suivant.setTranslateX(520);
        suivant.setTranslateY(-275);
        suivant.setMinSize(60, 60);
        suivant.setOnAction(event -> {
            OptionSon.playSelectedButton();
            if (hasToSave){
                majTab();
            }
            App a = new App();
            OptionSon.playSelectedButton();
            OptionSon.mediaPlayerMusique.stop();
            String sound;
            if (Parametre.modeDejeu.equals("ModeChallenge")){
                sound = new File(OptionSon.stringMusicInGameFile[3]).toURI().toString();
            }else {
                if (Parametre.fondMapindex == 3){
                    sound = (new File(OptionSon.stringMusicInGameFile[0]).toURI().toString());
                }else{
                    sound = (new File(OptionSon.stringMusicInGameFile[Parametre.fondMapindex]).toURI().toString());
                }
            }
            OptionSon.mediaPlayerMusique = new AudioClip(sound);
            OptionSon.mediaPlayerMusique.setCycleCount(AudioClip.INDEFINITE);
            OptionSon.mediaPlayerMusique.setVolume(OptionSon.nivSonMusique -0.55);
            OptionSon.muteSound();
            OptionSon.mediaPlayerMusique.play();
            a.start(stage);
            event.consume();
        });

        scene.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                suivant.fire();
            }
            else if(key.getCode() == KeyCode.Q || key.getCode() == KeyCode.A){
                if(!Parametre.modeDejeu.equals("ModeChallenge")){
                    btnmodePlayerA.fire();
                }
            }
            else if(key.getCode() == KeyCode.DIGIT1 || key.getCode() == KeyCode.AMPERSAND){
                btnSetPlayerAlvl.fire();
            }
            else if(key.getCode() == KeyCode.B){
                if(!Parametre.modeDejeu.equals("ModeChallenge")){
                    btnmodePlayerB.fire();
                }
            }
            else if(key.getCode() == KeyCode.DIGIT2){
                btnSetPlayerBlvl.fire();
            }
            else if(key.getCode() == KeyCode.V){
                resetUltA.fire();
            }
            else if(key.getCode() == KeyCode.N){
                resetUltB.fire();
            }
            else if(key.getCode() == KeyCode.D){
                rapidBallA.fire();
            }
            else if(key.getCode() == KeyCode.F){
                waveBallA.fire();
            }
            else if(key.getCode() == KeyCode.G){
                earthWallA.fire();
            }
            else if(key.getCode() == KeyCode.H){
                thunderBallA.fire();
            }
            else if(key.getCode() == KeyCode.M || key.getCode() == KeyCode.COMMA){
                rapidBallB.fire();
            }
            else if(key.getCode() == KeyCode.L){
                waveBallB.fire();
            }
            else if(key.getCode() == KeyCode.K){
                earthWallB.fire();
            }
            else if(key.getCode() == KeyCode.J){
                thunderBallB.fire();
            }
            else if(key.getCode() == KeyCode.S){
                btnMajTab.fire();
            }
            else if(key.getCode() == KeyCode.UP){
                increase.fire();
            }
            else if(key.getCode() == KeyCode.DOWN){
                decrease.fire();
            }
        });

        switch (Menu.getGraphismes()) {
            case 0:
                root.setId("root");
                break;
        
            case 1:
                root.setId("root");
                break;

            case 2:
                root.setId("root2");
                break;
        }

        btnMajTab.setText("Save");
        btnMajTab.setOnAction(event -> {
            OptionSon.playSelectedButton();
                    switch (Menu.userPosition.getLast()){
                        case "ElementSize":
                            hasToSave = true;
                            majTab();
                            btnMajTab.setText("Saved");
                            break;
                        case "ElementSizeQM":
                        case "MenuMultiQM":
                            hasToSave = true;
                            isSaved=true;
                            majTab();
                            setParametersToGame();
                            Menu.userPosition.removeLast();
                            ResumeGame e = new ResumeGame();
                            e.start(stage);
                            break;
                    }
            event.consume();
        });
        btnMajTab.setTranslateY(190);
        btnMajTab.setTranslateX(-20);

        suivant.setId("next");
        retour.setId("retour");
        r.getStyleClass().addAll("border");
        r.setId(Parametre.getActualMap());
        modePlayerA.getStyleClass().addAll("selectiontexte", "textsize13");
        modePlayerB.getStyleClass().addAll("selectiontexte", "textsize13");
        nbPoint.getStyleClass().addAll("selectiontexte", "textsize15");
        ultPlayerAinfo.getStyleClass().addAll("selectiontexte", "textsize13");
        ultPlayerBinfo.getStyleClass().addAll("selectiontexte", "textsize13");
        indication.getStyleClass().addAll("selectiontexte", "textsize18");
        resetUltA.getStyleClass().addAll("remove", "boutton");
        resetUltB.getStyleClass().addAll("remove", "boutton");
        rapidBallA.getStyleClass().addAll("fireball", "boutton");
        rapidBallB.getStyleClass().addAll("fireball", "boutton");
        waveBallA.getStyleClass().addAll("waterball", "boutton");
        waveBallB.getStyleClass().addAll("waterball", "boutton");
        earthWallA.getStyleClass().addAll("earthwall", "boutton");
        earthWallB.getStyleClass().addAll("earthwall", "boutton");
        thunderBallA.getStyleClass().addAll("thunderball", "boutton");
        thunderBallB.getStyleClass().addAll("thunderball", "boutton");
        increase.setId("increase");
        decrease.setId("decrease");
        btnmodePlayerA.getStyleClass().addAll("spbtn","textsize15");
        btnmodePlayerB.getStyleClass().addAll("spbtn","textsize15");
        racketA_preview.getStyleClass().addAll("preview");
        racketB_preview.getStyleClass().addAll("preview");
        ball_preview.getStyleClass().addAll("preview");
        btnMajTab.getStyleClass().addAll("spbtn","textsize13");
        btnSetPlayerAlvl.getStyleClass().addAll("spbtn","textsize13");
        btnSetPlayerBlvl.getStyleClass().addAll("spbtn","textsize13");

        scene.getStylesheets()
                .addAll(getClass().getResource("stylesheet/choixUltimatestylesheet.css").toExternalForm());




        root.getChildren().addAll(retour , r, racketA_preview , racketB_preview , ball_preview ,
                rapidBallA, waveBallA, earthWallA, thunderBallA, ultPlayerAinfo, resetUltA ,indication, btnMajTab,
                V, S, D, F, G, H);
        switch (Parametre.modeDejeu) {
            case "ModeChallenge":
                root.getChildren().addAll(suivant, enter, escape);
                break;
            case "ModePoint":
                root.getChildren().addAll(modePlayerA, modePlayerB, suivant, rapidBallB, btnmodePlayerA, btnmodePlayerB,
                         resetUltB, ultPlayerBinfo, nbPoint, increase, decrease, waveBallB, earthWallB
                        , thunderBallB, AQ, B, N, M, L, K, J, escape, enter);
                break;
            case "ModeInfini":
                root.getChildren().addAll(modePlayerA, modePlayerB, suivant, rapidBallB, btnmodePlayerA, btnmodePlayerB,
                        resetUltB, ultPlayerBinfo, waveBallB, earthWallB, thunderBallB, AQ, B, N, M, L, K, J, escape, enter);
                break;

        }
        if (Menu.userPosition.getLast().equals("ElementSizeQM") || Menu.userPosition.getLast().equals("MenuMultiQM")){
            root.getChildren().removeAll(enter,suivant);
        }
        root.requestFocus();
        stage.show();
    }

    public static void setNextValue(Text t) {
        int actualpts = ScoreBoard.nbPoint;
        if (actualpts == 100) {
            ScoreBoard.nbPoint = 1;
        } else {
            ScoreBoard.nbPoint++;

        }
        t.setText("Match en " + ScoreBoard.nbPoint + " points gagnant !");
    }

    public static void setPreviousValue(Text t) {
        int actualpts = ScoreBoard.nbPoint;
        if (actualpts == 1) {
            ScoreBoard.nbPoint = 100;
        } else {
            ScoreBoard.nbPoint--;
        }
        t.setText("Match en " + ScoreBoard.nbPoint + " points gagnant !");
    }
}
