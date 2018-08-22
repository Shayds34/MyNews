package com.example.theshayds.mynewstest.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.Models.NYTimesNews;
import com.example.theshayds.mynewstest.R;
import com.example.theshayds.mynewstest.Utils.ApiStreams;
import com.example.theshayds.mynewstest.Utils.NetworkStatus;
import com.example.theshayds.mynewstest.Utils.ArticleAdapter;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NewsListActivity extends AppCompatActivity{

    // Use for Data
    private Disposable disposable;
    private List<NYTimesNews> nyTimesNewsList = new ArrayList<>();

    // Use For Design UI
    RecyclerView mRecyclerView;
    ArticleAdapter adapter;


    @Override
    public void onDestroy(){
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void disposeWhenDestroy() {
        if(this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Articles Searched");
        setSupportActionBar(mToolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Setup RecyclerView
        this.configureRecyclerView();


        // Checking Network Status
        if (NetworkStatus.getInstance(this).isOnline()) {
            this.retrofitRequestArticleSearch();
        } else {
            Snackbar snackbar = Snackbar.make(NewsListActivity.this.findViewById(R.id.drawer_layout), "There is no Internet connexion available...", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    // Create Subscriber for Article Search with all parameters
    private void retrofitRequestArticleSearch( ){
        // Get parameters from getStringExtra() to search articles
        Intent mIntent = getIntent();
        final String mQuery = mIntent.getStringExtra("query");

        disposable = ApiStreams.streamArticlesParameters(mQuery).subscribeWith(new DisposableObserver<ArticleSearch>() {
            @Override
            public void onNext(ArticleSearch article) {
                createListArticleSearch(nyTimesNewsList, article);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "ON ERROR " + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "ON COMPLETE");
            }
        });
    }


    // Create a list of NYTimesNews with articles from Most Popular API
    private void createListArticleSearch(List<NYTimesNews> nyTimesNewsList, ArticleSearch articleSearch) {

        for (ArticleSearch.Doc mResult : articleSearch.getResponse().getDocs()) {

            // Create a news
            NYTimesNews news = new NYTimesNews();

            // Add all information to the list
            news.setTitle(mResult.getSnippet());
            news.setSection(mResult.getSectionName());
            news.setUrl(mResult.getWebUrl());

            // TODO Date Format
            news.setPublishedDate(mResult.getPubDate());

            // Add static url to complete image url path
            if (mResult.getMultimedia().size() != 0){
                news.setImageURL("https://static01.nyt.com/" + mResult.getMultimedia().get(0).getUrl());
            }
            nyTimesNewsList.add(news);
        }
    }



    // Configure RecyclerView, Adapter and LayoutManager
    private void configureRecyclerView() {
        mRecyclerView = findViewById(R.id.search_recycler_view);
        adapter = new ArticleAdapter(this, nyTimesNewsList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }




}
