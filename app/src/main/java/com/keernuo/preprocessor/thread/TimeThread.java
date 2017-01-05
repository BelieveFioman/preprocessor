package com.keernuo.preprocessor.thread;

import android.os.Handler;
import android.os.Message;

import com.keernuo.preprocessor.consts.GlobalConst;

/**
 * Created by Fioman on 2016/12/19 0019.
 * Description:更新主显示界面的时间
 */

public class TimeThread extends Thread{
    private Handler handler;

    public TimeThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                Message msg = new Message();
                msg.what = GlobalConst.HANDLER_MESSAGE_UPDATE_TIME;
                handler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
