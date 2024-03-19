package gui;

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
import model.Court.Court;

import java.io.File;

import static gui.ParametresSaved.*;



public class ResumeGame {
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.requestFocus();
        Scene scene =new Scene(root, 1100, 600);
        stage.setScene(scene);

        // ------------Affichage ---------------------------------------------
        Region r = new Region();
        r.setMaxSize(880, 480);
        r.setTranslateY(20);

        Region rA = new Region();
        rA.setMaxSize(860, 350);
        rA.setTranslateX(0);
        rA.setStyle("-fx-background-color : rgba(0, 0, 0, 0.6); -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.4),10, 0.9, 0.6, 0);");

        Text titre = new Text("Résumé de votre partie");
        titre.setFont(new Font(32));
        titre.setTranslateY(-265);
        titre.setTranslateX(-20);

        // button retour
        Button retour = new Button();
        retour.setTranslateX(-523);
        retour.setTranslateY(-275);
        retour.setMinSize(50, 50);
        retour.setCancelButton(true);
        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
                switch (Menu.userPosition.getLast()) {
                    case "QuickMatch":
                        Menu m = new Menu();
                        m.start(stage);
                        break;
                    case "ResumeQMOption":
                    case "MenuMultiQM":
                    case "Option":
                        Menu.userPosition.removeLast();
                        Option o = new Option();
                        o.OpenOption(stage);
                        break;
                }
        });


        //Boolean qui est à true si le menu est ouvert depuis les options
        if (Menu.userPosition.getLast().equals("Option")){
            if (isSaved){
                titre.setText("Vos paramêtres sont ceci");
                Menu.userPosition.add("ResumeQMOption");
                ResumeGame rg = new ResumeGame();
                rg.start(stage);
                ParametresSaved.setParametersToGame();
            }
        }else{
            titre.setText("Résumé de votre partie");
        }
        // Déclaration Text

        if (isSaved){



            Text nbPoint = new Text();
            Text txtPouvoirA = new Text("Pouvoir de A :");
            Text txtPouvoirB = new Text("Pouvoir de B :");

            // Font des Text

            nbPoint.setFont(new Font(15));
            txtPouvoirA.setFont(new Font(15));
            txtPouvoirB.setFont(new Font(15));

            // Emplacement des Text

            nbPoint.setTranslateY(-240);
            nbPoint.setTranslateX(-20);
            txtPouvoirA.setTranslateY(-40);
            txtPouvoirA.setTranslateX(-300);
            txtPouvoirB.setTranslateY(-40);
            txtPouvoirB.setTranslateX(300);

            Rectangle racketA_preview = new Rectangle();
            Rectangle racketB_preview = new Rectangle();
            Circle ball_preview = new Circle();

            // Déclaration et emplacement des 3 preview

            racketA_preview.setFill(Joueurs.couleurRacketA);
            racketA_preview.setWidth(10);
            racketA_preview.setTranslateX(-400);
            racketA_preview.setHeight(Court.racketSize);

            racketB_preview.setFill(Joueurs.couleurRacketB);
            racketB_preview.setWidth(10);
            racketB_preview.setTranslateX(400);
            racketB_preview.setHeight(Court.racketSize);

            ball_preview.setFill(Joueurs.couleurBalle);
            ball_preview.setRadius(Court.ballRadius);

            // --------------fin Affichage ----------------------------------

            // Bouton des coups spéciaux :

            Button ultButtonA = new Button();
            ultButtonA.setFont(new Font(25));
            ultButtonA.setMinSize(60, 40);
            ultButtonA.setTranslateX(-300);
            ultButtonA.setTranslateY(10);


            Button ultButtonB = new Button();
            ultButtonB.setFont(new Font(25));
            ultButtonB.setMinSize(60, 40);
            ultButtonB.setTranslateX(300);
            ultButtonB.setTranslateY(10);


            // -----------Fin des bouton coups spéciaux

            boolean isChallenge=false;

            if (ParametresSaved.tabParameters[0].equals("ModeInfini") || ParametresSaved.tabParameters[0].equals("ModePoint")){
                if (Integer.parseInt(ParametresSaved.tabParameters[8]) <=0){
                    nbPoint.setText("Match en mode Infinie");
                }else {
                    nbPoint.setText("Match en " + ParametresSaved.tabParameters[8] + " points gagnant !");
                }
                racketA_preview.setHeight(Double.parseDouble(ParametresSaved.tabParameters[5]));
                racketB_preview.setHeight(Double.parseDouble(ParametresSaved.tabParameters[5]));
                ball_preview.setRadius(Double.parseDouble(ParametresSaved.tabParameters[6]));

            } else if (ParametresSaved.tabParameters[0].equals("ModeChallenge")) {
                isChallenge=true;
                nbPoint.setText("Mode challenge vous avez 30s pour marquer le plus de point");
            }

            Text btnSetPlayerAlvl = new Text();
            btnSetPlayerAlvl.setTranslateX(-330 + 70);
            btnSetPlayerAlvl.setTranslateY(-140);
            Text btnmodePlayerA = new Text();
            btnmodePlayerA.setTranslateX(-330);
            btnmodePlayerA.setTranslateY(-140);
            if (!isChallenge){
                if (ParametresSaved.tabParameters[3].equals("Atrue1") || ParametresSaved.tabParameters[3].equals("Atrue2")) {
                    btnmodePlayerA.setText("CPU A");
                    root.getChildren().add(btnSetPlayerAlvl);
                    if (ParametresSaved.tabParameters[3].equals("Atrue2")){
                        btnSetPlayerAlvl.setText("Lvl" + 2);
                    }else{
                        btnSetPlayerAlvl.setText("Lvl" + 1);
                    }
                } else {
                    btnmodePlayerA.setText("Player A");
                }
            }



            Text btnSetPlayerBlvl = new Text();
            btnSetPlayerBlvl.setTranslateX(330- 70);
            btnSetPlayerBlvl.setTranslateY(-140);
            Text btnmodePlayerB = new Text();
            btnmodePlayerB.setTranslateX(330);
            btnmodePlayerB.setTranslateY(-140);
            if (!isChallenge){
                if (ParametresSaved.tabParameters[4].equals("Btrue1") || ParametresSaved.tabParameters[4].equals("Btrue2")) {
                    root.getChildren().add(btnSetPlayerBlvl);
                    btnmodePlayerB.setText("CPU B ");
                    if ( ParametresSaved.tabParameters[4].equals("Btrue2")){
                        btnSetPlayerBlvl.setText("Lvl" + 2);
                    } else {
                        btnSetPlayerBlvl.setText("Lvl" + 1);
                    }
                } else {
                    btnmodePlayerB.setText("Player B");
                }
            }

            // configuration boutton jouer
            Button jouer = new Button("Jouer");
            jouer.setTranslateY(225);
            jouer.setMinSize(150, 50);
          //  jouer.setFont(new Font(15)); // CSS
            jouer.setOnAction(event -> {
                OptionSon.playSelectedButton();
                switch (Menu.userPosition.getLast()){
                    case "QuickMatch":
                        App a = new App();
                        OptionSon.playSelectedButton();
                        OptionSon.mediaPlayerMusique.stop();
                        int i;
                        if (Parametre.modeDejeu.equals("ModeChallenge")){
                            i = 3;
                        }else{
                            if (Parametre.fondMapindex == 3){
                                i = 0 ;
                            }else{
                                i = Parametre.fondMapindex;
                            }
                        }
                        OptionSon.mediaPlayerMusique = new AudioClip(new File(OptionSon.stringMusicInGameFile[i]).toURI().toString());
                        OptionSon.mediaPlayerMusique.setVolume(OptionSon.nivSonMusique -0.55);
                        OptionSon.muteSound();
                        OptionSon.mediaPlayerMusique.play();
                        a.start(stage);
                        break;
                }
                event.consume();
            });

            Button modifier = new Button("Modifier");
            modifier.setTranslateX(-290);
            modifier.setTranslateY(225);
            modifier.setMinSize(50, 25);
            modifier.setOnAction(event -> {
                OptionSon.playSelectedButton();
                if (!Menu.userPosition.getLast().equals("ResumeQMOption")){
                    MenuMulti m = new MenuMulti();
                    Menu.userPosition.offer("MenuMulti");
                    OptionSon.playSelectedButton();
                    m.start(stage);
                }else {
                    MenuMulti m = new MenuMulti();
                    m.start(stage);
                }
                event.consume();
            });

            Button supp = new Button("Supprimer");
            supp.setTranslateX(290);
            supp.setTranslateY(+225);
            supp.setMinSize(50, 25);
            supp.setOnAction(event -> {
                OptionSon.playSelectedButton();
                ParametresSaved.tabParameters = null;
                ParametresSaved.isSaved = false;
                if (!Menu.userPosition.getLast().equals("ResumeQMOption") && !Menu.userPosition.getLast().equals("MenuMultiQM")){
                    MenuMulti m = new MenuMulti();
                    Menu.userPosition.offer("MenuMulti");
                    OptionSon.playSelectedButton();
                    m.start(stage);
                }else {
                    ResumeGame resumeGame = new ResumeGame();
                    resumeGame.start(stage);
                }
                event.consume();
            });

            Text S = new Text("[S]");
            S.setFill(Color.WHITE);
            S.setTranslateX(360);
            S.setTranslateY(225);

            Text M = new Text("[M]");
            M.setFill(Color.WHITE);
            M.setTranslateX(-230);
            M.setTranslateY(225);

            Text escape = new Text("[ESCAPE]");
            escape.setFill(Color.WHITE);
            escape.setTranslateX(-460);
            escape.setTranslateY(-280);
            

            scene.setOnKeyPressed(key -> {
            
                if(key.getCode() == KeyCode.ENTER){
                jouer.fire();
                }
                if(key.getCode() == KeyCode.M || key.getCode() == KeyCode.COMMA){
                modifier.fire();
                }
                if (key.getCode() == KeyCode.S){
                    supp.fire();
                }
            });

            switch (Menu.getGraphismes()) {
                case 0:
                case 1:
                    root.setId("root");
                    break;

                case 2:
                    root.setId("root2");
                    break;
            }

            retour.setId("retour");
            r.getStyleClass().addAll("border");
            r.setId(Parametre.getActualMap());
            nbPoint.getStyleClass().addAll("selectiontexte","textsize15");
            titre.getStyleClass().addAll("selectiontexte");
            txtPouvoirA.getStyleClass().addAll("selectiontexte","textsize15");
            txtPouvoirB.getStyleClass().addAll("selectiontexte","textsize15");
            btnmodePlayerA.getStyleClass().addAll("selectiontexte","textsize15");
            btnmodePlayerB.getStyleClass().addAll("selectiontexte","textsize15");
            jouer.getStyleClass().addAll("spbtn","textsize18");
            racketA_preview.getStyleClass().addAll("preview");
            racketB_preview.getStyleClass().addAll("preview");
            ball_preview.getStyleClass().addAll("preview");
            modifier.getStyleClass().addAll("spbtn");
            supp.getStyleClass().addAll("spbtn");
            btnSetPlayerAlvl.getStyleClass().addAll("selectiontexte","textsize13");
            btnSetPlayerBlvl.getStyleClass().addAll("selectiontexte","textsize13");


            btnMajTab.setOnAction(event -> {
                OptionSon.playSelectedButton();
                majTab();
                event.consume();
            });

            switch (ParametresSaved.tabParameters[0]) {
                case "ModeChallenge":
                    switch (ParametresSaved.tabParameters[1]){
                        case "BalleRapideUlt":
                            ultButtonA.getStyleClass().addAll("fireball", "boutton");
                            break;
                        case "BalleWave":
                            ultButtonA.getStyleClass().addAll("waterball", "boutton");
                            break;
                        case "ThunderBall":
                            ultButtonA.getStyleClass().addAll("thunderball", "boutton");
                            break;
                        case "EarthWall":
                            ultButtonA.getStyleClass().addAll("earthwall", "boutton");
                            break;
                        default:
                            break;
                    }
                    break;
                case "ModePoint":
                case "ModeInfini":
                    switch (ParametresSaved.tabParameters[1]){
                        case "BalleRapideUlt":
                            ultButtonA.getStyleClass().addAll("fireball", "boutton");
                            break;
                        case "BalleWave":
                            ultButtonA.getStyleClass().addAll("waterball", "boutton");
                            break;
                        case "ThunderBall":
                            ultButtonA.getStyleClass().addAll("thunderball", "boutton");
                            break;
                        case "EarthWall":
                            ultButtonA.getStyleClass().addAll("earthwall", "boutton");
                            break;
                        default:
                            break;
                    }
                    switch (ParametresSaved.tabParameters[2]){
                        case "BalleRapideUlt":
                            ultButtonB.getStyleClass().addAll("fireball", "boutton");
                            break;
                        case "BalleWave":
                            ultButtonB.getStyleClass().addAll("waterball", "boutton");
                            break;
                        case "ThunderBall":
                            ultButtonB.getStyleClass().addAll("thunderball", "boutton");
                            break;
                        case "EarthWall":
                            ultButtonB.getStyleClass().addAll("earthwall", "boutton");
                            break;
                        default:
                            break;
                    }
                    break;
            }
            root.getChildren().add(0, r);
            root.getChildren().add(1,rA);
            root.getChildren().addAll( modifier,racketA_preview , racketB_preview , ball_preview , nbPoint , supp , S, M,escape);
            if (ParametresSaved.tabParameters[0].equals("ModeInfini") || ParametresSaved.tabParameters[0].equals("ModePoint")){
                root.getChildren().addAll(btnmodePlayerA, btnmodePlayerB);
                if (!ParametresSaved.tabParameters[1].equals("Anull") && !ParametresSaved.tabParameters[1].equals("null")){
                    root.getChildren().addAll(ultButtonA , txtPouvoirA);
                }
                if (!ParametresSaved.tabParameters[2].equals("Bnull") && !ParametresSaved.tabParameters[1].equals("null")){
                    root.getChildren().addAll(ultButtonB , txtPouvoirB);
                }
            } else if (ParametresSaved.tabParameters[0].equals("ModeChallenge")) {
                if (!ParametresSaved.tabParameters[1].equals("Anull") && !ParametresSaved.tabParameters[1].equals("null")){
                    root.getChildren().addAll(ultButtonA);
                }
            }
            if (!Menu.userPosition.getLast().equals("ResumeQMOption") && !Menu.userPosition.getLast().equals("MenuMultiQM")){
                root.getChildren().addAll( jouer);
            }
            scene.getStylesheets()
                    .addAll(getClass().getResource("stylesheet/resumeGamestylesheet.css").toExternalForm());
        }else{
            Button createGame = new Button("Créer une configuration");

            Text C = new Text("[C]");
            C.setFill(Color.WHITE);
            C.setTranslateX(175);
            C.setTranslateY(0);

            Text escape = new Text("[ESCAPE]");
            escape.setFill(Color.WHITE);
            escape.setTranslateX(-460);
            escape.setTranslateY(-280);

            retour.setId("retour");
            root.setId("root");
            stage.setScene(scene);
            titre.getStyleClass().addAll("texte");
            createGame.getStyleClass().addAll("spbtn");
            createGame.setOnAction(event -> {
                OptionSon.playSelectedButton();
                Menu.userPosition.offer("MenuMulti");
                OptionSon.playSelectedButton();
                Menu.userPosition.add("ResumeQMOption");
                MenuMulti m = new MenuMulti();
                m.start(stage);
                event.consume();
            });

            scene.setOnKeyPressed(key -> {
                if(key.getCode() == KeyCode.C){
                    createGame.fire();
                }      
            });

            root.getChildren().addAll(createGame, C, escape);
            scene.getStylesheets().addAll(getClass().getResource("stylesheet/optionSonstylesheet.css").toExternalForm());
        }
        root.getChildren().addAll(retour, titre);
        root.requestFocus();
        stage.show();
    }

}
