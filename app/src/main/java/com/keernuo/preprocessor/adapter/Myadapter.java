package com.keernuo.preprocessor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keernuo.preprocessor.R;


/**
 * Created by Fioman on 2016/12/7 0007.
 * GridView的适配器
 */
public class Myadapter extends BaseAdapter {

    private final LayoutInflater inflater;
    /**
     * 上下文对象
     */
    private Context context;

    /**
     * 数据源
     */
    private double[] data;

    public Myadapter(Context context, double[] data) {
        this.context = context;
        this.data = data;
        /**
         * 填充界面的工具
         */
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Double getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int positon) {
        return positon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
            vHolder = new ViewHolder();

            vHolder.ivAlertor = (ImageView) convertView.findViewById(R.id.iv_alert);
            vHolder.tvData = (TextView) convertView.findViewById(R.id.tv_receive_data);
            convertView.setTag(vHolder);
        }

        vHolder = (ViewHolder) convertView.getTag();

        if (position == 0) {
            vHolder.ivAlertor.setVisibility(View.VISIBLE);
        } else {
            vHolder.ivAlertor.setVisibility(View.GONE);
        }
        Double showData = data[position];

        vHolder.tvData.setText(showData+"");

        return convertView;
    }

    class ViewHolder {
        TextView tvData;
        ImageView ivAlertor;
    }

}
