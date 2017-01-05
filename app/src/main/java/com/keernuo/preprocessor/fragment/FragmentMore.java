package com.keernuo.preprocessor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.keernuo.preprocessor.R;
import com.keernuo.preprocessor.adapter.Myadapter;


/**
 * Created by Fioman on 2016/12/8 0008.
 * Description: 多信号通道的Fragment
 */

public class FragmentMore extends Fragment {
    /**
     * GridView控件
     */
    private GridView gridView;
    /**
     * Adapter
     */
    private Myadapter adapter;

    /**
     * 模拟数据
     */
    private double[] data = {20.950,20.950,20.950,20.950,20.950,20.950,20.950,20.950};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, null);
        adapter = new Myadapter(getActivity(), data);
        gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        return view;
    }
}
