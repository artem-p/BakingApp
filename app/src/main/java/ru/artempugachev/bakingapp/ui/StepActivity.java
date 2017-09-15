package ru.artempugachev.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.model.Step;

public class StepActivity extends AppCompatActivity {
    private Step step = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(MainActivity.STEP_EXTRA)) {
                Step step = intent.getParcelableExtra(MainActivity.STEP_EXTRA);
                fillStepViews(step);
            } else {
                showNoStepData();
            }
        } else {
            showNoStepData();
        }
    }

    /**
     * Fill views with step data
     * */
    private void fillStepViews(Step step) {
        setTitle(step.getTitle());
    }


    /**
     * Show error message as we don't have step data.
     * */
    private void showNoStepData() {
        // todo
    }
}
