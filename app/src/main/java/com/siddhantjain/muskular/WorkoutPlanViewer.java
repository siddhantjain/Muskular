package com.siddhantjain.muskular;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WorkoutPlanViewer extends AppCompatActivity implements ExerciseInfoArrayAdapter.AdapterCallback{
    private ListView lvExercise;
    private int dayOfThePlan;
    private ExerciseInfoArrayAdapter mStepsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plan_viewer);
        lvExercise = (ListView) findViewById(R.id.lvTempList);
        List<ExerciseInfo> ExerciseInfoList = new ArrayList<ExerciseInfo>();


        List<List<String>> exercises_list = new ArrayList<List<String>>();
        exercises_list = readCsv(getApplicationContext());
        dayOfThePlan = getDayOfThePlan();
        for(int i=0;i< (exercises_list.get(3)).size();i++){
            if( (Integer.parseInt(exercises_list.get(3).get(i)))== dayOfThePlan){
                ExerciseInfoList.add(new ExerciseInfo(exercises_list.get(0).get(i),Integer.valueOf(exercises_list.get(1).get(i))));
            }
        }
        this.mStepsAdapter = new ExerciseInfoArrayAdapter(this, R.id.lvTempList, ExerciseInfoList);
        lvExercise.setAdapter(mStepsAdapter);

        lvExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onMethodCallback(String exerciseName){
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(WorkoutPlanViewer.this).create(); //Read Update
        alertDialog.setTitle("Exercise Steps");
        //TODO: SID to figure out how to find the exercise name from the item clicked
        String exerciseSteps = "Exercise Steps here";
        alertDialog.setMessage(exerciseName + " steps to be populated here");
        alertDialog.show();  //<-- See This

    }
    // TODO: 10/23/2015 AKI TO ADD SERVER CALL
    private int getDayOfThePlan(){
        //insert code to get the required date
        int day = 2;
        return day;
    }

    //// TODO: 10/23/2015 AKI TO ADD SERVER CALL
    private String getExerciseSteps(String ExerciseName){
        //insert code to get the required steps
        String temporaryReturnVar = "Exercise Steps to be read from DB";
        return temporaryReturnVar;
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
