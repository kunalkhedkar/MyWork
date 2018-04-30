package com.example.kunal.databasewithroom;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MarkActivity extends AppCompatActivity {

    RecyclerView mark_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mark_recyclerView=findViewById(R.id.mark_recyclerView);

        final String id=getIntent().getStringExtra("id");
        Log.d("TAG", "onCreate: MarkActivity id "+id);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MarkActivity.this,AddMarksActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });


        AppDatabase db = AppDatabase.getDatabase(MarkActivity.this);

//        final List<Mark> marksList=db.markDao().getMarksByID(Integer.parseInt(id));
         List<Mark> marksList=new ArrayList<>();


        final RecyclerView recyclerView = findViewById(R.id.mark_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

         final AdapterMarks adapterMarks=new AdapterMarks(marksList);

        mark_recyclerView.setAdapter(adapterMarks);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MarkActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        Intent intent=new Intent(MarkActivity.this,AddMarksActivity.class);

                        startActivity(intent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do
                    }
                })
        );



        //live data
        MarkViewModel viewModel = ViewModelProviders.of(this).get(MarkViewModel.class);

        viewModel.getMarksList(Integer.parseInt(id)).observe(MarkActivity.this, new Observer<List<Mark>>() {
            @Override
            public void onChanged(@Nullable List<Mark> mark) {
                adapterMarks.addItems(mark);
            }
        });


    }

}
