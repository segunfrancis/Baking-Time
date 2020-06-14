package com.project.segunfrancis.bakingtime.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.databinding.ActivityDetailsBinding;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.model.Step;
import com.project.segunfrancis.bakingtime.ui.steps.StepDetailsActivity;
import com.project.segunfrancis.bakingtime.util.StepAdapter;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;
import static com.project.segunfrancis.bakingtime.util.AppConstants.isTablet;

public class DetailsActivity extends AppCompatActivity implements StepAdapter.OnStepItemClickListener {

    private ActivityDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        /*if (!isTablet(this)) {
            //mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
            Intent intent = getIntent();
            Recipe recipe = (Recipe) intent.getSerializableExtra(INTENT_KEY);
            if (recipe != null) {
                setUpStepAdapter(recipe);
                mBinding.setRecipe(recipe);
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(recipe.getName());
            }
            mBinding.ingredients.setOnClickListener(v -> toggleArrow(mBinding.detailsRecyclerView, mBinding.ingredients));
            mBinding.steps.setOnClickListener(v -> toggleArrow(mBinding.stepsRecyclerView, mBinding.steps));
        } else {
            Intent intent = getIntent();
            Recipe recipe = (Recipe) intent.getSerializableExtra(INTENT_KEY);
            DetailsFragment fragment = new DetailsFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable(INTENT_KEY, recipe);
            fragment.setArguments(bundle);

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.details_fragment_container, fragment)
                    .commit();
        }*/
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
    public void onStepItemClick(Step step) {
        Intent intent = new Intent(DetailsActivity.this, StepDetailsActivity.class);
        intent.putExtra(INTENT_KEY, step);
        startActivity(intent);
    }
}
