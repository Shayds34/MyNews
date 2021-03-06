package com.example.theshayds.mynewstest;

import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.Models.MostPopular;
import com.example.theshayds.mynewstest.Models.TopStories;
import com.example.theshayds.mynewstest.Utils.ApiStreams;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(JUnit4.class)
public class ApiStreamsTest {

    @Test
    public void streamTopStories() {

        // Stream
        Observable<TopStories> mTopStoriesObservable = ApiStreams.streamTopStories();

        // Create Observer
        TestObserver<TopStories> mTestObserver = new TestObserver<>();

        // Launch Observable
        mTopStoriesObservable.subscribeWith(mTestObserver)
                            .assertNoErrors()
                            .assertNoTimeout()
                            .awaitTerminalEvent();

        // Get List
        TopStories mTopStories = mTestObserver.values().get(0);

        // Check if connection status is OK
        assertEquals("OK", mTopStories.getStatus());

        // Check if section is Home
        assertEquals("home", mTopStories.getSection());

        // Check if results exist
        assertNotNull(mTopStories.getResults());
    }


    @Test
    public void streamMostPopular() {
        // Stream
        Observable<MostPopular> mMostPopularObservable = ApiStreams.streamMostPopular();

        // Create Observer
        TestObserver<MostPopular> mTestObserver = new TestObserver<>();

        // Launch Observable
        mMostPopularObservable.subscribeWith(mTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        // Get List
        MostPopular mMostPopular = mTestObserver.values().get(0);

        // Check if connection status is OK
        assertEquals("OK", mMostPopular.getStatus());

        // Check if results exist
        assertNotNull(mMostPopular.getResults());
    }

    @Test
    public void streamArticleSearchArts() {

        // Stream
        Observable<ArticleSearch> mArticleSearchObservable = ApiStreams.streamArticleSearch();

        // Create Observer
        TestObserver<ArticleSearch> mTestObserver = new TestObserver<>();

        // Create Observable
        mArticleSearchObservable.subscribeWith(mTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        // Get List
        ArticleSearch mArticleSearch = mTestObserver.values().get(0);

        // Check if status is OK
        assertEquals("OK", mArticleSearch.getStatus());

        // Check if results exist
        assertNotNull(mArticleSearch.getResponse());
    }

    @Test
    public void streamArticleSearchWithParameters() {

        // Query terms
        String mQuery = "Tesla Motors";
        String mFilterQuery = "Business";

        // Stream
        Observable<ArticleSearch> mArticleSearchObservable = ApiStreams.streamArticlesParameters(mQuery, mFilterQuery);

        // Create Observer
        TestObserver<ArticleSearch> mTestObserver = new TestObserver<>();

        // Create Observable
        mArticleSearchObservable.subscribeWith(mTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        // Get List
        ArticleSearch mArticleSearch = mTestObserver.values().get(0);

        // Check if status is OK
        assertEquals("OK", mArticleSearch.getStatus());

        // Check if results exist
        assertNotNull(mArticleSearch.getResponse());
    }

    @Test
    public void streamArticleSearchWithParametersAndDate() {

        // Query terms
        String mQuery = "Tesla Motors";
        String mFilterQuery = "Business";
        String mBeginDate = "20190722";
        String mEndDate = "20190723";

        // Stream
        Observable<ArticleSearch> mArticleSearchObservable = ApiStreams.streamArticlesWithDate(mQuery, mFilterQuery, mBeginDate, mEndDate);

        // Create Observer
        TestObserver<ArticleSearch> mTestObserver = new TestObserver<>();

        // Create Observable
        mArticleSearchObservable.subscribeWith(mTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        // Get List
        ArticleSearch mArticleSearch = mTestObserver.values().get(0);

        // Check if status is OK
        assertEquals("OK", mArticleSearch.getStatus());

        // Check if results exist
        assertNotNull(mArticleSearch.getResponse());
    }
}