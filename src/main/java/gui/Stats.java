package gui;

import java.util.ArrayList;

//Cette classe est là pour pouvoir afficher les statistiques dans le tableView de java fx

@SuppressWarnings("DuplicateCondition")
public class Stats {
    public String name;
    public String stat;

    public static ArrayList<Stats> previousStats = new ArrayList<Stats>();


    //Méthode qui va set la liste à partir du contenu du fichier
    public static void setPreviousStats() {
        previousStats= new ArrayList<>();
        String s = LeaderBoardTab.lb.getLeaderBoardPrevious();
        int t=s.length();
        String p="";
        String n="";
        boolean flag=false;
        for (int i = 1; i < t-1; i++) {
            if (i==t-2){
                p= String.valueOf(s.charAt(i));
                previousStats.add(new Stats(n , p));
            }else if (s.charAt(i)=='|'){
                if (s.charAt(i+1)=='|'){
                    n="";
                    p="";
                    flag=false;
                }
            }else if (s.charAt(i)=='/'){
                flag = true;
            }else if (!flag){
                n=n+s.charAt(i);
            }else if (flag){
                p= String.valueOf(s.charAt(i));
                if (i==t-2){
                    break;
                }
                if (s.charAt(i+1)=='|'){
                    previousStats.add(new Stats(n , p));
                }else{
                    p= p+ s.charAt(i+1);
                    previousStats.add(new Stats(n , p));
                    i+=1;
                }
            }
        }
    }


    //Les getters et setter qui sont nécessaires pour la tableview
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }


    //Constructeur
    public Stats(String name, String point) {
        this.name = name;
        this.stat = point;
    }
}
