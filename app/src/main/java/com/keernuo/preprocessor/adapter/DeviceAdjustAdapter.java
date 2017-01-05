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
public class DeviceAdjustAdapter extends BaseAdapter {
    private Context context;
    private List<DeviceData> devDatas;
    private LayoutInflater inflater;
    /**
     * 目前为止前几页的总共的个数
     */
    private int allItems = 0;

    public DeviceAdjustAdapter(Context context, List<DeviceData> devDatas) {
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
            convertView = inflater.inflate(R.layout.list_dev_adjust_item, null);
            vHolder = new ViewHolder();
            vHolder.tvDevAddress = (TextView) convertView.findViewById(R.id.tv_device_address);
            vHolder.tvSensorName = (TextView) convertView.findViewById(R.id.tv_sensor);
            vHolder.tvMeasureUnit = (TextView) convertView.findViewById(R.id.tv_measure_unit);
            vHolder.tvAoel = (TextView) convertView.findViewById(R.id.tv_Aoel_value);
            vHolder.ibtnDevAdjust = (ImageButton) convertView.findViewById(R.id.ibtn_dev_adjust);

            convertView.setTag(vHolder);
        }

        vHolder = (ViewHolder) convertView.getTag();
        DeviceData data = devDatas.get(position + FragmentAlertSetting.index * FragmentAlertSetting.VIEW_COUNT);

        vHolder.tvDevAddress.setText(data.getDeviceAddress() + "");
        vHolder.tvSensorName.setText(data.getSensorName());
        vHolder.tvMeasureUnit.setText(data.getMeasureUnit());
        vHolder.tvAoel.setText(data.getDevAoel()+"");

        return convertView;
    }

    class ViewHolder {
        TextView tvDevAddress;
        TextView tvSensorName;
        TextView tvAoel;
        TextView tvMeasureUnit;
        ImageButton ibtnDevAdjust;
    }
}
