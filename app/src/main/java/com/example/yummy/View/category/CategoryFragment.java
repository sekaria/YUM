package com.example.yummy.View.category;

import static com.example.yummy.View.home.HomeActivity.EXTRA_DETAIL;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.Categories;
import com.example.yummy.Model.Meals;
import com.example.yummy.R;
import com.example.yummy.Utils.Utils;
import com.example.yummy.View.adapter.RecyclerViewMealByCategory;
import com.example.yummy.View.detail.DetailActivity;

import java.util.List;

public class CategoryFragment extends Fragment implements CategoryView {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public static final String ARG_CATEGORY = "ARG_CATEGORY";

    public static CategoryFragment newInstance(Categories.Category category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        NestedScrollView nestedScrollView = view.findViewById(R.id.nestedScrollView);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) nestedScrollView.getLayoutParams();


        if (getArguments() != null) {
            Categories.Category category = (Categories.Category) getArguments().getSerializable(ARG_CATEGORY);
            if (category != null) {
                CategoryPresenter presenter = new CategoryPresenter(this);
                presenter.getMealByCategory(category.getStrCategory());
            }
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMeals(List<Meals.Meal> meals) {
        if (isAdded()) {
            RecyclerViewMealByCategory adapter = new RecyclerViewMealByCategory(requireContext(), meals);
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new RecyclerViewMealByCategory.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    TextView mealName = view.findViewById(R.id.mealName);
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(requireContext(), "Error", message);
    }
}