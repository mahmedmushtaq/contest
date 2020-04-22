package com.contest.competition.classes;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by M Ahmed Mushtaq on 6/15/2018.
 */

public class Validator {

    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    public static boolean isValidEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean isValidName(String name){
        return name.length() > 5 && name.length() < 20;
    }


    public static boolean isValidPassword(String password){
      return password.length() >= 4 && password.length() <= 20;
    }

    public static boolean isValidUsername(String username){
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static String usernameLengthChecker(String username){
        if(username.length() > 15)
            return username.substring(0,15)+"..";
        else return username;
    }

    public static boolean validateWebResult(String result){
        return result.contains("successful");
    }


    public static boolean isLinkUserId(String link){
        return link.contains("userId");
    }

    public static boolean isLinkUserFollowReq(String link){
        return link.contains("followId");
    }

    public static boolean isLinkContestReq(String link) { return link.contains("contestReqId");}

    public static boolean isLinkContestReqDeleted(String link) { return link.contains("contestReqDeletedId");}

    public static boolean isLinkToContest(String  link){return link.contains("contestId");}


    public static boolean isLinkToSinglePost(String link){return link.contains("singlePostId");}



}
