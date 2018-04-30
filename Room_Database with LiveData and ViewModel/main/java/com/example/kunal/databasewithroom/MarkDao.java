package com.example.kunal.databasewithroom;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MarkDao  {

    @Query("SELECT * FROM Mark")
    List<Mark> getAllMarks();

//    @Query("SELECT * FROM Mark where userId=:id")
//    List<Mark> getMarksByID(int id);

    @Insert()
    long[] InsertAll(Mark... marks);


    @Query("SELECT * FROM Mark where userId=:id")
    LiveData<List<Mark>> getMarksByID(int id);

}
