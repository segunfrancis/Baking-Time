package com.project.segunfrancis.bakingtime.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.databinding.FragmentDetailsBinding;
import com.project.segunfrancis.bakingtime.model.Step;
import com.project.segunfrancis.bakingtime.ui.SharedViewModel;
import com.project.segunfrancis.bakingtime.ui.adapters.IngredientsAdapter;
import com.project.segunfrancis.bakingtime.ui.adapters.StepAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SegunFrancis
 */
public class DetailsFragment extends Fragment implements StepAdapter.OnStepItemClickListener {

    private SharedViewModel mViewModel;
    private FragmentDetailsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false);
        mBinding.ingredients.setOnClickListener(v -> toggleArrow(mBinding.detailsRecyclerView, mBinding.ingredients));
        mBinding.steps.setOnClickListener(v -> toggleArrow(mBinding.stepsRecyclerView, mBinding.steps));
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView ingredientRecyclerView = view.findViewById(R.id.details_recyclerView);
        RecyclerView stepRecyclerView = view.findViewById(R.id.steps_recyclerView);
        mViewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), recipe -> {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipe.getName());

            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(recipe.getIngredients());
            ingredientRecyclerView.setAdapter(ingredientsAdapter);
            ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            ingredientRecyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

            StepAdapter stepAdapter = new StepAdapter(recipe.getSteps(), this);
            stepRecyclerView.setAdapter(stepAdapter);
            stepRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            stepRecyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        });
    }

    @Override
    public void onStepItemClick(Step step) {
        mViewModel.setStepMutableLiveData(step);
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
}
