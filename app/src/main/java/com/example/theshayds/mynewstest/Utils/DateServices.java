package com.example.theshayds.mynewstest.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateServices {


    public static String dateFormat(String date){

        // Format Date "yyyy-MM-dd'T'HH:mm:ss"
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

        Date mDate = null;

        try {
            mDate = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(mDate);
    }

    public static String dateFormatBis(String date){

        // Format Date "yyyy-MM-dd"
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Date mDate = null;

        try {
            mDate = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(mDate);

    }
}
