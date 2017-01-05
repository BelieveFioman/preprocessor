package com.keernuo.preprocessor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Fioman on 2016/12/23 0023.
 * Description:下拉菜单选项的ListView,自定义的ListView
 */

public class SpinnerListView extends ListView{
    private  int mWidth = 0 ;

    public SpinnerListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpinnerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);
    }

    //重写onMeasure方法,解决默认横屏占满屏幕的问题

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            int childWidth = getChildAt(i).getMeasuredWidth();
            mWidth = Math.max(mWidth, childWidth);
        }

        setMeasuredDimension(mWidth,height);
    }

    /**
     * 设置宽度,如果不设置,则默认包裹内容
     */
    protected void setListWidth(int width) {
        mWidth = width;
        System.out.println("setWidth");
    }
}
