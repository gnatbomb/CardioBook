package ca.ualberta.nbombard.nbombard_cardiobook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nicholas Bombardieri on 2019/02/02
 * @author nichbomb
 * Keeps track of:
 *      MeasurementContexts: a superclass for measurement information. Has getters and setters
 *                  the values.
 *          -dateStamp: the date of the reading
 *          -timeStamp: the time of the reading
 *          -comment: the comment attatched to a reading.
 *      Symptoms: a superclass for all the symptoms we want to measure. Has getters and setters for
 *                 the values.
 *          -systolicPressure
 *          -diastolicPressure
 *          -heartRate
 */

public class Measurement{
    private MeasurementContext dateStamp;
    private MeasurementContext timeStamp;
    private MeasurementContext comment;
    private Symptom systolicPressure;
    private Symptom diastolicPressure;
    private Symptom heartRate;
    private Boolean isAbnormal;

    /**
     * @param date, String. Date of reading.
     * @param time, String. Time of reading.
     * @param sysPress, integer. Value of systolic pressure
     * @param diaPress, integer. Value of diastolic pressure
     * @param hr, integer. Value of heartRate
     * @param c, String. Comment.
     */
    Measurement(String date, String time, int sysPress, int diaPress, int hr, String c){
        this.dateStamp = new DateStamp(date);
        this.timeStamp = new TimeStamp(time);
        this.systolicPressure = new SystolicPressure(sysPress);
        this.diastolicPressure = new DiastolicPressure(diaPress);
        this.heartRate = new HeartRate(hr);
        this.comment = new Comment(c);
        determineAbnormality();
    }

    public void determineAbnormality(){
        int sysPress;
        int diaPress;
        sysPress = this.systolicPressure.getValue();
        diaPress = this.diastolicPressure.getValue();
        if ((sysPress > 140) || (sysPress < 90) || (diaPress > 90) || (diaPress < 60)){
            this.isAbnormal = true;
        }
        else{
            this.isAbnormal = false;
        }
    }

    public void updateValues(String date, String time, int sysPress, int diaPress, int hr, String c){
        this.dateStamp.setValue(date);
        this.timeStamp.setValue(time);
        this.systolicPressure.setValue(sysPress);
        this.diastolicPressure.setValue(diaPress);
        this.heartRate.setValue(hr);
        this.comment.setValue(c);
        determineAbnormality();
    }

    public String getDate(){
        return this.dateStamp.getValue();
    }

    public String getTime(){
        return this.timeStamp.getValue();
    }

    public String getComment(){
        return this.comment.getValue();
    }

    public int getSysPress(){
        return this.systolicPressure.getValue();
    }

    public int getDiaPress(){
        return this.diastolicPressure.getValue();
    }

    public int getHeartRate(){
        return this.heartRate.getValue();
    }

    public boolean getAbnormality(){ return this.isAbnormal;}

}
