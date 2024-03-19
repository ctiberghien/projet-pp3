package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Court.Court;

import java.util.Objects;


public class ElementSize {

    //set les 2 checkbox
    public static CheckBox collisions_centrees = new CheckBox("HitBox avec\nla surface");

    //set les 3 sliders
    private static final Slider sliderBallSpeed = new Slider(50,450, Court.ballSpeedX);
    private static final Slider sliderRacket = new Slider(50,300,Court.racketSize);
    private static final Slider sliderBall = new Slider(5,30,Court.ballRadius);


    public void start(Stage stage) {

        // Déclaration de la nouvelle fenêtre

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);

        //Déclaration du rectangle

        Region r = new Region();
        r.setMaxSize(880, 480);
        r.setTranslateY(20);



        // Emplacement des sliders

        sliderRacket.setTranslateX(-310);
        sliderBall.setTranslateX(0);
        sliderBallSpeed.setTranslateX(310);
        sliderBall.setTranslateY(-250);
        sliderRacket.setTranslateY(-250);
        sliderBallSpeed.setTranslateY(-250);
        sliderBallSpeed.setFocusTraversable(false);
        sliderBall.setFocusTraversable(false);
        sliderRacket.setFocusTraversable(false);
        // Mise des tailles des curseurs

        sliderBallSpeed.setMaxWidth(180);
        sliderBallSpeed.setMaxHeight(160);
        sliderRacket.setMaxWidth(180);
        sliderRacket.setMaxHeight(160);
        sliderBall.setMaxWidth(180);
        sliderBall.setMaxHeight(160);


        // Déclaration des 3 Text

        Text selectionRadiusBalle = new Text("Taille de la balle");
        Text selectionVitesseBalle = new Text("vitesse de la balle:");
        Text selectionRacketTaille = new Text("Taille des raquettes:");

        Text R = new Text("[R]");
        R.setFill(Color.WHITE);
        R.setTranslateX(-487);
        R.setTranslateY(-120);

        Text C = new Text("[C]");
        C.setFill(Color.WHITE);
        C.setTranslateY(200);
        C.setTranslateX(-220);

        Text enter = new Text("[ENTER]");
        enter.setFill(Color.WHITE);
        enter.setTranslateX(510);
        enter.setTranslateY(-230);

        Text escape = new Text("[ESCAPE]");
        escape.setFill(Color.WHITE);
        escape.setTranslateY(-230);
        escape.setTranslateX(-510);

        // Font des Text

        selectionRadiusBalle.setFont(new Font(13));
        selectionVitesseBalle.setFont(new Font(13));
        selectionRacketTaille.setFont(new Font(13));

        // Emplacement des Text

        selectionRadiusBalle.setTranslateY(-280);
        selectionVitesseBalle.setTranslateY(-280);
        selectionRacketTaille.setTranslateY(-280);

        selectionRadiusBalle.setTranslateX(0);
        selectionVitesseBalle.setTranslateX(+310);
        selectionRacketTaille.setTranslateX(-310);

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

