package com.example.gajanand.simpletask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyDb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simple.db";
    public static SQLiteDatabase db;
    private static MyDb mInstance = null;

    String table = "create table if not exists user (id varchar,name varchar,email varchar,password varchar" +
            ",com_code varchar,status varchar,forgot varchar);";

    public MyDb(Context context) {//, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + "SimpleTask"
                + File.separator + DATABASE_NAME, null, 1);

    }

    public static MyDb getInstance(Context context) {
        int i = 0;
        while (i < 2) {
            if (mInstance == null) {
                try {
                    mInstance = new MyDb(context.getApplicationContext());//.getApplicationContext());
                    db = mInstance.getWritableDatabase();
                    //                    db.enableWriteAheadLogging();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        if (mInstance != null) {
                            mInstance.close();
                            mInstance = null;
                            i++;
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }
        }

        return mInstance;
    }

    public static boolean insertUserDetails(List<UserListResponse> userListResponses) {


        long rowId = -1;
        for (UserListResponse user : userListResponses) {

            ContentValues cv = new ContentValues();
            cv.put("id", user.getId());
            cv.put("name", user.getName());
            cv.put("email", user.getEmail());
            cv.put("password", user.getPassword());
            cv.put("com_code", user.getCom_code());
            cv.put("status", user.getStatus());
            cv.put("forgot", user.getForgot());

            Log.d("Gajanand", "insertUserDetails: " + user.getForgot());


            rowId = db.insert("user", null, cv);

            if (rowId == -1)
                break;
        }

        return rowId != -1;

    }

    public static List<UserListResponse> fetchAlldata() {

        UserListResponse userListResponse = new UserListResponse();

//        Cursor cursor=db.query("user",new String[]{"id","name","email","password","com_code","status","forgot"},null,
//        null,null,null,null,null);

        List<UserListResponse> list = new ArrayList<>();

        try {

            Cursor cursor = db.rawQuery("select * from user ", null);
            Log.d("Gajanand", "fetchAlldata: " + cursor);


            if (null != cursor) {
                if (cursor.moveToFirst()) {

                    do {
                        userListResponse.setId(cursor.getString(cursor.getColumnIndex("id")));
                        userListResponse.setName(cursor.getString(cursor.getColumnIndex("name")));
                        userListResponse.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        userListResponse.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                        userListResponse.setCom_code(cursor.getString(cursor.getColumnIndex("com_code")));
                        userListResponse.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                        userListResponse.setForgot(cursor.getString(cursor.getColumnIndex("forgot")));

                        list.add(userListResponse);

                        Log.d("Gajanand", "fetchAlldata: " + cursor.getString(cursor.getColumnIndex("name")));
                    } while (cursor.moveToNext());
                }


            }
        } catch (Exception e) {
            Log.d("Gajanand", "fetchAlldata: " + e.toString());
        }
        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
