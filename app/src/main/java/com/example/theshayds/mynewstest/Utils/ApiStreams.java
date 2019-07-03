package com.example.theshayds.mynewstest.Utils;

import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.Models.MostPopular;
import com.example.theshayds.mynewstest.Models.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiStreams {
    private static final String API_KEY = "6sY6IUCABxIDivZZqpoyO2VSwA6oJxOO";

    // Stream to populate TopStories' fragment list.
    public static Observable<TopStories> streamTopStories(){
        ApiServices mApiServices = ApiServices.retrofit.create(ApiServices.class);
        return mApiServices.getTopStories(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
    // Stream to populate MostPopular's fragment list.
    public static Observable<MostPopular> streamMostPopular(){
        ApiServices mApiServices = ApiServices.retrofit.create(ApiServices.class);
        return mApiServices.getMostPopular(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Stream to populate third fragment list.
    public static Observable<ArticleSearch> streamArticleSearch(){
        ApiServices mApiServices = ApiServices.retrofit.create(ApiServices.class);
        return mApiServices.getArticleSearch(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Stream to populate searched articles for NewsListActivity list.
    public static Observable<ArticleSearch> streamArticlesParameters(String query, String filterQuery){
        ApiServices mApiServices = ApiServices.retrofit.create(ApiServices.class);
        return mApiServices.getAllSearchedArticles(query, filterQuery, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Stream to populate searched articles for NewsListActivity list with begin/end date parameters
    public static Observable<ArticleSearch> streamArticlesWithDate(String query, String filterQuery, String beginDate, String endDate){
        ApiServices mApiServices = ApiServices.retrofit.create(ApiServices.class);
        return mApiServices.getAllSearchedArticlesWithDate(query, filterQuery, beginDate, endDate, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
