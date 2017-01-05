package com.keernuo.preprocessor.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.keernuo.preprocessor.R;
import com.keernuo.preprocessor.utils.SharedPreferencesUtils;
import com.keernuo.preprocessor.utils.SystemDateTimeSet;
import com.keernuo.preprocessor.view.DropEditText;
import com.keernuo.preprocessor.view.IPEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Fioman on 2016/12/12 0012.
 * Description: 基本设置的Fragment
 */

public class FragmentBasicSetting extends Fragment {
    /**
     * 保存按钮
     */
    private Button btnSave;
    /**
     * 设备地址,设备的IP地址
     */
    private IPEditText etIpAddress;
    /**
     * 设备的波特率,不能编辑,只能选择固定的值
     */
    private DropEditText etBaudRate;
    /**
     * 修改密码对话框
     */
    private EditText  etPassword;
    /**
     * 日期设置控件
     */
    private EditText etDate;
    /**
     * 时间设置控件
     */
    private EditText etTime;
    /**
     * Fragmeng用到的view组件
     */
    private View view;

    /**
     * Calendar对象
     */
    private Calendar calendar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basic_setting, null);
        initViews();
        setListeners();
        return view;
    }

    private void setListeners() {
        //保存按钮的监听器
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击保存的时候,获取各个的值,然后保存到偏好设置里面
                //将IP保存到偏好设置里面
                SharedPreferencesUtils.setIpAddress(etIpAddress.getText(getActivity()).toString());
                //将波特率保存到偏好设置里面
                SharedPreferencesUtils.setBaudRate(Integer.parseInt(etBaudRate.getText().toString()));
                //将密码也保存到偏好设置里面
                SharedPreferencesUtils.setPassword(etPassword.getText().toString());

                Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_SHORT).show();
            }
        });
        //时间设置的监听器
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay+":"+minute);
                        try {
                            SystemDateTimeSet.setTime(hourOfDay,minute);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });
        //日期设置的监听器
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        etDate.setText(android.text.format.DateFormat.format("yyyy-MM-dd",calendar));
                        try {
                            SystemDateTimeSet.setDate(year,monthOfYear,dayOfMonth);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }

    private void initViews() {
        etDate = (EditText) view.findViewById(R.id.et_date_setting);
        etTime = (EditText) view.findViewById(R.id.et_time_setting);
        etBaudRate = (DropEditText) view.findViewById(R.id.et_bound_rate);
        etIpAddress = (IPEditText) view.findViewById(R.id.et_device_address);
        btnSave = (Button) view.findViewById(R.id.btn_save);
        etPassword = (EditText) view.findViewById(R.id.et_password_chage);

        etIpAddress.setText(SharedPreferencesUtils.getIpAddress());
        etPassword.setText(SharedPreferencesUtils.getPassword());

        etBaudRate.setAdapter(new BaseAdapter() {
            private List<String> list = new ArrayList<String>() {
                {
                    add("9600");
                    add("100");
                    add("4500");
                    add("3600");
                    add("20800");
                }
            };
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(getActivity());
                tv.setPadding(2,2,2,2);
                tv.setHeight(26);
                tv.setText(list.get(position));
                return tv;
            }
        });

        etDate.setInputType(InputType.TYPE_NULL);
        etTime.setInputType(InputType.TYPE_NULL);
    }
}
