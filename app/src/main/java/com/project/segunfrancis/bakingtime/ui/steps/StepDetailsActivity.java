package com.project.segunfrancis.bakingtime.ui.steps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.project.segunfrancis.bakingtime.R;
import com.project.segunfrancis.bakingtime.data_source.local.IngredientDao;
import com.project.segunfrancis.bakingtime.data_source.local.IngredientExecutors;
import com.project.segunfrancis.bakingtime.data_source.local.IngredientRoomDatabase;
import com.project.segunfrancis.bakingtime.databinding.ActivityStepDetailsBinding;
import com.project.segunfrancis.bakingtime.model.Step;

import static com.project.segunfrancis.bakingtime.util.AppConstants.INTENT_KEY;
import static com.project.segunfrancis.bakingtime.util.AppConstants.USER_AGENT;
import static com.project.segunfrancis.bakingtime.util.AppConstants.currentWindow;
import static com.project.segunfrancis.bakingtime.util.AppConstants.playWhenReady;
import static com.project.segunfrancis.bakingtime.util.AppConstants.playbackPosition;

public class StepDetailsActivity extends AppCompatActivity {

    private ActivityStepDetailsBinding mBinding;
    private SimpleExoPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_details);
        Step step = (Step) getIntent().getSerializableExtra(INTENT_KEY);
        mBinding.setStep(step);
        setLandscapeOrPortrait(step);
        initializePlayer();
        getLocalData();
    }

    private void setLandscapeOrPortrait(Step step) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !step.getVideoURL().equals("")) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mBinding.videoView.getLayoutParams();
            params.width = FrameLayout.LayoutParams.MATCH_PARENT;
            params.height = FrameLayout.LayoutParams.MATCH_PARENT;
            mBinding.videoView.setLayoutParams(params);
            mBinding.recipeDescriptionTextView.setVisibility(View.GONE);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mBinding.videoView.getLayoutParams();
            params.width = FrameLayout.LayoutParams.MATCH_PARENT;
            params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
            mBinding.videoView.setLayoutParams(params);
        }
    }

    private void initializePlayer() {
        mPlayer = new SimpleExoPlayer.Builder(this).build();
        mBinding.videoView.setPlayer(mPlayer);
        Step step = (Step) getIntent().getSerializableExtra(INTENT_KEY);
        Uri uri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        mPlayer.setPlayWhenReady(playWhenReady);
        mPlayer.seekTo(currentWindow, playbackPosition);
        mPlayer.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, USER_AGENT);
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //hideSystemUi(mBinding.videoView);
        if ((Util.SDK_INT < 24 || mPlayer == null)) {
            initializePlayer();
        }
    }*/

/*    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            /*playWhenReady = mPlayer.getPlayWhenReady();
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();*/
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void getLocalData() {
        IngredientDao dao = IngredientRoomDatabase.getDatabase(this).mRecipeDao();
        Toast.makeText(this, dao.getIngredients().getIngredients().get(0), Toast.LENGTH_SHORT).show();
    }
}
