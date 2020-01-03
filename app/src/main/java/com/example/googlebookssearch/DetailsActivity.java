package com.example.googlebookssearch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.googlebookssearch.fragments.DetailsFragment;
import com.example.googlebookssearch.objects.Book;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity.TAG";
    private static final String SELECTED = "selected";
    Book selected; //the book results selected from the ListView of results
    DetailsFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Result Details");
        setContentView(R.layout.activity_detail);

        // Get the transferred data from viewActivity.
//        Intent intent = getIntent();
//        selected = intent.getParcelableExtra(SELECTED);
//        Log.d(TAG, "onCreate: " + selected.toString());

        // first time
        if(savedInstanceState == null) {
            fragment = DetailsFragment.newInstance();

            showDetails();
        }

    }

    private void showDetails() {
//        DetailsFragment fragment = DetailsFragment.newInstance();

        Bundle bundle = new Bundle();

        //put your selected Book result in bundle
//        bundle.putSerializable(SELECTED, selected);
        fragment.setArguments(bundle);
        //send to the Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, DetailsFragment.TAG)
                .commit();
    }


}
