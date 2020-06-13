package com.project.segunfrancis.bakingtime.ui.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.model.Step;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Step step = (Step) getIntent().getSerializableExtra(INTENT_KEY);
        Toast.makeText(this, step.getShortDescription(), Toast.LENGTH_SHORT).show();
    }
}
