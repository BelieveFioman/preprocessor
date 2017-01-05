package com.keernuo.preprocessor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Fioman on 2016/12/27 0027.
 * Description:数据库管理操作类
 */

public class KenOpenHelper extends SQLiteOpenHelper {

    public KenOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static final String CREATE_HISTORY_DATA  = "create table HISTORY_DATA ("
            + "id integer primary key autoincrement, "
            + "sensor_site integer, "
            + "sensor_name text, "
            + "density real, "
            + "sensor_unit text, "
            + "temperate real, "
            + "humidity real, "
            +"sample_time text"
            + " )";
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
