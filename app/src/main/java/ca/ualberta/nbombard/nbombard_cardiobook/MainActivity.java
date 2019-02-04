package ca.ualberta.nbombard.nbombard_cardiobook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mMeasurementTexts;
    private ArrayList<String> mFlags;

    private static final String FILENAME = "file.sav";
    private Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: ");
        //RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //RecyclerViewAdapter adapter = new RecyclerViewAdapter();
    }


    /**
     * loads the data from the file object. I reused some of the code here from lab 2.
     */
    private void loadFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Type listType = new TypeToken<ArrayList<Measurement>>() {}.getType();
            myDataset = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            myDataset = new ArrayList<Measurement>();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Saves the set of measurements into the file.
     */
    private void saveFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            gson.toJson(myDataset, writer);
            writer.flush();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void updateMeasurementListView(){
        loadFile();

    }
}
