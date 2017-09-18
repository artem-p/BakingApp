package ru.artempugachev.bakingapp.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.artempugachev.bakingapp.R;
import ru.artempugachev.bakingapp.model.Step;
import ru.artempugachev.bakingapp.ui.activity.MainActivity;
import ru.artempugachev.bakingapp.ui.fragments.StepFragment;

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
                setTitle(step.getTitle());

                FragmentManager fm = getSupportFragmentManager();
                StepFragment stepFragment = new StepFragment();
                Bundle arguments = new Bundle();

                arguments.putParcelable(MainActivity.STEP_EXTRA, step);
                stepFragment.setArguments(arguments);

                fm.beginTransaction()
                        .add(R.id.step_fragment_container, stepFragment)
                        .commit();
            }
        }
    }
}

