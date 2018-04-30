package com.example.kunal.databasewithroom;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface User_Contact_Dao {


    @Query("SELECT * FROM User_Contact")
    List<User_Contact> getAllUserContact();

    @Insert()
    long[] insetUserContact(User_Contact... user_contacts);
}
