package com.example.theshayds.mynewstest.Views;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.Models.MostPopular;
import com.example.theshayds.mynewstest.Models.NYTimesNews;
import com.example.theshayds.mynewstest.Models.TopStories;
import com.example.theshayds.mynewstest.R;
import com.example.theshayds.mynewstest.Utils.ApiStreams;
import com.example.theshayds.mynewstest.Utils.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ArticleSearchFragment extends Fragment {

    // Use for Data
    private Disposable disposable;
    private List<NYTimesNews> nyTimesNewsList = new ArrayList<>();
    private View mView;

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

    public static ArticleSearchFragment newInstance() {
       return (new ArticleSearchFragment());
    }

    public ArticleSearchFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_article, container, false);
        this.configureRecyclerView();


        // Checking Network Status
        if (NetworkStatus.getInstance(mView.getContext()).isOnline()) {
            this.retrofitRequestArticleSearch();
        } else {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.drawer_layout), "There is no Internet connexion available...", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        return mView;
    }


    // Create subscriber for Article Search
    private void retrofitRequestArticleSearch(){
        disposable = ApiStreams.streamArticleSearch().subscribeWith(new DisposableObserver<ArticleSearch>() {
            @Override
            public void onNext(ArticleSearch articleSearch) {
                createListArticleSearch(nyTimesNewsList, articleSearch);
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
        mRecyclerView = mView.findViewById(R.id.recycler_view);
        adapter = new ArticleAdapter(getActivity(), nyTimesNewsList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
