package gui;

import model.RacketController;
import model.Ultimate.Ultimate;

import javafx.scene.input.KeyCode;

public class Player implements RacketController {
    public State state = State.IDLE;
    public boolean stateUlt = false;
    private final String nom;
    private KeyCode up;
    private KeyCode down;
    private KeyCode ult;
    private Ultimate coupUltimate;
    private boolean isbot;
    private KeyCode pause;

    // Methods

    @Override
    public State getState() {
        return state;
    }

    public boolean getStateUlt() {
        return this.stateUlt;
    }

    public String getNom() {
        return this.nom;
    }

    public KeyCode getUp() {
        return this.up;
    }

    public void setUp(KeyCode up) {
        this.up = up;
    }

    public void setDown(KeyCode down) {
        this.down = down;
    }

    public void setUlt(KeyCode ult) {
        this.ult = ult;
    }

    public KeyCode getDown() {
        return this.down;
    }

    public KeyCode getPause() {
        return this.pause;
    }

    public void setPause(KeyCode pause) {
        this.pause = pause;
    }

    public boolean isUpSameAsDown() {
        return this.up == this.down;
    }

    public static boolean isKeySame(KeyCode a, KeyCode b) {
        return a == b;
    }

    public KeyCode getUlt() {
        return ult;
    }

    public Ultimate getCoupUltimate() {
        return coupUltimate;
    }

    public void setCoupUltimate(Ultimate coupUltimate) {
        this.coupUltimate = coupUltimate;
    }

    public void setIsbot(boolean isbot) {
        this.isbot = isbot;
    }

    public boolean getIsBot() {
        return this.isbot;
    }


    public Player(KeyCode up, KeyCode down, KeyCode ult, KeyCode pause) {
        this.nom = "";
        this.up = up;
        this.down = down;
        this.ult = ult;
        this.pause = pause;
    }
}
