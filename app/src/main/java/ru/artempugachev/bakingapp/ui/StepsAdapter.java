package ru.artempugachev.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Step;

/**
 * Adapter for steps recycler view
 */

public final class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private List<Step> steps;
    private StepClickListener stepClickListener;

    public StepsAdapter(List<Step> steps, StepClickListener stepClickListener) {
        this.steps = steps;
        this.stepClickListener = stepClickListener;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int recipeCardLayoutId = R.layout.step_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(recipeCardLayoutId, parent, shouldAttachToParentImmediately);
        return new StepsAdapter.StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        if (steps != null && !steps.isEmpty()) {
            Step step = steps.get(position);
            holder.stepTitle.setText(step.asText());
        }
    }

    @Override
    public int getItemCount() {
        return steps != null ? steps.size() : 0;
    }

    public final class StepsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        @BindView(R.id.stepTitle)
        TextView stepTitle;

        public StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            stepClickListener.onStepClick(getAdapterPosition());
        }
    }

    public interface StepClickListener {
        void onStepClick(int position);
    }
}
