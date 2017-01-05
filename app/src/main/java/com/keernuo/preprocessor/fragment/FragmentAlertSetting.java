package com.keernuo.preprocessor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.keernuo.preprocessor.R;
import com.keernuo.preprocessor.adapter.AlertSettingAdapter;
import com.keernuo.preprocessor.entity.DeviceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fioman on 2016/12/13 0013.
 * Description:报警设置的Fragment
 */
public class FragmentAlertSetting extends Fragment {

    /**
     * 用于统计总共多少页
     */
    public static int pageTotal;
    /**
     * 用于一个页面显示多少个Item
     */
    public static final int VIEW_COUNT = 8;
    /**
     * 用于显示页号的索引
     */
    public static int index = 0;
    /**
     * 上一页和下一页的两个Button
     */
    private Button btnListPre;
    private Button btnListNext;
    /**
     * 页数显示
     */
    private TextView tvPage;
    /**
     * 数据源
     */
    private List<DeviceData> devDatas = new ArrayList<>();
    /**
     * Adapter
     */
    private AlertSettingAdapter adapter;
    /**
     * ListView
     */
    private ListView lsvAlertSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_setting, null);
        //获取数据源,暂时是模拟的
        getDeviceData();
        //先将indext重置为0,每次跳转到这个界面的时候显示的都是第一页的数据.
        index  = 0;
        //根据获取的数据计算页数
        pageTotal =(int) Math.ceil((float)devDatas.size()/(float) VIEW_COUNT);
        //控件初始化
        btnListPre = (Button) view.findViewById(R.id.btn_list_pre);
        btnListNext = (Button) view.findViewById(R.id.btn_list_next);
        lsvAlertSetting = (ListView) view.findViewById(R.id.lsv_alert_setting);
        tvPage = (TextView) view.findViewById(R.id.list_page);
        tvPage.setText(1+"/"+pageTotal);
        //为ListView配置Adapter
        adapter = new AlertSettingAdapter(getActivity(), devDatas);
        lsvAlertSetting.setAdapter(adapter);
        //配置监听器
        setListeners();

        return view;
    }

    private void setListeners() {
        //先检查连个Button是否可用
        checkButton();
        //点击上一页
        btnListPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //向前翻页
                leftView();
                tvPage.setText(index+1+"/"+pageTotal);
            }
        });
        //点击下一页
        btnListNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //向后面翻页
                rightView();
                tvPage.setText(index+1+"/"+pageTotal);
            }
        });

    }

    private void checkButton() {
        //如果Index小于等于0,表示不能向前翻页了,将其设置我不可用,并背景设置为灰色
        if (index <= 0) {
            btnListPre.setEnabled(false);
            btnListPre.setBackgroundResource(R.drawable.image_page_disable);
            if (pageTotal <= 1) {
                btnListNext.setEnabled(false);
                btnListNext.setBackgroundResource(R.drawable.image_page_disable);
            } else {
                btnListNext.setEnabled(true);
                btnListNext.setBackgroundResource(R.drawable.image_page_enable);
            }
        } else if (devDatas.size() - index * VIEW_COUNT <= VIEW_COUNT) {
            btnListNext.setEnabled(false);
            btnListNext.setBackgroundResource(R.drawable.image_page_disable);
            btnListPre.setEnabled(true);
            btnListPre.setBackgroundResource(R.drawable.image_page_enable);
        } else { //否则就讲两个Button都设置为可以点击的
            btnListNext.setEnabled(true);
            btnListPre.setEnabled(true);
            btnListNext.setBackgroundResource(R.drawable.image_page_enable);
            btnListPre.setBackgroundResource(R.drawable.image_page_enable);
        }
    }

    private void leftView() {
        index--;
        //刷新ListView里面的值
        adapter.notifyDataSetChanged();
        //检查Button是否可用
        checkButton();
    }

    private void rightView() {
        index++;
        //刷新ListView里面的值
        adapter.notifyDataSetChanged();
        //检查Button是否可用
        checkButton();
    }

    /**
     * 获取设备的相关值
     */
    private void getDeviceData() {
        DeviceData data = new DeviceData();
        data.setDeviceAddress(8001);
        data.setSensorName("EX");
        data.setOneAlert("AL:10.00");
        data.setTwoAlert("AH:20.00");
        data.setThreeAlert("AH:50.00");
        data.setMeasureUnit("PPM");
        data.setAlertOut("OUT1");
        for (int i = 0; i < 6; i++) {
            devDatas.add(data);
        }
    }
}
