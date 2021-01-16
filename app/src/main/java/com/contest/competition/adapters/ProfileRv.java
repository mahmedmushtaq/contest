package com.contest.competition.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.contest.competition.adapters.adapterdataclasses.ContestRvData;
import com.contest.competition.adapters.adapterdataclasses.SinglePostsRvData;
import com.contest.competition.adapters.holders.ContestDataRvHolder;
import com.contest.competition.adapters.holders.SimpleTvRvHolder;
import com.contest.competition.adapters.holders.SinglePostRvHolder;
import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.interfaces.HomeRvListener;
import com.contest.competition.classes.interfaces.ProfileRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.ContestData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.models.ProfileHeaderData;
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.classes.models.SimpleTvData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.views.FollowBtnStyle;
import com.contest.competition.utils.views.Menu;

import com.contest.competition.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.List;

import com.contest.competition.utils.views.PostView;

/**
 * Created by M Ahmed Mushtaq on 6/20/2018.
 */

public class ProfileRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int HEADER = 0;
    private int BODY = 1;
    private Context mContext;
    private ProfileRvListener mProfileListener;
    private LoginSharedPrefer mPrefer;
    private HomeRvListener mHomeRvListener;

    private ArrayHolder mArrayHolder;
    private int mCheck;


    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }


    public void setCheck(int check){
        mCheck = check;
    }





    public void addProfileListener(ProfileRvListener addProfileListener){
        this.mProfileListener = addProfileListener;
    }

    public void setHomeRvListener(HomeRvListener listener){
        mHomeRvListener = listener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if(viewType == HEADER){
           // return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_rv_header,parent,false));
            return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_rv_header,parent,false));
        }
        else if(viewType == PostView.SHOW_CONTEST){
            //return new ContestDataRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv_contest_layout,parent,false));
            return new ContestDataRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_layout,parent,false));
        }

        else if(viewType == PostView.SHOW_SIMPLE_TV ){
            return  new SimpleTvRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_rv_layout,parent,false));
            //return  new SimpleTvRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_rv_layout,parent,false));

        }else if(viewType == PostView.SHOW_SIMPLE_POST){
          //  return new PostBodyRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv_post,parent,false));
            return new SinglePostRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_rv_layout,parent,false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int regionalPositionIncludeHeader) {
        int bodyItemPosition = regionalPositionIncludeHeader-1;
        mPrefer = new LoginSharedPrefer(mContext);

        if(bodyItemPosition > -1){
            if(mCheck == KeyStore.MY_OWN_PROFILE) {
                if (mArrayHolder.getOwnProfileData().size() > 0) {

                    if (bodyItemPosition == mArrayHolder.getOwnProfileData().size() - 1) {
                        //reaching bottom
                        if (mHomeRvListener != null)
                            mHomeRvListener.onReachingBottom(regionalPositionIncludeHeader);
                    }
                }
            }else{
                if (mArrayHolder.getOtherUserProfileData().size() > 0) {

                    if (bodyItemPosition == mArrayHolder.getOtherUserProfileData().size() - 1) {
                        //reaching bottom
                        if (mHomeRvListener != null)
                            mHomeRvListener.onReachingBottom(regionalPositionIncludeHeader);
                    }
                }
            }
        }





        if(holder instanceof HeaderHolder){
            HeaderHolder holder1 = (HeaderHolder) holder;
                headerData(holder1,regionalPositionIncludeHeader);


        }else if(holder instanceof ContestDataRvHolder){
            //=========================================================
//          ============================ BODY  ========================
            //=========================================================

          //  final BodyContestHolder body = (BodyContestHolder) holder;
           putContestData(holder,bodyItemPosition,regionalPositionIncludeHeader);




        }else if(holder instanceof SimpleTvRvHolder){
            putSimpleTv(holder,bodyItemPosition);
            //show simple text here
        }else if(holder instanceof SinglePostRvHolder){
            //show posts
            putPostData(holder,bodyItemPosition,regionalPositionIncludeHeader);
        }

    }



    @Override
    public int getItemCount() {
        if(mCheck == KeyStore.MY_OWN_PROFILE)
     return mArrayHolder.getOwnProfileData().size()+1;
        else return mArrayHolder.getOtherUserProfileData().size()+1;

    }

    @Override
    public int getItemViewType(int position) {
         if(position == 0){
             return HEADER;
         }else {
             PostData data;
             if(mCheck == KeyStore.MY_OWN_PROFILE)
             data = mArrayHolder.getOwnProfileData().get(position-1);
             else   data = mArrayHolder.getOtherUserProfileData().get(position-1);
             if(data instanceof ContestData)
                 return PostView.SHOW_CONTEST;
             else if(data instanceof SimplePostData)
                 return PostView.SHOW_SIMPLE_POST;
             else if(data instanceof SimpleTvData)
                 return PostView.SHOW_SIMPLE_TV;

         }

        return super.getItemViewType(position);
    }








    private void setOtherUserMenu(final HeaderHolder holder, final ProfileHeaderData data){
        Menu.setMenuClickListener(new Menu.menuClickListener() {
            @Override
            public void onMenuClick(int position, PowerMenuItem item) {
              if(position == 0) mProfileListener.viewProfile(holder.profilePic,data);
            }
        });

        List<PowerMenuItem> items = new ArrayList<>();

        items.add(new PowerMenuItem("View profile",false));

        Menu.createMenu(mContext,items,holder.profilePic,Menu.SHOWN_AS_DROP_DOWN);



    }
    private void setProfileMenu(final HeaderHolder holder, final ProfileHeaderData data){

        Menu.setMenuClickListener(new Menu.menuClickListener() {
            @Override
            public void onMenuClick(int position, PowerMenuItem item) {
                if(position == 0) mProfileListener.updateProfile(holder.profilePic,holder.pb,data);
                else if(position == 1) mProfileListener.viewProfile(holder.profilePic,data);
            }
        });

        List<PowerMenuItem> items = new ArrayList<>();
        items.add(new PowerMenuItem("Update profile",false));
        items.add(new PowerMenuItem("View profile",false));

        Menu.createMenu(mContext,items,holder.profilePic,Menu.SHOWN_AS_DROP_DOWN);







    }








    private void headerData(final HeaderHolder holder1, int position){

        if(mArrayHolder.getProfileHeaderData().size() > 0) {
            final ProfileHeaderData data = mArrayHolder.getProfileHeaderData().get(position);
            holder1.pb.setVisibility(View.GONE);


            final LoginSharedPrefer prefer = new LoginSharedPrefer(mContext);



            Glide.with(mContext).load(Addresses.getWebAddress() + data.getProfilePic()).into(holder1.profilePic);
            holder1.name.setText(HighLighter.capitalEachWordFirstLetter(data.getName()));
            holder1.username.setText(data.getUsername());

           // Drawable drawable = mContext.getResources().getDrawable(R.drawable.accept);
            // drawable.setColorFilter(mContext.getResources().getColor(R.color.peter), PorterDuff.Mode.MULTIPLY );


            if(data.getUsername().equals(prefer.getUsername())) {
                holder1.edit.setVisibility(View.VISIBLE);
                holder1.creditsLl.setVisibility(View.VISIBLE);

            } else {
                holder1.edit.setVisibility(View.GONE);
                holder1.creditsLl.setVisibility(View.GONE);
            }





            holder1.profilePic.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(data.getUsername().equals(prefer.getUsername())) {

                        setProfileMenu(holder1, data);
                    }
                    else {
                        holder1.edit.setVisibility(View.GONE);
                        setOtherUserMenu(holder1,data);
                    }
                    return true;
                }
            });


            holder1.profilePicEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.getUsername().equals(prefer.getUsername())){
                        setProfileMenu(holder1,data);
                    }else{
                        holder1.edit.setVisibility(View.GONE);
                        setOtherUserMenu(holder1,data);
                    }

                }
            });


            holder1.backActvityIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mProfileListener.onClickBackIcon();
                }
            });


