package com.example.googlebookssearch.fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.googlebookssearch.R;
import com.example.googlebookssearch.objects.Book;

public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment.TAG";
    private static final String SELECTED = "selected";

    Book selectedBook; //the book results selected from the ListView of results
    ImageView coverImageView;
    TextView titleTextView;
    TextView authorTextView;
    TextView descriptionTextView;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            selectedBook = (Book) args.getSerializable(SELECTED);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        coverImageView = view.findViewById(R.id.coverImageView);
        titleTextView = view.findViewById(R.id.titleTextView);
        authorTextView = view.findViewById(R.id.authorTextView);
        descriptionTextView = view.findViewById(R.id.descriptonTextView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            selectedBook = (Book) getArguments().getSerializable(SELECTED);
        }

        if (selectedBook != null && getContext() != null) {
            //changes the image URL to https from http in order to display
            String httpsString = selectedBook.getCoverImage();
            httpsString = httpsString.replace("http", "https");
            //RequestOptions formats the imageView for the cover image
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.color.colorAccent);
            //load the imageView for the cover image in the item view
            Glide.with(getContext())
                    .load(httpsString)
                    .override(240, 240)
                    .apply(options)
                    .into(coverImageView);

            titleTextView.setText(selectedBook.getTitle());
            authorTextView.setText(selectedBook.getAuthor());
            descriptionTextView.setText(selectedBook.getDescription());
            descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
            Log.d(TAG, "onActivityCreated: " + selectedBook.getTitle());

        }
    }
}
