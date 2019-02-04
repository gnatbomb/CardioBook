package ca.ualberta.nbombard.nbombard_cardiobook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * MainActivity displays the list of measurements and gives the option to make a new measurement
 * @author Nicholas Bombardieri
 * written on 219/02/04
 *
 * Implements a RecyclerView to display the measurements. Help retreived from:
 *          https://developer.android.com/guide/topics/ui/layout/recyclerview on 2019/02/04
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";

    private Gson gson = new Gson();

    //recyclerview stuff
    private RecyclerView recyclerView;
    private MeasurementAdapter adapter;
    private List<Measurement> measurementsList;
    private RecyclerView.LayoutManager layoutManager;
    private Button newMeasure;

    /**
     * onCreate sets up the recyclerview and the button which takes you to the
     *      RecordMeasurement activity. It also loads the data from the savefile.
     * @param savedInstanceState, allows for the saving of information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recyclerView = findViewById(R.id.recyclerView);
        newMeasure = findViewById(R.id.newMeasureButton);
        newMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecordMeasurement();
            }
        });

        //measurementsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        measurementsList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadFile();
        //measurementsList.add(new Measurement("2019/02/02", "12:30",
                //90, 95, 80, "big sad"));
        adapter = new MeasurementAdapter(this, measurementsList);
        recyclerView.setAdapter(adapter);

    }


    /**
     * takes the user to the recordMeasurement activity
     */
    public void openRecordMeasurement(){
        Intent intent = new Intent(this, recordMeasurement.class);
        startActivity(intent);
        this.finish();
    }


    /**
     * loads the data from the file object. I reused some of the code from lab 2.
     */
    private void loadFile() {
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
    }

}
