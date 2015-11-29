package com.siddhantjain.muskular;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siddhantjain.muskular.fatsecret.FatSecretGet;
import com.siddhantjain.muskular.fatsecret.FatSecretSearch;
import com.siddhantjain.muskular.foodget.FoodServing;
import com.siddhantjain.muskular.foodget.ServingSizeAdapter;
import com.siddhantjain.muskular.foodsearch.FoodItem;
import com.siddhantjain.muskular.foodsearch.SearchRowAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodContentSearch extends AppCompatActivity {

    private FatSecretSearch mFatSecretSearch;
    private FatSecretGet mFatSecretGet;
    private String mBrand;
    private SearchRowAdapter mSearchAdapter;
    private ServingSizeAdapter mServingAdapter;
    private ArrayList<FoodItem> mItem;
    private ListView mListView;
    private ListView mServingListView;
    private ArrayList<FoodServing> mServingItem;
    private String mQuery;
    private String mFatContent;
    private String mProteinContent;
    private String mCalorieContent;
    private String mCarbsContent;
    private String mTempFoodName;
    private String mTempServingDesc;
    private String mTempServingQuant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_content_search);
        mFatSecretSearch = new FatSecretSearch();
        Intent intent = getIntent();
        listViewConfigurations();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mQuery = query;
            searchFood(query, 0);
        }
        getImplementation();
    }

    private void listViewConfigurations() {
        mFatSecretGet = new FatSecretGet();
        mItem = new ArrayList<FoodItem>();
        mServingItem = new ArrayList<FoodServing>();
        mServingAdapter = new ServingSizeAdapter(this.getBaseContext(),mServingItem);
        mSearchAdapter = new SearchRowAdapter(this.getBaseContext(), mItem);
        mListView = (ListView)findViewById(R.id.lvSearchDropDown);
        mListView.setAdapter(mSearchAdapter);

    }

    private void getImplementation() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position: " + position);
                System.out.println("FoodName: " + mItem.get(position).getFoodDescription());
                getFood(Long.valueOf(mItem.get(position).getID()));
            }
        });
    }

    private void getFood(final long id) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... arg0) {
                JSONObject foodGet = mFatSecretGet.getFood(id);
                System.out.println(foodGet);
                try {
                    if (foodGet != null) {
                        String tempFoodType = foodGet.getString("food_type");
                        if(tempFoodType.equals("Generic")){
                            mTempFoodName = foodGet.getString("food_name");
                            JSONObject servings = foodGet.getJSONObject("servings");
                            JSONArray serving = servings.getJSONArray("serving");
                            for(int i=0;i<serving.length();i++){
                                JSONObject serving_item = serving.optJSONObject(i);
                                System.out.println("Serving item: "+serving_item);
                                mCalorieContent = serving_item.getString("calories");
                                mCarbsContent = serving_item.getString("carbohydrate");
                                mProteinContent = serving_item.getString("protein");
                                mFatContent = serving_item.getString("fat");
                                mTempServingDesc = serving_item.getString("serving_description");
                                String measurementQuantity = serving_item.getString("metric_serving_amount");
                                String measurementUnit = serving_item.getString("metric_serving_unit");
                                String measurementMultiplier = serving_item.getString("number_of_units");
                                Double tempProduct = Double.valueOf(measurementMultiplier) * Double.valueOf(measurementQuantity);
                                mTempServingQuant = String.valueOf(tempProduct) + measurementUnit;
                                mServingItem.add(new FoodServing(mTempFoodName, mTempServingDesc, mTempServingQuant,String.valueOf(id) ,
                                        mCalorieContent, mProteinContent, mCarbsContent, mFatContent));
                            }
                        }
                        else {
                            mTempFoodName = foodGet.getString("food_name");
                            JSONObject servings = foodGet.getJSONObject("servings");
                            JSONObject serving = servings.getJSONObject("serving");
                            System.out.println("Serving item: "+serving);
                            mCalorieContent = serving.getString("calories");
                            mCarbsContent = serving.getString("carbohydrate");
                            mProteinContent = serving.getString("protein");
                            mFatContent = serving.getString("fat");
                            mTempServingDesc = serving.getString("serving_description");
                            double measurementQuantity = serving.getDouble("metric_serving_amount");
                            String measurementUnit = serving.getString("metric_serving_amount");
                            double measurementMultiplier = serving.getDouble("number_of_units");
                            measurementQuantity = measurementQuantity * measurementMultiplier;
                            mTempServingQuant = String.valueOf(measurementQuantity) + measurementUnit;
                            mServingItem.add(new FoodServing(mTempFoodName, mTempServingDesc, mTempServingQuant,String.valueOf(id) ,
                                    mCalorieContent, mProteinContent, mCarbsContent, mFatContent));
                        }
                    }

                } catch (JSONException exception) {
                    return "Error";
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                /*
                final Dialog servingDialog = new Dialog(FoodContentSearch.this);
                servingDialog.show();
                servingDialog.setContentView(R.layout.dialog_serving_size_picker);
                */
                AlertDialog.Builder servingDialog = new AlertDialog.Builder(FoodContentSearch.this);
                LayoutInflater inflater = FoodContentSearch.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_serving_size_picker, null);
                servingDialog.setView(dialogView);
                if (result.equals("Error")) {
                    Toast.makeText(getBaseContext(), "No Items Containing Your Search", Toast.LENGTH_SHORT).show();
                }
                else {
                    mServingListView = (ListView) dialogView.findViewById(R.id.lvServingSizeSelector);
                    if (mServingListView!=null) {
                        mServingListView.setAdapter(mServingAdapter);
                        servingDialog.show();
                        servingDialog.setTitle("Select your serving Size");
                        mServingAdapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getBaseContext(), "Couldn't find the listView", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }.execute();
    }

    private void searchFood(final String item, final int page_num) {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                //mProgressMore.setVisibility(View.VISIBLE);
                //mProgressSearch.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String... arg0) {
                JSONObject food = mFatSecretSearch.searchFood(item, page_num);
                JSONArray FOODS_ARRAY;
                try {
                    if (food != null) {
                        FOODS_ARRAY = food.getJSONArray("food");
                        if (FOODS_ARRAY != null) {
                            for (int i = 0; i < FOODS_ARRAY.length(); i++) {
                                JSONObject food_items = FOODS_ARRAY.optJSONObject(i);
                                String food_name = food_items.getString("food_name");
                                String food_description = food_items.getString("food_description");
                                String[] row = food_description.split("-");
                                String id = food_items.getString("food_type");
                                if (id.equals("Brand")) {
                                    mBrand = food_items.getString("brand_name");
                                }
                                if (id.equals("Generic")) {
                                    mBrand = "Generic";
                                }
                                String food_id = food_items.getString("food_id");
                                mItem.add(new FoodItem(food_name, row[1].substring(1),
                                        "" + mBrand, food_id));
                            }
                        }
                    }
                } catch (JSONException exception) {
                    return "Error";
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.equals("Error"))
                    System.out.println("Error in getting results");
                mSearchAdapter.notifyDataSetChanged();
               // mSearchAdapter.notifyDataSetChanged();
            //    updateList();
                //mProgressMore.setVisibility(View.INVISIBLE);
                //mProgressSearch.setVisibility(View.INVISIBLE);
                //SEARCH_RETAIN = true;
            }
        }.execute();
    }


    private void updateList() {
        if (mSearchAdapter.getCount() == 0) {
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
        }
    }

}
