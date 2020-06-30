package com.project.segunfrancis.bakingtime.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.data_source.local.IngredientDao;
import com.project.segunfrancis.bakingtime.data_source.local.IngredientExecutors;
import com.project.segunfrancis.bakingtime.data_source.local.IngredientRoomDatabase;
import com.project.segunfrancis.bakingtime.databinding.ActivityDetailsBinding;
import com.project.segunfrancis.bakingtime.model.Ingredient;
import com.project.segunfrancis.bakingtime.model.IngredientsForWidget;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.model.Step;
import com.project.segunfrancis.bakingtime.ui.SharedViewModel;
import com.project.segunfrancis.bakingtime.ui.steps.StepDetailsActivity;
import com.project.segunfrancis.bakingtime.ui.adapters.StepAdapter;
import com.project.segunfrancis.bakingtime.widget.BakingService;

import java.util.ArrayList;
import java.util.List;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;
import static com.project.segunfrancis.bakingtime.util.AppConstants.isTablet;

public class DetailsActivity extends AppCompatActivity implements StepAdapter.OnStepItemClickListener {

    private ActivityDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        IngredientRoomDatabase database = IngredientRoomDatabase.getDatabase(this);
        IngredientDao dao = database.mRecipeDao();
        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra(INTENT_KEY);
        SharedViewModel viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.setRecipeMutableLiveData(recipe);

        if (!isTablet(this)) {
            if (recipe != null) {
                setUpStepAdapter(recipe);
                mBinding.setRecipe(recipe);
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(recipe.getName());
            }
        }

        List<Ingredient> ingredients = recipe.getIngredients();
        List<String> ingredientsForWidget = new ArrayList<>();
        for (Ingredient a : ingredients) {
            ingredientsForWidget.add(a.getIngredient() + "\n" +
                    "Quantity: " + a.getQuantity() + "\n" +
                    "Measure: " + a.getMeasure() + "\n");

            Log.d("DetailsActivity", a.getIngredient() + "\n" +
                    "Quantity: " + a.getQuantity() + "\n" +
                    "Measure: " + a.getMeasure() + "\n");
        }
        IngredientExecutors.getInstance().diskIO().execute(() -> {
            IngredientsForWidget forWidget = new IngredientsForWidget();
            forWidget.setIngredients(ingredientsForWidget);
            dao.insertIngredients(forWidget);
        });
        //BakingService.actionStartBakingService(this, ingredientsForWidget);
    }

    private void setUpStepAdapter(Recipe recipe) {
        StepAdapter adapter = new StepAdapter(recipe.getSteps(), this);
        mBinding.stepsRecyclerView.setAdapter(adapter);
        mBinding.stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.stepsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onStepItemClick(Step step) {
        Intent intent = new Intent(DetailsActivity.this, StepDetailsActivity.class);
        intent.putExtra(INTENT_KEY, step);
        startActivity(intent);
    }
}
