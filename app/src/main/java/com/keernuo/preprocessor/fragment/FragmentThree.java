package com.keernuo.preprocessor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keernuo.preprocessor.R;


/**
 * Created by Fioman on 2016/12/9 0009.
 * Description: 三通道的Fragment
 */
public class FragmentThree extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, null);
        return view;
    }
}
