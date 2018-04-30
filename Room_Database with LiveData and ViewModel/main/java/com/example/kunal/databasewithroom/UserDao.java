package com.example.kunal.databasewithroom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KunaL on 28-Apr-18.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getAllUser();


    @Insert()
    long[]  InsertAll(User... users);


}
