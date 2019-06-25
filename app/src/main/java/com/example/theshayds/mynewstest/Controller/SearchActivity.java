package com.example.theshayds.mynewstest.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.theshayds.mynewstest.R;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {

    // Use Button with Spinner Style
    Button mBeginDateButton, mEndDateButton;

    EditText mSearchTerm;
    CheckBox mArts, mEntrepreneurs, mBusiness, mPolitics, mTravel, mSports;
    Button mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViewById(R.id.fields_begin_date).setVisibility(View.VISIBLE);
        findViewById(R.id.fields_end_date).setVisibility(View.VISIBLE);

        // Date
        mBeginDateButton = findViewById(R.id.button_being_date);
        mEndDateButton = findViewById(R.id.button_end_date);

        // Calendar
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

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

        // Default Date on the Begin Date Spinner
        mBeginDateButton.setText(dayOfMonth + "/" + month  + "/" + year);

        mBeginDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                mBeginDateButton.setText(day +"/" + (month + 1) + "/" + year);
                            }
                        }, year,month,dayOfMonth);
                datePickerDialog.show();
            }
        });

        mEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                mEndDateButton.setText(day +"/" + (month + 1) + "/" + year);
                            }
                        }, year,month,dayOfMonth);
                datePickerDialog.show();
            }
        });

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
