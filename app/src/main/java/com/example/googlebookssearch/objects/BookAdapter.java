package com.example.googlebookssearch.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.googlebookssearch.R;

import java.util.Collections;
import java.util.List;

public class BookAdapter extends BaseAdapter {

    private final List<Book> list;
    private final LayoutInflater mInflater;


    //ViewHolder added as a static inner class
    static class ViewHolder {
        TextView titleView;
        TextView authorView;
        TextView yearView;
    }

    public BookAdapter(Context context, List<Book> list) {
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
            holder.titleView = convertView.findViewById(R.id.titleTextView);
            holder.authorView = convertView.findViewById(R.id.authorTextView);
            holder.yearView = convertView.findViewById(R.id.yearTextView);
            convertView.setTag(holder);
        } else {
            holder = (BookAdapter.ViewHolder) convertView.getTag();
        }

        //Book object properties used to populate each list item in the Grid
        Book book = list.get(position);
        holder.titleView.setText(book.getTitle());
        holder.authorView.setText(book.getAuthor());
        holder.yearView.setText(book.getPublishDate());

        return convertView;
    }
}
