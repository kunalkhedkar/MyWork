package com.example.admin.clientvisit.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.clientvisit.database.DbUtil;
import com.example.admin.clientvisit.model.ClientData;
import com.example.admin.clientvisit.model.FeedbackData;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "visit_db";
    //table
    public static final String CLIENT_TABLE = "client_table";
    public static final String FEEDBACK_TABLE = "feedback_table";
    public static final String BUSINESS_TABLE = "business_table";
    public static final String OWNER_TABLE = "owner_table";
    public static final String CONTACT_TABLE = "contact_table";
    public static final String BUSINESS_OWNER_TABLE = "business_owner_table";

    //business
    public static final String B_ID_KEY = "b_id";
    public static final String BUSINESS_NAME_KEY = "business_name";
    //owner
    public static final String O_ID_KEY = "b_id";
    public static final String OWNER_NAME_KEY = "owner_name";
    //contact
    public static final String MONILE_KEY = "mobile";
    public static final String PHONE_KEY = "phone";
    public static final String EMAIL_KEY = "phone";
    public static final String LATITUDE_KEY = "latitude";
    public static final String LONGITUDE_KEY = "longitude";
    public static final String WEBSITE_KEY = "website";
    //feedback | visit
    public static final String DATE_KEY = "date";
    public static final String TIME_KEY = "time";
    public static final String FEEDBACK_KEY = "feedback";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

         String CREATE_TABLE_BUSINESS = "CREATE TABLE " + BUSINESS_TABLE + "("
                 + B_ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                 + BUSINESS_NAME_KEY +" TEXT"
                 + ")";

        String CREATE_TABLE_OWNER = "CREATE TABLE " + OWNER_TABLE + "("
                + O_ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + OWNER_NAME_KEY +" TEXT"
                + ")";


        String CREATE_TABLE_CONTACT = "CREATE TABLE "
                + CONTACT_TABLE + "("
                + B_ID_KEY + " INTEGER,"
                + MONILE_KEY + " TEXT,"
                + PHONE_KEY + " TEXT,"
                + EMAIL_KEY + " TEXT,"
                + LATITUDE_KEY + " TEXT,"
                + LONGITUDE_KEY + " TEXT,"
                + WEBSITE_KEY + " TEXT,"
                + "foreign key ( "+ B_ID_KEY + ") references "+BUSINESS_TABLE+"("+B_ID_KEY+")"
                + ")";

        String CREATE_TABLE_FEEDBACK = "CREATE TABLE "
                + FEEDBACK_TABLE + "("
                + B_ID_KEY + " INTEGER,"
                + DATE_KEY + " TEXT,"
                + TIME_KEY + " TEXT,"
                + LATITUDE_KEY + " TEXT,"
                + LONGITUDE_KEY + " TEXT,"
                + FEEDBACK_KEY + " TEXT,"
                + "foreign key ( "+ B_ID_KEY + ") references "+BUSINESS_TABLE+"("+B_ID_KEY+")"
                + ")";

        String CREATE_TABLE_BUSINESS_OWNER = "CREATE TABLE "
                + BUSINESS_OWNER_TABLE + "("
                + B_ID_KEY + " INTEGER,"
                + O_ID_KEY + " INTEGER,"
                + "foreign key ( "+ B_ID_KEY + ") references "+BUSINESS_TABLE+"("+B_ID_KEY+"),"
                + "foreign key ( "+ O_ID_KEY + ") references "+OWNER_TABLE+"("+O_ID_KEY+")"
                + ")";



        db.execSQL(CREATE_TABLE_BUSINESS);
        db.execSQL(CREATE_TABLE_OWNER);
        db.execSQL(CREATE_TABLE_CONTACT);
        db.execSQL(CREATE_TABLE_FEEDBACK);
        db.execSQL(CREATE_TABLE_BUSINESS_OWNER);


//        db.execSQL("create table " + FEEDBACK_TABLE + "(feedbcakID INTEGER PRIMARY KEY AUTOINCREMENT,date TEXT,time TEXT,lat TEXT,lng TEXT,addressBrief TEXT,feedback TEXT,clientID INTEGER, foreign key (clientID) references client_table(clientID))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean insertClientData(ClientData clientData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.beginTransaction();

        try {
            //business
            contentValues.put(BUSINESS_NAME_KEY, clientData.getBusinessName());
            db.insert(BUSINESS_TABLE, null, contentValues);
            //owner
            contentValues.clear();
            contentValues.put(OWNER_NAME_KEY, DbUtil.buildOwnerNameStringFromList(clientData));
            db.insert(OWNER_TABLE, null, contentValues);
            //contact
            contentValues.clear();
            contentValues.put(MONILE_KEY, clientData.getMobile());
            contentValues.put(PHONE_KEY, clientData.getPhone());
            contentValues.put(EMAIL_KEY, clientData.getEmail());
            contentValues.put(LATITUDE_KEY, clientData.getLatitude());
            contentValues.put(LONGITUDE_KEY, clientData.getLongitude());
            contentValues.put(WEBSITE_KEY, clientData.getWebsite());
            db.insert(CONTACT_TABLE, null, contentValues);

            db.setTransactionSuccessful();

        }catch (Exception e){

        }finally {
            db.endTransaction();
        }
//        long result = db.insert(CLIENT_TABLE, null, contentValues);
//
//        if (result == -1)
//            return false;
//        else
            return true;
    }
//
//    public ArrayList<ClientData> getAllClientData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor result = db.rawQuery("select * from " + CLIENT_TABLE, null);
//
//        ArrayList<ClientData> clientList = new ArrayList<>();
//        if (result.getCount() == 0) {
//
//        } else {
//            while (result.moveToNext()) {
//                Log.d("TAG", "id: " + Integer.parseInt(result.getString(0)));
//                ClientData clientData = new ClientData(
//                        Integer.parseInt(result.getString(0)),
//                        result.getString(1),
//                        result.getString(2),
//                        result.getString(3),
//                        result.getString(4),
//                        result.getString(5),
//                        result.getString(6),
//                result.getBlob(7));
//
//                clientList.add(clientData);
//            }
//        }
//
//        return clientList;
//    }
//
//
//
//    //--------------------------  feedback
//
//    public boolean insertFeedbackData(FeedbackData feedbackData) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("date", feedbackData.getDate());
//        contentValues.put("time",feedbackData.getTime());
//        contentValues.put("lat",feedbackData.getLatitude());
//        contentValues.put("lng",feedbackData.getLongitude());
//        contentValues.put("feedback",feedbackData.getFeedback());
//        contentValues.put("clientID",feedbackData.getClientId());
//
//        long result = db.insert(FEEDBACK_TABLE, null, contentValues);
//
//        if (result == -1)
//            return false;
//        else
//            return true;
//    }
//
//
//    public ArrayList<FeedbackData> getFeedbackData(String clientId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor result = db.rawQuery("select * from " + FEEDBACK_TABLE +" where clientID="+clientId, null);
//
//        ArrayList<FeedbackData> feedbackList = new ArrayList<>();
//        if (result.getCount() == 0) {
//
//        } else {
//            while (result.moveToNext()) {
//                Log.d("TAG", "id: " + Integer.parseInt(result.getString(0)));
//                FeedbackData feedbackData = new FeedbackData(
//                        result.getString(1),
//                        result.getString(2),
//                        result.getString(3),
//                        result.getString(4),
//                        result.getString(5),
//                        result.getString(6));
//
//                Log.d("TAG", "getFeedbackData: "+feedbackData);
//
//                feedbackList.add(feedbackData);
//            }
//        }
//
//        return feedbackList;
//    }

}
