package ru.artempugachev.bakingapp.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.model.Step;
import ru.artempugachev.bakingapp.ui.activity.MainActivity;
import ru.artempugachev.bakingapp.ui.adapters.StepPagerAdapter;
import ru.artempugachev.bakingapp.ui.fragments.StepFragment;

public class StepActivity extends AppCompatActivity {
    private Step step = null;
    @BindView(R.id.step_pager)
    ViewPager stepPager;

    @BindView(R.id.step_tabs)
    TabLayout stepTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);


        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(MainActivity.RECIPE_EXTRA)) {
                Recipe recipe = intent.getParcelableExtra(MainActivity.RECIPE_EXTRA);

                setUpViewPager(recipe);
            }
        }
    }

    private void setUpViewPager(Recipe recipe) {
        StepPagerAdapter stepPagerAdapter = new StepPagerAdapter(getSupportFragmentManager());

        FragmentManager fm = getSupportFragmentManager();

        for (Step step : recipe.getSteps()) {
            StepFragment stepFragment = new StepFragment();
            Bundle arguments = new Bundle();

            arguments.putParcelable(MainActivity.STEP_EXTRA, step);
            stepFragment.setArguments(arguments);

            stepPagerAdapter.addStepFragment(stepFragment);
        }
//
//        fm.beginTransaction()
//                .add(R.id.step_fragment_container, stepFragment)
//                .commit();



        stepPager.setAdapter(stepPagerAdapter);
        stepTabs.setupWithViewPager(stepPager);
    }
}

