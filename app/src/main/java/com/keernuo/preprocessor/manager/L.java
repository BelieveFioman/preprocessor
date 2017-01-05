package com.keernuo.preprocessor.manager;

import android.util.Log;

/**
 *  Created by Fioman on 2016/12/19 0019.
 *  Description:根据指令去查询的静态工具类
 */
public class L {

    public static boolean DEBUG = true;
    public static String TAG = "KEERNUO";

    private L(){
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void v(String content){
        if(DEBUG){
            try {
                Log.v(TAG, content);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void i(String content){
        if(DEBUG){
            try {
                Log.i(TAG, content);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void d(String content){
        if(DEBUG){
            try {
                Log.d(TAG, content);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void d(String info, Object... args){
        if(DEBUG){
            try {
                Log.d(TAG, String.format(info, args));
            }catch (Exception e){
                Log.d(TAG, info);
            }
        }
    }

    public static void w(String content){
        if(DEBUG){
            try {
                Log.w(TAG, content);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }public static void w(String content, Exception ex){
        if(DEBUG){
            w(content);
            L.e(TAG,ex);
        }
    }

    public static void e(String content){
        if(DEBUG){
            try {
                Log.e(TAG, content);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void e(Throwable throwable){
        if(DEBUG){
            try {
                throwable.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void e(String content, Exception ex){
        if(DEBUG){
            try {
                Log.e(TAG, content);
            }catch (Exception e){
                e.printStackTrace();
            }
            L.e(TAG,ex);
        }
    }

    public static void e(String content, Throwable ex){
        if(DEBUG){
            try {
                Log.e(TAG, content);
            }catch (Exception e){
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
    }

    public static void e(String content, Error ex){
        if(DEBUG){
            try {
                Log.e(TAG, content);
            }catch (Exception e){
                e.printStackTrace();
            }
            L.e(TAG,ex);
        }
    }

    public static void throwable(Throwable ex){
        if(DEBUG&&ex!=null){
            ex.printStackTrace();
        }
    }
}
