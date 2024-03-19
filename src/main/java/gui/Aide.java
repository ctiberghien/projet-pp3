package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Aide {

    static int page = 0;
    static LinkedList<LinkedList<Text>> texteAide = new LinkedList<>();
   
    public void start(Stage primaryStage, boolean b) {
        StackPane root = new StackPane();
        Stage newWindow = new Stage();
        Scene scene = new Scene(root, 1100, 600);
        if (b) {
            // New window (Stage)
            newWindow.setTitle("Aide");
            newWindow.setScene(scene);
            newWindow.setResizable(false);

            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);

            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(primaryStage);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);
        }

        Text escape = new Text("[ESCAPE]");
        escape.setTranslateX(-440);
        escape.setTranslateY(-260);
        escape.setFill(Color.WHITE);

        Text enter = new Text("[ENTER]");
        enter.setTranslateX(440);
        enter.setTranslateY(220);
        enter.setFill(Color.WHITE);

        Button retour = new Button();
        retour.setMinSize(50, 50);
        retour.setCancelButton(true);
        retour.setTranslateY(-250);
        retour.setTranslateX(-500);

        Button suivant = new Button();
        suivant.setTranslateX(500);
        suivant.setTranslateY(230);
        suivant.setMinSize(50, 50);

        Text aide = new Text("Aide Bonk");
        aide.setTranslateY(-250);

        StackPane texte = new StackPane();
        texte.setMaxWidth(300);
        texte.setMaxWidth(400);

        try {
            texteAide = recupFichier();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 

        int cptPosR = -250;
        LinkedList<Text> pagedefaut = texteAide.get(0);
        for (int i = 0; i < pagedefaut.size(); i++) {
            Text t = pagedefaut.get(i);
            t.setTranslateY(cptPosR);
            t.setFill(Color.WHITE);
            texte.getChildren().addAll(t);
            cptPosR+=15;
        }

        retour.setOnAction(ev -> {
            if (b) {
                newWindow.close();
            } else {
                Menu m = new Menu();
                m.start(primaryStage);
                OptionSon.playSelectedButton();
            }
            ev.consume();
        });

        suivant.setOnAction(ev -> {
            OptionSon.playSelectedButton();
            if (page==0) {
                texte.getChildren().clear();
                int cptPos = -250;
                LinkedList<Text> pagecourante = texteAide.get(1);
                for (int i = 0; i < pagecourante.size(); i++) {
                    Text t = pagecourante.get(i);
                    t.setTranslateY(cptPos);
                    t.setFill(Color.WHITE);
                    texte.getChildren().addAll(t);
                    cptPos+=15;
                }
                page=1;
            }
            else if (page==1) {
                texte.getChildren().clear();
                int cptPos = -250;
                LinkedList<Text> pagecourante = texteAide.get(2);
                for (int i = 0; i < pagecourante.size(); i++) {
                    Text t = pagecourante.get(i);
                    t.setTranslateY(cptPos);
                    t.setFill(Color.WHITE);
                    texte.getChildren().addAll(t);
                    cptPos+=15;
                }
                page=2;
            }
            else {
                texte.getChildren().clear();
                int cptPos = -250;
                LinkedList<Text> pagecourante = texteAide.get(0);
                for (int i = 0; i < pagecourante.size(); i++) {
                    Text t = pagecourante.get(i);
                    t.setTranslateY(cptPos);
                    t.setFill(Color.WHITE);
                    texte.getChildren().addAll(t);
                    cptPos+=15;
                }
                page=0;
            }
            ev.consume();
        });

        scene.setOnKeyPressed(key -> {
            if(key.getCode() == KeyCode.ENTER){
                suivant.fire();
            }
        });

        //ajout du css

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

        scene.getStylesheets().addAll(getClass().getResource("stylesheet/aidestylesheet.css").toExternalForm());
        suivant.setId("next");
        retour.setId("retour");
        //escape.setId("escape");
        //enter.setId("enter");

        if (!b) primaryStage.setScene(scene);
        root.getChildren().addAll(aide,texte, suivant, retour, escape, enter);
        if (b) newWindow.show();
        else primaryStage.show();
    }

    public static LinkedList<LinkedList<Text>> recupFichier() throws FileNotFoundException {
        LinkedList<LinkedList<Text>> l = new LinkedList<>();
        LinkedList<Text> page1 = new LinkedList<>();
        LinkedList<Text> page2 = new LinkedList<>();
        LinkedList<Text> page3 = new LinkedList<>();
        Scanner sc = new Scanner(new File("config/Aide"));
        String s ="";
        s=sc.nextLine();
        Text t = new Text(s);
        page1.add(t);
        s=sc.nextLine();
        t = new Text(s);
        page1.add(t);
        s=sc.nextLine();
        t = new Text(s);
        page1.add(t);
        s="- Touches -\n";
        t = new Text(s);
        page1.add(t);
        s="\n";
        t = new Text(s);
        page1.add(t);
        s="La raquette 1 peut aller en haut avec la touche ["+Joueurs.getA().getUp().toString()+"]\n";
        t = new Text(s);
        page1.add(t);
        s="La raquette 1 peut aller en bas avec la touche ["+Joueurs.getA().getDown().toString()+"]\n";
        t = new Text(s);
        page1.add(t);
        s="La raquette 2 peut aller en haut avec la touche ["+Joueurs.getB().getUp().toString()+"]\n";
        t = new Text(s);
        page1.add(t);
        s="La raquette 2 peut aller en bas avec la touche ["+Joueurs.getB().getDown().toString()+"]\n";
        t = new Text(s);
        page1.add(t);
        s="La raquette 1 peut activer son ultimate avec la touche ["+Joueurs.getA().getUlt().toString()+"]\n";
        t = new Text(s);
        page1.add(t);
        s="La raquette 2 peut activer son ultimate avec la touche ["+Joueurs.getB().getUlt().toString()+"]\n";
        t = new Text(s);
        page1.add(t);
        s="Le jeu peut être mis en pause avec la touche ["+Joueurs.getA().getPause().toString()+"]\n";
        t = new Text(s);
        page1.add(t);
        s="\n";
        t = new Text(s);
        page1.add(t);
        int cpt = 0;
        while (sc.hasNextLine()) {
            s=sc.nextLine();
            t = new Text(s);
            if (cpt>55) {
                page3.add(t);
            } else if (cpt>22) {
                page2.add(t);
            } else {
                page1.add(t);
            }
            cpt++;
        }
        sc.close();
        l.add(page1); l.add(page2); l.add(page3);
        return l;
    }
}