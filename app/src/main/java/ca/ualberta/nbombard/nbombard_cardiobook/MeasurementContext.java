package ca.ualberta.nbombard.nbombard_cardiobook;

/**
 * Superclass for date, time, and comment.
 * Provides getters and setters for these objects.
 * Written by Nicholas Bombardieri on 2019/02/02
 * @author nichbomb
 *
 */

public class MeasurementContext {
    private String value;

    MeasurementContext(String v){
        this.value = v;
    }

    MeasurementContext(){
        this.value = "";
    }

    public String getValue(){
        return this.value;
    }

    public void setValue(String v){
        this.value = v;
    }
}
