package com.example.kunal.asynctaskdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by KunaL on 13-Aug-17.
 */
class Mytask extends AsyncTask {


    ProgressBar progressBar;

    Mytask(View view){
        progressBar=view.findViewById(R.id.progressbar);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        progressBar.setMax(100000);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);




    }

    @Override
    protected Object doInBackground(Object[] objects) {

        for (int i = 0; i < 100000; i++) {
            Log.d("TAG", "doInBackground:   " + i);

            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        int status= (int) values[0];

        progressBar.setProgress(status);

    }

    @Override
    protected void onPostExecute(Object o) {
        progressBar.setVisibility(View.INVISIBLE);

    }
}

