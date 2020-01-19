package com.example.googlebookssearch.objects;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.googlebookssearch.R;

import java.util.Collections;
import java.util.List;

public class YTVideoAdapter extends BaseAdapter {

    private static final String TAG = "YTVideoAdapter";
    private final List<YTVideo> list;
    private final LayoutInflater mInflater;
    Context context; //context

    //ViewHolder added as a static inner class
    static class ViewHolder {
        ImageView thumbImage;
        TextView titleView;
        TextView channelView;
        TextView releaseDateView;
    }

    public YTVideoAdapter(Context context, List<YTVideo> list) {
        this.context = context;
        //If the list is empty, just return an empty list so the list won't be null
        if (list.isEmpty()){
            this.list = Collections.emptyList();
        } else {
            this.list = list;
        }
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list != null){
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder pattern
        YTVideoAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.book_item_view, parent, false);
            convertView.setBackgroundResource(R.drawable.background_view);
            holder = new YTVideoAdapter.ViewHolder();
            holder.thumbImage = convertView.findViewById(R.id.thumbImage);
            holder.titleView = convertView.findViewById(R.id.titleTextView);
            holder.channelView = convertView.findViewById(R.id.channelTextView);
            holder.releaseDateView = convertView.findViewById(R.id.releaseDateTextView);
            convertView.setTag(holder);
        } else {
            holder = (YTVideoAdapter.ViewHolder) convertView.getTag();
        }

        //Book object properties used to populate each list item in the Grid
        YTVideo ytVideo = list.get(position);

        //changes the image URL to https from http in order to display
        String httpsString = ytVideo.getThumbnail();
        httpsString = httpsString.replace("http", "https");

        //RequestOptions formats the imageView for the cover image
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.color.colorAccent);

        //load the imageView for the thumbnail image in the item view
        Glide.with(context)
                .load(httpsString)
                .override(100, 100)
                .apply(options)
                .into(holder.thumbImage);
        holder.titleView.setText(ytVideo.getTitle());
        holder.channelView.setText(ytVideo.getChannel());
        holder.releaseDateView.setText(ytVideo.getReleaseDate());
        Log.d(TAG, "getView: " + ytVideo.getThumbnail());
        return convertView;
    }


}
