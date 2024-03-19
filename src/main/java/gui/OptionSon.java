package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Random;

public class OptionSon {

    //Valeur des niveaux sonore entre 0.0 et 1.0
    public static double nivSonMusique;
    public static double nivSonBruit;

    //Curseurs btn et textes des niveaux sonores publiques pour pouvoir les récupérer dans la version pause du son
    public static Slider sliderMusique = new Slider();
    public static Slider sliderBruit = new Slider();
    public static Button muteMusique = new Button();
    public static Button muteBruit = new Button();
    public static Button testSon = new Button();
    public static Text textNiveauSonMusique = new Text();
    public static Text textNiveauSonBruit = new Text();
    public static Text textMusic = new Text("Music");
    public static Text textSoundEffect = new Text("Sound Effect ");

    //Chemin ou tous les sons se trouvent
    private static final String stringPosFiles = "src/main/resources/Sound/";

    //Tableau des media des sons de rebond
    public static String[] reboundMedia = new String[] {(new File(stringPosFiles + "Bruit/Rebound[1].wav").toURI().toString())
            ,(new File(stringPosFiles + "Bruit/Rebound[2].wav").toURI().toString())
            ,(new File(stringPosFiles + "Bruit/Rebound[3].wav").toURI().toString())
            ,(new File(stringPosFiles + "Bruit/Rebound[4].wav").toURI().toString())

    };

    //Media player pour le son bonk
    public static AudioClip mediaPlayerBonk = new AudioClip(new File(stringPosFiles+"Bruit/bonk-sound-effect.wav").toURI().toString());

    //Emplacement de la musique des menus
    public static String stringMusicMenuFile = stringPosFiles+"Music/RetrogenesisPrivatePress_Cyberpunk_Edgerunners.wav";

    //Media player et média unique qui lance la musique
    public static AudioClip mediaPlayerMusique;
    public static String musicMedia = new File(stringMusicMenuFile).toURI().toString();


    //Tableau des emplacements des musiques des niveaux
    public static String[] stringMusicInGameFile = new String[]{stringPosFiles+"Music/AMK_LoveAndRobot.wav" ,/*Son niveau pizza*/
            stringPosFiles+"Music/BitRush_Arcade2015_Login ScreenLoL.wav", /*Son niveau sushi*/
            stringPosFiles+"Music/V_Cyberpunk.wav", /*Son niveau route*/
            stringPosFiles+"Music/Arcade2017_LoginScreenLoL.wav" /*Son niveau challenge*/};

    //Audio file le son des boutons
    public static AudioClip mediaPlayerSelected = new AudioClip(new File(stringPosFiles+"Bruit/Select.wav").toURI().toString());

    //Média et MediaPlayer pour les sons des Ult charger
    public static AudioClip mediaPlayerChargedUlt = new AudioClip(new File(stringPosFiles+"Bruit/charged.wav").toURI().toString());


