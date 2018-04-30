package com.example.kunal.databasewithroom;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MarkViewModel extends AndroidViewModel {

    private LiveData<List<Mark>> markList;
    private AppDatabase appDatabase;


    public MarkViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

//        markList = appDatabase.markDao().getMarksByID(id);

    }

    public LiveData<List<Mark>> getMarksList(int id) {
        markList = appDatabase.markDao().getMarksByID(id);
        return markList;
    }

}
