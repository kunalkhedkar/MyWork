package com.example.kunal.databasewithroom;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM Contact")
    List<Contact> getAllContactDetails();

    @Insert()
    long[] insetContact(Contact... contacts);

}