        // La valeur des curseurs est envoyée dans la classe Joueurs et set le
        // remplissage des éléments preview
        sliderRacket.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            if (sliderRacket.getValue()+(sliderBall.getValue()*2)>Court.height) {
                sliderBall.setValue((Court.height-sliderRacket.getValue())/2);
            }
            Court.racketSize = (sliderRacket.getValue());
            racketA_preview.setHeight(Court.racketSize);
            racketB_preview.setHeight(Court.racketSize);
        });
        sliderBall.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            if (sliderRacket.getValue()+(sliderBall.getValue()*2)>Court.height) {
                sliderRacket.setValue(Court.height - (sliderBall.getValue() * 2));
            }
            Court.ballRadius = (sliderBall.getValue());
            ball_preview.setRadius(Court.ballRadius);
        });
        sliderBallSpeed.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            Court.ballSpeedX = sliderBallSpeed.getValue();
            Court.ballSpeedY = sliderBallSpeed.getValue();
        });

        //Code pour le bouton des collisions
        Button collision = new Button();
        collision.setTranslateX(-315);
        collision.setTranslateY(200);
        if (collisions_centrees.isSelected()){
            collision.setText("HitBox sur les bords de la balle");
        }else{
            collision.setText("HitBox au centre de la balle");
        }
        collision.setOnAction(event -> {
            OptionSon.playSelectedButton();
            if (collisions_centrees.isSelected()){
                collision.setText("HitBox au centre de la balle");
                collisions_centrees.setSelected(false);
            }else{
                collision.setText("Collisions sur les bords de la balle");
                collisions_centrees.setSelected(true);
            }
            event.consume();
        });

        //Code du bouton reset

        Button reset = new Button("Reset");
        reset.setTranslateX(-490);
        reset.setTranslateY(-180);
        reset.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Court.racketSize = 100.0;
            Court.ballRadius = 10.0;
            Court.ballSpeedX = 200;
            Court.ballSpeedY= 200;
            ElementSize.collisions_centrees.setSelected(false);
            racketA_preview.setHeight(Court.racketSize);
            racketB_preview.setHeight(Court.racketSize);
            ball_preview.setRadius(Court.ballRadius);
            sliderRacket.setValue(Court.racketSize);
            sliderBallSpeed.setValue(Court.ballSpeedX);
            sliderBall.setValue(Court.ballRadius);
            collision.setText("HitBox au centre de la balle");
            event.consume();
        });


        // Code du Button suivant

        Button suivant = new Button();
        suivant.setTranslateX(510);
        suivant.setTranslateY(-260);
        suivant.setMinSize(80, 80);
        suivant.setOnAction(event -> {
            OptionSon.playSelectedButton();
            switch (Menu.userPosition.getLast()) {
                case "Skin":
                    Joueurs.playerA.setCoupUltimate(null);
                    Joueurs.playerB.setCoupUltimate(null);
                    switch (Parametre.modeDejeu) {
                        case "ModePoint":
                        case "ModeInfini":
                            ChoixUltimate ch = new ChoixUltimate();
                            Menu.userPosition.offer("ElementSize");
                            Joueurs.playerB.setIsbot(false);
                            Joueurs.playerA.setIsbot(false);
                            ch.start(stage);
                            break;
                    }
                    break;
                case "MenuMultiQM":
                    Menu.userPosition.offer("ElementSizeQM");
                    ChoixUltimate choixUltimate=new ChoixUltimate();
                    choixUltimate.start(stage);
                    break;
            }
            event.consume();
        });
        if (!Parametre.modeDejeu.isEmpty())
                root.getChildren().addAll(suivant);


        // Code du Button retour

        Button retour = new Button();
        retour.setTranslateX(-510);
        retour.setTranslateY(-260);
        retour.setMinSize(80, 80);
        retour.setCancelButton(true);
        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
            if ("Skin".equals(Menu.userPosition.getLast())) {
                Skin sk = new Skin();
                Menu.userPosition.removeLast();
                sk.start(stage);
                event.consume();
            } else if ("MenuMultiQM".equals(Menu.userPosition.getLast())) {
                Menu.userPosition.removeLast();
                MenuMulti m = new MenuMulti();
                m.start(stage);
            }
            event.consume();
        });

        //ajout du css

        switch (Menu.getGraphismes()) {
            case 0:
            case 1:
                root.setId("root");
                break;

            case 2:
                root.setId("root2");
                break; 
        }

        ball_preview.getStyleClass().addAll("preview");
        racketA_preview.getStyleClass().addAll("preview");
        racketB_preview.getStyleClass().addAll("preview");
        r.getStyleClass().addAll("border");
        r.setId(Parametre.getActualMap());
        retour.setId("retour");
        suivant.setId("next");
        collision.setId("collision");
        selectionRadiusBalle.getStyleClass().addAll("selectiontexte","textsize15");
        collision.getStyleClass().addAll("spbtn","textsize9");
        reset.getStyleClass().addAll("spbtn","textsize13");
        selectionVitesseBalle.getStyleClass().addAll("selectiontexte","textsize15");
        selectionRacketTaille.getStyleClass().addAll("selectiontexte","textsize15");


        scene.setOnKeyPressed(key -> {
            if(key.getCode() == KeyCode.ENTER){
                OptionSon.playSelectedButton();
                Joueurs.playerA.setCoupUltimate(null);
                Joueurs.playerB.setCoupUltimate(null);
                switch (Parametre.modeDejeu) {
                    case "ModePoint":
                    case "ModeInfini":
                        ChoixUltimate ch = new ChoixUltimate();
                        Menu.userPosition.offer("ElementSize");
                        Joueurs.playerB.setIsbot(false);
                        Joueurs.playerA.setIsbot(false);
                        ch.start(stage);
                        break;
                }
            }
            else if(key.getCode() == KeyCode.C){
                collisions_centrees.setSelected(!collisions_centrees.isSelected());
            }
            else if(key.getCode() == KeyCode.R){
                reset.fire();
            }
        });

        //On met la vitesse de la balle par défaut à 200 et on ajoute la valeur de curseur
        Court.ballSpeedY=(sliderBallSpeed.getValue() + 200);
        Court.ballSpeedX=(sliderBallSpeed.getValue() + 200);

        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("stylesheet/choixUltimatestylesheet.css")).toExternalForm());


        // Affichage des éléments
        root.getChildren().addAll(r, retour  , collision, reset,selectionRadiusBalle, selectionVitesseBalle, R, C, enter, escape, 
                selectionRacketTaille, racketA_preview, racketB_preview, ball_preview , sliderRacket , sliderBallSpeed , sliderBall);
        stage.show();
    }
}
