package ru.artempugachev.bakingapp.ui.fragments;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        }

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (step != null) {
            fillStepViews(step);
        } else {
            showNoStepData();
        }
    }

    /**
     * Fill views with step data
     * */
    private void fillStepViews(Step step) {
        initializePlayer(step.getVideoUrl());
        stepDescription.setText(step.getDescription());
    }


    private void initializePlayer(String videoUrl) {
        playerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.baking_placeholder));

        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
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
    public void onDestroy() {
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
