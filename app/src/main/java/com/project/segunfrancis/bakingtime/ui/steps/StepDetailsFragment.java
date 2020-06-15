package com.project.segunfrancis.bakingtime.ui.steps;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.project.segunfrancis.bakingtime.databinding.FragmentStepDetailsBinding;
import com.project.segunfrancis.bakingtime.ui.SharedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import static com.project.segunfrancis.bakingtime.util.AppConstants.USER_AGENT;
import static com.project.segunfrancis.bakingtime.util.AppConstants.currentWindow;
import static com.project.segunfrancis.bakingtime.util.AppConstants.playWhenReady;
import static com.project.segunfrancis.bakingtime.util.AppConstants.playbackPosition;

/**
 * Created by SegunFrancis
 */
public class StepDetailsFragment extends Fragment {

    private SharedViewModel mViewModel;
    private FragmentStepDetailsBinding mBinding;
    private SimpleExoPlayer mPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentStepDetailsBinding.inflate(inflater, container, false);
        mViewModel.getStepMutableLiveData().observe(getViewLifecycleOwner(), step -> mBinding.setStep(step));
        initializePlayer();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getStepMutableLiveData().observe(getViewLifecycleOwner(), step -> {
            if (step.getVideoURL().equals("")) {
                mBinding.noVideoImageView.setVisibility(View.VISIBLE);
                mBinding.videoView.setVisibility(View.GONE);
            } else {
                mBinding.noVideoImageView.setVisibility(View.INVISIBLE);
                mBinding.videoView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initializePlayer() {
        mPlayer = new SimpleExoPlayer.Builder(requireContext()).build();
        mBinding.videoView.setPlayer(mPlayer);
        mViewModel.getStepMutableLiveData().observe(getViewLifecycleOwner(), step -> {
            Uri uri = Uri.parse(step.getVideoURL());
            MediaSource mediaSource = buildMediaSource(uri);
            mPlayer.setPlayWhenReady(playWhenReady);
            mPlayer.seekTo(currentWindow, playbackPosition);
            mPlayer.prepare(mediaSource, false, false);
        });
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(requireContext(), USER_AGENT);
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }
}
