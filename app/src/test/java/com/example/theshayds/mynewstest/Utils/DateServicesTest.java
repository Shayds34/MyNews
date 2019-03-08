package com.example.theshayds.mynewstest.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateServicesTest {

    @Test
    public void dateFormat() {

        String mTestDate = "2019-03-08T11:52:52";
        String expectedDate = "08/03/2019";

        assertEquals(expectedDate, DateServices.dateFormat(mTestDate));
    }

    @Test
    public void dateFormatBis() {

        String mTestDate = "2019-03-08";
        String expectedDate = "08/03/2019";

        assertEquals( expectedDate, DateServices.dateFormatBis(mTestDate));
    }
}