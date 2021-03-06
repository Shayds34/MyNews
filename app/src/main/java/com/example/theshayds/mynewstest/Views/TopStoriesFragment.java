package com.example.theshayds.mynewstest.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.theshayds.mynewstest.Models.NYTimesNews;
import com.example.theshayds.mynewstest.Models.TopStories;
import com.example.theshayds.mynewstest.R;
import com.example.theshayds.mynewstest.Utils.ApiStreams;
import com.example.theshayds.mynewstest.Utils.ArticleAdapter;
import com.example.theshayds.mynewstest.Utils.DateServices;
import com.example.theshayds.mynewstest.Utils.NetworkStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class TopStoriesFragment extends Fragment {

    public static final String TAG = "TopStoriesFragment";

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

    public TopStoriesFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_article, container, false);

        mProgressBar = mView.findViewById(R.id.progress_bar);
        this.configureRecyclerView();


        // Checking Network Status
        if (NetworkStatus.getInstance(mView.getContext()).isOnline()) {
            nyTimesNewsList.clear();
            this.retrofitRequestTopStories();
        } else {
            Snackbar snackbar = Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(R.id.drawer_layout), "There is no Internet connexion available...", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        return mView;
    }


    // Create Subscriber for Top Stories
    private void retrofitRequestTopStories(){
        disposable = ApiStreams.streamTopStories().subscribeWith(new DisposableObserver<TopStories>() {
            @Override
            public void onNext(TopStories article) {
                createListTopStories(nyTimesNewsList, article);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }


    // Create a list of NYTimesNews with articles from TopStories API
    private void createListTopStories(List<NYTimesNews> nyTimesNewsList, TopStories article) {
        adapter.clearNews();

        for (TopStories.Result mResult : article.getResults()){

            // Create a news
            NYTimesNews news = new NYTimesNews();

            // Add all information to the list
            news.setTitle(mResult.getTitle());
            news.setSection(mResult.getSection());
            news.setUrl(mResult.getUrl());

            // Format Date
            String outputText = DateServices.dateFormat(mResult.getPublishedDate());
            news.setPublishedDate(outputText);

            // Show article thumbnail
            if (mResult.getMultimedia().size() != 0){
                news.setImageURL(mResult.getMultimedia().get(0).getUrl());
            }

            nyTimesNewsList.add(news);

            // Sort list
            Collections.sort(nyTimesNewsList, (o1, o2) -> o1.getPublishedDate().compareTo(o2.getPublishedDate()));
            Collections.reverse(nyTimesNewsList);
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