//            if(data.getUsername().equals(mPrefer.getUsername())){
//                holder1.boostedFollowersBtn.setVisibility(View.VISIBLE);
//            }else{
//                holder1.boostedFollowersBtn.setVisibility(View.GONE);
//            }

//            holder1.boostedFollowersBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(mProfileListener != null)
//                        mProfileListener.onClickBoostFollowers(data);
//                }
//            });





            holder1.noOfPosts.setText(data.getNoOfPosts());
            holder1.noOfCredits.setText(data.getNoOfCredits());
            holder1.noOfFollower.setText(data.getNoOfFollowerUsers());
            holder1.noOfFollowing.setText(data.getNoOfFollowingUsers());
            holder1.noOfCompetitionLoose.setText(data.getNoOfContestLose());//noofcompetition remaining
            holder1.noOfCompetitionWin.setText(data.getNoOfContestWin());//noofcompetition remaining

            holder1.creditsLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mProfileListener!= null)
                        mProfileListener.onClickCredits();
                }
            });

            holder1.noOfFollowingll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProfileListener.viewFollowingsUsers();
                }
            });



            holder1.noOfFollowerll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProfileListener.viewFollowers();
                }
            });

            holder1.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mProfileListener.onClickEditIcon();
                }
            });




            if(data.getFollowing() != null) {
                 holder1.creditsLl.setVisibility(View.GONE);
                 holder1.otherUserProfileLl.setVisibility(View.VISIBLE);

                if (data.getFollowing().contains("yes")) {
                    // holder1.followBtn.setIconResource(mContext.getResources().getDrawable(R.drawable.accept));
                  //
                  //  holder1.acceptBtn.setVisibility(View.VISIBLE);
                   // holder1.waitFollowRequestBtn.setVisibility(View.GONE);
                    holder1.followBtn.setVisibility(View.VISIBLE);
                    FollowBtnStyle.setFollowedBtn(holder1.followBtn,mContext);

//                    holder1.followBtn.setText("Followed");
//                    holder1.followBtn.setBackgroundColor(mContext.getResources().getColor(R.color.peter));
//                    holder1.followBtn.setBackgroundColor(mContext.getResources().getColor(R.color.peter));



                } else if (data.getFollowing().contains("no")) {
                    holder1.followBtn.setVisibility(View.VISIBLE);
                   // holder1.acceptBtn.setVisibility(View.GONE);
                   // holder1.waitFollowRequestBtn.setVisibility(View.GONE);
                 //   holder1.followBtn.setText("Follow");
                    FollowBtnStyle.setFollowBtn(holder1.followBtn,mContext);
                }else if(data.getFollowing().contains("wait")){
                 //   Log.e(KeyStore.TAG, "onBindViewHolder: works");
                    holder1.followBtn.setVisibility(View.VISIBLE);
                   // holder1.acceptBtn.setVisibility(View.GONE);
                   // holder1.waitFollowRequestBtn.setVisibility(View.VISIBLE);
                //    holder1.followBtn.setText("Request");
                    FollowBtnStyle.setRequestedBtn(holder1.followBtn,mContext);
                }

            }

