package com.example.admin.expandablefloatingbutton;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab1, fab2, fab3;
    boolean isFABOpen;

    TextView fab3Title, fab2Title, fab1Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        fab1Title = findViewById(R.id.fab1Title);
        fab2Title = findViewById(R.id.fab2Title);
        fab3Title = findViewById(R.id.fab3Title);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "fab1", Toast.LENGTH_SHORT).show();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "fab2", Toast.LENGTH_SHORT).show();
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "fab3", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(!isFABOpen){
            super.onBackPressed();
        }else{
            closeFABMenu();
        }
    }

    private void showFABMenu() {
        isFABOpen = true;

        fab1Title.setVisibility(View.VISIBLE);
        fab2Title.setVisibility(View.VISIBLE);
        fab3Title.setVisibility(View.VISIBLE);

        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));

        fab1Title.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2Title.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3Title.animate().translationY(-getResources().getDimension(R.dimen.standard_155));


    }

    private void closeFABMenu() {
        isFABOpen = false;

        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);

        fab1Title.animate().translationY(0);
        fab2Title.animate().translationY(0);
        fab3Title.animate().translationY(0);

        fab1Title.setVisibility(View.GONE);
        fab2Title.setVisibility(View.GONE);
        fab3Title.setVisibility(View.GONE);

    }
}
