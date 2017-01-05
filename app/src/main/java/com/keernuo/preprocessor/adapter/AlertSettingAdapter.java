package com.keernuo.preprocessor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.keernuo.preprocessor.R;
import com.keernuo.preprocessor.entity.DeviceData;
import com.keernuo.preprocessor.fragment.FragmentAlertSetting;

import java.util.List;

/**
 * Created by Fioman on 2016/12/13 0013.
 * Description:AlertSettting的Adapter
 */
public class AlertSettingAdapter extends BaseAdapter {
    private Context context;
    private List<DeviceData> devDatas;
    private LayoutInflater inflater;
    /**
     * 目前为止前几页的总共的个数
     */
    private int allItems = 0;

    public AlertSettingAdapter(Context context, List<DeviceData> devDatas) {
        this.context = context;
        this.devDatas = devDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        allItems = FragmentAlertSetting.VIEW_COUNT * FragmentAlertSetting.index;
        //值的总个数-前几页的个数就是当前页面要显示的个数.如果比默认的值小,说明已经是最后一页,只需要显示目前的数量即可
        if (devDatas.size() - allItems < FragmentAlertSetting.VIEW_COUNT) {
            return devDatas.size() - allItems;
        }
        return FragmentAlertSetting.VIEW_COUNT;
    }

    @Override
    public DeviceData getItem(int position) {
        return devDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_alert_setting_item, null);
            vHolder = new ViewHolder();
            vHolder.tvDevAddress = (TextView) convertView.findViewById(R.id.tv_device_address);
            vHolder.tvSensorName = (TextView) convertView.findViewById(R.id.tv_sensor);
            vHolder.tvOneAlert = (TextView) convertView.findViewById(R.id.tv_one_alert);
            vHolder.tvTwoAlert = (TextView) convertView.findViewById(R.id.tv_two_alert);
            vHolder.tvThreeAlert = (TextView) convertView.findViewById(R.id.tv_three_alert);
            vHolder.tvMeasureUnit = (TextView) convertView.findViewById(R.id.tv_measure_unit);
            vHolder.tvALertOut = (TextView) convertView.findViewById(R.id.tv_alert_out);
            vHolder.ibtnModeSetting = (ImageButton) convertView.findViewById(R.id.ibtn_mode_setting);
            vHolder.ibtnModeSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSettingDialog();
                }
            });

            convertView.setTag(vHolder);
        }

        vHolder = (ViewHolder) convertView.getTag();
        DeviceData data = devDatas.get(position + FragmentAlertSetting.index * FragmentAlertSetting.VIEW_COUNT);

        vHolder.tvDevAddress.setText(data.getDeviceAddress() + "");
        vHolder.tvSensorName.setText(data.getSensorName());
        vHolder.tvOneAlert.setText(data.getOneAlert());
        vHolder.tvTwoAlert.setText(data.getTwoAlert());
        vHolder.tvThreeAlert.setText(data.getThreeAlert());
        vHolder.tvMeasureUnit.setText(data.getMeasureUnit());
        vHolder.tvALertOut.setText(data.getAlertOut());

        return convertView;
    }

    //显示报警模式设置对话框
    private void showSettingDialog() {
    }

    class ViewHolder {
        TextView tvDevAddress;
        TextView tvSensorName;
        TextView tvOneAlert;
        TextView tvTwoAlert;
        TextView tvThreeAlert;
        TextView tvMeasureUnit;
        TextView tvALertOut;
        ImageButton ibtnModeSetting;
    }
}
