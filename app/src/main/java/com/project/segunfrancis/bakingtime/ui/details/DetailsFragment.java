package com.project.segunfrancis.bakingtime.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.model.Step;
import com.project.segunfrancis.bakingtime.util.StepAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;

/**
 * Created by SegunFrancis
 */
public class DetailsFragment extends Fragment implements StepAdapter.OnStepItemClickListener {

    public DetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        /*RecyclerView recyclerView = rootView.findViewById(R.id.steps_recyclerView);
        if (getArguments() != null) {
            Recipe recipe = (Recipe) getArguments().getSerializable(INTENT_KEY);
            StepAdapter adapter = new StepAdapter(recipe.getSteps(), this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        }*/
        return rootView;
    }

    @Override
    public void onStepItemClick(Step step) {

    }
}
