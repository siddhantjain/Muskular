package com.siddhantjain.muskular;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
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
import com.siddhantjain.muskular.utils.DataStore;

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
    private ArrayList<String> mServingDesc;
    private String mQuery;
    private double mFatContent;
    private double mProteinContent;
    private int mCalorieContent;
    private double mCarbsContent;
    private String mTempFoodName;
    private String mTempServingDesc;
    private String mTempServingQuant;
    private double mMetricServingAmount;
    private String mMetricServingAmountUnit;
    private int servingDescVal=0;
    private int servingQuantVal=1;

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
        mServingDesc = new ArrayList<String>();
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
                getFood(Long.valueOf(mItem.get(position).getID()));
            }
        });
    }

    private void servingGetter(ListView servingListView){
        servingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getFood(final long id) {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                 mServingDesc.clear();
                 mServingItem.clear();
                 //mServingAdapter.notifyDataSetChanged();
            }
            @Override
            protected String doInBackground(String... arg0) {
                JSONObject foodGet = mFatSecretGet.getFood(id);
                //System.out.println(foodGet);
                try {
                    if (foodGet != null) {
                        String tempFoodType = foodGet.getString("food_type");
                        if(tempFoodType.equals("Generic")){
                            mTempFoodName = foodGet.getString("food_name");
                            JSONObject servings = foodGet.getJSONObject("servings");
                            JSONArray serving = servings.getJSONArray("serving");
                            JSONObject first_serving_item = serving.getJSONObject(0);
                            mCalorieContent = first_serving_item.getInt("calories");
                            mCarbsContent = first_serving_item.getDouble("carbohydrate");
                            mProteinContent = first_serving_item.getDouble("protein");
                            mFatContent = first_serving_item.getDouble("fat");
                            String measurementQuantity = first_serving_item.getString("metric_serving_amount");
                            mMetricServingAmountUnit = first_serving_item.getString("metric_serving_unit");
                            String measurementMultiplier = first_serving_item.getString("number_of_units");
                            mMetricServingAmount = Double.valueOf(measurementMultiplier) * Double.valueOf(measurementQuantity);
                            mTempServingQuant = String.valueOf(mMetricServingAmount) + mMetricServingAmountUnit;
                            for(int i=0;i<serving.length();i++){
                                JSONObject serving_item = serving.optJSONObject(i);
                                System.out.println("Serving item: " + serving_item);
                                mTempServingDesc = serving_item.getString("serving_description");
                                mServingDesc.add(mTempServingDesc);
                                mServingItem.add(new FoodServing(mTempFoodName, mTempServingDesc, mTempServingQuant,String.valueOf(id) ,
                                        mCalorieContent, mProteinContent, mCarbsContent, mFatContent));
                            }
                        }
                        else {
                            mTempFoodName = foodGet.getString("food_name");
                            JSONObject servings = foodGet.getJSONObject("servings");
                            JSONObject serving = servings.getJSONObject("serving");
                            System.out.println("Serving item: "+serving);
                            mCalorieContent = serving.getInt("calories");
                            mCarbsContent = serving.getDouble("carbohydrate");
                            mProteinContent = serving.getDouble("protein");
                            mFatContent = serving.getDouble("fat");
                            mTempServingDesc = serving.getString("serving_description");
                            double measurementQuantity = serving.getDouble("metric_serving_amount");
                            mMetricServingAmountUnit = serving.getString("metric_serving_amount");
                            double measurementMultiplier = serving.getDouble("number_of_units");
                            mMetricServingAmount = measurementQuantity * measurementMultiplier;
                            mTempServingQuant = String.valueOf(mMetricServingAmount) + mMetricServingAmountUnit;
                            mServingItem.add(new FoodServing(mTempFoodName, mTempServingDesc, mTempServingQuant,String.valueOf(id) ,
                                    mCalorieContent, mProteinContent, mCarbsContent, mFatContent));
                            mServingDesc.add(mTempServingDesc);
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
                AlertDialog.Builder servingDialog = new AlertDialog.Builder(FoodContentSearch.this);
                LayoutInflater inflater = FoodContentSearch.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_serving_size_picker, null);
                servingDialog.setView(dialogView);
                if (result.equals("Error")) {
                    Toast.makeText(getBaseContext(), "No Items Containing Your Search", Toast.LENGTH_SHORT).show();
                }
                else {
                    final NumberPicker mNpServingQuant = (NumberPicker) dialogView.findViewById(R.id.npServingQuantPicker);
                    NumberPicker mNpServingDesc = (NumberPicker) dialogView.findViewById(R.id.npServingTypePicker);
                    Button btnSubmitServing = (Button) dialogView.findViewById(R.id.btnSubmitServing);
                    //mServingListView = (ListView) dialogView.findViewById(R.id.lvServingSizeSelector);
                    if (btnSubmitServing!=null) {
                        //mServingListView.setAdapter(mServingAdapter);
                        servingDialog.show();
                        mNpServingQuant.setMaxValue(100);
                        mNpServingQuant.setMinValue(1);
                        mNpServingDesc.setMaxValue(mServingDesc.size() - 1);
                        mNpServingDesc.setMinValue(0);
                        mNpServingDesc.setDisplayedValues(mServingDesc.toArray(new String[mServingDesc.size()]));
                        mNpServingDesc.setWrapSelectorWheel(true);
                        mNpServingDesc.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                servingDescVal = newVal;
                            }
                        });
                        mNpServingQuant.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                servingQuantVal = newVal;
                            }
                        });

                        btnSubmitServing.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                int pos = 0;
                                for (; pos < mServingItem.size(); pos++) {
                                    //System.out.println("LHS: " + mServingItem.get(pos).getServingDescription());
                                    //System.out.println("Serving Desc Val: " + servingDescVal);
                                    //System.out.println(mServingDesc);
                                    //System.out.println("RHS: " + mServingDesc.get(servingDescVal));
                                    if (mServingItem.get(pos).getServingDescription().equals(mServingDesc.get(servingDescVal)))
                                        break;
                                }
                                mCarbsContent = (mServingItem.get(pos).getCarbohydrates() * (((double) servingQuantVal)));
                                mProteinContent = (mServingItem.get(pos).getProteins() * (((double) servingQuantVal)));
                                mFatContent = (mServingItem.get(pos).getFats() * (((double) servingQuantVal)));
                                mCalorieContent = (mServingItem.get(pos).getCalories() * servingQuantVal);
                                final SharedPreferences sharedPref = DataStore.getUserProfileStore(getBaseContext());
                                final SharedPreferences.Editor editor = sharedPref.edit();
                                int tempIntValue;
                                int tempDoubleValue;
                                System.out.println(mCalorieContent + " " + mCarbsContent + " " + mProteinContent + " " + mFatContent);
                                finish();
                                //System.out.println("multiplier" + servingQuantVal);
                                //System.out.println(mCalorieContent + " " + mCarbsContent + " " + mProteinContent + " " + mFatContent);
                            }
                        });
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
