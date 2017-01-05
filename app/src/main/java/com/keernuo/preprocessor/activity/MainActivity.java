package com.keernuo.preprocessor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.keernuo.dn9000.mode.jni.ModbusJni;
import com.keernuo.preprocessor.entity.Sensor;
import com.keernuo.preprocessor.service.DateService;
import com.keernuo.preprocessor.R;
import com.keernuo.preprocessor.app.KenApplication;
import com.keernuo.preprocessor.consts.GlobalConst;
import com.keernuo.preprocessor.entity.KenDevice;
import com.keernuo.preprocessor.fragment.FragmentEmpty;
import com.keernuo.preprocessor.fragment.FragmentMore;
import com.keernuo.preprocessor.fragment.FragmentOne;
import com.keernuo.preprocessor.fragment.FragmentThree;
import com.keernuo.preprocessor.fragment.FragmentTwo;
import com.keernuo.preprocessor.manager.CmdManager;
import com.keernuo.preprocessor.manager.ScanChannel;
import com.keernuo.preprocessor.utils.TimeFormat;
import com.keernuo.preprocessor.view.WaitDialog;

import java.util.List;

public class MainActivity extends FragmentActivity {
    /**
     * 气泵图标
     */
    private ImageView ivAirPubm;
    /**
     * 换向阀图标
     */
    private ImageView ivPointValave;
    /**
     * 电磁阀图标
     */
    private ImageView ivMegneticValave;
    /**
     * 吹扫阀图标
     */
    private ImageView ivBlowValave;
    /**
     * 25度恒温器图标
     */
    private ImageView ivThermostat_25;
    /**
     * 120度恒温器图标
     */
    private ImageView ivThermostat_120;
    /**
     * 气泵图标(显示在底部栏)
     */
    private ImageView ivAirPubmShow;
    /**
     * 换向阀图标(显示在底部栏)
     */
    private ImageView ivPointValaveShow;
    /**
     * 电磁阀图标(显示在底部栏)
     */
    private ImageView ivMegneticValaveShow;
    /**
     * 吹扫阀图标(显示在底部栏)
     */
    private ImageView ivBlowValaveShow;
    /**
     * 25度恒温器图标(显示在底部栏)
     */
    private ImageView ivThermostat_25Show;
    /**
     * 120度恒温器图标(显示在底部栏)
     */
    private ImageView ivThermostat_120Show;
    /**
     * 气泵的状态,默认是不工作的状态
     */
    private boolean airPumbState = false;
    /**
     * 换向阀的状态
     */
    private boolean pointValaveState = false;
    /**
     * 电磁阀的状态
     */
    private boolean megneticValaveState = false;
    /**
     * 吹扫阀的状态
     */
    private boolean blowValaveState = false;
    /**
     * 25度恒温器的状态
     */
    private boolean thermostat_25_state = false;
    /**
     * 120度恒温器的状态
     */
    private boolean thermostat_120_state = false;
    /**
     * 系统的app
     */
    private KenApplication kenApp = KenApplication.getInstance();

    /**
     * 没有扫描到设备的时候显示的Fragment
     */
    private Fragment fragmentEmpty;

