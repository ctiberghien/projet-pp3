package gui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.event.*;
import javafx.scene.text.*;

public class MenuMulti {

    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);

        Region bord = new Region();
        bord.setTranslateX(125);
        bord.setTranslateY(80);
        bord.setMinSize(850, 450);

        // texte "chooseyourmode"
        Region title = new Region();
        title.setMinSize(900,   70);
        title.setTranslateX(100);
        title.setTranslateY(10);

        // Regions buttons

        Region N = new Region();
        N.setMinSize(30, 30);
        N.setTranslateX(580);
        N.setTranslateY(185);

        Region I = new Region();
        I.setMinSize(30, 30);
        I.setTranslateX(545);
        I.setTranslateY(315);

        Region C = new Region();
        C.setMinSize(30, 30);
        C.setTranslateX(620);
        C.setTranslateY(440);

        Region escape = new Region();
        escape.setMinSize(63, 20);
        escape.setTranslateX(5);
        escape.setTranslateY(40);

        // configuration du texte qui precise le mode de jeu
        Text information = new Text();
        information.setX(250);
        information.setY(570);
        information.setTextAlignment(TextAlignment.CENTER);
        information.setFont(Font.font("Candara"));


        // configuration des boutton des différents mode de jeu
        Button btnModePoint = new Button();
        btnModePoint.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                information.setText("Soyez le premier a atteindre un nombre de points pour gagner ! ");
                root.getChildren().addAll(information);
            }
        });
        btnModePoint.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                root.getChildren().remove(information);
            }
        });
        btnModePoint.setTranslateX(210);
        btnModePoint.setTranslateY(165);
        btnModePoint.setMinSize(350, 60);
        btnModePoint.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Parametre.modeDejeu = "ModePoint";
            if (Menu.userPosition.getLast().equals("ResumeQMOption")){
                ElementSize elementSize = new ElementSize();
                elementSize.start(stage);
                Menu.userPosition.offer("MenuMultiQM");
            }else {
                Menu.userPosition.offer("MenuMulti");
                Skin sk = new Skin();
                sk.start(stage);
            }
            event.consume();
        });

        Button btnModeInfini = new Button();
        btnModeInfini.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                information.setText("Affrontez vous dans un mode sans fin ! ");
                root.getChildren().addAll(information);
            }
        });
        btnModeInfini.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                root.getChildren().remove(information);
            }
        });
        btnModeInfini.setTranslateX(btnModePoint.getTranslateX());
        btnModeInfini.setTranslateY(btnModePoint.getTranslateY() + btnModePoint.getMinHeight() + 70);
        btnModeInfini.setMinSize(325, 60);
        btnModeInfini.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Parametre.modeDejeu = "ModeInfini";
            if (Menu.userPosition.getLast().equals("ResumeQMOption")){
                ElementSize elementSize = new ElementSize();
                elementSize.start(stage);
                Menu.userPosition.offer("MenuMultiQM");
            }else {
                Menu.userPosition.offer("MenuMulti");
                Skin sk = new Skin();
                sk.start(stage);
            }
            event.consume();
        });

        Button btnModeChallenge = LeaderBoard.btnLB(stage);
        btnModeChallenge.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                information.setText(" Survivez et marquez le + de points en moins de 30 secondes ! ");
                root.getChildren().addAll(information);
            }
        });
        btnModeChallenge.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                root.getChildren().remove(information);
            }
        });

        btnModeChallenge.setTranslateX(btnModeInfini.getTranslateX());
        btnModeChallenge.setTranslateY(btnModeInfini.getTranslateY() + btnModeInfini.getMinHeight() + 70);
        btnModeChallenge.setMinSize(570, 60);

        // configuration du boutton retour
        Button retour = new Button();
        retour.setCancelButton(true);
        retour.setMinSize(50, 50);

        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
            switch (Menu.userPosition.getLast()){
                case "Menu":
                case "MenuMulti":
                    Menu m = new Menu();
                    Menu.userPosition.removeLast();
                    m.start(stage);
                    break;
                case "ResumeQMOption":
                    ResumeGame r = new ResumeGame();
                    r.start(stage);
                    break;
            }
            event.consume();
        });

        scene.setOnKeyPressed(key -> {
            if(key.getCode() == KeyCode.N){
                btnModePoint.fire();
            }
            else if(key.getCode() == KeyCode.I){
                btnModeInfini.fire();
            }
            else if(key.getCode() == KeyCode.C){
                btnModeChallenge.fire();
            }
        });

        // ajout css

        switch (Menu.getGraphismes()) {
            case 0:
                root.setId("root");
                bord.setId("bord");
                break;
        
            case 1:
                root.setId("root1");
                bord.setId("bord1");
                break;
            
            case 2:
                root.setId("root2");
                bord.setId("bord2");
                break; 
        }

        scene.getStylesheets()
                .addAll(this.getClass().getResource("stylesheet/menuMultistylesheet.css").toExternalForm());
        title.setId("title");
        information.setId("information");
        btnModePoint.getStyleClass().add("button");
        btnModePoint.setId("modePoint");
        btnModeInfini.getStyleClass().add("button");
        btnModeInfini.setId("modeInfini");
        btnModeChallenge.getStyleClass().add("button");
        btnModeChallenge.setId("modeChallenge");
        N.setId("n");
        I.setId("i");
        C.setId("c");
        escape.setId("escape");
        retour.setId("retour");

        root.getChildren().addAll(bord, retour, btnModePoint, btnModeInfini, btnModeChallenge, title, N, I, C, escape);
        root.requestFocus();
    }

}