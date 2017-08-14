package com.example.kunal.dialogwithalertdialog_builder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onclickrun(View view){
        Mydialog mydialog=new Mydialog();
        mydialog.show(getFragmentManager(),"LOW_BATTERY");
    }

}
