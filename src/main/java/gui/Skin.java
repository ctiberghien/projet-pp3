package gui;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import model.Court.Court;

public class Skin {

    public static boolean hasToSave = false;

    public void start(Stage stage) {

        // Déclaration de la nouvelle fenêtre

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);

        // Déclaration du rectangle

        Region r = new Region();
        r.setMaxSize(880, 480);
        r.setTranslateY(20);

        // Déclaration des Color-picker (valeur par défaut : noir)

        final ColorPicker colorPicker_balle = new ColorPicker();
        final ColorPicker colorPicker_racketA = new ColorPicker();
        final ColorPicker colorPicker_racketB = new ColorPicker();

        // Emplacement des Color-picker

        colorPicker_racketA.setTranslateX(-340);
        colorPicker_balle.setTranslateX(0);
        colorPicker_racketB.setTranslateX(+330);

        colorPicker_balle.setTranslateY(-250);
        colorPicker_racketA.setTranslateY(-250);
        colorPicker_racketB.setTranslateY(-250);

        // Déclaration des 3 Text

        Text selectionBalle = new Text("Sélectionnez la couleur de la balle:");
        Text selectionRacketA = new Text("Sélectionnez la couleur de la raquette A:");
        Text selectionRacketB = new Text("Sélectionnez la couleur de la raquette B:");

        // Font des Text

        selectionBalle.setFont(new Font(10));
        selectionRacketA.setFont(new Font(10));
        selectionRacketB.setFont(new Font(10));

        // Emplacement des Text

        selectionBalle.setTranslateY(-280);
        selectionRacketA.setTranslateY(-280);
        selectionRacketB.setTranslateY(-280);

        selectionBalle.setTranslateX(0);
        selectionRacketA.setTranslateX(-340);
        selectionRacketB.setTranslateX(+330);

        // Déclaration et emplacement des 3 preview

        Rectangle racketA_preview = new Rectangle();
        racketA_preview.setFill(Joueurs.couleurRacketA);
        racketA_preview.setHeight(100.0);
        racketA_preview.setWidth(10);
        racketA_preview.setTranslateX(-400);

        Rectangle racketB_preview = new Rectangle();
        racketB_preview.setFill(Joueurs.couleurRacketB);
        racketB_preview.setHeight(100.0);
        racketB_preview.setWidth(10);
        racketB_preview.setTranslateX(400);

        Circle ball_preview = new Circle();
        ball_preview.setFill(Joueurs.couleurBalle);
        ball_preview.setRadius(10);

        // La valeur des ColorPicker est envoyée dans la classe Joueurs et set le
        // remplissage des éléments preview

