package gui;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.*;

public class Parametre {
    public static String modeDejeu = "";
    // ModeChallenge ; ModePoint ; ModeInfini
    public static int fondMapindex = 0;
    public static String[] fondMap = { "map1", "map2", "map3", "map1_2" };
    // map1 ; map2 ; map3
    public static int botLvlB=1;  
    public static int botLvlA=1;

    public static String getNextMap() {
        if (Menu.getGraphismes()==2) {
            switch (fondMapindex) {
                case 3:
                    fondMapindex = 1;
                    break;
                case 1:
                    fondMapindex = 2;
                    break;
                default:
                    fondMapindex = 3;
            }
            return fondMap[fondMapindex];
        }
        switch (fondMapindex) {
            case 0:
                fondMapindex = 1;
                break;
            case 1:
                fondMapindex = 2;
                break;
            default:
                fondMapindex = 0;
        }
        return fondMap[fondMapindex];
        
    }

    public static String getPreviousMap() {
        if (Menu.getGraphismes()==2) {
            switch (fondMapindex) {
                case 3:
                    fondMapindex = 2;
                    break;
                case 1:
                    fondMapindex = 3;
                    break;
                default:
                    fondMapindex = 1;
            }
            return fondMap[fondMapindex];
        }
        switch (fondMapindex) {
            case 0:
                fondMapindex = 2;
                break;
            case 1:
                fondMapindex = 0;
                break;
            default:
                fondMapindex = 1;
        }
        return fondMap[fondMapindex];
    }

    public static void setMapIndex(String map) {
        if (Menu.getGraphismes()==2) {
            switch (map) {
                case "map1_2":
                    fondMapindex = 3;
                    break;
                case "map2":
                    fondMapindex = 1;
                    break;
                case "map3":
                    fondMapindex = 2;
                    break;
                default:
                    fondMapindex = 0;
            }
            return;
        }
        switch (map) {
            case "map1":
                fondMapindex = 0;
                break;
            case "map2":
                fondMapindex = 1;
                break;
            case "map3":
                fondMapindex = 2;
                break;
            default:
                fondMapindex = 3;
        }
    }

    public static String getActualMap() {
        if (fondMapindex==0 && Menu.getGraphismes()==2) fondMapindex=3;
        return fondMap[fondMapindex];
    }


