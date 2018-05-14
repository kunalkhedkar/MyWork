package com.example.admin.clientvisit.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.clientvisit.database.AppDatabase;
import com.example.admin.clientvisit.database.FeedbackEntity;

import java.util.ArrayList;
import java.util.List;

public class FeedbackViewModel extends AndroidViewModel {

    private MutableLiveData<List<FeedbackEntity>> feedbackList;
    List<FeedbackEntity> feedbackEntities = new ArrayList<>();
    private static AppDatabase appDatabase;
    private int id;

    public FeedbackViewModel(@NonNull Application application, int id) {
        super(application);
        this.id = id;
        appDatabase = AppDatabase.getInstance(application);
        feedbackList = new MutableLiveData<>();
    }

    public LiveData<List<FeedbackEntity>> getFeedbackList() {
        new GetFeedback().execute();
//        new GetFeedbackLoader(getApplication()).forceLoad();
        return feedbackList;
    }

    class GetFeedback extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            feedbackEntities = appDatabase.feedbackDao().getFeedbackByBusinessId(id);
            feedbackList.postValue(feedbackEntities);
            return null;
        }
    }

    class GetFeedbackLoader extends AsyncTaskLoader{
        public GetFeedbackLoader(Context context) {
            super(context);
        }
        @Override
        public Object loadInBackground() {
            feedbackEntities = appDatabase.feedbackDao().getFeedbackByBusinessId(id);
            feedbackList.postValue(feedbackEntities);
            return null;
        }
    }


}
