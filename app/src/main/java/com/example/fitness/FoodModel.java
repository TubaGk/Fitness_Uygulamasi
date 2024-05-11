package com.example.fitness;

public class FoodModel {
    String foodId;
    String foodName;
    String foodCalori;

    public FoodModel() {
    }

    public FoodModel(String foodName, String foodCalori,String foodId) {
        this.foodName = foodName;
        this.foodCalori = foodCalori;
        this.foodId=foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCalori() {
        return foodCalori;
    }

    public void setFoodCalori(String foodCalori) {
        this.foodCalori = foodCalori;
    }
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

}
