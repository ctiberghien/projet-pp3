package gui;

import javafx.scene.control.Button;
import model.Court.Court;
import model.Court.ScoreBoard;
import model.Ultimate.BalleRapideUlt;
import model.Ultimate.BalleWave;
import model.Ultimate.ThunderBallUlt;
import model.Ultimate.WallUlt;

import java.io.*;
import java.util.Scanner;

public class ParametresSaved {
    //Le but de cette classe est de lire le fichier de config qui contiendra les paramètres d'une partie et de la configurer sans que l'utilisateur le fasse lui-même.
    public static String[] tabParameters;
    private static String stringContentNextFile ="";
    public static boolean isSaved = true;

    private static String previousContent;
    private static int compteurRead = 0;

    public static boolean hasToSave = false;


    //Fonction qui lit le fichier et l'envoi a l'analyse s'il n'existe pas va le créer avec createFile
    private static void readFile() {
        if (compteurRead == 0){
            compteurRead+=1;
            String content = "";
            try {
                File doc = new File("config/QuickMatchConfig");

                BufferedReader obj = new BufferedReader(new FileReader(doc));

                String string;
                while ((string = obj.readLine()) != null) {
                    content = string;
                }
                obj.close();
            } catch (IOException e) {
                createFile();
            }
            if (content.equals("")){
                isSaved = false;
                tabParameters =null;
            }else{
                previousContent=content;
                analyseStringToTab(content);
            }
        }
    }

