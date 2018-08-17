package com.example.theshayds.mynewstest.Utils;

import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.Models.MostPopular;
import com.example.theshayds.mynewstest.Models.TopStories;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    // Full API URL for TOP STORIES
    // https://api.nytimes.com/svc/topstories/v2/home.json?api-key=4e57ca5e25324139ab64c95a50c25ef8

    // Full API URL for MOST POPULAR
    // https://api.nytimes.com/svc/mostpopular/v2/mostemailed/all-sections/30.json?api-key=4e57ca5e25324139ab64c95a50c25ef8

    // Full API URL for ARTICLE SEARCH
    // https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=4e57ca5e25324139ab64c95a50c25ef8&q=sport


    @GET("svc/topstories/v2/home.json")
        Observable<TopStories> getTopStories(@Query("api-key") String apiKey);

    // TODO MostPopular
    @GET("svc/mostpopular/v2/mostemailed/all-sections/30.json")
        Observable<MostPopular> getMostPopular(@Query("api-key") String apiKey);


    @GET("svc/search/v2/articlesearch.json?api-key=4e57ca5e25324139ab64c95a50c25ef8&q=sports")
        Observable<ArticleSearch> getArticleSearch();

    @GET("svc/search/v2/articlesearch.json")
        Observable<ArticleSearch> getAllSearchedArticles(@Query("q") String query, @Query("api-key") String apiKey);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

}
