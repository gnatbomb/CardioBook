package ca.ualberta.nbombard.nbombard_cardiobook;

/**
 * Superclass for all of the symptoms, including systolic pressure, diastolic pressure, and heart
 *          rate.
 * Written by Nicholas Bombardieri on 2019/02/02
 *
 *
 */

public class Symptom {
    private int value;

    Symptom(int v){
        this.value = v;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int v){
        this.value = v;
    }
}
