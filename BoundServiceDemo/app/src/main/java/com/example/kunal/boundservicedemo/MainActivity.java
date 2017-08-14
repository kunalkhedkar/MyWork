package com.example.kunal.boundservicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView show;
    private BoundServiceTest boundServiceTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show= (TextView) findViewById(R.id.show);

        Intent intent=new Intent(this,BoundServiceTest.class);
        bindService(intent,mserviceConnection,BIND_AUTO_CREATE);

    }

    public void onClickMessageButton(View view) {

        show.setText(boundServiceTest.getMessage());
    }

    public void onClickNumberButton(View view) {
        show.setText(boundServiceTest.getNumber()+"");
    }



    private ServiceConnection mserviceConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {



            BoundServiceTest.localBinderService localBinderService= (BoundServiceTest.localBinderService) iBinder;
            boundServiceTest=localBinderService.getService();
            Toast.makeText(MainActivity.this, "Activity been connected  to Bounded service successfully", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(MainActivity.this, "Activity been Disconnected to Bounded service successfully", Toast.LENGTH_SHORT).show();
        }
    };
}
