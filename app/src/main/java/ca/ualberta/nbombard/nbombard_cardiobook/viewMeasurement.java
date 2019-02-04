package ca.ualberta.nbombard.nbombard_cardiobook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class viewMeasurement extends AppCompatActivity {
    EditText dateField;
    EditText timeField;
    EditText sysPresField;
    EditText diaPresField;
    EditText hrField;
    EditText commentField;
    ArrayList<Measurement> measurementsList;
    Measurement measurement;
    String FILENAME = "file.sav";
    int measurementIndex;

    boolean badInput;
    String date;
    String time;
    String sysReading;
    int sysValue;
    String diaReading;
    int diaValue;
    String hrReading;
    int hrValue;
    String comment;
    Button saveButton;
    Button deleteButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_measurement);
        saveButton = findViewById(R.id.saveMeasurementButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSubmit();
            }
        });
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMeasurement();
            }
        });
        dateField = findViewById(R.id.dateField);
        timeField = findViewById(R.id.timeField);
        sysPresField = findViewById(R.id.sysPresField);
        diaPresField = findViewById(R.id.diaPresField);
        hrField = findViewById(R.id.hrField);
        commentField = findViewById(R.id.commentField);






        getIncomingIntent();

    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("measurementId")){
            measurementIndex = getIntent().getIntExtra("measurementId", 0);
            loadValue(measurementIndex);
        }
    }

    public void loadValue(int i){

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

        measurement = measurementsList.get(i);
        dateField.setText(measurement.getDate());
        timeField.setText(measurement.getTime());
        sysPresField.setText(String.valueOf(measurement.getSysPress()));
        diaPresField.setText(String.valueOf(measurement.getDiaPress()));
        hrField.setText(String.valueOf(measurement.getHeartRate()));
        commentField.setText(measurement.getComment());
    }

    public void deleteMeasurement(){
        measurementsList.remove(measurementIndex);
        Gson gson = new Gson();
        File dir = getFilesDir();
        File file = new File(dir, FILENAME);
        deleteFile(FILENAME);
        File newFile = new File(dir, FILENAME);
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            gson.toJson(measurementsList, writer);
            writer.flush();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        this.finish();
    }


    public void checkAndSubmit(){
        badInput = false;
        date = dateField.getText().toString().trim();
        time = timeField.getText().toString().trim();
        sysReading = sysPresField.getText().toString().trim();
        if (sysReading.length() < 1){
            badInput = true;
            toastIt("Please enter a systolic pressure.");
        }
        else {
            sysValue = Integer.parseInt(sysReading);
        }
        if (! badInput) {
            diaReading = diaPresField.getText().toString().trim();
            if (diaReading.length() < 1) {
                badInput = true;
                toastIt("Please enter a diastolic pressure.");
            } else {
                diaValue = Integer.parseInt(diaReading);
            }
        }
        hrReading = hrField.getText().toString().trim();
        if (! badInput) {
            if (hrReading.length() < 1) {
                badInput = true;
                toastIt("Please enter a heart rate.");
            } else {
                hrValue = Integer.parseInt(hrReading);
            }
        }
        comment = commentField.getText().toString().trim();
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
                measurement.updateValues(date, time, sysValue, diaValue, hrValue, comment);
                saveAndSubmit();
            }
        }
    }


    public void toastIt(String toastString){
        Toast.makeText(getApplicationContext(),
                toastString,
                Toast.LENGTH_SHORT).show();
    }

    public void saveToFile(){
        measurementsList.remove(measurementIndex);
        measurementsList.add(measurement);
        Gson gson = new Gson();
        File dir = getFilesDir();
        File file = new File(dir, FILENAME);
        deleteFile(FILENAME);
        File newFile = new File(dir, FILENAME);
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



    public void saveAndSubmit(){
        Intent intent = new Intent(this, MainActivity.class);
        saveToFile();
        startActivity(intent);
        this.finish();
    }
}
