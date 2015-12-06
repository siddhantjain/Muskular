package com.siddhantjain.muskular.foodget;

import java.io.Serializable;

/**
 * Created by siddhaja on 11/26/2015.
 */
public class FoodServing {

    private String FOOD_NAME;
    private String FOOD_SERVING_DESCRIPTION;
    private String FOOD_SERVING_QUANTITY;
    private String FOOD_ID;
    private int calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public FoodServing(String food_name, String food_serving_description, String food_serving_quantity, String food_id,
                       int calories, double proteins, double carbohydrates, double fats) {
        super();
        this.FOOD_NAME = food_name;
        this.FOOD_SERVING_DESCRIPTION = food_serving_description;
        this.FOOD_SERVING_QUANTITY = food_serving_quantity;
        this.FOOD_ID = food_id;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fats = fats;
        this.calories=calories;
    }

    public String getID() {
        return FOOD_ID;
    }

    public String getServingDescription() {
        return FOOD_SERVING_DESCRIPTION;
    }

    public String getFoodName() {
        return FOOD_NAME;
    }

    public String getServingQuantity() {
        return FOOD_SERVING_QUANTITY;
    }
}

