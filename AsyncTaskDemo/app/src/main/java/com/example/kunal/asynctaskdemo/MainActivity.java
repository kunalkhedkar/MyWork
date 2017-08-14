package com.example.kunal.asynctaskdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

    }

    public void startTask(View view) {
      //  Mytask mytask = new Mytask(progressBar);
        //mytask.execute(progressBar);

        Task task=new Task();
        task.execute(progressBar);

    }



class Task extends AsyncTask<View,View,Void>{

    @Override
    protected Void doInBackground(View... views) {

        publishProgress(views[0]);
        return null;
    }


    @Override
    protected void onProgressUpdate(View... values) {
        ProgressBar p=(ProgressBar)values[0].findViewById(R.id.progressbar);
        p.setProgress(10000);
        p.setProgress(1000);

    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(10000);
    }




}

}
