package com.example.kunal.boundservice_using_messanger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by KunaL on 14-Aug-17.
 */
public class BoundServiceMessanger extends Service {

public static final int COMMAND_1=1;
    public static final int COMMAND_2=2;
    public static final int COMMAND_3=3;

    Messenger messenger=new Messenger(new Incominghandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
            return messenger.getBinder();
    }



    class Incominghandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case COMMAND_1:
                    Toast.makeText(getApplicationContext(), "Command 1", Toast.LENGTH_SHORT).show();
                                break;

                case COMMAND_2:
                    Toast.makeText(getApplicationContext(), "Command 22", Toast.LENGTH_SHORT).show();
                    break;

                case COMMAND_3:
                    Toast.makeText(getApplicationContext(), "Command 333", Toast.LENGTH_SHORT).show();
                    break;
            }



        }
    }



}
