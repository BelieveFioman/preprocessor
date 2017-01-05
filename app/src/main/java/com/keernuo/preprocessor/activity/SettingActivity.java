package com.keernuo.preprocessor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.keernuo.preprocessor.R;
import com.keernuo.preprocessor.consts.GlobalConst;
import com.keernuo.preprocessor.fragment.FragmentAboutUs;
import com.keernuo.preprocessor.fragment.FragmentAlertSetting;
import com.keernuo.preprocessor.fragment.FragmentBasicSetting;
import com.keernuo.preprocessor.fragment.FragmentDevAdjust;
import com.keernuo.preprocessor.fragment.FragmentDevManger;
import com.keernuo.preprocessor.utils.KenUtils;
import com.keernuo.preprocessor.utils.TimeFormat;

public class SettingActivity extends FragmentActivity {
    /**
     * 时间显示控件
     */
    private TextView tvTime ;
    /**
     * 整个界面的view的对象,主要用来隐藏软键盘的
     */
    private FrameLayout frameLayout ;
    /**
     * 返回按钮的监听器
     */
    private ImageButton btnBack;
    /**
     * RdioGroup
     */
    private RadioGroup radioGroup;
    /**
     * 设备校准按钮
     */
    private RadioButton btnDeviceAdjust;
    /**
     * 设备管理按钮
     */
    private RadioButton btnDeviceManager;
    /**
     * 报警设置按钮
     */
    private RadioButton btnAlertSetting;
    /**
     * 基本设置按钮
     */
    private RadioButton btnBasicSetting;
    /**
     * 关于我们的Fragment
     */
    private FragmentAboutUs fragmentAboutUs = new FragmentAboutUs();
    /**
     * 设备校准的Fragment
     */
    private FragmentDevAdjust fragmentDevAdjust = new FragmentDevAdjust();
    /**
     * 设备管理的Fragment
     */
    private FragmentDevManger fragmentDevManger = new FragmentDevManger();
    /**
     * 报警设置的Fragment
     */
    private FragmentAlertSetting fragmentAlertSetting = new FragmentAlertSetting();
    /**
     * 基本设置的Fragemnt
     */
    private FragmentBasicSetting fragmentBasicSetting = new FragmentBasicSetting();

    /**
     * Fragment的管理器
     */
    private FragmentManager fm = getSupportFragmentManager();

    /**
     * Fragemnt操作事务
     */
    private FragmentTransaction transaction;

    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (GlobalConst.ACTION_TIME_UPDATE.equals(intent.getAction())) {
                tvTime.setText(TimeFormat.getNowTime(System.currentTimeMillis()));
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);
        /**
         * 进来先加载基本设置的Fragment
         */
        transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_setting_content, fragmentBasicSetting);
        transaction.commit();
        /**
         * 初始化控件
         */
        initViews();
        /**
         * 配置监听器
         */
        setListeners();
        //注册广播接收器
        registerReceiver(timeReceiver, new IntentFilter(GlobalConst.ACTION_TIME_UPDATE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timeReceiver);
    }

    private void setListeners() {


         //RadioGroup的监听器
        radioGroup.setOnCheckedChangeListener(listener);
        /**
         * 为返回按键配置监听器
         */
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /**
         * 为FrameLayout设置监听器,主要用来隐藏软键盘
         */
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KenUtils.hideSoftKeyboad(SettingActivity.this,v);
            }
        });
    }

    private void initViews() {
        btnBack = (ImageButton) findViewById(R.id.ibtn_back);
        radioGroup = (RadioGroup) findViewById(R.id.radio_settings);
        btnBasicSetting = (RadioButton) findViewById(R.id.btn_basic_setting);
        tvTime = (TextView) findViewById(R.id.tv_time);

        frameLayout = (FrameLayout) findViewById(R.id.fragment_setting_content);
    }

    /**
     * 监听RadioGroup的变化
     */
    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            switch (checkedId) {
                case R.id.btn_basic_setting: //基本设置按钮
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_setting_content, fragmentBasicSetting);
                    transaction.commit();
                    break;
                case R.id.btn_alert_setting://报警设置按钮
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_setting_content, fragmentAlertSetting);
                    transaction.commit();
                    break;
                case R.id.btn_device_manager://设备管理按钮
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_setting_content, fragmentDevManger);
                    transaction.commit();
                    break;
                case R.id.btn_device_adjust:
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_setting_content, fragmentDevAdjust);
                    transaction.commit();
                    break;
                case R.id.btn_about_us:
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_setting_content, fragmentAboutUs);
                    transaction.commit();
                    break;
            }
        }
    };
}