    private static int readCompteur=0;
    //Fonction qui va lire les parametre une seule fois et va mettre toutes les valeurs dans les champs/objet associer
    public static void readficParameters(){
        if (readCompteur==0){
            String[] tab = new String[28];
            try {
                File file = new File("config/.Parametre.txt");
                BufferedReader obj = new BufferedReader(new FileReader(file));
                int i=0;
                String tmp = obj.readLine();
                while (tmp != null){
                    tab[i] = tmp;
                    i+=1;
                    tmp = obj.readLine();
                }
                tab[i]=tmp;
                obj.close();
            } catch (IOException e) {
                try {
//                    File myObj = new File(".//src/main/java/gui/.Parametre.txt");
////                    myObj.createNewFile();
                    FileWriter myWriter = new FileWriter("config/.Parametre.txt");
                    myWriter.write(defaultParameters);
                    hasToSave = true;
                    myWriter.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            }
            try {
                /*if (hasToSave || !verifyParameters(tab)){
                    tab = defaultParameterstTab;
                }*/
                Joueurs.getA().setUp(KeyCode.getKeyCode(tab[1].substring(1)));
                Joueurs.getA().setDown(KeyCode.getKeyCode(tab[2].substring(1)));
                Joueurs.getA().setPause(KeyCode.getKeyCode(tab[3].substring(1)));
                Joueurs.getA().setUlt(KeyCode.getKeyCode(tab[4].substring(1)));
                 Joueurs.couleurRacketA = Color.web(tab[5].substring(1));
                Joueurs.getB().setUp(KeyCode.getKeyCode(tab[8].substring(1)));
                Joueurs.getB().setDown(KeyCode.getKeyCode(tab[9].substring(1)));
                Joueurs.getB().setUlt(KeyCode.getKeyCode(tab[10].substring(1)));
                Joueurs.couleurRacketB = Color.web(tab[11].substring(1));
                Joueurs.couleurBalle = Color.web(tab[14].substring(1));
                Parametre.setMapIndex(tab[17].substring(1));
                OptionSon.nivSonMusique = Double.parseDouble(tab[20]);
                OptionSon.nivSonBruit = Double.parseDouble(tab[23]);
                Graphismes.setGraphismes(Integer.parseInt(tab[26]));
            }catch (Exception ignored){}
        }
        readCompteur+=1;
    }

    public static boolean hasToSave = false;
    public static void writeficParametres(){
        if (Skin.hasToSave || ConfigTouche.hasToSave || Graphismes.hasToSave || OptionSon.hasToSave){
            String stringContentNextFile = "JoueurA :\n" +
                    "-" + Joueurs.getA().getUp().getName() + "\n" +
                    "-" + Joueurs.getA().getDown().getName() + "\n" +
                    "-" + Joueurs.getA().getPause().getName() + "\n" +
                    "-" + Joueurs.getA().getUlt().getName() + "\n" +
                    "#"+ Joueurs.couleurRacketA +"\n" +
                    "\n" +
                    "JoueurB :\n" +
                    "-" + Joueurs.getB().getUp().getName() + "\n" +
                    "-" + Joueurs.getB().getDown().getName() + "\n" +
                    "-" + Joueurs.getB().getUlt().getName() + "\n" +
                    "#"+ Joueurs.couleurRacketB +"\n" +
                    "\n" +
                    "Balle :\n" +
                    "#"+ Joueurs.couleurBalle + "\n" +
                    "\n" +
                    "Terrain:\n" +
                    "#"+ Parametre.getActualMap()+"\n" +
                    "\n" +
                    "Son Musique:\n" +
                    OptionSon.nivSonMusique+"\n" +
                    "\n" +
                    "Son Bruitage:\n" +
                    OptionSon.nivSonBruit+"\n" +
                    "\n" +
                    "Graphismes:\n" +
                    Graphismes.getGraphismes();
            try {
                    FileWriter myWriter = new FileWriter(".//src/main/java/gui/.Parametre.txt");
                    myWriter.write(stringContentNextFile);
                    myWriter.close();
            } catch (IOException ignore) {}
        }
    }

    public static boolean verifyParameters(String[] tab){
        if (tab != null){
            int ind = 0;
            switch (ind){
                case 0:
                    if (tab[ind].equals("JoueurA :")){
                        ind+=1;
                        break;
                    }else {
                        return false;
                    }
                case 1:
                case 2:
                case 4:
                case 8:
                case 9:
                case 10:
                    try {
                        KeyCode.valueOf(tab[ind].substring(1));
                        ind+=1;
                    }catch (Exception e){
                        return false;
                    }
                    break;
                case 3:
                    if (tab[ind].matches("[A-Z]")){
                        ind+=1;
                        break;
                    }else {
                        return false;
                    }
                case 5:
                case 11:
                case 14:
                    if (tab[ind].length()== 11 || tab[ind].charAt(0) == '#'){
                        ind+=1;
                    }else {
                        return false;
                    }
                    break;
                case 6:
                case 12:
                case 15:
                case 18:
                case 21:
                case 24:
                    if (tab[ind].equals("\n")){
                        ind+=1;
                    }else {
                        return false;
                    }
                    break;
                case 7:
                    if (tab[ind].equals("JoueurB :\n")){
                        ind+=1;
                    }else {
                        return false;
                    }
                    break;
                case 13:
                    if (tab[ind].equals("Balle :\n")){
                        ind+=1;
                        break;
                    }else {
                        return false;
                    }
                case 16:
                    if (tab[ind].equals("Terrain:\n")){
                        ind+=1;
                        break;
                    }else {
                        return false;
                    }
                case 17:
                    if (tab[ind].equals("#map1\n") || tab[ind].equals("#map2\n") || tab[ind].equals("#map3\n") || tab[ind].equals("#map1_2\n")){
                        ind+=1;
                        break;
                    }else {
                        return false;
                    }
                case 19:
                    if (tab[ind].equals("Son Musique:\n")){
                        ind+=1;
                        break;
                    }else {
                        return false;
                    }
                case 23:
                case 20:
                    try {
                        double d = Double.parseDouble(tab[ind]);
                        if (d==-1 || (d>= 0.0 && d<= 1.00)){
                            ind+=1;
                        }else {
                            return false;
                        }
                        break;
                    }catch (Exception e){
                        return false;
                    }
                case 22:
                    if (tab[ind].equals("Son Bruitage:\n")){
                        ind+=1;
                        break;
                    }else {
                        return false;
                    }
                case 25:
                    if (tab[ind].equals("Graphismes:\n")){
                        ind+=1;
                        break;
                    }else {
                        return false;
                    }
                case 26:
                    if (tab[ind].equals("0") ||tab[ind].equals("1") ||tab[ind].equals("2")){
                        ind+=1;
                        break;
                    }else {
                        return false;
                    }
                default:
                    return true;
            }
            return true;
        }else {
            return false;
        }
    }

    private static final String defaultParameters = "JoueurA :\n" +
                                                    "-Z\n" +
                                                    "-S\n" +
                                                    "-P\n" +
                                                    "-W\n" +
                                                    "#0x000000ff\n" +
                                                    "\n" +
                                                    "JoueurB :\n" +
                                                    "-Up\n" +
                                                    "-Down\n" +
                                                    "-M\n" +
                                                    "#0x000000ff\n" +
                                                    "\n" +
                                                    "Balle :\n" +
                                                    "#0x000000ff\n" +
                                                    "\n" +
                                                    "Terrain:\n" +
                                                    "#map3\n" +
                                                    "\n" +
                                                    "Son Musique:\n" +
                                                    "1.0\n" +
                                                    "\n" +
                                                    "Son Bruitage:\n" +
                                                    "1.0\n" +
                                                    "\n" +
                                                    "Graphismes:\n" +
                                                    "0";

    private static String[] defaultParameterstTab = new String[]{"JoueurA :\n","-Z\n","-S\n","-P\n","-W\n","#0x000000ff\n",
            "\n","JoueurB :\n","-Up\n","-Down\n","-M\n","#0x000000ff\n","\n","Balle :\n","#0x000000ff\n","\n","Terrain:\n",
            "#map3\n","\n","Son Musique:\n","1.0\n","\n","Son Bruitage:\n","1.0\n","\n","Graphismes:\n","0"};


}