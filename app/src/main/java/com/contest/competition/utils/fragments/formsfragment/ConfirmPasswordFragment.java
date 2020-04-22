package com.contest.competition.utils.fragments.formsfragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.contest.competition.R;

/**
 * Created by M Ahmed Mushtaq on 6/15/2018.
 */

public class ConfirmPasswordFragment extends Fragment {

    signUpConfirmPasswordListener mSignUpConfirmPasswordListener;

    public interface signUpConfirmPasswordListener{
        void onWritingConfirmPassword(String cnPassword);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSignUpConfirmPasswordListener = (signUpConfirmPasswordListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_confirm_password_layout,container,false);

        EditText cnPassword = view.findViewById(R.id.cnPassword_sign_up_et);
        cnPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >= 4 || s.length() <= 20) mSignUpConfirmPasswordListener.onWritingConfirmPassword(s.toString());
                else mSignUpConfirmPasswordListener.onWritingConfirmPassword(null);
            }
        });


        return view;
    }


}