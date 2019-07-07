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

    @Test
    public void dateFormatTer(){

        String mTestDate = "07/07/2019";
        String expectedDate = "20190707";

        assertEquals ( expectedDate, DateServices.dateFormatTer(mTestDate));
    }

    @Test
    public void addDigitToDate(){

        int mTestDay = 3;
        String expectedDay = "03";
        assertEquals( expectedDay, DateServices.addDigitToDate(mTestDay));

        int mTestMonth = 6;
        String expectedMonth = "06";
        assertEquals(expectedMonth, DateServices.addDigitToDate(mTestMonth));
    }
}