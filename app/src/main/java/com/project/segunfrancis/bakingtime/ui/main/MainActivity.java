package com.project.segunfrancis.bakingtime.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.databinding.ActivityMainBinding;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.ui.details.DetailsActivity;
import com.project.segunfrancis.bakingtime.util.MarginItemDecoration;
import com.project.segunfrancis.bakingtime.util.MarginItemDecorationTablet;
import com.project.segunfrancis.bakingtime.util.RecipeAdapter;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;
import static com.project.segunfrancis.bakingtime.util.AppConstants.isTablet;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    private MainViewModel mViewModel;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);

        mViewModel.message.observe(this, this::displaySnackBar);
        mViewModel.recipeList.observe(this, recipes -> {
            RecipeAdapter adapter = new RecipeAdapter(recipes, MainActivity.this);
            mBinding.recipeRecyclerView.setAdapter(adapter);
            if (isTablet(MainActivity.this)) {
                mBinding.recipeRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                mBinding.recipeRecyclerView.addItemDecoration(new MarginItemDecorationTablet(16));
            } else {
                mBinding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mBinding.recipeRecyclerView.addItemDecoration(new MarginItemDecoration(16));
            }
        });
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
