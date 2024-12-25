package com.rlue.storesystem.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_MANAGER= "create table manager ("
            + "name String primary key, "
            + "pwd integer, "
            + "token integer)";

    public static final String CREATE_GOODS= "create table goods("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "price integer,"
            + "count integer)";

    public static final String CREATE_CUSTOMER= "create table customer("
            + "id integer primary key autoincrement, "
            + "name text,"
            + "company text,"
            + "address text,"
            + "email integer,"
            + "phone integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        Log.d("database","调用SQLiteOpenHelper的构造方法");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("database","调用SQLiteOpenHelper的onCreate");
        db.execSQL(CREATE_MANAGER);
        db.execSQL(CREATE_CUSTOMER);
        db.execSQL(CREATE_GOODS);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("database","调用SQLiteOpenHelper的onUpgrade");
    }
}
