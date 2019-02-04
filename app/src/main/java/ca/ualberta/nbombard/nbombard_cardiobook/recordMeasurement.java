package ca.ualberta.nbombard.nbombard_cardiobook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads in information for a new reading.
 * @author Nicholas Bombadieri
 * written on 2019/02/04
 */

public class recordMeasurement extends AppCompatActivity {

    private Button saveButton;
    private String date;
    private String time;
    private String comment;
    private String sysReading;
    private int sysValue;
    private String diaReading;
    private int diaValue;
    private String hrReading;
    private int hrValue;
    private EditText dateText;
    private EditText timeText;
    private EditText sysText;
    private EditText diaText;
    private EditText hrText;
    private EditText commentText;
    private boolean badInput;
    private List<Measurement> measurementsList;


    /**
     * Grabs the references for the textFields and button.
     * @param savedInstanceState, lets me save.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_measurement);
        saveButton = findViewById(R.id.saveMeasurementButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSubmit();
            }
        });
        dateText = findViewById(R.id.dateField);
        timeText = findViewById(R.id.timeField);
        sysText = findViewById(R.id.sysPresField);
        diaText = findViewById(R.id.diaPresField);
        hrText = findViewById(R.id.hrField);
        commentText = findViewById(R.id.commentField);
    }

    /**
     * Checks the validity of inputs. Tells the user what to fix if they have invalid inputs.
     */

    public void checkAndSubmit(){
        badInput = false;
        date = dateText.getText().toString().trim();
        time = timeText.getText().toString().trim();
        sysReading = sysText.getText().toString().trim();
        if (sysReading.length() < 1){
            badInput = true;
            toastIt("Please enter a systolic pressure.");
        }
        else {
            sysValue = Integer.parseInt(sysReading);
        }
        if (! badInput) {
            diaReading = diaText.getText().toString().trim();
            if (diaReading.length() < 1) {
                badInput = true;
                toastIt("Please enter a diastolic pressure.");
            } else {
                diaValue = Integer.parseInt(diaReading);
            }
        }
        hrReading = hrText.getText().toString().trim();
        if (! badInput) {
            if (hrReading.length() < 1) {
                badInput = true;
                toastIt("Please enter a heart rate.");
            } else {
                hrValue = Integer.parseInt(hrReading);
            }
        }
        comment = commentText.getText().toString().trim();
        if (! badInput) {
            if (date.length() < 1) {
                toastIt("Please enter a reading date.");
            } else if (!date.matches("\\d{4}/\\d{2}/\\d{2}")) {
                toastIt("Please make sure your date follows the format yyyy/mm/dd");
            } else if (time.length() < 1) {
                toastIt("Please enter a reading time.");
            } else if (!time.matches("\\d{2}:\\d{2}")) {
                toastIt("Please make sure your time follows the format hh:mm");
            } else if (sysValue < 0) {
                toastIt("Please enter a valid systolic pressure.");
            } else if (diaValue < 0) {
                toastIt("Please enter a valid diastolic pressure0.");
            } else if (hrValue < 0) {
                toastIt("Please enter a valid heart-rate.");
            } else if (comment.length() > 20) {
                toastIt("Please enter a comment shorter than 21 characters.");
            } else {
                saveAndSubmit();
            }
        }
    }

    /**
     * helper function. Helps display the invalid input that the user needs to fix.
     * @param toastString, information about what to fix.
     */
    public void toastIt(String toastString){
        Toast.makeText(getApplicationContext(),
                toastString,
                Toast.LENGTH_SHORT).show();
    }


    /**
     * Saves the new measurement to the file.
     * @param measurement, the new measurement to be saved to the savefile.
     */
    public void saveToFile(Measurement measurement){
         String FILENAME = "file.sav";
         Gson gson = new Gson();
         try {
             FileInputStream fis = openFileInput(FILENAME);
             BufferedReader in = new BufferedReader(new InputStreamReader(fis));

             Type listType = new TypeToken<ArrayList<Measurement>>() {}.getType();
             measurementsList = gson.fromJson(in, listType);
             } catch (FileNotFoundException e) {
             measurementsList = new ArrayList<Measurement>();
             } catch (Exception e) {
             throw new RuntimeException();
         }
         File dir = getFilesDir();
         File file = new File(dir, FILENAME);
         deleteFile(FILENAME);
         File newFile = new File(dir, FILENAME);
         measurementsList.add(measurement);
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            gson.toJson(measurementsList, writer);
            writer.flush();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Creates the measurement object, calls the save function, and returns to the main activity.
     */

    public void saveAndSubmit(){
        Measurement measurement = new Measurement(date, time, sysValue, diaValue, hrValue,
                comment);
        Intent intent = new Intent(this, MainActivity.class);
        saveToFile(measurement);
        startActivity(intent);
        this.finish();
    }
}
