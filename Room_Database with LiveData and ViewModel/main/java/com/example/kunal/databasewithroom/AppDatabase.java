package com.example.kunal.databasewithroom;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import static android.graphics.PorterDuff.Mode.ADD;

/**
 * Created by KunaL on 28-Apr-18.
 */


@Database(entities = {User.class, Mark.class, Contact.class, User_Contact.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract MarkDao markDao();

    public abstract ContactDao contactDao();

    public abstract User_Contact_Dao user_contact_dao();


    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user_details_db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }


// update database structure without changing existing database

//    static final Migration FROM_1_TO_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(final SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Repo ADD COLUMN createdAt TEXT");
//        }
//    };

}
