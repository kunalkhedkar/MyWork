package com.example.kunal.fragmentrecycleview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneFragment one=new OneFragment();
        TwoFragment two=new TwoFragment();
        ThreeFragment three= new ThreeFragment();



        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.containerOne,one,"one");
        transaction.add(R.id.containerTwo,two,"two");
        transaction.commit();

    }
}
