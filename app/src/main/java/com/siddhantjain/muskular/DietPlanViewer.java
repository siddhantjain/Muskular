package com.siddhantjain.muskular;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.siddhantjain.muskular.fatsecret.FatSecretGet;
import com.siddhantjain.muskular.fatsecret.FatSecretSearch;
import com.siddhantjain.muskular.foodsearch.FoodItem;
import com.siddhantjain.muskular.foodsearch.SearchRowAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DietPlanViewer extends AppCompatActivity {
    private SearchView searchView;
    private FatSecretSearch mFatSecretSearch;
    private FatSecretGet mFatSecretGet;
    private String mBrand;
    private SearchRowAdapter mSearchAdapter;
    private ArrayList<FoodItem> mItem;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan_viewer);
        searchView = (SearchView)findViewById(R.id.svFoodContentSearch);
        SearchManager searchManager = (SearchManager) getSystemService(FoodContentSearch.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.svFoodContentSearch);
        searchView.setSubmitButtonEnabled(true);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void configureListView(){
        mListView = (ListView) findViewById(R.id.lvSearchDropDown);
        Intent tIntent = new Intent(DietPlanViewer.this,FoodContentSearch.class);
        Bundle bundle = new Bundle();
        bundle = tIntent.getExtras();
        mItem = (ArrayList<FoodItem>)bundle.getSerializable("foodItemList");
        if(mItem!=null) {
            mSearchAdapter = new SearchRowAdapter(this, mItem);
            mListView.setAdapter(mSearchAdapter);
        }
        else{
            System.out.println("Didn't find anything for this food item");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_plan_viewer, menu);
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


    private void updateList() {
        if (mSearchAdapter.getCount() == 0) {
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
        }
    }
}
