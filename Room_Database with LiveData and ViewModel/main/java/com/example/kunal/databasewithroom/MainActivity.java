package com.example.kunal.databasewithroom;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);


        AppDatabase db = AppDatabase.getDatabase(MainActivity.this);

        final List<User> users=db.userDao().getAllUser();
        final List<Contact> contacts=db.contactDao().getAllContactDetails();

        List<ModelUserContact> modelUserContacts= buildList(users,contacts);


        Adapter adapter=new Adapter(modelUserContacts);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,AddUser.class));

            }
        });



        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        Intent intent=new Intent(MainActivity.this,MarkActivity.class);
                        intent.putExtra("id",String.valueOf(users.get(position).getId()));
                        startActivity(intent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    private List<ModelUserContact> buildList(List<User> users, List<Contact> contacts) {

        List<ModelUserContact> modelUserContacts=new ArrayList<>();

        for (int i=0;i<users.size();i++){

            User u=users.get(i);
            Contact c=contacts.get(i);

            modelUserContacts.add(new ModelUserContact(u.getId(),u.getName(),u.getPhone(),c.getId(),c.getMobile(),c.getLandLine(),c.getParentMobile()));
        }
        return modelUserContacts;
    }


}
