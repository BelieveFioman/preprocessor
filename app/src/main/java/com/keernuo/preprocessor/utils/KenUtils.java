package com.keernuo.preprocessor.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Fioman on 2016/12/23 0023.
 * Description:App相关的工具类
 */

public class KenUtils {
    /**
     * 点击界面上的非输入框的地方,可以隐藏软键盘
     */
    public static void hideSoftKeyboad(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
