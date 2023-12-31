package com.example.yummy.View.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.Categories;
import com.example.yummy.R;
import com.example.yummy.Utils.Utils;
import com.example.yummy.View.adapter.RecyclerViewHomeAdapter;
import com.example.yummy.View.category.CategoryActivity;

import java.io.Serializable;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements HomeView {

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DETAIL = "detail";

    private RecyclerView recyclerViewCategory;
    private ProgressBar progressBar;

    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerViewCategory = findViewById(R.id.recycler_category);
        progressBar = findViewById(R.id.progressBar);

        presenter = new HomePresenter(this);
        presenter.getCategories();

        SearchView searchView = findViewById(R.id.editTextSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchCategories(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    presenter.getCategories();
                }
                return false;
            }
        });
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
    public void setCategory(final List<Categories.Category> category) {
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();

        homeAdapter.setOnItemClickListener(new RecyclerViewHomeAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
                intent.putExtra(EXTRA_POSITION, position);
                HomeActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(this, "Title", message);
    }

    @Override
    public void showNoResults() {
        TextView noResultsTextView = findViewById(R.id.noResultsTextView);
        noResultsTextView.setVisibility(View.VISIBLE);
        recyclerViewCategory.setVisibility(View.GONE);
    }

    @Override
    public void setSearchResults(List<Categories.Category> searchResults) {
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(searchResults, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();

        homeAdapter.setOnItemClickListener(new RecyclerViewHomeAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra(EXTRA_CATEGORY, (Serializable) searchResults);
                intent.putExtra(EXTRA_POSITION, position);
                HomeActivity.this.startActivity(intent);
            }
        });
    }
}