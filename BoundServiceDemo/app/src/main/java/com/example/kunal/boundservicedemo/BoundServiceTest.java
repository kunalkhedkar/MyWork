package com.example.kunal.boundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Created by KunaL on 14-Aug-17.
 */
public class BoundServiceTest extends Service {

localBinderService mbinder=new localBinderService();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    class localBinderService extends Binder{

        BoundServiceTest getService(){
            return BoundServiceTest.this;
        }

    }



    public String getMessage(){
        return "hello , welcome to my world";
    }


    public int getNumber(){

        final Random mGenerator = new Random();
        return mGenerator.nextInt(100);

    }




}
