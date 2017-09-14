package ru.artempugachev.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;

public class RecipeDetailsActivity extends AppCompatActivity {
    @BindView(R.id.ingredientsTextView)
    TextView ingredientsTextView;

    @BindView(R.id.stepsRecycler)
    RecyclerView stepsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
    }
}
