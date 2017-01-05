package com.keernuo.preprocessor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keernuo.preprocessor.R;


/**
 * Created by Fioman on 2016/12/13 0013.
 * Description:设备管理的Fragment
 */
public class FragmentDevManger extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dev_manager, null);
        return view;
    }
}
