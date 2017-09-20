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

import java.util.List;

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

                setCurrentStep(intent);
            }
        }
    }

    private void setCurrentStep(Intent intent) {
        if (intent.hasExtra(MainActivity.CURRENT_STEP_ID_EXTRA)) {
            int stepId = intent.getIntExtra(MainActivity.CURRENT_STEP_ID_EXTRA, 0);
            stepPager.setCurrentItem(stepId);
        }
    }

    private void setUpViewPager(final Recipe recipe) {
        final StepPagerAdapter stepPagerAdapter = new StepPagerAdapter(getSupportFragmentManager());

        FragmentManager fm = getSupportFragmentManager();

        for (Step step : recipe.getSteps()) {
            StepFragment stepFragment = new StepFragment();
            Bundle arguments = new Bundle();

            arguments.putParcelable(MainActivity.STEP_EXTRA, step);
            stepFragment.setArguments(arguments);

            stepPagerAdapter.addStepFragment(stepFragment, step.getPageTitle());
        }
//
//        fm.beginTransaction()
//                .add(R.id.step_fragment_container, stepFragment)
//                .commit();



        stepPager.setAdapter(stepPagerAdapter);
        stepTabs.setupWithViewPager(stepPager);

        // set fragment name in action bar title
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                List<Step> steps = recipe.getSteps();

                if (steps != null && steps.size() > position) {
                    String actionBarTitle = steps.get(position).getTitle();
                    getSupportActionBar().setTitle(actionBarTitle);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        stepPager.addOnPageChangeListener(onPageChangeListener);
        onPageChangeListener.onPageSelected(0);
    }
}

