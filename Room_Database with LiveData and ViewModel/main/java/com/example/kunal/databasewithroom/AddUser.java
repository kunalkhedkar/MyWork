package com.example.kunal.databasewithroom;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddUser extends AppCompatActivity {

    EditText name, phone, mark;
    EditText mobile, landline, parentMobile;
    String sMobile, sLandline, sParentMobile;
    String sName, sPhone, sMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        mark = findViewById(R.id.mark);

        mobile = findViewById(R.id.mobile);
        landline = findViewById(R.id.landline);
        parentMobile = findViewById(R.id.parentMobile);

    }

    public void onSaveClick(View view) {
        sName = name.getText().toString();
        sPhone = phone.getText().toString();

        sMobile = mobile.getText().toString();
        sLandline = landline.getText().toString();
        sParentMobile = parentMobile.getText().toString();

        AppDatabase db = AppDatabase.getDatabase(AddUser.this);

//        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build();
//



        try {
            db.beginTransaction();

            long uid[] = db.userDao().InsertAll(new User(0, sName, sPhone));

            long cid[] = db.contactDao().insetContact(new Contact(0, sMobile, sLandline, sParentMobile));

            long ucid[] = db.user_contact_dao().insetUserContact(new User_Contact((int)uid[0],(int)cid[0]));

            Log.d("TAG", "onSaveClick: user_contact "+ucid[0]);
            db.setTransactionSuccessful();
            startActivity(new Intent(AddUser.this, MainActivity.class));


        } catch (Exception e) {

        }finally {
            db.endTransaction();
        }

    }
}
