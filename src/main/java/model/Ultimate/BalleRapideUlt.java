package model.Ultimate;

public class BalleRapideUlt extends Ultimate {

    public static double launchUlt(double balleSpeed) {
        return balleSpeed * 2;
    }

    public static double removeUlt(double balleSpeed) {
        return balleSpeed / 2;
    }

    @Override
    public String getUltName() {
        return "BalleRapideUlt";
    }

    public static double nextBallX(double deltaT, double ballX, double BallSpeedX) {
        return deltaT * BallSpeedX + ballX;
    }

    public static double nextBallY(double deltaT, double ballY, double BallSpeedY) {
        return deltaT * BallSpeedY + ballY;
    }
}
