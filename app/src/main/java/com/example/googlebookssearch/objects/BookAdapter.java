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

public class BookAdapter extends BaseAdapter {

    private static final String TAG = "BookAdapter";
    private final List<Book> list;
    private final LayoutInflater mInflater;
    Context context; //context



    //ViewHolder added as a static inner class
    static class ViewHolder {
        ImageView coverImage;
        TextView titleView;
        TextView authorView;
        TextView yearView;
    }

    public BookAdapter(Context context, List<Book> list) {
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
        return list.get(position); //item at position
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //ViewHolder pattern
        BookAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_view, parent, false);
            convertView.setBackgroundResource(R.drawable.background_view);
            holder = new BookAdapter.ViewHolder();
            holder.coverImage = convertView.findViewById(R.id.coverImage);
            holder.titleView = convertView.findViewById(R.id.titleTextView);
            holder.authorView = convertView.findViewById(R.id.authorTextView);
            holder.yearView = convertView.findViewById(R.id.yearTextView);
            convertView.setTag(holder);
        } else {
            holder = (BookAdapter.ViewHolder) convertView.getTag();
        }

        //Book object properties used to populate each list item in the Grid
        Book book = list.get(position);

        //changes the image URL to https from http in order to display
        String httpsString = book.getCoverImage();
        httpsString = httpsString.replace("http", "https");

        //RequestOptions formats the imageView for the cover image
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.color.colorAccent);

        //load the imageView for the cover image in the item view
        Glide.with(context)
                .load(httpsString)
                .override(100, 100)
                .apply(options)
                .into(holder.coverImage);
        holder.titleView.setText(book.getTitle());
        holder.authorView.setText(book.getAuthor());
        holder.yearView.setText(book.getPublishDate());
        Log.d(TAG, "getView: " + book.getCoverImage());

        return convertView;
    }
}