//            if(holder1.followBtn.getVisibility() == View.VISIBLE){
//                holder1.creditsLl.setVisibility(View.GONE);
//            }else {
//                holder1.creditsLl.setVisibility(View.VISIBLE);
//            }


//            holder1.sendMessageBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(mProfileListener != null)
//                        mProfileListener.onClickSendMessage(data);
//                }
//            });


                holder1.followBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   mProfileListener.startFollowing(holder1.followBtn, holder1.acceptBtn, holder1.waitFollowRequestBtn);
                        if(holder1.followBtn.getText().equals("Follow")) {
                            mProfileListener.startFollowing(holder1.followBtn);
                        } else if(holder1.followBtn.getText().equals("Following")) {
                            mProfileListener.cancelFollowing(holder1.followBtn);
                        }else if(holder1.followBtn.getText().equals("Requested") ){
                            mProfileListener.cancelFollowRequest(holder1.followBtn);
                        }
                    }
                });












//            else{
//                holder1.followBtn.setVisibility(View.GONE);
//                holder1.acceptBtn.setVisibility(View.GONE);
//            }

        }
    }


    private class HeaderHolder extends RecyclerView.ViewHolder {
        CircularImageView profilePic;
        TextView username,creditsText,name,noOfCompetitionWin,noOfCompetitionLoose,noOfCredits,noOfPosts,noOfFollowing,noOfFollower;
        LinearLayout creditsLl,noOfFollowingll,noOfFollowerll,otherUserProfileLl;
        Button followBtn;
        ProgressBar pb;
        ImageView backActvityIcon,edit,profilePicEdit;
        //Button boostedFollowersBtn;
        public HeaderHolder(View v) {
            super(v);

            profilePic = v.findViewById(R.id.pF_userProfile);
            pb = v.findViewById(R.id.pFheader_pB);
            username = v.findViewById(R.id.pf_headerUsername);
            noOfCompetitionWin = v.findViewById(R.id.no_of_competition_win_tv);
            noOfCompetitionLoose = v.findViewById(R.id.no_of_competition_loose_tv);
            noOfCredits = v.findViewById(R.id.no_of_credits);
            noOfPosts = v.findViewById(R.id.no_of_posts_pfHeader);
            noOfFollowing = v.findViewById(R.id.no_of_following_pfHeader);
            noOfFollower = v.findViewById(R.id.no_of_follower_pfHeader);
            creditsText = v.findViewById(R.id.credits_text);

            followBtn = v.findViewById(R.id.follow_btn_pfHeader);
            creditsLl = v.findViewById(R.id.credit_ll);
            noOfFollowerll = v.findViewById(R.id.no_of_follower_pfHeader_ll);
            noOfFollowingll = v.findViewById(R.id.no_of_following_pfHeader_ll);
            otherUserProfileLl = v.findViewById(R.id.profile_rv_header_other_user_profile_ll);

            //sendMessageBtn = v.findViewById(R.id.profile_rv_header_send_message);
            name = v.findViewById(R.id.pf_name);
            edit = v.findViewById(R.id.profile_activityEditPfHeader);
            backActvityIcon = v.findViewById(R.id.backActivity_pf);
            profilePicEdit = v.findViewById(R.id.header_editProfilePic);
            //boostedFollowersBtn = v.findViewById(R.id.boost_followersHeader);
          //  creditsLl.setVisibility(View.VISIBLE);


        }


    }



    private void putSimpleTv(RecyclerView.ViewHolder holder, int position) {
        SimpleTvRvHolder body = (SimpleTvRvHolder) holder;
        PostData postData ;
        if(mCheck == KeyStore.MY_OWN_PROFILE)
         postData = mArrayHolder.getOwnProfileData().get(position);
        else postData = mArrayHolder.getOtherUserProfileData().get(position);
        SimpleTvData data = (SimpleTvData) postData;
        body.simpleTv.setText(data.getSimpleTv());

    }







//    put data

    private void putContestData(RecyclerView.ViewHolder holder, final int bodyItemPosition, final int position){
        //PutContestData.putContestData((ContestDataRvHolder) holder,position,mHomeRvListener,mContext,mPrefer.getUsername());


        //ContestRvData.putContestData(holder,position,mHomeRvListener,mContext,mPrefer.getUsername(), SinglePostsRvData.PROFILE_RV);
        ContestRvData.setProfileCheck(mCheck);
        ContestRvData.setArrayHolder(mArrayHolder);
        ContestRvData.putContestData(holder,position,mHomeRvListener,mContext,mPrefer.getUsername(), SinglePostsRvData.PROFILE_RV);

    }

    private void putPostData(RecyclerView.ViewHolder holder, final int bodyItemPosition, final int position){
     // SimplePostRvData.putPostData(holder,position,mHomeRvListener,mContext,SimplePostRvData.PROFILE_RV);
        SinglePostsRvData.setProfileCheck(mCheck);
        SinglePostsRvData.setArrayHolder(mArrayHolder);
        SinglePostsRvData.putPostData(holder,position,mHomeRvListener,mContext,SinglePostsRvData.PROFILE_RV);
    }


}
