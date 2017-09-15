package ru.artempugachev.bakingapp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
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
import ru.artempugachev.bakingapp.model.Recipe;
import ru.artempugachev.bakingapp.model.Step;

public class StepActivity extends AppCompatActivity {
    private Step step = null;

    @BindView(R.id.stepPlayer)
    SimpleExoPlayerView playerView;

    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

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
        initializePlayer(step.getVideoUrl());
    }


    private void initializePlayer(String videoUrl) {
        playerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.baking_placeholder));

        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            playerView.setPlayer(player);

            // Prepare the MediaSource.

            Uri uri = Uri.parse(videoUrl);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, false);

            player.setPlayWhenReady(false);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }

    /**
     * Show error message as we don't have step data.
     * */
    private void showNoStepData() {
        // todo
    }
}

