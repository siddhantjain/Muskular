package com.siddhantjain.muskular;

import android.util.Log;
import android.widget.ProgressBar;


public class ExerciseInfo {
    private final static String TAG = ExerciseInfo.class.getSimpleName();
    public enum ExerciseState {
        NOT_STARTED,
        PARTIALLY_COMPLETE,
        COMPLETE
    }
    private volatile ExerciseState mExerciseState = ExerciseState.NOT_STARTED;
    private final String mExercisename;
    private volatile int mProgress;
    private final int mExerciseSets;
    private volatile ProgressBar mProgressBar;


    public ExerciseInfo(String exercisename, Integer exerciseSets) {
        mExercisename = exercisename;
        mProgress = 0;
        mExerciseSets = exerciseSets;
        mProgressBar = null;
    }


    public ProgressBar getProgressBar() {
        return mProgressBar;
    }
    public void setProgressBar(ProgressBar progressBar) {
        Log.d(TAG, "setProgressBar " + mExercisename + " to " + progressBar);
        mProgressBar = progressBar;
    }

    public void setExerciseState(ExerciseState state) {
        mExerciseState = state;
    }
    public ExerciseState getExerciseState() {
        return mExerciseState;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(Integer progress) {
        this.mProgress = progress;
    }

    public int getmExerciseSets() {
        return mExerciseSets;
    }

    public String getExercisename() {
        return mExercisename;
    }
}
