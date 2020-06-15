package com.project.segunfrancis.bakingtime.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.databinding.ActivityMainBinding;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.ui.details.DetailsActivity;
import com.project.segunfrancis.bakingtime.util.MarginItemDecoration;
import com.project.segunfrancis.bakingtime.util.MarginItemDecorationTablet;
import com.project.segunfrancis.bakingtime.ui.adapters.RecipeAdapter;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;
import static com.project.segunfrancis.bakingtime.util.AppConstants.isConnectionAvailable;
import static com.project.segunfrancis.bakingtime.util.AppConstants.isTablet;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mBinding.setLifecycleOwner(this);

        if (isConnectionAvailable()) {
            viewModel.message.observe(this, this::displaySnackBar);
            viewModel.recipeList.observe(this, recipes -> {
                RecipeAdapter adapter = new RecipeAdapter(recipes, MainActivity.this);
                mBinding.recipeRecyclerView.setAdapter(adapter);
            });
            viewModel.state.observe(this, state -> {
                switch (state) {
                    case LOADING:
                        mBinding.mainProgressBar.setVisibility(View.VISIBLE);
                    case SUCCESS:
                        mBinding.mainProgressBar.setVisibility(View.GONE);
                    case ERROR:
                        mBinding.mainProgressBar.setVisibility(View.GONE);
                }
            });
        } else {
            mBinding.mainProgressBar.setVisibility(View.GONE);
            displaySnackBar(getResources().getString(R.string.no_network));
        }

        if (isTablet(MainActivity.this)) {
            mBinding.recipeRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
            mBinding.recipeRecyclerView.addItemDecoration(new MarginItemDecorationTablet(16));
        } else {
            mBinding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mBinding.recipeRecyclerView.addItemDecoration(new MarginItemDecoration(16));
        }
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(INTENT_KEY, recipe);
        startActivity(intent);
    }

    private void displaySnackBar(String message) {
        Snackbar.make(mBinding.mainConstraintLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