        colorPicker_balle.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.couleurBalle = colorPicker_balle.getValue();
            ball_preview.setFill(colorPicker_balle.getValue());
            event.consume();
        });

        colorPicker_racketA.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.couleurRacketA = colorPicker_racketA.getValue();
            racketA_preview.setFill(colorPicker_racketA.getValue());
            event.consume();
        });

        colorPicker_racketB.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.couleurRacketB = colorPicker_racketB.getValue();
            racketB_preview.setFill(colorPicker_racketB.getValue());
            event.consume();
        });

        // Code du Button suivant

        Button suivant = new Button();
        suivant.setTranslateX(510);
        suivant.setTranslateY(-270);
        suivant.setMinSize(80, 80);
        suivant.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.playerA.setCoupUltimate(null);
            Joueurs.playerB.setCoupUltimate(null);
            switch (Parametre.modeDejeu) {
                case "ModePoint":
                case "ModeInfini":
                    ElementSize e = new ElementSize();
                    Menu.userPosition.offer("Skin");
                    Joueurs.playerB.setIsbot(false);
                    Joueurs.playerA.setIsbot(false);
                    e.start(stage);
                    break;
                case "ModeChallenge":
                    Court.racketSize = 100.0;
                    Court.ballRadius = 10.0;
                    Court.ballSpeedX = 200;
                    Court.ballSpeedY = 200;
                    ElementSize.collisions_centrees.setSelected(false);
                    Joueurs.playerB.setIsbot(true);
                    Joueurs.playerA.setIsbot(false);
                    ChoixUltimate c = new ChoixUltimate();
                    Menu.userPosition.offer("Skin");
                    c.start(stage);
                    break;
            }
            event.consume();
        });
        if(!Menu.userPosition.getLast().equals("Option")){
            root.getChildren().addAll(suivant);
        }
        racketA_preview.setFill(Joueurs.couleurRacketA);
        racketB_preview.setFill(Joueurs.couleurRacketB);
        ball_preview.setFill(Joueurs.couleurBalle);
        colorPicker_racketA.setValue(Joueurs.couleurRacketA);
        colorPicker_racketB.setValue(Joueurs.couleurRacketB);
        colorPicker_balle.setValue(Joueurs.couleurBalle);


        //Regions des boutons

        Text enter = new Text("[ENTER]");
        enter.setFill(Color.WHITE);
        enter.setTranslateX(175);
        enter.setTranslateY(237);

        Text enter2 = new Text("[ENTER]");
        enter2.setFill(Color.WHITE);
        enter2.setTranslateX(510);
        enter2.setTranslateY(-230);

        Text escape = new Text("[ESCAPE]");
        escape.setFill(Color.WHITE);
        escape.setTranslateX(-510);
        escape.setTranslateY(-230);


        // Code du bouton save

        Button btnSave = new Button();
        btnSave.setMinWidth(280);
        btnSave.setMinHeight(40);
        btnSave.setTranslateY(237);

        btnSave.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Joueurs.couleurBalle = colorPicker_balle.getValue();
            Joueurs.couleurRacketA = colorPicker_racketA.getValue();
            Joueurs.couleurRacketB = colorPicker_racketB.getValue();
            hasToSave = true;
            Option o2 = new Option();
            o2.OpenOption(stage);
            event.consume();
        });

        // Code du Button retour

        Button retour = new Button();
        retour.setTranslateX(-510);
        retour.setTranslateY(-270);
        retour.setMinSize(80, 80);
        retour.setCancelButton(true);

        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
            String previous = Menu.userPosition.removeLast();
            Parametre.modeDejeu = "";
            switch (previous) {
                case "Option":
                    Option o = new Option();
                    o.OpenOption(stage);
                    event.consume();

                    break;
                case "MenuMulti":
                    MenuMulti m = new MenuMulti();
                    m.start(stage);
                    event.consume();
                    break;
            }
            event.consume();
        });
        Circle map1 = new Circle(5);
        map1.setTranslateY(180);
        map1.setTranslateX(-50);
        map1.setFill(Color.rgb(255, 255, 255, 0.7));
        Circle map2 = new Circle(5);
        map2.setTranslateY(180);
        map2.setFill(Color.rgb(255, 255, 255, 0.7));
        Circle map3 = new Circle(5);
        map3.setTranslateX(50);
        map3.setTranslateY(180);
        map3.setFill(Color.rgb(255, 255, 255, 0.7));

        // code du boutton next/previous map fond
        Button next = new Button();
        next.setTranslateX(100);
        next.setTranslateY(180);
        next.setMinSize(30, 30);
        next.setOnAction(event -> {
            OptionSon.playSelectedButton();
            r.setId(Parametre.getNextMap());
            setNavBgCircle(map1, map2, map3);
            event.consume();
        });

        scene.setOnKeyPressed(key -> {
            if(key.getCode() == KeyCode.RIGHT){
                r.setId(Parametre.getNextMap());
            }
            else if(key.getCode() == KeyCode.LEFT){
                r.setId(Parametre.getPreviousMap());
            }
            else if(key.getCode() == KeyCode.ENTER){
                if (Menu.userPosition.getLast().equals("Option")){
                    btnSave.fire();
                }
                else{
                    OptionSon.playSelectedButton();
                    Joueurs.playerA.setCoupUltimate(null);
                    Joueurs.playerB.setCoupUltimate(null);
                    switch (Parametre.modeDejeu) {
                        case "ModePoint":
                        case "ModeInfini":
                            ElementSize e = new ElementSize();
                            Menu.userPosition.offer("Skin");
                            Joueurs.playerB.setIsbot(false);
                            Joueurs.playerA.setIsbot(false);
                            e.start(stage);
                            break;
                        case "ModeChallenge":
                            Court.racketSize = 100.0;
                            Court.ballRadius = 10.0;
                            Court.ballSpeedX = 200;
                            Court.ballSpeedY = 200;
                            ElementSize.collisions_centrees.setSelected(false);
                            Joueurs.playerB.setIsbot(true);
                            Joueurs.playerA.setIsbot(false);
                            ChoixUltimate c = new ChoixUltimate();
                            Menu.userPosition.offer("Skin");
                            c.start(stage);
                            break;
                    }   
                }
            }
            else if(key.getCode() == KeyCode.ESCAPE){
                retour.fire();
            }
            key.consume();
        });

        Button previous = new Button();
        previous.setTranslateX(-100);
        previous.setTranslateY(180);
        previous.setMinSize(30, 30);
        previous.setOnAction(event -> {
            OptionSon.playSelectedButton();
            r.setId(Parametre.getPreviousMap());
            setNavBgCircle(map1, map2, map3);
            event.consume();
        });
        // ajout du css

        // ajout du css

        switch (Menu.getGraphismes()) {
            case 0:
                root.setId("root");
                break;

            case 1:
                root.setId("root1");
                break;

            case 2:
                root.setId("root2");
                break;
        }
        setNavBgCircle(map1, map2, map3);
        btnSave.setId("save");
        ball_preview.getStyleClass().addAll("preview");
        racketA_preview.getStyleClass().addAll("preview");
        racketB_preview.getStyleClass().addAll("preview");
        retour.setId("retour");
        suivant.setId("next");
        r.setId(Parametre.getActualMap());
        next.setId("nextMap");
        previous.setId("previousMap");
        r.getStyleClass().addAll("border");
        selectionBalle.getStyleClass().addAll("selectiontexte", "textsize13");
        selectionRacketA.getStyleClass().addAll("selectiontexte", "textsize13");
        selectionRacketB.getStyleClass().addAll("selectiontexte", "textsize13");
        colorPicker_balle.getStyleClass().addAll("colorpicker");
        colorPicker_racketA.getStyleClass().addAll("colorpicker");
        colorPicker_racketB.getStyleClass().addAll("colorpicker");


        scene.getStylesheets().add(this.getClass().getResource("stylesheet/skinstylesheet.css").toExternalForm());

        // Affichage des éléments
        root.getChildren().addAll(r, map1, map2, map3, retour, selectionBalle, selectionRacketA, selectionRacketB,
                colorPicker_balle, colorPicker_racketA, colorPicker_racketB,
                racketA_preview, racketB_preview, ball_preview,
                next, previous, enter2, escape);
        if (Menu.userPosition.getLast().equals("Option")){
            root.getChildren().addAll(btnSave,enter);
            root.getChildren().remove(enter2);
        }
        root.requestFocus();
    }

    public static void setNavBgCircle(Circle c1, Circle c2, Circle c3) {
        c1.setId(null);
        c2.setId(null);
        c3.setId(null);

        switch (Parametre.getActualMap()) {
            case "map1":
                c1.setId("navBgmap");
                break;
            case "map1_2":
                c1.setId("navBgmap");
                break;
            case "map2":
                c2.setId("navBgmap");
                break;
            case "map3":
                c3.setId("navBgmap");
                break;
            default:
                return;
        }
    }
}