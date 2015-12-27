
package com.siddhantjain.muskular;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddhaja on 12/27/2015.
 */

public class TwitterArrayAdapter extends BaseAdapter {
    private final List<TwitterTweet> items;
    private final Context context;

    public TwitterArrayAdapter(Context context, List<TwitterTweet> items) {
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
            convertView = View.inflate(context, R.layout.tweet_item, null);
            holder = new ViewHolder();
            holder.TweetText = (TextView) convertView.findViewById(R.id.tvTweetText);
            holder.TweetCreatedAt = (TextView) convertView.findViewById((R.id.tvTweetCreatedAt));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TweetText.setText(items.get(position).getText());
        String tweetCreatedAtComplete =  items.get(position).getCreatedAt();
        String[] timeList = tweetCreatedAtComplete.split(" ");
        String tweetCreatedAtExtracted = timeList[0] + " " + timeList[1] + " " + timeList[2];
        holder.TweetCreatedAt.setText(tweetCreatedAtExtracted);
        return convertView;
    }

    static class ViewHolder {
        TextView TweetText;
        TextView TweetCreatedAt;
    }
}
