package com.example.yummy.View.home;

import androidx.annotation.NonNull;

import com.example.yummy.Model.Categories;
import com.example.yummy.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    private HomeView view;

    public HomePresenter(HomeView view) {
        this.view = view;
    }

    void getCategories() {

        view.showLoading();

        Call<Categories> categoriesCall = Utils.getApi().getCategories();
        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call,
                                   @NonNull Response<Categories> response) {

                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {

                    view.setCategory(response.body().getCategories());

                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categories> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    void searchCategories(String query) {
        view.showLoading();
        Call<Categories> categoriesCall = Utils.getApi().searchCategories(query);
        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call, @NonNull Response<Categories> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    List<Categories.Category> allCategories = response.body().getCategories();
                    List<Categories.Category> filteredCategories = new ArrayList<>();

                    for (Categories.Category category : allCategories) {
                        if (category.getStrCategory().toLowerCase().contains(query.toLowerCase())) {
                            filteredCategories.add(category);
                        }
                    }

                    if (filteredCategories.isEmpty()) {
                        view.showNoResults();
                    } else {
                        view.setSearchResults(filteredCategories);
                    }
                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categories> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}