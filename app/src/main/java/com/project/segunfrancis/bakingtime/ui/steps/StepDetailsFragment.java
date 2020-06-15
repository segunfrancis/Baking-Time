package com.project.segunfrancis.bakingtime.ui.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.databinding.FragmentStepDetailsBinding;
import com.project.segunfrancis.bakingtime.ui.SharedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by SegunFrancis
 */
public class StepDetailsFragment extends Fragment {

    private SharedViewModel mViewModel;
    private FragmentStepDetailsBinding mBinding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentStepDetailsBinding.inflate(inflater, container, false);
        mViewModel.getStepMutableLiveData().observe(getViewLifecycleOwner(), step -> {
            Toast.makeText(requireContext(), step.getDescription(), Toast.LENGTH_SHORT).show();
            mBinding.setStep(step);
        });
        return mBinding.getRoot();
    }
}
