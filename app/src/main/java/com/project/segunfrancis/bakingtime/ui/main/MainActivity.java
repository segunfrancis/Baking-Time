package com.project.segunfrancis.bakingtime.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.databinding.ActivityMainBinding;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.ui.details.DetailsActivity;
import com.project.segunfrancis.bakingtime.util.RecipeAdapter;

import java.util.List;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;

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
            mBinding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        });
    }

    @Override
    public void onItemClick(Recipe recipe) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class).putExtra(INTENT_KEY, recipe));
    }

    private void displaySnackBar(String message) {
        Snackbar.make(mBinding.mainConstraintLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
