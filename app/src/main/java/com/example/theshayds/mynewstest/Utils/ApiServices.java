package com.example.theshayds.mynewstest.Utils;

import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.Models.MostPopular;
import com.example.theshayds.mynewstest.Models.TopStories;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    // API Request for "Top Stories" tab
    @GET("svc/topstories/v2/home.json?sort=newest")
        Observable<TopStories> getTopStories(@Query("api-key") String apiKey);

    // API Request for "Most Popular" tab
    @GET("svc/mostpopular/v2/viewed/7.json?sort=newest")
        Observable<MostPopular> getMostPopular(@Query("api-key") String apiKey);

    // API Request for "Arts" tab
    @GET("svc/search/v2/articlesearch.json?sort=newest&q=arts")
        Observable<ArticleSearch> getArticleSearch(@Query("api-key") String apiKey);

    // API Request for "SearchActivity", "NewsListActivity" and "NotificationActivity"
    @GET("svc/search/v2/articlesearch.json?sort=newest")
        Observable<ArticleSearch> getAllSearchedArticles(@Query("q") String query, @Query("fq") String filterQuery, @Query("api-key") String apiKey);

    // API Request for "SearchActivity", "NewsListActivity" and "NotificationActivity" using a begin/end date
    @GET("svc/search/v2/articlesearch.json?sort=newest")
    Observable<ArticleSearch> getAllSearchedArticlesWithDate(@Query("q") String query, @Query("fq") String filterQuery, @Query("begin_date") String beginDate, @Query("end_date") String endDate, @Query("api-key") String apiKey);


    // Retrofit builder with New York Times base URL
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
