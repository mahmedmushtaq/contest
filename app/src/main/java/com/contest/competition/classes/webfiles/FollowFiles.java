package com.contest.competition.classes.webfiles;

/**
 * Created by M Ahmed Mushtaq on 6/17/2018.
 */

public class FollowFiles {
//    first mistake prevent search from preventing double data in following_req table
//    second after start notFollowReq activity check this data is present in table if present retrieve its id
//    cancel following req by id
//    update following_req accept value with yes by sending particular id

    public static  String followFile = "/utils/data/follow/startFollowing.php";
    public static final String followingUserInfoFile = "/utils/data/follow/getFollowingUsersInfo.php";
    public static final String followerUserInfoFile = "/utils/data/follow/getFollowerUsersInfo.php";
    public static final String acceptFollowReq = "/utils/data/follow/acceptFollowReq.php";
    public static final String retrieveFollowReq = "/utils/data/follow/retrieveFollowReq.php";
    public static final String cancelFollowingFile = "/utils/data/follow/cancelFollowing.php";
    public static final String cancelFollowingReqFile = "/utils/data/follow/cancelFollowReq.php";

}
