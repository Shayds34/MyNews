package com.example.theshayds.mynewstest.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.theshayds.mynewstest.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    TextView mBeginDate, mEndDate;
    Spinner mBeginDateSpinner, mEndDateSpinner;
    EditText mSearchTerm;
    CheckBox mArts, mEntrepreneurs, mBusiness, mPolitics, mTravel, mSports;
    Button mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Date Text View and Spinner
        mBeginDate = findViewById(R.id.textView_begin_date);
        mBeginDateSpinner = findViewById(R.id.spinner_begin_date);
        mEndDate = findViewById(R.id.textView_end_date);
        mEndDateSpinner = findViewById(R.id.spinner_end_date);

        // Edit text
        mSearchTerm = findViewById(R.id.search_query_text);

        // Checkboxes
        mArts = findViewById(R.id.checkbox_arts);
        mEntrepreneurs = findViewById(R.id.checkbox_entrepreneurs);
        mBusiness = findViewById(R.id.checkbox_business);
        mPolitics = findViewById(R.id.checkbox_politics);
        mTravel = findViewById(R.id.checkbox_travel);
        mSports = findViewById(R.id.checkbox_sports);

        mSearch = findViewById(R.id.button_search);

        final Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Search Articles");
        setSupportActionBar(mToolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // TODO : add If mSearchQuery.isEmpty() ?
        // TODO : at least one isChecked()
        mSearch.setOnClickListener(v -> {

            mSearchTerm = findViewById(R.id.search_query_text);

            ArrayList<String> mQueries = new ArrayList<>();
            String mSearchQuery = mSearchTerm.getText().toString();

            if(!mSearchQuery.equals("")){
                mQueries.add(mSearchQuery);
            }

            if(mArts.isChecked()){
                mQueries.add("arts");
            }
            if(mEntrepreneurs.isChecked()){
                mQueries.add("entrepreneurs");
            }
            if(mBusiness.isChecked()){
                mQueries.add("business");
            }
            if(mPolitics.isChecked()){
                mQueries.add("politics");
            }
            if(mTravel.isChecked()){
                mQueries.add("travel");
            }
            if(mSports.isChecked()){
                mQueries.add("sports");
            }

            // Separate queries with "&" symbol
            String result = TextUtils.join("&", mQueries);

            Intent mIntent = new Intent(SearchActivity.this, NewsListActivity.class);
            mIntent.putExtra("query", result);
            startActivity(mIntent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close this activity and return to previous activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
