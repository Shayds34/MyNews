package com.example.theshayds.mynewstest.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateServices {

    public static final String TAG = "DateServices";

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

    public static String dateFormatTer(String date){

        // Format Date "yyyyMMdd"
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);

        Date mDate = null;

        try {
            mDate = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(mDate);
    }

    public static String addDigitToDate(int dayOrMonth){
        String result;

        // Try if day or month is < to 10, then add "0" in front of the single digit day or month.
        if(dayOrMonth < 10){
            result = "0" + dayOrMonth;
        } else {
            result = String.valueOf(dayOrMonth);
        }

        return result;
    }
}
