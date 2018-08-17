package com.example.theshayds.mynewstest.Utils;

import com.example.theshayds.mynewstest.Controller.ArticleActivity;
import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.Models.MostPopular;
import com.example.theshayds.mynewstest.Models.TopStories;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiStreams {

    private static final String API_KEY = "4e57ca5e25324139ab64c95a50c25ef8";
    public static final String Q_STRING = "sport";

    public static Observable<TopStories> streamTopStories(){
        ApiServices mApiServices = ApiServices.retrofit.create(ApiServices.class);
        return mApiServices.getTopStories(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // TODO use this to fetch Most Popular
    public static Observable<MostPopular> streamMostPopular(){
        ApiServices mApiServices = ApiServices.retrofit.create(ApiServices.class);
        return mApiServices.getMostPopular(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // TODO use this to fetch Article Search
    public static Observable<ArticleSearch> streamArticleSearch(){
        ApiServices mApiServices = ApiServices.retrofit.create(ApiServices.class);
        return mApiServices.getArticleSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ArticleSearch> streamArticlesParameters(String query){
        ApiServices mApiServices = ApiServices.retrofit.create(ApiServices.class);
        return mApiServices.getAllSearchedArticles(query, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

}
