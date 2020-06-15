package com.project.segunfrancis.bakingtime.ui;

import com.project.segunfrancis.bakingtime.model.Recipe;
import com.project.segunfrancis.bakingtime.model.Step;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by SegunFrancis
 */
public class SharedViewModel extends ViewModel {
    private MutableLiveData<Recipe> mRecipeMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Step> mStepMutableLiveData = new MutableLiveData<>();

    public LiveData<Recipe> getRecipeMutableLiveData() {
        return mRecipeMutableLiveData;
    }

    public void setRecipeMutableLiveData(Recipe recipe) {
        mRecipeMutableLiveData.setValue(recipe);
    }

    public LiveData<Step> getStepMutableLiveData() {
        return mStepMutableLiveData;
    }

    public void setStepMutableLiveData(Step step) {
        mStepMutableLiveData.setValue(step);
    }
}
