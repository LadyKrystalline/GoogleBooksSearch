package com.example.googlebookssearch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.googlebookssearch.R;
import com.example.googlebookssearch.objects.Book;

public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment.TAG";
    private static final String SELECTED = "selected";

    private Book selectedBook; //the book results selected from the ListView of results

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            selectedBook = args.getParcelable(SELECTED);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        //then get your ArrayList data in fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            selectedBook = getArguments().getParcelable(SELECTED);
        }
        if (selectedBook != null) {

        }
    }
}
