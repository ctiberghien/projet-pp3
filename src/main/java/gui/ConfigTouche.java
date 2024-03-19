package gui;

import javafx.scene.*;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.stage.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class ConfigTouche {
    public static boolean hasToSave = false;
    private static boolean hasClicked;
    private static final Text warning = new Text("*Attention vous devez mettre une touche valide");

    public void OpenConfig(Stage stage) {
        Pane root = new Pane();
        root.requestFocus();
        Scene scene = new Scene(root, 1100, 600);
        warning.setFill(Color.TRANSPARENT);
        warning.setTranslateX(275 - 130);
        warning.setTranslateY(235);

        // Code des boutons

        Button btnSave = new Button();
        btnSave.setMinWidth(260);
        btnSave.setMinHeight(40);
        btnSave.setTranslateY(485);
        btnSave.setTranslateX(635);

        btnSave.setOnAction(event -> {
            OptionSon.playSelectedButton();
            hasToSave = true;
            Option o2 = new Option();
            o2.OpenOption(stage);
            event.consume();
        });

        Button retour = new Button();
        retour.setMinWidth(50);
        retour.setMinHeight(50);
        retour.setCancelButton(true);
        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Option o = new Option();
            o.OpenOption(stage);
            event.consume();
        });

        Button setUpA = new Button(Joueurs.getA().getUp().getName());
        setUpA.setTextFill(Color.BLACK);
        setUpA.setMinWidth(80);
        setUpA.setMinHeight(35);
        setUpA.setTranslateY(300);
        setUpA.setTranslateX(430);

        setUpA.setOnMouseClicked(event -> {
            event.consume();
            setUpA.setText("Listening...");
            hasClicked = false;
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    KeyCode tmp = Joueurs.getA().getUp();
                    if (!hasClicked) {
                        Joueurs.getA().setUp(event.getCode());
                        setUpA.setText(Joueurs.getA().getUp().getName());
                        hasClicked = true;
                        warning.setFill(Color.TRANSPARENT);
                    }
                    if (Joueurs.getA().isUpSameAsDown()
                            || Player.isKeySame(Joueurs.getA().getUp(), Joueurs.getB().getUp())
                            || Player.isKeySame(Joueurs.getA().getUp(), Joueurs.getB().getDown())
                            || Player.isKeySame(Joueurs.getA().getUp(), Joueurs.getB().getUlt())
                            || Player.isKeySame(Joueurs.getA().getUp(), Joueurs.getA().getUlt())
                            || Player.isKeySame(Joueurs.getA().getUp(), Joueurs.getA().getPause())) {
                        Joueurs.getA().setUp(tmp);
                        setUpA.setText(Joueurs.getA().getUp().getName());
                        warning.setTranslateX(275 - 130);
                        warning.setTranslateY(345);
                        warning.setFill(Color.RED);
                    }
                }
            });
        });

        Button setDownA = new Button(Joueurs.getA().getDown().getName());
        setDownA.setTextFill(Color.BLACK);
        setDownA.setMinWidth(80);
        setDownA.setMinHeight(35);
        setDownA.setTranslateY(360);
        setDownA.setTranslateX(430);

        setDownA.setOnMouseClicked(event -> {
            event.consume();
            setDownA.setText("Listening...");
            hasClicked = false;
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    KeyCode tmp = Joueurs.getA().getDown();
                    if (!hasClicked) {
                        Joueurs.getA().setDown(event.getCode());
                        setDownA.setText(Joueurs.getA().getDown().getName());
                        hasClicked = true;
                      //  warning .setTranslateY(50);
                       // warning .setTranslateX(650);
                        warning.setFill(Color.TRANSPARENT);
                    }
                    if (Joueurs.getA().isUpSameAsDown()
                            || Player.isKeySame(Joueurs.getA().getDown(), Joueurs.getB().getDown())
                            || Player.isKeySame(Joueurs.getA().getDown(), Joueurs.getB().getUp())
                            || Player.isKeySame(Joueurs.getA().getDown(), Joueurs.getA().getPause())
                            || Player.isKeySame(Joueurs.getA().getDown(), Joueurs.getA().getUlt())
                            || Player.isKeySame(Joueurs.getA().getDown(), Joueurs.getB().getUlt())) {
                        Joueurs.getA().setDown(tmp);
                        setDownA.setText(Joueurs.getA().getDown().getName());
                        warning.setTranslateX(275 - 100);
                        warning.setTranslateY(410);
                        warning.setFill(Color.RED);
                    }
                }
            });
        });

        Button setUpB = new Button(Joueurs.getB().getUp().getName());
        setUpB.setTextFill(Color.BLACK);
        setUpB.setMinWidth(80);
        setUpB.setMinHeight(35);
        setUpB.setTranslateY(300);
        setUpB.setTranslateX(885);

        setUpB.setOnMouseClicked(event -> {
            event.consume();
            setUpB.setText("Listening...");
            hasClicked = false;
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    KeyCode tmp = Joueurs.getB().getUp();
                    if (!hasClicked) {
                        Joueurs.getB().setUp(event.getCode());
                        setUpB.setText(Joueurs.getB().getUp().getName());
                        hasClicked = true;
                        warning.setFill(Color.TRANSPARENT);
                    }
                    if (Joueurs.getB().isUpSameAsDown()
                            || Player.isKeySame(Joueurs.getB().getUp(), Joueurs.getA().getUp())
                            || Player.isKeySame(Joueurs.getB().getUp(), Joueurs.getA().getDown())
                            || Player.isKeySame(Joueurs.getB().getUp(), Joueurs.getA().getPause())
                            || Player.isKeySame(Joueurs.getB().getUp(), Joueurs.getB().getUlt())
                            || Player.isKeySame(Joueurs.getB().getUp(), Joueurs.getA().getUlt())) {
                        Joueurs.getB().setUp(tmp);
                        setUpB.setText(Joueurs.getB().getUp().getName());
                        warning.setTranslateX(700-100);
                        warning.setTranslateY(345);
                        warning.setFill(Color.RED);
                    }
                }
            });
        });

        Button setDownB = new Button(Joueurs.getB().getDown().getName());
        setDownB.setTextFill(Color.BLACK);
        setDownB.setMinWidth(80);
        setDownB.setMinHeight(35);
        setDownB.setTranslateY(360);
        setDownB.setTranslateX(885);

        setDownB.setOnMouseClicked(event -> {
            event.consume();
            setDownB.setText("Listening...");
            hasClicked = false;
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    KeyCode tmp = Joueurs.getB().getDown();
                    if (!hasClicked) {
                        Joueurs.getB().setDown(event.getCode());
                        setDownB.setText(Joueurs.getB().getDown().getName());
                        hasClicked = true;
                        warning.setFill(Color.TRANSPARENT);
                    }
                    if (Joueurs.getB().isUpSameAsDown()
                            || Player.isKeySame(Joueurs.getB().getDown(), Joueurs.getA().getDown())
                            || Player.isKeySame(Joueurs.getB().getDown(), Joueurs.getA().getUp())
                            || Player.isKeySame(Joueurs.getB().getDown(), Joueurs.getA().getPause())
                            || Player.isKeySame(Joueurs.getB().getDown(), Joueurs.getA().getUlt())
                            || Player.isKeySame(Joueurs.getB().getDown(), Joueurs.getB().getUlt())) {
                        Joueurs.getB().setDown(tmp);
                        setDownB.setText(Joueurs.getB().getDown().getName());
                        warning.setTranslateX(700-100);
                        warning.setTranslateY(410);
                        warning.setFill(Color.RED);
                    }
                }
            });
        });

        Button setPauseA = new Button();
        Button setPause = new Button();
        setPauseA = pause(scene, setPauseA, setPause);
        setPause = pause(scene, setPause, setPauseA);
        setPauseA.setTranslateY(240);
        setPauseA.setTranslateX(430);
        setPause.setTranslateY(240);
        setPause.setTranslateX(885);

        Button setUltA = new Button(Joueurs.getA().getUlt().getName());
        // setUltA.setTextFill(Color.BLACK);
        setUltA.setMinWidth(80);
        setUltA.setMinHeight(35);
        setUltA.setTranslateY(420);
        setUltA.setTranslateX(430);

        setUltA.setOnMouseClicked(event -> {
            event.consume();
            setUltA.setText("Listening...");
            hasClicked = false;
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    KeyCode tmp = Joueurs.getA().getUlt();
                    if (!hasClicked) {
                        Joueurs.getA().setUlt(event.getCode());
                        setUltA.setText(Joueurs.getA().getUlt().getName());
                        hasClicked = true;
                        warning.setFill(Color.TRANSPARENT);
                    }
                    if (Player.isKeySame(Joueurs.getA().getUlt(), Joueurs.getB().getDown())
                            || Player.isKeySame(Joueurs.getA().getUlt(), Joueurs.getB().getUp())
                            || Player.isKeySame(Joueurs.getA().getUlt(), Joueurs.getA().getDown())
                            || Player.isKeySame(Joueurs.getA().getUlt(), Joueurs.getA().getUp())
                            || Player.isKeySame(Joueurs.getA().getUlt(), Joueurs.getA().getPause())
                            || Player.isKeySame(Joueurs.getA().getUlt(), Joueurs.getB().getUlt())) {
                        Joueurs.getA().setUlt(tmp);
                        setUltA.setText(tmp.name());
                        warning.setTranslateX(275 - 100);
                        warning.setTranslateY(465);
                        warning.setFill(Color.RED);
                    }
                }
            });
        });

        Button setUltB = new Button(Joueurs.getB().getUlt().getName());
        setUltB.setMinWidth(80);
        setUltB.setMinHeight(35);
        setUltB.setTranslateY(420);
        setUltB.setTranslateX(885);

        setUltB.setOnMouseClicked(event -> {
            event.consume();
            setUltB.setText("Listening...");
            hasClicked = false;
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    KeyCode tmp = Joueurs.getB().getUlt();
                    if (!hasClicked) {
                        Joueurs.getB().setUlt(event.getCode());
                        setUltB.setText(Joueurs.getB().getUlt().getName());
                        hasClicked = true;
                        warning.setFill(Color.TRANSPARENT);
                    }
                    if (Player.isKeySame(Joueurs.getB().getUlt(), Joueurs.getB().getDown())
                            || Player.isKeySame(Joueurs.getB().getUlt(), Joueurs.getB().getUp())
                            || Player.isKeySame(Joueurs.getB().getUlt(), Joueurs.getA().getDown())
                            || Player.isKeySame(Joueurs.getB().getUlt(), Joueurs.getA().getUp())
                            || Player.isKeySame(Joueurs.getB().getUlt(), Joueurs.getA().getPause())
                            || Player.isKeySame(Joueurs.getB().getUlt(), Joueurs.getA().getUlt())) {
                        Joueurs.getB().setUlt(tmp);
                        setUltB.setText(tmp.name());
                        warning.setTranslateX(600);
                        warning.setTranslateY(460);
                        warning.setFill(Color.RED);
                    }
                }
            });
        });
        // Design
        int aX = 215;
        int aY = 220;
        int bX = aX + 430;
        int bY = aY;

        Region pAt = new Region();
        pAt.setTranslateX(aX + 65);
        pAt.setTranslateY(aY - 40);
        pAt.setMinSize(180, 35);

        Region pAup = new Region();
        pAup.setTranslateX(aX);
        pAup.setTranslateY(aY + 90);
        pAup.setMinSize(150, 27);

        Region pAdown = new Region();
        pAdown.setTranslateX(aX - 39);
        pAdown.setTranslateY(aY + 150);
        pAdown.setMinSize(190, 30);

        Region pBt = new Region();
        pBt.setTranslateX(bX + 70);
        pBt.setTranslateY(bY - 40);
        pBt.setMinSize(180, 35);

        Region pBup = new Region();
        pBup.setTranslateX(bX);
        pBup.setTranslateY(bY + 90);
        pBup.setMinSize(150, 27);

        Region pBdown = new Region();
        pBdown.setTranslateX(bX - 39);
        pBdown.setTranslateY(bY + 150);
        pBdown.setMinSize(190, 30);

        Region overlay = new Region();
        overlay.setTranslateX(90);
        overlay.setTranslateY(30);
        overlay.setMinSize(1040, 540);

        Region pApause = new Region();
        pApause.setTranslateX(aX - 50);
        pApause.setTranslateY(aY + 30);
        pApause.setMinSize(200, 22);

        Region pBpause = new Region();
        pBpause.setTranslateX(bX - 50);
        pBpause.setTranslateY(bY + 30);
        pBpause.setMinSize(200, 22);

        Region pAult = new Region();
        pAult.setTranslateX(aX - 15);
        pAult.setTranslateY(aY + 210);
        pAult.setMinSize(165, 22);

        Region pBUlt = new Region();
        pBUlt.setTranslateX(bX - 15);
        pBUlt.setTranslateY(bY + 210);
        pBUlt.setMinSize(165, 22);

        Region escape = new Region();
        escape.setMinSize(63, 20);
        escape.setTranslateX(60);
        escape.setTranslateY(10);

        Line separator = new Line(575, 150, 575, 480);
        Line underline = new Line(165, 220, 990, 220);

        Polygon saveBorder = new Polygon();
        saveBorder.getPoints().addAll(
                600.0, 480.0,
                880.0, 480.0,
                930.0, 530.0,
                650.0, 530.0);

        // Empêcher de pouvoir sélectionner les boutons avec les fleches directionnelles

        setUpA.setFocusTraversable(false);
        setDownA.setFocusTraversable(false);
        setUpB.setFocusTraversable(false);
        setDownB.setFocusTraversable(false);
        retour.setFocusTraversable(false);
        setUltA.setFocusTraversable(false);
        setUltB.setFocusTraversable(false);
        setPause.setFocusTraversable(false);
        setPauseA.setFocusTraversable(false); 
        btnSave.setFocusTraversable(false);

        // ajout du css

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

        scene.getStylesheets().addAll(getClass().getResource("stylesheet/configTouchestylesheet.css").toExternalForm());
        btnSave.setId("save");
        btnSave.getStyleClass().addAll("button");
        retour.setId("retour");
        overlay.setId("overlay");
        setUpA.getStyleClass().add("cadre");
        setDownA.getStyleClass().add("cadre");
        setUpB.getStyleClass().add("cadre");
        setDownB.getStyleClass().add("cadre");
        pAt.setId("pAt");
        pAup.setId("pAup");
        pAdown.setId("pAdown");
        pBt.setId("pBt");
        pBup.setId("pBup");
        pBdown.setId("pBdown");
        setPause.getStyleClass().add("cadre");
        setPauseA.getStyleClass().add("cadre");
        setUltA.getStyleClass().add("cadre");
        setUltB.getStyleClass().add("cadre");
        pBpause.getStyleClass().add("txtpause");
        pApause.getStyleClass().addAll("txtpause");
        pAult.getStyleClass().add("txtult");
        pBUlt.getStyleClass().add("txtult");
        separator.setId("separator");
        saveBorder.setId("saveBorder");
        underline.setId("underline");
        escape.setId("escape");

        stage.setScene(scene);
        root.getChildren().addAll(overlay, pBpause, pApause, pAult, pBUlt, pAt, pBt, pAup, pAdown, pBup, pBdown, retour,
                setUpA, setDownA,
                setUpB, setDownB, warning, saveBorder, btnSave, separator, underline, setPause, setPauseA, setUltA,
                setUltB, escape);
        root.requestFocus();
    }

    public static Button pause(Scene scene, Button achanger, Button changerLettre) {
        achanger.setText(Joueurs.getA().getPause().getName());
        achanger.setMinWidth(80);
        achanger.setMinHeight(35);
        achanger.setOnMouseClicked(event -> {
            achanger.setText("Listening...");
            hasClicked = false;
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    KeyCode tmp = Joueurs.getA().getPause();
                    if (!hasClicked) {
                        Joueurs.getA().setPause(event.getCode());
                        achanger.setText(Joueurs.getA().getPause().getName());
                        changerLettre.setText(Joueurs.getA().getPause().getName());
                        hasClicked = true;
                        warning.setFill(Color.TRANSPARENT);
                    }
                    if (event.getCode() == Joueurs.getA().getUp() || event.getCode() == Joueurs.getA().getDown()
                            || event.getCode() == Joueurs.getB().getUp() || event.getCode() == Joueurs.getB().getDown()
                            || event.getCode() == Joueurs.getB().getUlt() || event.getCode() == Joueurs.getA().getUlt()
                            || !event.getCode().isLetterKey()) {
                        Joueurs.getA().setPause(tmp);
                        achanger.setText(Joueurs.getA().getPause().getName());
                        changerLettre.setText(Joueurs.getA().getPause().getName());
                        warning.setTranslateX(275 - 130);
                        warning.setTranslateY(285);
                        warning.setFill(Color.RED);
                    }
                }
            });
        });
        return achanger;
    }
}