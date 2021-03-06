package com.siddhantjain.muskular.foodsearch;

import java.io.Serializable;

/**
 * Created by siddhaja on 11/22/2015.
 */
public class FoodItem implements Serializable {
    private String FOOD_NAME;
    private String FOOD_DESCRIPTION;
    private String FOOD_BRAND;
    private String FOOD_ID;

    public FoodItem(String food_name, String food_description, String food_brand, String food_id) {
        super();
        this.FOOD_NAME = food_name;
        this.FOOD_DESCRIPTION = food_description;
        this.FOOD_BRAND = food_brand;
        this.FOOD_ID = food_id;
    }

    public String getID() {
        return FOOD_ID;
    }

    public String getBrand() {
        return FOOD_BRAND;
    }

    public String getTitle() {
        return FOOD_NAME;
    }

    public String getFoodDescription() {
        return FOOD_DESCRIPTION;
    }
}