    /**
     * 单通道的Fragment
     */
    private Fragment fragmentOne;
    /**
     * 2个通道的Fragment
     */
    private Fragment fragmentTwo;
    /**
     * 3个通道的Fragment
     */
    private Fragment fragmentThree;
    /**
     * 4个通道的Fragment
     */
    private Fragment fragmentFour;
    /**
     * 5个以及5个以上的fragment
     */
    private Fragment fragmentMore;
    /**
     * Fragment的管理器
     */
    private FragmentManager fm = getSupportFragmentManager();
    /**
     * modbusJni
     */
    private ModbusJni modbusJni = new ModbusJni();
    /**
     * 系统设置按钮
     */
    private Button btnSettings;
    /**
     * 利用BroadcastReceiver来实时更新界面上的时间
     */
    private BroadcastReceiver dateRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (GlobalConst.ACTION_TIME_UPDATE.equals(intent.getAction()) ) {
                tvDate.setText(TimeFormat.getNowDateAndTime(System.currentTimeMillis()));
            }
        }
    };

    /**
     * 用于接收广播的IntentFilter
     */
    private IntentFilter intentFilter = new IntentFilter(GlobalConst.ACTION_TIME_UPDATE);
    /**
     * 用于处理线程消息的Hander
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GlobalConst.HANDLER_MESSAGE_UPDATE_DATA:
                    String time = TimeFormat.getNowTime(System.currentTimeMillis());
                    tvDate.setText(time);
                    break;
            }
        }
    };
    /**
     * 显示时间的控件
     */
    private TextView tvDate;
    /**
     * Fragment的事务管理类
     */
    private FragmentTransaction transaction;
    /**
     * 启动时间更新服务的意图
     */
    private Intent timeService = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏,并进行全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        //初始化控件
        initViews();
        //配置监听器
        setListeners();
        //注册广播
        registerReceiver(dateRefreshReceiver, intentFilter);
       //启动服务,时间改变后就发送广播,通知UI层修改时间
        startDateService();
        //启动一个对话框,告诉用户正在扫描设备
        dialogShow();
    }

    private void startDateService() {
        timeService = new Intent(this, DateService.class);
        this.startService(timeService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dateRefreshReceiver);
        stopService(timeService);
    }

    private void scanDevice() {
        //先去扫描设备,获取连接上的传感器的数量
        ScanChannel.getInstance().scan(modbusJni);
        System.out.println("kenApp.sensors的size:->" + ScanChannel.kenDevices.size());

        for (KenDevice kenDevice : ScanChannel.kenDevices) {
            System.out.println("sensor里面的值是多少->" + kenDevice.getChannelAccount() + kenDevice.getSensorList() + kenDevice.getSite());
            List<Sensor> list = CmdManager.getChannels(modbusJni, kenDevice);
            kenApp.sensors.addAll(list);
        }
        if (kenApp.sensors.size()!=0) {
            System.out.println("kenApp.channels的size:->" + kenApp.sensors.size() + kenApp.sensors.get(0).toString());
        }
    }

    /**
     * 显示等待扫描对话框
     */
    private void dialogShow() {
        WaitDialog dialog = new WaitDialog(this).showDialog("设备扫描中....")
                .setDoInBackground(new WaitDialog.backTask() {
                    @Override
                    public boolean doInBackground() {
                        scanDevice();
                        return true;
                    }

                    @Override
                    public void taskEnd(boolean ret) {
                        //根据设备的连接的个数,显示相应的Fragment,一共要显示5种.分别是单通道,两个通道,三通道,四通道和多通道
                        dataDisplay();
                    }
                });
    }

    private void dataDisplay() {
        switch (kenApp.sensors.size()) {
            case 0:
                fragmentEmpty = new FragmentEmpty();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_content, fragmentEmpty);
                transaction.commit();
                break;
            case 1:
                fragmentOne = new FragmentOne();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_content, fragmentOne);
                transaction.commit();
                break;
            case 2:
                fragmentTwo = new FragmentTwo();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_content, fragmentTwo);
                transaction.commit();
                break;
            case 3:
                fragmentThree = new FragmentThree();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_content, fragmentThree);
                transaction.commit();
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                fragmentMore = new FragmentMore();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_content, fragmentMore);
                transaction.commit();
                break;
        }
    }

    private void setListeners() {
        /**
         *点击气泵之后的监听器,气泵的继电器编号是3,对应到板子的编号是NO 0;
         */
        ivAirPubm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (airPumbState) {
                    airPumbState = false; //如果点击的时候,气泵是打开状态.就关闭对应的继电器.并且将其状态改为false
                    //调用底层代码关闭继电器,气泵对应的继电器现在设置为3
                    modbusJni.led_set(3,0);
                    ivAirPubmShow.setVisibility(View.INVISIBLE); //让其设置是不可见的
                } else {
                    airPumbState = true; //如果点击的时候,气泵是关闭状态.就打开对应的继电器,并且讲其状态改为true
                    //调用底层代码打开继电器,气泵对应的继电器设置为3
                    modbusJni.led_set(3,1);
                    //让其在底部栏设置为可见的
                    ivAirPubmShow.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 点击换向阀之后的监听器,换向阀的继电器编号是4,对应到板子上的编号是No 1;
         */
        ivPointValave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pointValaveState) {
                    pointValaveState = false;
                    modbusJni.led_set(4, 0);
                    ivPointValaveShow.setVisibility(View.INVISIBLE);
                } else {
                    pointValaveState = true;
                    modbusJni.led_set(4,1);
                    ivPointValaveShow.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 点击电磁阀之后的监听器,电磁阀的编号设置为5.对应到板子上的编号是 No2
         */
        ivMegneticValave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (megneticValaveState) {
                    megneticValaveState = false;
                    modbusJni.led_set(5, 0);
                    ivMegneticValaveShow.setVisibility(View.INVISIBLE);
                } else {
                    megneticValaveState = true;
                    modbusJni.led_set(5,1);
                    ivMegneticValaveShow.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 点击吹扫阀之后的监听器,吹扫阀的编号设置为6 对应到板子上的编号是No3
         */
        ivBlowValave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blowValaveState) {
                    blowValaveState = false;
                    modbusJni.led_set(6, 0);
                    ivBlowValaveShow.setVisibility(View.INVISIBLE);
                } else {
                    blowValaveState = true;
                    modbusJni.led_set(6,1);
                    ivBlowValaveShow.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 点击25度恒温器图标之后的监听器,30度恒温器图标的编号设置为7,对应到板子上的编号是No4
         */
        ivThermostat_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thermostat_25_state) {
                    thermostat_25_state = false;
                    modbusJni.led_set(7, 0);
                    ivThermostat_25Show.setVisibility(View.INVISIBLE);
                } else {
                    thermostat_25_state = true;
                    modbusJni.led_set(7,1);
                    ivThermostat_25Show.setVisibility(View.VISIBLE);
                }
            }
        });
        /**
         * 点击120度恒温器图标之后的监听器,120度恒温器图标的编号设置为8,对应到板子上的编号是No5
         */
        ivThermostat_120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thermostat_120_state) {
                    thermostat_120_state = false;
                    modbusJni.led_set(8, 0);
                    ivThermostat_120Show.setVisibility(View.INVISIBLE);
                } else {
                    thermostat_120_state = true;
                    modbusJni.led_set(8,1);
                    ivThermostat_120Show.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 点击系统设置之后的事件监听
         */
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        tvDate = (TextView) findViewById(R.id.tv_date);
        btnSettings = (Button) findViewById(R.id.btn_settings);
        ivAirPubm = (ImageView) findViewById(R.id.iv_air_pump);
        ivPointValave = (ImageView) findViewById(R.id.iv_point_valve);
        ivBlowValave = (ImageView) findViewById(R.id.iv_blow_valve);
        ivMegneticValave = (ImageView) findViewById(R.id.iv_megnetic_valve);
        ivThermostat_25 = (ImageView) findViewById(R.id.iv__25_thermostat);
        ivThermostat_120 = (ImageView) findViewById(R.id.iv__120_thermostat);
        ivAirPubmShow = (ImageView) findViewById(R.id.iv_air_pump_show);
        ivPointValaveShow = (ImageView) findViewById(R.id.iv_point_valave_show);
        ivMegneticValaveShow = (ImageView) findViewById(R.id.iv_megnetic_valve_show);
        ivBlowValaveShow = (ImageView) findViewById(R.id.iv_blow_valve_show);
        ivThermostat_25Show = (ImageView) findViewById(R.id.iv__25_thermostat_show);
        ivThermostat_120Show = (ImageView) findViewById(R.id.iv__120_thermostat_show);
    }
}
