package com.siddhantjain.muskular;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.siddhantjain.muskular.models.ProgramMetadata;
/**
 * Created by siddhaja on 12/29/2015.
 */
public class ProgramInfoArrayAdapter extends BaseAdapter {
    private final List<ProgramMetadata> items;
    private final Context context;

    public ProgramInfoArrayAdapter(Context context, List<ProgramMetadata> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.program_list_item, null);
            holder = new ViewHolder();
            holder.ProgramTitle = (TextView) convertView.findViewById(R.id.tvProgramName);
            holder.ProgramAuthor = (TextView) convertView.findViewById((R.id.tvProgramAuthorName));
            holder.ProgramDifficulty = (TextView) convertView.findViewById(R.id.tvProgramDifficulty);
            holder.ProgramCommitment = (TextView) convertView.findViewById((R.id.tvProgramTimeCommitment));
            holder.ProgramImage = (ImageView) convertView.findViewById(R.id.ivProgramImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.ProgramTitle.setText(items.get(position).getId());
        holder.ProgramAuthor.setText(items.get(position).getTrainer().getNickName());
        holder.ProgramDifficulty.setText(items.get(position).getDifficulty());
        holder.ProgramCommitment.setText(items.get(position).getDaysPerWeek());

        return convertView;
    }

    static class ViewHolder {
        ImageView ProgramImage;
        TextView ProgramTitle;
        TextView ProgramDifficulty;
        TextView ProgramCommitment;
        TextView ProgramAuthor;
    }
}
