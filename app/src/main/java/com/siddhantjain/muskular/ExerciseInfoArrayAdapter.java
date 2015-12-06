package com.siddhantjain.muskular;

/**
 * Created by siddhaja on 11/18/2015.
 */
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class ExerciseInfoArrayAdapter extends ArrayAdapter<ExerciseInfo> {

    private AdapterCallback mAdapterCallback;
    public static interface AdapterCallback {
        void onMethodCallback(String exercise_name);
    }
    private static class ViewHolder {
        TextView textViewTitle;
        TextView textViewTarget;
        ProgressBar progressBar;
        Button button;
        ExerciseInfo info;
    }


    private static final String TAG = ExerciseInfoArrayAdapter.class.getSimpleName();
    private Context mContext;

    public ExerciseInfoArrayAdapter(Context context, int textViewResourceId,
                                    List<ExerciseInfo> objects) {
        super(context, textViewResourceId, objects);
        try {
            this.mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ExerciseInfo info = getItem(position);
        // We need to set the convertView's progressBar to null.
        int i=0;

        ViewHolder holder = null;

        if(null == row) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.workout_plan_row, parent, false);

            holder = new ViewHolder();
            holder.textViewTitle = (TextView) row.findViewById(R.id.tvExerciseName);
            holder.textViewTarget = (TextView) row.findViewById(R.id.tvExerciseTarget);
            holder.progressBar = (ProgressBar) row.findViewById(R.id.pbExerciseProgress);
            holder.button = (Button)row.findViewById(R.id.btnRepLogger);
            holder.info = info;

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
            holder.info.setProgressBar(null);
            holder.info = info;
            holder.info.setProgressBar(holder.progressBar);
        }

        holder.textViewTitle.setText(info.getExercisename());
        holder.textViewTarget.setText("Target: " + String.valueOf(info.getmExerciseSets()) + " Sets");
        holder.progressBar.setProgress(info.getProgress());
        holder.progressBar.setMax(info.getmExerciseSets());
        info.setProgressBar(holder.progressBar);

        holder.button.setEnabled((info.getExerciseState() == ExerciseInfo.ExerciseState.NOT_STARTED) ||
                (info.getExerciseState() == ExerciseInfo.ExerciseState.PARTIALLY_COMPLETE));
        final Button button = holder.button;
        final ProgressBar progressBar = holder.progressBar;
        final TextView exerciseName = holder.textViewTitle;
        final LinearLayout exercise_row = (LinearLayout) row.findViewById(R.id.llExerciserow);
        holder.textViewTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mAdapterCallback.onMethodCallback(info.getExercisename());
                } catch (ClassCastException exception) {
                    // do something
                }

            }
        });
        holder.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getProgress() == info.getmExerciseSets()-1) {
                    int oldProgress = info.getProgress();
                    info.setProgress(oldProgress + 1);
                    progressBar.setProgress(oldProgress+1);
                    info.setExerciseState(ExerciseInfo.ExerciseState.COMPLETE);
                    button.setEnabled(false);
                    button.getBackground().setAlpha(50);
                    button.invalidate();
                } else {
                    int oldProgress = info.getProgress();
                    info.setProgress(oldProgress + 1);
                    progressBar.setProgress(oldProgress+1);
                    info.setExerciseState(ExerciseInfo.ExerciseState.PARTIALLY_COMPLETE);
                }

            }
        });



        return row;
    }



}
