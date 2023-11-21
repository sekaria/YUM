package com.example.yummy.View.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.Search;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Search.Meal> meals;
    private List<Search.Meal> originalMeals; // Add this line for backup

    // Constructor and other methods

    public SearchAdapter(List<Search.Meal> meals) {
        this.meals = meals;
        this.originalMeals = new ArrayList<>(meals); // Initialize originalMeals
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Add your ViewHolder components here

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize your ViewHolder components here
        }
    }

    // Other adapter methods...

    // Add this method for search functionality
    public void filter(String text) {
        meals.clear();
        if (text.isEmpty()) {
            meals.addAll(originalMeals);
        } else {
            text = text.toLowerCase();
            for (Search.Meal meal : originalMeals) {
                if (meal.getStrMeal().toLowerCase().contains(text)) {
                    meals.add(meal);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

