package com.keernuo.preprocessor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.keernuo.preprocessor.R;

/**
 * Created by Fioman on 2016/12/23 0023.
 * Description:自定义的Edittext,带有下拉菜单功能
 */

public class DropEditText extends FrameLayout implements AdapterView.OnItemClickListener{
    private EditText editText;//输入框
    private ImageView  dropImage;  //右边的图片按钮
    private PopupWindow popupWindow; //点击图片弹出的popupwindow
    private SpinnerListView spinnerListView; //popupwindow用到的布局


    private int drawableLeft;
    private int drapMode; //flow_parent  or wrap_content
    private String hint;

    public DropEditText(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DropEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);

        LayoutInflater.from(context).inflate(R.layout.edit_layout, this);
        spinnerListView = (SpinnerListView)LayoutInflater.from(context).inflate(R.layout.pop_view, null);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DropEditText, defStyle, 0);
        drawableLeft = ta.getResourceId(R.styleable.DropEditText_drawableRight, R.drawable.spinner_normal);
        drapMode = ta.getInt(R.styleable.DropEditText_dropMode, 0);
        hint = ta.getString(R.styleable.DropEditText_hint);

        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        editText = (EditText) findViewById(R.id.dropview_edit);
        dropImage = (ImageView) findViewById(R.id.dropview_image);

        editText.setSelectAllOnFocus(true);
        dropImage.setImageResource(drawableLeft);

        if (!TextUtils.isEmpty(hint)) {
            editText.setText(hint);
        }

        dropImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(v);
                }
            }
        });
        spinnerListView.setOnItemClickListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //如果布局发生改变,并且dropMode是flower_parent,则设置ListView的宽度
        if (changed && 0 == drapMode) {
            spinnerListView.setListWidth(102);
        }
    }

    /**
     * 设置Adapter
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        spinnerListView.setAdapter(adapter);

        popupWindow = new PopupWindow(spinnerListView, 102, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setFocusable(true); //让popwin获取焦点
    }

    /**
     * 获取输入框内的内容
     */
    public String getText() {
        return editText.getText().toString();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        editText.setText(spinnerListView.getAdapter().getItem(position).toString());
        popupWindow.dismiss();
    }
}
