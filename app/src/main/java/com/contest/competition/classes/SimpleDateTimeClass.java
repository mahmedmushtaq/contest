package com.contest.competition.classes;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SimpleDateTimeClass {



    public static String getSimpleTime(){


        Date dNow = new Date( );
       // SimpleDateFormat ft =  new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss a zzz");
        SimpleDateFormat ft =  new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss");
        ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        return ft.format(dNow);
    }

    public static void dateTimeComparison(String serverDateString,String nowDateString){
        Calendar firstDateCalendar = Calendar.getInstance();
        Calendar secondDateCalendar = Calendar.getInstance();
       // firstDateCalendar.setTime(new Date(nowDate));
        try {
            Date serverDate  = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(serverDateString);
            Date nowDate  = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(nowDateString);

            firstDateCalendar.setTime(nowDate);
            secondDateCalendar.setTime(serverDate);

            int year = firstDateCalendar.get(Calendar.YEAR)- secondDateCalendar.get(Calendar.YEAR);
            int month = firstDateCalendar.get(Calendar.MONTH)- secondDateCalendar.get(Calendar.MONTH);
            int day = firstDateCalendar.get(Calendar.DAY_OF_MONTH)-secondDateCalendar.get(Calendar.DAY_OF_MONTH);
            int hour = firstDateCalendar.get(Calendar.HOUR_OF_DAY)-secondDateCalendar.get(Calendar.HOUR_OF_DAY);
            int min = firstDateCalendar.get(Calendar.MINUTE) - secondDateCalendar.get(Calendar.MINUTE);











            Log.e("SimpleDateTime", "dateTimeComparison: days comparison = "+min);


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
