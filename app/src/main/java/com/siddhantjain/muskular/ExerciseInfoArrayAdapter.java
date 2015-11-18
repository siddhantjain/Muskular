package com.siddhantjain.muskular;

/**
 * Created by siddhaja on 11/18/2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class ExerciseInfoArrayAdapter extends ArrayAdapter<ExerciseInfo> {

    private static class ViewHolder {
        TextView textView;
        ProgressBar progressBar;
        Button button;
        ExerciseInfo info;
    }


    private static final String TAG = ExerciseInfoArrayAdapter.class.getSimpleName();

    public ExerciseInfoArrayAdapter(Context context, int textViewResourceId,
                                    List<ExerciseInfo> objects) {
        super(context, textViewResourceId, objects);
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
            holder.textView = (TextView) row.findViewById(R.id.tvExerciseName);
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

        holder.textView.setText(info.getExercisename());
        holder.textView.setTextColor(Color.parseColor("#000000"));
        holder.progressBar.setProgress(info.getProgress());
        holder.progressBar.setMax(info.getmExerciseSets());
        info.setProgressBar(holder.progressBar);

        holder.button.setEnabled((info.getExerciseState() == ExerciseInfo.ExerciseState.NOT_STARTED) ||
                (info.getExerciseState() == ExerciseInfo.ExerciseState.PARTIALLY_COMPLETE));
        final Button button = holder.button;
        final ProgressBar progressBar = holder.progressBar;
        holder.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getProgress() == info.getmExerciseSets()-1) {
                    int oldProgress = info.getProgress();
                    info.setProgress(oldProgress + 1);
                    progressBar.setProgress(oldProgress+1);
                    info.setExerciseState(ExerciseInfo.ExerciseState.COMPLETE);
                    button.setEnabled(false);
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
