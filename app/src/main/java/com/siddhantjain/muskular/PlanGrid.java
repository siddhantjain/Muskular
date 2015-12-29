package com.siddhantjain.muskular;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.siddhantjain.muskular.api.APICallback;
import com.siddhantjain.muskular.api.APIClient;
import com.siddhantjain.muskular.api.MuskAPI;
import com.siddhantjain.muskular.models.ProgramMetadata;
import com.siddhantjain.muskular.models.ProgramSelectResponse;
import com.siddhantjain.muskular.models.ProgramSelectResponseData;
import com.siddhantjain.muskular.utils.DataStore;

import java.util.List;

public class PlanGrid extends AppCompatActivity {
    List<ProgramMetadata> eligiblePrograms;
    ListView programList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Insert code to get plans from database by sending user preferences*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_grid);
        eligiblePrograms = DataStore.getEligiblePrograms();
        programList = (ListView) findViewById(R.id.lvProgramList);
        ProgramInfoArrayAdapter adapter = new ProgramInfoArrayAdapter(this,eligiblePrograms);
        programList.setAdapter(adapter);
        programList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                update_profile(view);
            }
        });
        Log.v("ELIGIBLE_PROGRAMS", eligiblePrograms.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan_grid, menu);
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
    public void update_profile(View view){
        ProgramMetadata chosenProgramMetadata = eligiblePrograms.get(0);
        Log.v("PROGRAMSELECT", chosenProgramMetadata.toString());
        Log.v("PROGRAMSELECT", chosenProgramMetadata.getId());
        MuskAPI APIGuy = APIClient.getAPIClient();
        APIGuy.programSelect(DataStore.getUserId(), chosenProgramMetadata.getId(), DataStore.getUwyaId(), DataStore.getUwywId(), new APICallback<ProgramSelectResponse, ProgramSelectResponseData>(getApplicationContext()) {
            @Override
            public void onSuccess(ProgramSelectResponseData data) {
                Log.v("PROGRAMSELECT", data.toString());
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

    }
}
