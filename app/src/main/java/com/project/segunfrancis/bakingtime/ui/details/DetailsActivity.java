package com.project.segunfrancis.bakingtime.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.databinding.ActivityDetailsBinding;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.model.Step;
import com.project.segunfrancis.bakingtime.ui.steps.StepDetailsActivity;
import com.project.segunfrancis.bakingtime.util.StepAdapter;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;

public class DetailsActivity extends AppCompatActivity implements StepAdapter.OnItemClickListener {

    private ActivityDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra(INTENT_KEY);
        setUpStepAdapter(recipe);
        mBinding.setRecipe(recipe);
        mBinding.ingredients.setOnClickListener(v -> {
            toggleArrow();
        });
    }

    private void setUpStepAdapter(Recipe recipe) {
        StepAdapter adapter = new StepAdapter(recipe.getSteps(), this);
        mBinding.stepsRecyclerView.setAdapter(adapter);
        mBinding.stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.stepsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }

    private void toggleArrow() {
        if (mBinding.detailsRecyclerView.getVisibility() == View.VISIBLE) {
            mBinding.detailsRecyclerView.setVisibility(View.GONE);
            mBinding.arrowImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp));
        } else {
            mBinding.detailsRecyclerView.setVisibility(View.VISIBLE);
            mBinding.arrowImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_upward_black_24dp));
        }
    }

    @Override
    public void onItemClick(Step step) {
        Intent intent = new Intent(DetailsActivity.this, StepDetailsActivity.class);
        intent.putExtra(INTENT_KEY, step);
        startActivity(intent);
    }
}
