package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;

public class OptionSonPopUp extends OptionSon {

    public void start(Stage primaryStage) {

        Pane secondaryLayout = new Pane();

        Scene secondScene = new Scene(secondaryLayout, 1100, 600);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Option du son");
        newWindow.setScene(secondScene);
        newWindow.setResizable(false);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primaryStage);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        // Bouton retour pour revenir aux options
        Button retour = new Button();
        retour.setMinHeight(50);
        retour.setMinWidth(50);
        retour.setCancelButton(true);

        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
            newWindow.close();
            event.consume();
        });

        setTextSliders(secondaryLayout);

        Button btnSave = new Button();
        btnSave.setPrefSize(300, 50);
        btnSave.setTranslateY(500);
        btnSave.setTranslateX(550-btnSave.getPrefWidth()/2);
        

        btnSave.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Object[] o = new Object[27];// À changer si les parameters sont plus long
            FileReader reader;
            try {
                File file = new File(".//src/main/java/gui/.Parametre.txt");
                reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                o = br.lines().toArray();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            writeParametre(o , nivSonMusique, nivSonBruit);
            OptionSon.mediaPlayerMusique.setCycleCount(MediaPlayer.INDEFINITE);
            OptionSon.mediaPlayerMusique.setVolume(OptionSon.nivSonMusique);
            event.consume();
            newWindow.close();
        });

        muteMusique = btnMuteMusicGenerator(secondaryLayout);
        muteBruit = btnMuteBruitGenerator(secondaryLayout);
        // Text textMusic = new Text("Music");
        // Text textSoundEffect = new Text("Sound Effect ");

        textNiveauSonMusique.setX(800);
        textNiveauSonMusique.setY(200);
        textMusic.setX(220);
        textMusic.setY(200);

        textNiveauSonBruit.setX(800);
        textNiveauSonBruit.setY(350);
        textSoundEffect.setX(170);
        textSoundEffect.setY(350);

        switch (Menu.getGraphismes()) {
            case 0:
                secondaryLayout.setId("root");
                break;
            case 1:
                secondaryLayout.setId("root");
                break;
            case 2:
                secondaryLayout.setId("root2");
                break;
        }

        retour.setId("retour");
        btnSave.setId("save");
        muteMusique.getStyleClass().addAll("spbtn");
        muteBruit.getStyleClass().addAll("spbtn");
        textMusic.getStyleClass().addAll("texte");
        textSoundEffect.getStyleClass().addAll("texte");
        textNiveauSonBruit.getStyleClass().addAll("texte");
        textNiveauSonMusique.getStyleClass().addAll("texte");

        secondScene.getStylesheets().addAll(getClass().getResource("stylesheet/optionSonstylesheet.css").toExternalForm());
        secondaryLayout.getChildren().addAll(retour, btnSave, muteMusique, muteBruit, textMusic, textSoundEffect);
        if (!isMuted(nivSonMusique)) {
            secondaryLayout.getChildren().addAll(sliderMusique, textNiveauSonMusique);
        }
        if (!isMuted(nivSonBruit)) {
            testSon.setText("Test Bruitage");
            testSon.setTranslateX(600);
            testSon.setTranslateY(400);

            testSon.setOnAction(event -> {
                playRebound();
                event.consume();
            });
            testSon.getStyleClass().addAll("spbtn", "textsize14");
            secondaryLayout.getChildren().addAll(sliderBruit, textNiveauSonBruit, testSon);
        }

        newWindow.show();

    }
}
