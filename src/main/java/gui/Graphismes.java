package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Graphismes extends Application{
    
    private static int graphismes;

    public static void setGraphismes(int graphismes) {
        Graphismes.graphismes = graphismes;
    }

    public void start(Stage stage) {
        // Déclaration de la nouvelle fenêtre

        StackPane root = new StackPane();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/iconBonk.png")));
        stage.setTitle("choisissez les graphismes");
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);

        Text Graphismetext = new Text();

        Text highResText = new Text("Graphismes élevés");
        Text lowResText = new Text("Graphismes bas");
        Text veryLowResText = new Text("Graphismes très bas");

        highResText.setFill(Color.WHITE);
        lowResText.setFill(Color.WHITE);
        veryLowResText.setFill(Color.WHITE);

        //ajout du css
        scene.getStylesheets().addAll(getClass().getResource("stylesheet/graphismestylesheet.css").toExternalForm());
        root.setId("root");

        Button highRes = new Button();
        Button lowRes = new Button();
        Button veryLowRes = new Button();

        if (graphismes==0) {
            highRes.setId("checked");
            lowRes.setId("unchecked");
            veryLowRes.setId("unchecked");
        }
        if (graphismes==1) {
            lowRes.setId("checked");
            highRes.setId("unchecked");
            veryLowRes.setId("unchecked");
        }
        if (graphismes==2) {
            veryLowRes.setId("checked");
            highRes.setId("unchecked");
            lowRes.setId("unchecked");
        }

        highRes.setMinWidth(110);
        highRes.setMinHeight(110);
        lowRes.setMinWidth(110);
        lowRes.setMinHeight(110);
        veryLowRes.setMinWidth(110);
        veryLowRes.setMinHeight(110);

        Text enter = new Text("[ENTER]");
        enter.setFill(Color.WHITE);
        enter.setTranslateX(60);
        enter.setTranslateY(100);
        
        Text F = new Text("[F]");
        F.setFill(Color.WHITE);
        F.setTranslateX(-250);
        F.setTranslateY(30);

        Text G = new Text("[G]");
        G.setFill(Color.WHITE);
        G.setTranslateX(0);
        G.setTranslateY(30);

        Text H = new Text("[H]");
        H.setFill(Color.WHITE);
        H.setTranslateX(250);
        H.setTranslateY(30);


        highResText.setTranslateX(-250);
        veryLowResText.setTranslateX(250);

        gereCheckbox(highRes, lowRes, veryLowRes, 0);

        gereCheckbox(lowRes, highRes, veryLowRes, 1);

        gereCheckbox(veryLowRes, lowRes, highRes, 2);

        // Code du Button retour

        Button retour = new Button();
        //retour.setTranslateX(-523);
        retour.setTranslateY(100);
        retour.setMinSize(50, 50);
        retour.setCancelButton(true);

        retour.setOnAction(ev -> {
            OptionSon.playSelectedButton();
//                modificationFichierParametre(a);
            hasToSave = true;
            Menu.setGraphismes(graphismes);
            if (graphismes==2) {
                Parametre.fondMapindex=3;
            }
            Menu m = new Menu();
            m.start(stage);
            ev.consume();
        });

            highRes.setTranslateY(-100);
            lowRes.setTranslateY(-100);
            veryLowRes.setTranslateY(-100);
            highRes.setTranslateX(-250);
            veryLowRes.setTranslateX(250);

            highResText.setTranslateX(-250);
            veryLowResText.setTranslateX(250);

            gereCheckbox(highRes, lowRes, veryLowRes, 0);

            gereCheckbox(lowRes, highRes, veryLowRes, 1);

            gereCheckbox(veryLowRes, lowRes, highRes, 2);

            scene.setOnKeyPressed(key -> {
                if(key.getCode() == KeyCode.F){
                    highRes.fire();
                }
                else if(key.getCode() == KeyCode.G){
                    lowRes.fire();
                }
                else if(key.getCode() == KeyCode.H){
                    veryLowRes.fire();
                }
                else if(key.getCode() == KeyCode.ENTER){
                    retour.fire();
                }
            });

            // Affichage des éléments
            highRes.getStyleClass().add("button");
            lowRes.getStyleClass().add("button");
            veryLowRes.getStyleClass().add("button");
            retour.setId("checked");
            root.getChildren().addAll(highRes, lowRes, veryLowRes, retour, highResText, lowResText, veryLowResText, enter, F, G, H);
            stage.show();
        }
    


        public static boolean hasToSave = false;
    // Fonction permettant de déselectionner les checkbox si une des checkbox est déjà activée et change la valeur des graphismes choisis
    public static void gereCheckbox(Button principale, Button autre1, Button autre2, int val) {
        principale.setOnAction(ev -> {
            OptionSon.playSelectedButton();
            if (autre1.getId().equals("checked") || autre2.getId().equals("checked")) {
                autre1.setId("unchecked");
                autre2.setId("unchecked");
                principale.setId("checked");
            } else {
                principale.setId("checked");
            }
            graphismes=val;
            ev.consume();
        });
    }
    
    // Fonction permettant de modifier le fichier de paramètres lors de la fer


    public static int getGraphismes() {
        return graphismes;
    }
}