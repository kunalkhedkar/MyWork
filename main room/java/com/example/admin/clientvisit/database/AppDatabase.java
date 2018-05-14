package com.example.admin.clientvisit.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {BusinessEntity.class,
        OwnerEntity.class,
        ContactEntity.class,
        FeedbackEntity.class,
        BusinessOwnerEntity.class}
        , version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "visit_db";

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
//                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract BusinessDao businessDao();
    public abstract OwnerDao ownerDao();
    public abstract FeedbackDao feedbackDao();
    public abstract ContactDao contactDao();
    public abstract BusinessOwnerDao businessOwnerDao();
}
