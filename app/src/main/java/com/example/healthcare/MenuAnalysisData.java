package com.example.healthcare;

import java.util.List;

public class MenuAnalysisData {
    private int foodIdx;
    private String food_list;
    private String cal;
    public int getFoodIdx() {
        return foodIdx;
    }

    public void setFoodIdx(int foodIdx) {
        this.foodIdx = foodIdx;
    }

    public String getFood_list() {
        return food_list;
    }

    public void setFood_list(String food_list) {
        this.food_list = food_list;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public List<MenuAnalysisData> invisibleChildren;


}
