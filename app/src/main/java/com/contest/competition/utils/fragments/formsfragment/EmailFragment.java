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

import com.contest.competition.classes.Validator;

/**
 * Created by M Ahmed Mushtaq on 6/15/2018.
 */

public class EmailFragment extends Fragment {

    signUpEmailListener mSignUpEmailListener;

    public interface signUpEmailListener{
        void onWritingEmail(String email);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSignUpEmailListener = (signUpEmailListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_email_layout,container,false);

        EditText email = view.findViewById(R.id.email_sign_up_et);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Validator.isValidEmail(s.toString())) mSignUpEmailListener.onWritingEmail(s.toString());
                else mSignUpEmailListener.onWritingEmail(null);

            }
        });


        return view;
    }



}

