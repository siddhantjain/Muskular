package com.siddhantjain.muskular.foodget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.siddhantjain.muskular.R;
import java.util.ArrayList;

/**
 * Created by siddhaja on 11/26/2015.
 */
public class ServingSizeAdapter extends ArrayAdapter<FoodServing> {
    private final Context mContext;
    private final ArrayList<FoodServing> foodServingItem;

    public ServingSizeAdapter(Context context, ArrayList<FoodServing> itemsArrayList) {
        super(context, R.layout.serving_size_row, itemsArrayList);
        this.mContext = context;
        this.foodServingItem = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.serving_size_row, parent, false);
        TextView mFoodName = (TextView) v.findViewById(R.id.tvFoodName);
        TextView mServingDesc = (TextView) v.findViewById(R.id.tvServingDesc);
        TextView mServingQuant = (TextView) v.findViewById(R.id.tvServingSize);
        mFoodName.setText(foodServingItem.get(position).getFoodName());
        mServingDesc.setText(foodServingItem.get(position).getServingDescription());
        mServingQuant.setText(foodServingItem.get(position).getServingQuantity());
        return v;
    }
}
