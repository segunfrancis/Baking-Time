package com.project.segunfrancis.bakingtime.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.model.Step;
import com.project.segunfrancis.bakingtime.ui.SharedViewModel;
import com.project.segunfrancis.bakingtime.util.IngredientsAdapter;
import com.project.segunfrancis.bakingtime.util.StepAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView ingredientRecyclerView = view.findViewById(R.id.details_recyclerView);
        RecyclerView stepRecyclerView = view.findViewById(R.id.steps_recyclerView);
        mViewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), recipe -> {
            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(recipe.getIngredients());
            ingredientRecyclerView.setAdapter(ingredientsAdapter);
            ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            ingredientRecyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

            StepAdapter stepAdapter = new StepAdapter(recipe.getSteps(), this);
            stepRecyclerView.setAdapter(stepAdapter);
            stepRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            stepRecyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

            Toast.makeText(requireContext(), recipe.getName() + " " + recipe.getId(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onStepItemClick(Step step) {
        mViewModel.setStepMutableLiveData(step);
    }

    private void setUpAdapter() {

    }
}
