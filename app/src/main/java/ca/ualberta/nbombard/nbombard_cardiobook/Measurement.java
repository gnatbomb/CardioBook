package ca.ualberta.nbombard.nbombard_cardiobook;

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

public class Measurement {
    private MeasurementContext dateStamp;
    private MeasurementContext timeStamp;
    private MeasurementContext comment;
    private Symptom systolicPressure;
    private Symptom diastolicPressure;
    private Symptom heartRate;

    /**
     *
     * @param sysPress, integer. Value of systolic pressure
     * @param diaPress, integer. Value of diastolic pressure
     * @param hr, integer. Value of heartRate
     * uses SimpleDateFormat. I found help on how to implement it here:
     *      https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
     */
    Measurement(int sysPress, int diaPress, int hr, String c){
        this.dateStamp = new DateStamp(new SimpleDateFormat("yyyy/MM/dd")
                .format(Calendar.getInstance().getTime()));
        this.timeStamp = new TimeStamp(new SimpleDateFormat("HH:mm")
                .format(Calendar.getInstance().getTime()));
        this.systolicPressure = new SystolicPressure(sysPress);
        this.diastolicPressure = new DiastolicPressure(diaPress);
        this.heartRate = new HeartRate(hr);
        this.comment = new Comment(c);
    }
}
