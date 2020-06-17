package com.project.segunfrancis.bakingtime.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.databinding.ActivityDetailsBinding;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.model.Step;
import com.project.segunfrancis.bakingtime.ui.SharedViewModel;
import com.project.segunfrancis.bakingtime.ui.steps.StepDetailsActivity;
import com.project.segunfrancis.bakingtime.ui.adapters.StepAdapter;
import com.project.segunfrancis.bakingtime.widget.BakingService;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;
import static com.project.segunfrancis.bakingtime.util.AppConstants.isTablet;

public class DetailsActivity extends AppCompatActivity implements StepAdapter.OnStepItemClickListener {

    private ActivityDetailsBinding mBinding;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent intent = getIntent();
        mRecipe = (Recipe) intent.getSerializableExtra(INTENT_KEY);
        SharedViewModel viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.setRecipeMutableLiveData(mRecipe);

        if (!isTablet(this)) {
            if (mRecipe != null) {
                setUpStepAdapter(mRecipe);
                mBinding.setRecipe(mRecipe);
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(mRecipe.getName());
            }
            mBinding.ingredients.setOnClickListener(v -> toggleArrow(mBinding.detailsRecyclerView, mBinding.ingredients));
            mBinding.steps.setOnClickListener(v -> toggleArrow(mBinding.stepsRecyclerView, mBinding.steps));
        }
    }

    private void setUpStepAdapter(Recipe recipe) {
        StepAdapter adapter = new StepAdapter(recipe.getSteps(), this);
        mBinding.stepsRecyclerView.setAdapter(adapter);
        mBinding.stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.stepsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void toggleArrow(RecyclerView recyclerView, MaterialButton button) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            button.setIcon(getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp));
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            button.setIcon(getResources().getDrawable(R.drawable.ic_arrow_upward_black_24dp));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_update_widget) {
            BakingService.actionUpdateWidget(this, mRecipe, mRecipe.getId());
            Snackbar.make(findViewById(R.id.details_constraintLayout), "Widget successfully updated", Snackbar.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepItemClick(Step step) {
        Intent intent = new Intent(DetailsActivity.this, StepDetailsActivity.class);
        intent.putExtra(INTENT_KEY, step);
        startActivity(intent);
    }
}
