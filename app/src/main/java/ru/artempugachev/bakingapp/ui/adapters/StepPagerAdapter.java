package ru.artempugachev.bakingapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.artempugachev.bakingapp.ui.fragments.StepFragment;

/**
 * Adapter for step pager
 */

public class StepPagerAdapter extends FragmentPagerAdapter {
    private List<StepFragment> stepFragments = new ArrayList<StepFragment>();
    private List<String> stepPageTitles = new ArrayList<String>();

    public StepPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (stepFragments != null && stepFragments.size() > position) {
            return stepFragments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
       return !stepFragments.isEmpty() ? stepFragments.size() : 0;
    }

    public void addStepFragment(StepFragment stepFragment, String stepPageTitle) {
        if (stepFragments != null) {
            stepFragments.add(stepFragment);
            stepPageTitles.add(stepPageTitle);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (stepPageTitles != null && stepPageTitles.size() > position) {
            return stepPageTitles.get(position);
        } else {
            return null;
        }
    }
}
