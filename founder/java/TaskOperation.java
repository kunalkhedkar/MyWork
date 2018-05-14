package com.example.silicon.founder_name;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Sandy on 27/04/2017.
 */

public class TaskOperation {

    static final String LOGTAG = "database";
    SQLiteDatabase sqLiteDatabase = null;
    DBHelper dbHelper = null;


    public TaskOperation(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() {
        Log.i(LOGTAG, "Database Opened");
        sqLiteDatabase = dbHelper.getWritableDatabase();


    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbHelper.close();

    }


    public ArrayList<Info_Modal> GetTaskList() {
        ArrayList<Info_Modal> data = new ArrayList<>();
        data = new ArrayList<Info_Modal>();
        try {

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM simpleTable", null);

            if (cursor != null && cursor.moveToFirst()) {

                do {

                    String dataFromDB1 = cursor.getString(1);
                    String dataFromDB2 = cursor.getString(2);

                    Log.d("TAG", "data from db1 " + dataFromDB1);
                    Log.d("TAG", "data from db2 " + dataFromDB2);

                    Info_Modal info_modal = new Info_Modal(dataFromDB1, dataFromDB2);

                    data.add(info_modal);

//                    data.addAll((ArrayList<Info_Modal>)fromString(dataFromDB));

                    Log.d("TAG", "GetTaskList: size" + data.size());

                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.d(LOGTAG, ex.getMessage());
        }

        return data;

    }


    public boolean insertTask(String s, String s1, byte[] image) {
        Log.d("TAG", "insertTask: " + s);
        Log.d("TAG", "insertTask: " + s1);
        ContentValues insertValues = new ContentValues();
        insertValues.put("lang", s);
        insertValues.put("founder", s1);
        insertValues.put("image", image);


        try {
            sqLiteDatabase.insert("simpleTable", "", insertValues);
            insertValues.clear();
            return true;
        } catch (Exception ex) {
            return false;
        }


    }

    public Bitmap getImageBitmapFromDB(String lang_name) {

        Bitmap bitmap=null;


        Cursor cursor = sqLiteDatabase.rawQuery("SELECT image FROM simpleTable where lang='"+lang_name+"'", null);
        if (cursor != null && cursor.moveToFirst()) {

            do {

                byte[] image = cursor.getBlob(0);

                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            } while (cursor.moveToNext());
        }

        return bitmap;
    }
}
