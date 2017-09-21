package ru.artempugachev.bakingapp.ui.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

/**
 * Step instructions and video
 */

public class StepFragment extends Fragment{
    @BindView(R.id.stepPlayer)
    SimpleExoPlayerView playerView;

    @BindView(R.id.stepDescription)
    TextView stepDescription;

    private SimpleExoPlayer player;
    private Step step = null;
    private boolean isTwoPane;


    public StepFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        ButterKnife.bind(this, rootView);

        Bundle arguments = getArguments();
        if (arguments != null) {
            step = arguments.getParcelable(MainActivity.STEP_EXTRA);
            isTwoPane = arguments.getBoolean(MainActivity.IS_TWO_PANE_EXTRA);
        }

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (step != null) {
            fillStepViews(step);
        } else {
            showNoStepData();
        }

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            // in landscape mode video should be fullscreen
            showFullscreenVideo();
        }
    }

    private void showFullscreenVideo() {
        // Hide status bar and set fullscreen mode
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        // hide action bar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // hide step tabs
        TabLayout stepTabs = (TabLayout) getActivity().findViewById(R.id.step_tabs);
        if (stepTabs != null) {
            stepTabs.setVisibility(View.GONE);
        }

        // expand player view to fullscreen
        playerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        playerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }


    /**
     * Fill views with step data
     * */
    private void fillStepViews(Step step) {
        if (step.getVideoUrl() != null && !step.getVideoUrl().isEmpty()) {
            initializePlayer(step.getVideoUrl());
        } else {
            hidePlayerView();
        }

        stepDescription.setText(step.getDescription());
    }

    private void hidePlayerView() {
        playerView.setVisibility(View.INVISIBLE);
    }


    private void initializePlayer(String videoUrl) {
        playerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.baking_placeholder));

//        if (player == null) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        playerView.setPlayer(player);

        // Prepare the MediaSource.

        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

        player.setPlayWhenReady(false);
//        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }


    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    /**
     * Show error message as we don't have step data.
     * */
    private void showNoStepData() {
        // todo
    }
}
