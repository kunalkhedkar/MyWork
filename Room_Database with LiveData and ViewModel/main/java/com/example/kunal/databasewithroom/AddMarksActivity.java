package com.example.kunal.databasewithroom;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddMarksActivity extends AppCompatActivity {

    EditText subject,mark;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marks);

        subject=findViewById(R.id.subject);
        mark=findViewById(R.id.mark);


        id=getIntent().getStringExtra("id");
        Log.d("TAG", "onCreate: AddMarksActivity  id "+id);
    }

    public void onSaveClickMark(View view) {

        String sSubject,sMark;
        sSubject=subject.getText().toString();
        sMark=mark.getText().toString();

        AppDatabase db = AppDatabase.getDatabase(AddMarksActivity.this);


        long i[]=null;

        try {
            db.beginTransaction();
             i= db.markDao().InsertAll(new Mark(0, sSubject, Integer.parseInt(sMark), Integer.parseInt(id)));
            db.setTransactionSuccessful();
            super.onBackPressed();

        }catch (Exception e){
            Log.d("TAG", "onSaveClickMark: exp "+e.getMessage());
        }finally {
            db.endTransaction();
        }
        Log.d("TAG", "onSaveClickMark: marks added "+i[0]);


    }
}
