package com.example.googlebookssearch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.googlebookssearch.R;
import com.example.googlebookssearch.objects.Book;
import com.example.googlebookssearch.objects.BookAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ResultsListFragment extends ListFragment implements AdapterView.OnItemClickListener{
    private static final String ARG_BOOKS = "ARG_BOOKS";

    public static ResultsListFragment newInstance(ArrayList<Book> bookArray) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOKS, bookArray);
        ResultsListFragment fragment = new ResultsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resultslist, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null){
            //This is a custom listView Item Adapter to populate the ListView
            BookAdapter adapter = new BookAdapter(getContext(),
                    (ArrayList<Book>) Objects.requireNonNull(getArguments().getSerializable(ARG_BOOKS)));
            setListAdapter(adapter);
            adapter.notifyDataSetChanged();
            ListView lv = getListView();
            lv.setDivider(Objects.requireNonNull(getActivity())
                    .getResources().getDrawable(R.color.colorWhite));
            lv.setDividerHeight(20);


        }
    }

    //TODO: When a result listing is clicked, it will lead to a Details page with more data on that book
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

