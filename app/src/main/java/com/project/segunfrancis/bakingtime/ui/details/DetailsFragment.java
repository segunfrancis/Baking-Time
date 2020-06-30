package com.project.segunfrancis.bakingtime.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.data_source.local.IngredientDao;
import com.project.segunfrancis.bakingtime.data_source.local.IngredientExecutors;
import com.project.segunfrancis.bakingtime.data_source.local.IngredientRoomDatabase;
import com.project.segunfrancis.bakingtime.databinding.FragmentDetailsBinding;
import com.project.segunfrancis.bakingtime.model.Ingredient;
import com.project.segunfrancis.bakingtime.model.IngredientsForWidget;
import com.project.segunfrancis.bakingtime.model.Step;
import com.project.segunfrancis.bakingtime.ui.SharedViewModel;
import com.project.segunfrancis.bakingtime.ui.adapters.IngredientsAdapter;
import com.project.segunfrancis.bakingtime.ui.adapters.StepAdapter;
import com.project.segunfrancis.bakingtime.widget.BakingService;

import java.util.ArrayList;
import java.util.List;

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
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IngredientRoomDatabase database = IngredientRoomDatabase.getDatabase(requireContext());
        IngredientDao dao = database.mRecipeDao();
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

            List<Ingredient> ingredients = recipe.getIngredients();
            List<String> ingredientsForWidget = new ArrayList<>();
            for (Ingredient a : ingredients) {
                ingredientsForWidget.add(a.getIngredient() + "\n" +
                        "Quantity: " + a.getQuantity() + "\n" +
                        "Measure: " + a.getMeasure() + "\n");
            }
            IngredientExecutors.getInstance().diskIO().execute(() -> {
                IngredientsForWidget forWidget = new IngredientsForWidget();
                forWidget.setIngredients(ingredientsForWidget);
                dao.insertIngredients(forWidget);
            });
            //BakingService.actionStartBakingService(requireContext(), ingredientsForWidget);
        });
    }

    @Override
    public void onStepItemClick(Step step) {
        mViewModel.setStepMutableLiveData(step);
    }
}
