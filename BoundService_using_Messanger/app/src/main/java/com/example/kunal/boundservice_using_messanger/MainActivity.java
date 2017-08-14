package com.example.kunal.boundservice_using_messanger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onclickCmd1(View view) {

        Message message=Message.obtain(null,BoundServiceMessanger.COMMAND_1,0,0,0);
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onclickCmd2(View view) {
        Message message=Message.obtain(null,BoundServiceMessanger.COMMAND_2,0,0,0);
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onclickCmd3(View view) {
        Message message=Message.obtain(null,BoundServiceMessanger.COMMAND_3,0,0,0);
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }






    public void onClickBindButton(View view) {

        Intent intent=new Intent(this,BoundServiceMessanger.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);

        Toast.makeText(MainActivity.this, "service bound successfully", Toast.LENGTH_SHORT).show();

    }




    ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            messenger=new Messenger(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