    public void OpenOptionSon(Stage stage) {
        setStringText(textNiveauSonBruit, nivSonBruit);
        setStringText(textNiveauSonMusique, nivSonMusique);
        mediaPlayerSelected.setVolume(nivSonBruit);
        mediaPlayerBonk.setVolume(nivSonBruit);

        Pane root = new Pane();
        Scene scene = new Scene(root, 1100, 600);

        // Bouton retour pour revenir aux options
        Button retour = new Button();
        retour.setMinHeight(50);
        retour.setMinWidth(50);
        retour.setCancelButton(true);

        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Option m = new Option();
            m.OpenOption(stage);
            event.consume();
        });

        setTextSliders(root);

        Button btnSave = generateSaveSound(stage);

        muteMusique = btnMuteMusicGenerator(root);
        muteBruit = btnMuteBruitGenerator(root);

        //Regions des boutons

        Region enter = new Region();
        enter.setMinSize(80, 30);
        enter.setTranslateX(715);
        enter.setTranslateY(505);
        
        Region S = new Region();
        S.setMinSize(30, 30);
        S.setTranslateX(540);
        S.setTranslateY(175);

        Region B = new Region();
        B.setMinSize(30, 30);
        B.setTranslateX(540);
        B.setTranslateY(325);

        Text T = new Text("[T]");
        T.setFill(Color.WHITE);
        T.setTranslateX(550);
        T.setTranslateY(420);

        Region escape = new Region();
        escape.setMinSize(63, 20);
        escape.setTranslateX(60);
        escape.setTranslateY(10);

        scene.setOnKeyPressed(key -> {
            if(key.getCode() == KeyCode.ENTER){
                btnSave.fire();
            }
            else if(key.getCode() == KeyCode.S){
                muteMusique.fire();
            }
            else if(key.getCode() == KeyCode.B){
                muteBruit.fire();
            }
            else if(key.getCode() == KeyCode.T){
                testSon.fire();
            }
        });

        // CSS

        switch (Menu.getGraphismes()) {
            case 0:
            case 1:
                root.setId("root");
                break;
            case 2:
                root.setId("root2");
                break;
        }

        root.setId("root");
        retour.setId("retour");
        btnSave.setId("save");
        enter.setId("enter");
        S.setId("s");
        B.setId("b");
        escape.setId("escape");
        muteMusique.getStyleClass().addAll("spbtn");
        muteBruit.getStyleClass().addAll("spbtn");
        textNiveauSonBruit.getStyleClass().addAll("texte");
        textNiveauSonMusique.getStyleClass().addAll("texte");
        testSon.getStyleClass().addAll("spbtn");
        textMusic.getStyleClass().addAll("texte");
        textSoundEffect.getStyleClass().addAll("texte");

        stage.setScene(scene);
        scene.getStylesheets().addAll(getClass().getResource("stylesheet/optionSonstylesheet.css").toExternalForm());
        root.getChildren().addAll(retour, btnSave, muteMusique, muteBruit, textMusic, textSoundEffect, enter, S, B, escape);
        if (!isMuted(nivSonMusique)) {
            root.getChildren().addAll(sliderMusique, textNiveauSonMusique);
        }
        if (!isMuted(nivSonBruit)) {
            testSon = new Button("Test Bruitage");
            testSon.setTranslateX(600);
            testSon.setTranslateY(400);
            testSon.setOnAction(event -> {
                playRebound();
                event.consume();
            });
            testSon.getStyleClass().addAll("spbtn", "textsize14");
            root.getChildren().addAll(sliderBruit, textNiveauSonBruit, testSon, T);
        }
        if(isMuted(nivSonBruit)){
            root.getChildren().remove(T);
        }
    }


    //Methode qui réécrit les paramètres
    public static void writeParametre(Object[] o, double mus , double bruit){
        StringBuilder s= new StringBuilder();
        for(int i=0;i<o.length;i++) {
            if (i==20) {
                s.append(mus).append("\n");
            } else if (i==23) {
                s.append(bruit).append("\n");
            }else{
                s.append(o[i]).append("\n");
            }
        }
        mediaPlayerMusique.setVolume(mus);
        nivSonMusique=mus;
        nivSonBruit=bruit;
        try {
            FileWriter file = new FileWriter(".//src/main/java/gui/.Parametre.txt");
            BufferedWriter bw = new BufferedWriter(file);
            bw.write(s.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    -- Méthodes pour ne pas surcharger openOptionSon --
    public static void setSliders(Pane root) {
        sliderMusique.setMin(0);
        sliderMusique.setMax(100);
        sliderMusique.setValue(nivSonMusique * 100);
        sliderMusique.setShowTickLabels(true);
        sliderMusique.setShowTickMarks(true);
        sliderMusique.setMajorTickUnit(5);
        sliderMusique.setMinorTickCount(5);
        sliderMusique.setBlockIncrement(10);
        sliderMusique.resize(250, 225);
        sliderMusique.setTranslateY(180);
        sliderMusique.setTranslateX(600);
        sliderMusique.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    String nValue = newValue.toString();
                    nValue = nValue.substring(0, 3);
                    if (!nValue.equals("100")) {
                        nValue = nValue.substring(0, 2);
                        if (nValue.equals("0.")) {
                            nValue = "0";
                        } else if (nValue.charAt(1) == '.') {
                            nValue = nValue.substring(0, 1);
                        }
                    }
                    nivSonMusique = sliderMusique.getValue() / 100;
                    textNiveauSonMusique.setText(": " + nValue + " %");
                    if (nivSonMusique == 0){
                        nivSonMusique+=1;
                        OptionSon.muteMusique.fire();
                    }
                });

        sliderBruit.setMin(0);
        sliderBruit.setMax(100);
        sliderBruit.setValue(nivSonBruit * 100);
        sliderBruit.setShowTickLabels(true);
        sliderBruit.setShowTickMarks(true);
        sliderBruit.setMajorTickUnit(5);
        sliderBruit.setMinorTickCount(5);
        sliderBruit.setBlockIncrement(10);
        sliderBruit.resize(250, 225);
        sliderBruit.setTranslateY(330);
        sliderBruit.setTranslateX(600);
        sliderBruit.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    String nValue = newValue.toString();
                    nValue = nValue.substring(0, 3);
                    if (!nValue.equals("100")) {
                        nValue = nValue.substring(0, 2);
                        if (nValue.equals("0.")) {
                            nValue = "0";
                        } else if (nValue.charAt(1) == '.') {
                            nValue = nValue.substring(0, 1);
                        }
                    }
                    textNiveauSonBruit.setText(": " + nValue + " %");
                    nivSonBruit = sliderBruit.getValue() / 100;
                    if (nivSonBruit == 0){
                        nivSonBruit+=1;
                        OptionSon.muteBruit.fire();
                    }
                });
    }

    public static void setText() {
        textNiveauSonMusique.setX(800);
        textNiveauSonMusique.setY(200);
        textMusic.setX(220);
        textMusic.setY(200);

        textNiveauSonBruit.setX(800);
        textNiveauSonBruit.setY(350);
        textSoundEffect.setX(170);
        textSoundEffect.setY(350);
    }

    public static void setStringText(Text t, double d) {
        int dint = (int) (d * 100);
        String nValue = String.valueOf(dint);
        t.setText(": " + nValue + " %");
    }

    public static void setTextSliders(Pane root){
        setText();
        setSliders(root);
    }

