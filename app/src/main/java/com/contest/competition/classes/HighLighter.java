package com.contest.competition.classes;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.contest.competition.R;


/**
 * Created by M Ahmed Mushtaq on 6/19/2018.
 */

public class HighLighter {

    public static Spannable threeThingsSpanner(String message,String name,String time, Context context){
        Spannable spannable = new SpannableString(name+" "+message+"   "+time+"   ");




        String afterMessage = name+"  "+message;
        String lastPortion = name+" "+message+" "+time+"   ";

        spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.black)), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.peter)), afterMessage.length(),lastPortion.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }




    public static Spannable setTotalVotesAndCommentsAndTotalSeen(String totalVotes, String andString, String totalComments,String totalSeen, Context context){
        Spannable spannable = new SpannableString(totalVotes+" "+andString+" "+totalComments+" "+andString+" "+totalSeen+" ");



        String afterMessage = totalVotes+" "+andString;
        String midPortion = totalVotes+" "+andString+" "+totalComments;



        spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.peter)), 0, totalVotes.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lightRed)), afterMessage.length(),midPortion.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

       return spannable;

    }



    public static String capitalFirstLetter(String text){
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public static String capitalEachWordFirstLetter(String text){
       String[] allCharacters = text.split(" ");
        StringBuilder capitalFirstLetterString = new StringBuilder();
       for(String s : allCharacters){
           if(s.length() > 0) {
               capitalFirstLetterString.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(" ");
           }
       }
       return capitalFirstLetterString.toString();
    }





}
