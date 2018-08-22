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
import android.widget.ProgressBar;

import com.example.theshayds.mynewstest.Models.MostPopular;
import com.example.theshayds.mynewstest.Models.NYTimesNews;
import com.example.theshayds.mynewstest.R;
import com.example.theshayds.mynewstest.Utils.ApiStreams;
import com.example.theshayds.mynewstest.Utils.ArticleAdapter;
import com.example.theshayds.mynewstest.Utils.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MostPopularFragment extends Fragment {

    // Use for Data
    private Disposable disposable;
    private List<NYTimesNews> nyTimesNewsList = new ArrayList<>();
    private View mView;

    // Use For Design UI
    ProgressBar mProgressBar;
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

    public static MostPopularFragment newInstance() {
       return (new MostPopularFragment());
    }

    public MostPopularFragment(){
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
        mProgressBar = mView.findViewById(R.id.progress_bar);
        this.configureRecyclerView();

        // Checking Network Status
        if (NetworkStatus.getInstance(mView.getContext()).isOnline()) {
            this.retrofitRequestMostPopular();
        } else {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.drawer_layout), "There is no Internet connexion available...", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        return mView;
    }


    // Create subscriber for Most Popular
    private void retrofitRequestMostPopular(){
        disposable = ApiStreams.streamMostPopular().subscribeWith(new DisposableObserver<MostPopular>() {
            @Override
            public void onNext(MostPopular mostPopular) {
                createListMostPopular(nyTimesNewsList, mostPopular);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "ON ERROR " + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "ON COMPLETE");
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    // Create a list of NYTimesNews with articles from Most Popular API
    private void createListMostPopular(List<NYTimesNews> nyTimesNewsList, MostPopular mostPopular) {
        for (MostPopular.Result mResult : mostPopular.getResults()){

            // Create a news
            NYTimesNews news = new NYTimesNews();

            // Add all information to the list
            news.setTitle(mResult.getTitle());
            news.setSection(mResult.getSection());
            news.setUrl(mResult.getUrl());

            // TODO Date Format
            news.setPublishedDate(mResult.getPublishedDate());

            if (mResult.getMedia().size() != 0){
                news.setImageURL(mResult.getMedia().get(0).getMediaMetadata().get(0).getUrl());
            }
            //TODO Sort list
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