//    -- Fin des méthodes pour ne pas surcharger openOptionSon --


    //-- Méthode pour générer les btn pour couper les sons --
    public static Button btnMuteMusicGenerator(Pane root){
        Button muteMusique = new Button();
        if (isMuted(nivSonMusique)) {
            muteMusique.setText("UNMUTE");
            OptionSon.nivSonMusique = 0;
        } else {
            muteMusique.setText("MUTE");
        }
        muteMusique.setTranslateX(400);
        muteMusique.setTranslateY(170);
        muteMusique.setOnAction(event -> {
            OptionSon.playSelectedButton();
            if (isMuted(nivSonMusique)) {
                nivSonMusique = 1;
                root.getChildren().addAll(sliderMusique, textNiveauSonMusique);
                sliderMusique.setValue(100);
                muteMusique.setText("MUTE");
                muteSound();
            } else {
                nivSonMusique = 0;
                root.getChildren().removeAll(sliderMusique, textNiveauSonMusique);
                muteSound();
                muteMusique.setText("UNMUTE");
            }
            event.consume();
        });
        return muteMusique;
    }
    public static Button btnMuteBruitGenerator(Pane root) {
        Button muteBruit = new Button();
        muteBruit.setTranslateX(400);
        muteBruit.setTranslateY(320);
        if (isMuted(nivSonBruit)) {
            muteBruit.setText("UNMUTE");
        } else {
            muteBruit.setText("MUTE");
        }
        muteBruit.setOnAction(event -> {
            OptionSon.playSelectedButton();
            if (isMuted(nivSonBruit)) {
                nivSonBruit = 1;
                sliderBruit.setValue(100);
                testSon = new Button("Test Bruitage");
                testSon.setTranslateX(600);
                testSon.setTranslateY(400);
                testSon.setOnAction(event2 -> {
                    playRebound();
                    event2.consume();
                });
                testSon.getStyleClass().addAll("spbtn", "textsize14");
                root.getChildren().addAll(sliderBruit, textNiveauSonBruit, testSon);
                muteBruit.setText("MUTE");
                muteSound();
            } else {
                nivSonBruit = 0;
                root.getChildren().removeAll(sliderBruit, textNiveauSonBruit, testSon);
                muteSound();
                muteBruit.setText("UNMUTE");
            }
            event.consume();
        });
        return muteBruit;
    }
    //-- Fin des méthodes pour générer les btn pour couper les sons --


    public static boolean isMuted(double d){
        return d == 0;
    }

    //Méthode qui joue le son de bouton
    public static void playSelectedButton(){
        if(nivSonBruit != 0){
            mediaPlayerSelected.play();
        }
    }

    public static void muteSound() {
        mediaPlayerMusique.setVolume(nivSonMusique);
        mediaPlayerBonk.setVolume(nivSonBruit);
        mediaPlayerSelected.setVolume(nivSonBruit);
    }

    public static boolean hasToSave = false;

    public static Button generateSaveSound(Stage stage) {
        Button btnSave = new Button();
        btnSave.setMinHeight(50);
        btnSave.setMinWidth(300);
        btnSave.setTranslateY(500);
        btnSave.setTranslateX(550-btnSave.getMinWidth()/2);

        btnSave.setOnAction(event -> {
            OptionSon.playSelectedButton();
            hasToSave = true;
            OptionSon.mediaPlayerMusique.setCycleCount(MediaPlayer.INDEFINITE);
            OptionSon.mediaPlayerMusique.setVolume(nivSonMusique);
            mediaPlayerMusique.stop();
            mediaPlayerMusique.play();
            event.consume();
            Option o2 = new Option();
            o2.OpenOption(stage);
            event.consume();
        });
        return btnSave;
    }

    public static void playRebound() {
        Random r = new Random();
        int i = r.nextInt(100);
        int ind;
        AudioClip res;
        if (i == 42) {
            res = mediaPlayerBonk;
        } else {
            if (i < 25) {
                ind = 0;
            } else if (i < 51) {
                ind = 1;
            } else if (i < 76) {
                ind = 2;
            } else {
                ind = 3;
            }
            res = new AudioClip(reboundMedia[ind]);
        }
        res.setVolume(OptionSon.nivSonBruit);
        res.play();
    }

    public static MediaPlayer playUlt(String nameUlt) {
        MediaPlayer res;
        Media ultSoundMedia  = new Media(new File("src/main/resources/Sound/Bruit/"+nameUlt+"_effect.wav").toURI().toString());
        res = new MediaPlayer(ultSoundMedia);
        res.setStartTime(Duration.ZERO);
        res.setVolume(nivSonBruit);
        return res;
    }

    public static void playChargedUlt(){
        OptionSon.mediaPlayerChargedUlt.play();
    }
}
