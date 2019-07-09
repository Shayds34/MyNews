package com.example.theshayds.mynewstest.Utils;

import com.example.theshayds.mynewstest.Models.NYTimesNews;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NYTimesNewsTest {


    @Test
    public void testSetAndGet(){
        String title = "Maya Angelou, Re-imagined Though Art";
        String section = "Arts";
        String url = "https://newarticles.com";
        String publishedDate = "2019-07-08";
        String imageUrl = "https://newarticles.com/image";

        NYTimesNews myNews = new NYTimesNews();

        // Set
        myNews.setTitle(title);
        myNews.setSection(section);
        myNews.setUrl(url);
        myNews.setPublishedDate(publishedDate);
        myNews.setImageURL(imageUrl);

        // Get
        String expectedTitle = myNews.getTitle();
        String expectedSection = myNews.getSection();
        String expectedUrl = myNews.getUrl();
        String expectedPublishedDate = myNews.getPublishedDate();
        String expectedImageUrl = myNews.getImageURL();

        // Test Setters & Getters
        assertEquals(expectedTitle, title);
        assertEquals(expectedSection, section);
        assertEquals(expectedUrl, url);
        assertEquals(expectedPublishedDate, publishedDate);
        assertEquals(expectedImageUrl, imageUrl);
    }
}