    //Fonction qui va créer le fichier s'il n'existe pas
    public static void createFile (){
        try {
            File myObj = new File("config/QuickMatchConfig");
            if (myObj.createNewFile()) {
                tabParameters = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Fonction qui va analyser le contenu du fichier pour le stocker dans un tableau
    [0] mode de Jeu
    Selon le mode de jeu ça change la taille
    Si infinie length = 9 ;
    Si Classic length = 9 ;
    Si Challenge length = 2 ;

    Pour classic et infini
    [1] "A"+UltA
    [2] "B"+UltB
    [3] "A"+isBotA+niv(if istrue)
    [4] "B"+isBotB+niv(if istrue)
    [5] tailleRacket
    [6] tailleBall
    [7] ballSpeed
    [8] nbPoint (Si infini alors ==-1)
    [9] Collision centré ou non(boolean)
    Pour Challenge
    [1]ult
    */
    public static void analyseStringToTab(String content){
        Scanner sc = new Scanner(content);
        sc.useDelimiter("/");
        try{
            String tmp;
            int ind=0;
            while (sc.hasNext()){
                tmp = sc.next();
                switch (ind){
                    case 0:
                        switch (tmp){
                            //Vérification du mode de jeu
                            case "ModePoint":
                            case "ModeInfini":
                                tabParameters =new String[10];
                                tabParameters[ind]=tmp;
                                break;
                             case "ModeChallenge":
                                tabParameters =new String[2];
                                tabParameters[ind]=tmp;
                                break;
                            default:
                                isSaved = false;
                                tabParameters =null;
                                return;
                        }
                        ind+=1;
                        break;
                    case 1:
                        //Vérification de l'ult du joueur A.
                        switch (tmp){
                            case "BalleRapideUlt":
                            case "BalleWave":
                            case "ThunderBall":
                            case "EarthWall":
                            case "Anull":
                            case "null":
                                tabParameters[ind]=tmp;
                                if (tabParameters[0].equals("ModeChallenge")){
                                    return;
                                }
                                break;
                            default:
                                isSaved = false;
                                tabParameters =null;
                                return;
                        }
                        ind+=1;
                        break;
                    case 2:
                        //Vérification de l'ult du joueur B.
                        switch (tmp){
                            case "BalleRapideUlt":
                            case "BalleWave":
                            case "ThunderBall":
                            case "EarthWall":
                            case "null":
                            case "Bnull":
                                tabParameters[ind]=tmp;
                                break;
                            default:
                                isSaved = false;
                                tabParameters =null;
                                return;
                        }
                        ind+=1;
                        break;
                    case 3:
                        //Vérification si A est bot
                        switch (tmp){
                            case "Atrue1":
                            case "Atrue2":
                            case "Afalse1":
                            case "false":
                            case "Afalse2":
                                tabParameters[ind]=tmp;
                                break;
                            default:
                                isSaved = false;
                                tabParameters =null;
                                return;
                        }
                        ind+=1;
                        break;
                    case 4:
                        //Vérification si B est bot
                        switch (tmp){
                            case "Btrue1":
                            case "Btrue2":
                            case "false":
                            case "Bfalse1":
                            case "Bfalse2":
                                tabParameters[ind]=tmp;
                                break;
                            default:
                                isSaved = false;
                                tabParameters =null;
                                return;
                        }
                        ind+=1;
                        break;
                    case 5:
                        //Vérification de la taille de la raquette
                        double tr = Double.parseDouble(tmp);
                        if (tr>=50 && tr<=300){
                            tabParameters[ind]=tmp;
                        }else {
                            isSaved = false;
                            tabParameters =null;
                            return;
                        }
                        ind+=1;
                        break;
                    case 6:
                        //Vérification de la taille de la balle
                        double tb = Double.parseDouble(tmp);
                        if (tb>=5 && tb<=30){
                            tabParameters[ind]=tmp;
                        }else {
                            isSaved = false;
                            tabParameters =null;
                            return;
                        }
                        ind+=1;
                        break;
                    case 7:
                        //Vérification de la vitesse de la balle
                        double vb = Double.parseDouble(tmp);
                        if (vb>=50 && vb<=450){
                            tabParameters[ind]=tmp;
                        }else {
                            isSaved = false;
                            tabParameters =null;
                            return;
                        }
                        ind+=1;
                        break;
                    case 8:
                        //Vérification du nombre de point
                        int nbP = Integer.parseInt(tmp);
                        if ((nbP>=-1 && nbP<=100)){
                            tabParameters[ind]=tmp;
                        }else{
                            isSaved = false;
                            tabParameters =null;
                            return;
                        }
                        ind+=1;
                        break;
                    case 9:
                        if (tmp.equals("true")){
                            tabParameters[ind]=tmp;
                        }else if (tmp.equals("false")){
                            tabParameters[ind]=tmp;
                        }else{
                            isSaved = false;
                            tabParameters =null;
                            return;
                        }
                        return;
                    default:
                        isSaved = false;
                        tabParameters =null;
                        return;
                }
            }
        }catch (Exception ignored){
            isSaved = false;
            tabParameters =null;
        }
    }

    //Fonction qui met à jour le string qui sera écrit dans le fichier
    public static void tabToString(){
        String res = "";
        if (tabParameters!=null){
            for (String tabParameter : tabParameters) {
                res += tabParameter + "/";
            }
        }else{
            res="42";
        }
        stringContentNextFile = res;
    }

    //Fonction pour ecrire dans le fichier
    private static void reWriteFile(){
        try {
            if (previousContent!=null){
                if (!previousContent.equals(stringContentNextFile)){
                    FileWriter myWriter = new FileWriter("config/QuickMatchConfig");
                    myWriter.write(stringContentNextFile);
                    myWriter.close();
                }
            }else{
                FileWriter myWriter = new FileWriter("config/QuickMatchConfig");
                myWriter.write(stringContentNextFile);
                myWriter.close();
            }
        } catch (IOException ignore) {}
    }

    //Fonction publique appeler quand on save les paramètres
    public static void saveParameters(){
        tabToString();
        reWriteFile();
    }


    //Fonction qui le fichier si ce n'est pas déjà fait et met la partie au réglage
    public static void setParametersToGame(){
        if (isSaved){
            switch (tabParameters[0]){
                //Vérification du mode de jeu
                case "ModeInfini":
                case "ModePoint":
                    Parametre.modeDejeu=tabParameters[0];
                    String ultA = tabParameters[1].substring(1);
                    String ultB = tabParameters[2].substring(1);
                    String isBotA = tabParameters[3].substring(1);
                    String isBotB = tabParameters[4].substring(1);
                    String rSize = tabParameters[5];
                    String bSize = tabParameters[6];
                    String bSpeed = tabParameters[7];
                    String points = tabParameters[8];
                    String collisions = tabParameters[9];
                    ScoreBoard.nbPoint = Integer.parseInt(points);
                    switch (ultA){
                        case "BalleRapideUlt":
                            Joueurs.getA().setCoupUltimate(new BalleRapideUlt());
                            break;
                        case "BalleWave":
                            Joueurs.getA().setCoupUltimate(new BalleWave());
                            break;
                        case "ThunderBall":
                            Joueurs.getA().setCoupUltimate(new ThunderBallUlt());
                            break;
                        case "EarthWall":
                            Joueurs.getA().setCoupUltimate(new WallUlt());
                            break;
                        default:
                            Joueurs.getA().setCoupUltimate(null);
                            break;
                    }
                    switch (ultB){
                        case "BalleRapideUlt":
                            Joueurs.getB().setCoupUltimate(new BalleRapideUlt());
                            break;
                        case "BalleWave":
                            Joueurs.getB().setCoupUltimate(new BalleWave());
                            break;
                        case "ThunderBall":
                            Joueurs.getB().setCoupUltimate(new ThunderBallUlt());
                            break;
                        case "EarthWall":
                            Joueurs.getB().setCoupUltimate(new WallUlt());
                            break;
                        default:
                            Joueurs.getB().setCoupUltimate(null);
                            break;
                    }
                    switch (isBotA){
                        case "false":
                        case "false1":
                        case "false2":
                            Joueurs.getA().setIsbot(false);
                            break;
                        case "true1":
                            Joueurs.getA().setIsbot(true);
                            Parametre.botLvlA = 1;
                            break;
                        case "true2":
                            Joueurs.getA().setIsbot(true);
                            Parametre.botLvlA = 2;
                            break;
                        default:
                            break;
                    }
                    switch (isBotB){
                        case "false":
                        case "false1":
                        case "false2":
                            Joueurs.getB().setIsbot(false);
                            break;
                        case "true1":
                            Joueurs.getB().setIsbot(true);
                            Parametre.botLvlB = 1;
                            break;
                        case "true2":
                            Joueurs.getB().setIsbot(true);
                            Parametre.botLvlB = 2;
                            break;
                        default:
                            break;
                    }
                    double tr = Double.parseDouble(rSize);
                    if (tr>=50 && tr<=300){
                        Court.racketSize=tr;
                    }
                    double tb = Double.parseDouble(bSize);
                    if (tb>=5 && tb<=30){
                        Court.ballRadius = tb;
                    }
                    double vb = Double.parseDouble(bSpeed);
                    if (vb>=50 && vb<=450){
                        Court.ballSpeedX=vb;
                        Court.ballSpeedY=vb;
                    }
                    if(collisions.equals("true")){
                        ElementSize.collisions_centrees.setSelected(true);
                    }else if (collisions.equals("false")){
                            ElementSize.collisions_centrees.setSelected(false);
                    }
                case "ModeChallenge":
                    Parametre.modeDejeu=tabParameters[0];
                    String ultAC = tabParameters[1].substring(1);
                    switch (ultAC){
                        case "BalleRapideUlt":
                            Joueurs.getA().setCoupUltimate(new BalleRapideUlt());
                            break;
                        case "BalleWave":
                            Joueurs.getA().setCoupUltimate(new BalleWave());
                            break;
                        case "ThunderBall":
                            Joueurs.getA().setCoupUltimate(new ThunderBallUlt());
                            break;
                        case "EarthWall":
                            Joueurs.getA().setCoupUltimate(new WallUlt());
                            break;
                        default:
                            Joueurs.getA().setCoupUltimate(null);
                            break;
                    }
                    Court.ballSpeedX = 200;
                    Court.ballSpeedY = 200;
                    Joueurs.getB().setIsbot(true);
                    break;
                default:
                    break;
            }
        }
    }

    public static void majTab(){
        isSaved = true;
        if (tabParameters!= null){
            tabParameters[0]=Parametre.modeDejeu;
            tabParameters[1]=(Joueurs.getA().getCoupUltimate() != null) ? Joueurs.getA().getCoupUltimate().getUltName() : "Anull";
            if(!Parametre.modeDejeu.equals("ModeChallenge")){
                if (tabParameters.length != 10){
                    tabParameters = new String[10];
                }
                tabParameters[0]=Parametre.modeDejeu;
                tabParameters[1]=(Joueurs.getA().getCoupUltimate() != null) ? Joueurs.getA().getCoupUltimate().getUltName() : "Anull";
                tabParameters[2]=(Joueurs.getB().getCoupUltimate()!= null) ? Joueurs.getB().getCoupUltimate().getUltName() : "Bnull";
                tabParameters[3]= ("A"+Joueurs.getA().getIsBot()+Parametre.botLvlA);
                tabParameters[4]= ("B"+Joueurs.getB().getIsBot()+Parametre.botLvlB);
                tabParameters[5]= String.valueOf(Court.racketSize);
                tabParameters[6]= String.valueOf(Court.ballRadius);
                tabParameters[7]= String.valueOf(Court.ballSpeedX);
                tabParameters[8]= String.valueOf(ScoreBoard.nbPoint);
                tabParameters[9]= String.valueOf(ElementSize.collisions_centrees.isSelected());
            }
        }else if (!Parametre.modeDejeu.equals("ModeChallenge")){
            tabParameters = new String[10];
            tabParameters[0]= Parametre.modeDejeu;
            tabParameters[1]=(Joueurs.getA().getCoupUltimate() != null) ? Joueurs.getA().getCoupUltimate().getUltName() : "Anull";
            tabParameters[2]=(Joueurs.getB().getCoupUltimate()!= null) ? Joueurs.getB().getCoupUltimate().getUltName() : "Bnull";
            tabParameters[3]= String.valueOf(Joueurs.getA().getIsBot());
            tabParameters[4]= String.valueOf(Joueurs.getB().getIsBot());
            tabParameters[5]= String.valueOf(Court.racketSize);
            tabParameters[6]= String.valueOf(Court.ballRadius);
            tabParameters[7]= String.valueOf(Court.ballSpeedX);
            tabParameters[8]= String.valueOf(ScoreBoard.nbPoint);
            tabParameters[9]= String.valueOf(ElementSize.collisions_centrees.isSelected());
        }else{
            tabParameters = new String[2];
            tabParameters[0]=Parametre.modeDejeu;
            tabParameters[1]=(Joueurs.getA().getCoupUltimate() != null) ? Joueurs.getA().getCoupUltimate().getUltName() : "null";
        }
    }


    public static Button btnMajTab = new Button();

    public static void initailiseTab(){
        if (compteurRead<=0){
            readFile();
        }
    }

}
