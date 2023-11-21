package com.example.yummy.View.detail;

import com.example.yummy.Model.Meals;

public interface DetailView {
    void showLoading();
    void hideLoading();
    void setMeal(Meals.Meal meal);
    void onErrorLoading(String message);

}
