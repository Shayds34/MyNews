package com.example.theshayds.mynewstest;

import com.example.theshayds.mynewstest.Models.TopStories;
import com.example.theshayds.mynewstest.Utils.ApiStreams;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObserverTest {

    private void baseForTestRequestNews(Observable<TopStories> observable){
        //Create observer
        TestObserver<TopStories> testObserver = new TestObserver<>();

        //subscribe to the observable
        observable.subscribeWith(testObserver).assertNoErrors().assertNoTimeout().awaitTerminalEvent();

        //get response
        TopStories topStories = testObserver.values().get(0);

        //test response
        assertEquals("OK", topStories.getStatus());
    }


    @Test
    public void testGetTopStoriesArticles(){
        this.baseForTestRequestNews(ApiStreams.streamTopStories());
    }
}
