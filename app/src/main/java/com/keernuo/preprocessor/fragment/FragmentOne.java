package com.keernuo.preprocessor.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.keernuo.dn9000.mode.jni.ModbusJni;
import com.keernuo.preprocessor.R;
import com.keernuo.preprocessor.app.KenApplication;
import com.keernuo.preprocessor.consts.GlobalConst;
import com.keernuo.preprocessor.entity.Sensor;
import com.keernuo.preprocessor.utils.DataFormat;


/**
 * Created by Fioman on 2016/12/8 0008.
 * Description: 单通道的Fragment
 */
public class FragmentOne extends Fragment {
    private TextView tvAlertClass;
    private SimpleDraweeView dvAlert;
    private TextView tvAlertNormal;
    private TextView tvDensity;
    private TextView tvSensorName;
    private TextView tvUnit;
    private Sensor sensor;
    ModbusJni modbusJni = new ModbusJni();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GlobalConst.HANDLER_MESSAGE_UPDATE_DATA:
                    sensor = (Sensor) msg.obj;
                    Log.i("MyTag", "handleMessage() ->浓度值是: +"+(double) sensor.gasDensity/10);
                    tvDensity.setText(DataFormat.getDensity(sensor.gasDensity, sensor.decimalPointNumber) + "");
                    if (sensor.warningState == 0) { //如果示数正常就正常显示,就只显示示数正常就可以了
                        dvAlert.setVisibility(View.GONE);
                        tvAlertClass.setVisibility(View.GONE);
                        tvAlertNormal.setVisibility(View.VISIBLE); //显示示数正常图标
                        tvDensity.setTextColor(getResources().getColor(R.color.tvColorNormal));
                    } else {//如果示数不正常,就根据值来判断报警类型.
                        tvAlertNormal.setVisibility(View.GONE);
                        dvAlert.setVisibility(View.VISIBLE);
                        tvAlertClass.setVisibility(View.VISIBLE);
                        tvDensity.setTextColor(getResources().getColor(R.color.tvColorAlert));

                        switch (sensor.warningState) {
                            case 1: //低报警
                                tvAlertClass.setText("(AL 低报警)");
                                break;
                            case 2://高报警
                                tvAlertClass.setText("(AH 高报警)");
                                break;
                            case 3: //低报警+高报警
                                tvAlertClass.setText("(AL+AH 低报警+高报警)");
                                break;
                        }
                    }
                break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, null);
        tvDensity = (TextView) view.findViewById(R.id.tv_receive_data);
        tvSensorName = (TextView) view.findViewById(R.id.tv_sensor_one);
        tvUnit = (TextView) view.findViewById(R.id.tv_unit_one);
        tvAlertNormal = (TextView) view.findViewById(R.id.tv_alert_normal);
        dvAlert = (SimpleDraweeView) view.findViewById(R.id.dv_alert);
        tvAlertClass = (TextView) view.findViewById(R.id.tv_alert_class);

        sensor = KenApplication.getInstance().sensors.get(0);
        tvSensorName.setText(sensor.molecularFormula);
        tvUnit.setText(DataFormat.getUnit(sensor.gasUnit));
        tvDensity.setText(DataFormat.getDensity(sensor.gasDensity, sensor.decimalPointNumber)+"");

        //加载报警图片
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(Uri.parse("asset://com.keernuo.preprocessor/alert_p.gif"))
                .build();
        dvAlert.setController(draweeController);

        //开启线程,不断地从后台读取数据
        //new GetDataThread(handler, sensor).start();
        return view;
    }
}
