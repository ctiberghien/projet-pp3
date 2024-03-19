package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("unchecked")
public class LeaderBoardTab {

    // boolean qui regarde permet de savoir si le joueur a deja enregistrer les
    // stats
    static boolean isDeclared = false;
    static int point = 0;

    public static LeaderBoard lb = new LeaderBoard();

    public void OpenLeaderBoard(Stage stage, Boolean isMaJable) {
        // Code des boutons
        Button retour = new Button();
        retour.setTranslateX(-520);
        retour.setMinHeight(50);
        retour.setMinWidth(50);

        Button valid = new Button("Valider");
        Button replay = new Button("rejouer");
        retour.setCancelButton(true);

        Region textClassement = new Region();
        textClassement.setMaxHeight(50);
        textClassement.setMaxWidth(400);

        Text txtPseudo = new Text("Entré.e le nom que vous voulez enregistrer");
        TextField pseudo = new TextField();
        replay.setTranslateY(-30);
        textClassement.setTranslateY(-30);
        stats.setTranslateY(-40);
        txtPseudo.setTranslateY(-20);
        pseudo.setTranslateY(-20);
        valid.setTranslateY(-10);

        VBox rootV = new VBox();
        // Add the Table to the VBox
        if (isMaJable) {
            // Boolean a true si on peut mettre de nouvelles statistiques
            rootV.getChildren().addAll(retour, textClassement, stats, replay, txtPseudo, pseudo, valid);
        } else {
            rootV.getChildren().addAll(retour, textClassement, stats);
        }
        retour.setOnAction(event -> {
            OptionSon.playSelectedButton();
            Menu m = new Menu();
            m.start(stage);
            event.consume();
        });

        valid.setOnAction(event -> {
            OptionSon.playSelectedButton();
            LeaderBoard.point = point;
            if (pseudo.getCharacters().length() == 0) {
                lb.setLeaderBoardtoadd("UNKNOWN");
            } else {
                lb.setLeaderBoardtoadd(pseudo.getCharacters().toString());
            }
            refreshTab();
            LeaderBoardTab o = new LeaderBoardTab();
            o.OpenLeaderBoard(stage, false);
            stats.getItems().add(Stats.previousStats.get(Stats.previousStats.size() - 1));
            point = 0;
            event.consume();
        });
        replay.setOnAction(event -> {
            OptionSon.playSelectedButton();
            LeaderBoardTab.point = 0;
            App game = new App();
            GameView.isWin = false;
            GameView.lbWin = false;
            GameView.i = 0;
            game.start(stage);
            event.consume();
        });
        if (!isDeclared) {
            stats.refresh();
            setTab();
            isDeclared = true;
        } else {
            Stats.setPreviousStats();
            stats.refresh();
            // setTab();
            // update seulement
        }
        // add CSS :

        switch (Menu.getGraphismes()) {
            case 0:
                rootV.setId("root");
                break;
            case 1:
                rootV.setId("root");
                break;
            case 2:
                rootV.setId("root2");
                break;
        }

        textClassement.setId("txtclassement");
        stats.setId("stats");
        replay.setId("replay");
        replay.getStyleClass().addAll("boutton");
        retour.setId("retour");
        retour.getStyleClass().addAll("left");
        txtPseudo.setId("txtPseudo");
        pseudo.setId("pseudotxtfield");
        valid.setId("valid");
        valid.getStyleClass().addAll("boutton");

        Scene scene = new Scene(rootV, 1100, 600);
        scene.getStylesheets().add(getClass().getResource("stylesheet/leaderboardstylesheet.css").toExternalForm());

        stage.setScene(scene);
        rootV.requestFocus();
    }

    static TableView<Stats> stats = new TableView<>();

    public static void setTab() {
        // stats.setItems(null);
        Stats.setPreviousStats();
        stats.setItems(getStatList());
        stats.getColumns().addAll(getNameColumn(), getStatColumn());
        stats.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Set the Placeholder for an empty table
        stats.setPlaceholder(new Label("No visible columns and/or data exist."));
    }

    public static ObservableList<Stats> getStatList() {
        Stats.setPreviousStats();
        return FXCollections.observableArrayList(Stats.previousStats);
    }

    public static TableColumn<Stats, String> getStatColumn() {
        TableColumn<Stats, String> statColumn = new TableColumn<>("Points");
        PropertyValueFactory<Stats, String> statCellValueFactory = new PropertyValueFactory<>("stat");
        statColumn.setCellValueFactory(statCellValueFactory);
        statColumn.setReorderable(false);
        return statColumn;
    }

    public static TableColumn<Stats, String> getNameColumn() {
        TableColumn<Stats, String> nameColumn = new TableColumn<>("Name");
        PropertyValueFactory<Stats, String> statCellValueFactory = new PropertyValueFactory<>("name");
        nameColumn.setCellValueFactory(statCellValueFactory);
        nameColumn.setReorderable(false);
        return nameColumn;
    }

    public static void refreshTab() {
        lb.refreshLeaderBoard();
        Stats.setPreviousStats();
        stats.refresh();
    }

}
