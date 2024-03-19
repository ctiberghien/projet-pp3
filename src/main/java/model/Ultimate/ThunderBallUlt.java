package model.Ultimate;

public class ThunderBallUlt extends Ultimate{

    static double cpt;

    @Override
    public String getUltName() {
        return "ThunderBall";
    }

    public static boolean verif() {
        cpt+=0.10;
        if(cpt>=4) {
            cpt = -cpt;
            return true;
        }
        return false;
    }
}
