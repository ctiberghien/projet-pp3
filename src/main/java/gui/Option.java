package gui;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.input.KeyCode;

public class Option {
    public void OpenOption(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1100, 600);

        // region rectangle
        Region border = new Region();
        border.setMinSize(1000, 300);
        border.setTranslateX(50);
        border.setTranslateY(150);

        // Region du titre

        Region title = new Region();
        title.setMinSize(400, 90);
        title.setTranslateX(340);
        title.setTranslateY(40);

        //Regions des boutons

        Region C = new Region();
        C.setMinSize(30, 30);
        C.setTranslateX(385);
        C.setTranslateY(255);

        Region S = new Region();
        S.setMinSize(30, 30);
        S.setTranslateX(665);
        S.setTranslateY(255);

        Region K = new Region();
        K.setMinSize(30, 30);
        K.setTranslateX(965);
        K.setTranslateY(255);

        Region P = new Region();
        P.setMinSize(30, 30);
        P.setTranslateX(660);
        P.setTranslateY(340);

        Region G = new Region();
        G.setMinSize(30, 30);
        G.setTranslateX(1015);
        G.setTranslateY(340);

        Region escape = new Region();
        escape.setMinSize(63, 20);
        escape.setTranslateX(60);
        escape.setTranslateY(10);

        // Code du bouton

        Button retour = new Button();
        retour.setMinWidth(50);
        retour.setMinHeight(50);
        retour.setCancelButton(true);

        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Menu m = new Menu();
            Menu.userPosition.removeLast();
            m.start(stage);
            event.consume();
        });

        Button config = new Button();
        config.setMinWidth(270);
        config.setMinHeight(45);
        config.setTranslateY(245);
        config.setTranslateX(100);
        config.setOnAction(event -> {
            OptionSon.playSelectedButton();
            ConfigTouche c = new ConfigTouche();
            c.OpenConfig(stage);
            event.consume();
        });

        // Code du bouton pour accéder au sous menu skin
        Button skin = new Button();
        skin.setMinWidth(150);
        skin.setMinHeight(45);
        skin.setTranslateY(245);
        skin.setTranslateX(800);

        skin.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Skin s = new Skin();
            Menu.userPosition.add("Option");
            s.start(stage);
        });

        // Code du bouton pour accéder au sous menu du son
        Button sound = new Button();
        sound.setMinWidth(170);
        sound.setMinHeight(45);
        sound.setTranslateY(245);
        sound.setTranslateX(480);

        sound.setOnAction(event -> {
            OptionSon.playSelectedButton();
            OptionSon s = new OptionSon();
            Menu.userPosition.add("Option");
            s.OpenOptionSon(stage);
        });

        // Code du bouton pour accéder au sous menu du son
        Button saveGame = new Button();
        saveGame.setMinWidth(550);
        saveGame.setMinHeight(45);
        saveGame.setTranslateY(330);
        saveGame.setTranslateX(95);

        saveGame.setOnAction(event -> {
            OptionSon.playSelectedButton();
            ResumeGame r = new ResumeGame();
            Menu.userPosition.add("Option");
            r.start(stage);
            event.consume();
        });

        Button graphisme = new Button();
        graphisme.setMinWidth(290);
        graphisme.setMinHeight(45);
        graphisme.setTranslateY(330);
        graphisme.setTranslateX(710);

        graphisme.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Graphismes g = new Graphismes();
            g.start(stage);
            event.consume();
        });

        scene.setOnKeyPressed(key -> {
            if(key.getCode() == KeyCode.C){
                OptionSon.playSelectedButton();
                ConfigTouche c = new ConfigTouche();
                c.OpenConfig(stage);
                key.consume();
            }
            else if(key.getCode() == KeyCode.S){
                OptionSon.playSelectedButton();
                OptionSon s = new OptionSon();
                Menu.userPosition.add("Option");
                s.OpenOptionSon(stage);
            }
            else if(key.getCode() == KeyCode.K){
                OptionSon.playSelectedButton();
                Skin s = new Skin();
                Menu.userPosition.add("Option");
                s.start(stage);
            }
            else if(key.getCode() == KeyCode.G){
                graphisme.fire();
            }
            else if(key.getCode() == KeyCode.P){
                saveGame.fire();
            }
        });

         switch (Menu.getGraphismes()) {
            case 0:
                root.setId("root");
                border.setId("border");
                break;
        
            case 1:
                root.setId("root");
                border.setId("border1");
                break;
            
            case 2:
                root.setId("root2");
                border.setId("border2");
                break; 
        }

        scene.getStylesheets().addAll(this.getClass().getResource("stylesheet/optionstylesheet.css").toExternalForm());
        title.setId("title");
        config.getStyleClass().addAll("button");
        config.setId("config");
        skin.getStyleClass().addAll("button");
        skin.setId("skin");
        graphisme.getStyleClass().addAll("button");
        graphisme.setId("graphisme");
        saveGame.getStyleClass().addAll("button");
        saveGame.setId("savegame");
        sound.getStyleClass().addAll("button");
        sound.setId("sound");
        retour.setId("retour");
        C.setId("c");
        S.setId("s");
        K.setId("k");
        P.setId("p");
        G.setId("g");
        escape.setId("escape");


        stage.setScene(scene);
        root.getChildren().addAll(border, retour, config, skin, title, sound , saveGame, graphisme, C, S, K, P, G, escape);
        root.requestFocus();
    }
}
