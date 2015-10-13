package com.siddhantjain.muskular;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WorkoutPlanViewer extends AppCompatActivity {
    private ListView lvExercise;
    private int dayOfThePlan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plan_viewer);
        lvExercise = (ListView) findViewById(R.id.lvTempList);
        List<List<String>> exercises_list = new ArrayList<List<String>>();
        List<String> exercise_name_list = new ArrayList<String>();
        List<String> exercise_reps_list = new ArrayList<String>();
        List<String> exercise_sets_list = new ArrayList<String>();
        exercises_list = readCsv(getApplicationContext());
        dayOfThePlan = getDayOfThePlan();
        for(int i=0;i< (exercises_list.get(3)).size();i++){
            if( (Integer.parseInt(exercises_list.get(3).get(i)))== dayOfThePlan){
                exercise_name_list.add(exercises_list.get(0).get(i)
                        + "\n" + exercises_list.get(1).get(i) + "X"
                        + exercises_list.get(2).get(i) );
            }
        }
        ArrayAdapter<String> arrayAdapterExercise = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                exercise_name_list );

        lvExercise.setAdapter(arrayAdapterExercise);
    }


    private int getDayOfThePlan(){
        //insert code to get the required date
        int day = 2;
        return day;
    }
    public final List<List<String>> readCsv(Context context) {
        List<String[]> list_modified = new ArrayList<String[]>();
        List<String> exerciseList = new ArrayList<String>();
        List<String> setsList = new ArrayList<String>();
        List<String> repsList = new ArrayList<String>();
        List<String> dowList = new ArrayList<String>();
        List<List<String>> combinedList = new ArrayList<List<String>>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(getAssets().open("dummyexerciseplan.csv")));
            String[] line;
            //reading the headers
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {
                exerciseList.add(line[0]);
                setsList.add(line[1]);
                repsList.add(line[2]);
                dowList.add(line[3]);
                list_modified.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        combinedList.add(exerciseList);
        combinedList.add(setsList);
        combinedList.add(repsList);
        combinedList.add(dowList);

        return combinedList;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout_plan_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
