package com.example.yummy.View.home;

import com.example.yummy.Model.Categories;
import com.example.yummy.Model.Meals;

import java.util.List;

public interface HomeView {
    void showLoading();
    void hideLoading();
    void setCategory(List<Categories.Category> category);
    void onErrorLoading(String message);
    void setSearchResults(List<Categories.Category> categories);
    void showNoResults();
}