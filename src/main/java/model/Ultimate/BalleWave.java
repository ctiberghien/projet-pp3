package model.Ultimate;

public class BalleWave extends Ultimate{

  static double cpt;

  @Override
  public String getUltName() {
      return "BalleWave";
  }

  public static double nextBallX(double deltaT, double ballX, double BallSpeedX) {
    return deltaT * BallSpeedX + ballX;
  }

  public static double nextBallY(double deltaT, double ballY, double BallSpeedY) {
      cpt += 0.03;
      return deltaT * Math.sin(cpt) * BallSpeedY*2 + ballY;
  }
}
