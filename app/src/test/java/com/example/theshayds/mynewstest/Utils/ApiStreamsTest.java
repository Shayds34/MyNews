package com.example.theshayds.mynewstest.Utils;

import com.example.theshayds.mynewstest.Models.ArticleSearch;
import com.example.theshayds.mynewstest.Models.MostPopular;
import com.example.theshayds.mynewstest.Models.TopStories;
import org.junit.Test;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import static org.junit.Assert.*;

public class ApiStreamsTest {

    @Test
    public void streamTopStories() {
        Observable<TopStories> mTopStoriesObservable = ApiStreams.streamTopStories();

        TestObserver<TopStories> mTestObserver = new TestObserver<>();
        mTopStoriesObservable.subscribeWith(mTestObserver)
                            .assertNoErrors()
                            .assertNoTimeout()
                            .awaitTerminalEvent();

        TopStories mTopStories = mTestObserver.values().get(0);

        // TODO : assert
        //assertTrue(mTopStories.getStatus().equals("OK"));
    }


    @Test
    public void streamMostPopular() {
        Observable<MostPopular> mMostPopularObservable = ApiStreams.streamMostPopular();

        TestObserver<MostPopular> mTestObserver = new TestObserver<>();
        mMostPopularObservable.subscribeWith(mTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        MostPopular mMostPopular = mTestObserver.values().get(0);

        // TODO : assert
    }

    @Test
    public void streamArticleSearch() {
        Observable<ArticleSearch> mArticleSearchObservable = ApiStreams.streamArticleSearch();

        TestObserver<ArticleSearch> mTestObserver = new TestObserver<>();
        mArticleSearchObservable.subscribeWith(mTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        ArticleSearch mArticleSearch = mTestObserver.values().get(0);

        // TODO : assert
    }
}