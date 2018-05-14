package com.example.silicon.founder_name;

/**
 * Created by Sandy on 26/04/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bhupendrasinha on 02-09-2016.
 */
public class DBHelper extends SQLiteOpenHelper {


    //Define Database details
    private static final String dbName = "DemoDB";
    private static final SQLiteDatabase.CursorFactory factory = null;
    private static final int version = 1;
    private static final String CreateDBCommand =
            "CREATE TABLE IF NOT EXISTS simpleTable " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT,lang TEXT NOT NULL,founder TEXT NOT NULL,image BLOB);";

    private static final String DropDBCommand = "DROP TABLE IF EXISTS simpleTable ";

    public DBHelper(Context context) {
        super(context, dbName, factory, version);
        Log.d("database Name", dbName);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CreateDBCommand);
        Log.d("database", CreateDBCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete when new version database schema is available
        db.execSQL(DropDBCommand);

        // Create tables again
        onCreate(db);

    }




}
