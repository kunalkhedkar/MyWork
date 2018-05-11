package com.example.admin.runtimepermissionsdesignpattern;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivityWithPermission {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCameraClicked(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have already CAMERA permission do stuff", Toast.LENGTH_SHORT).show();
        } else {
            requestRunTimePermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSITION_REQUEST);
        }

    }

    public void onContactClicked(View view) {
        Log.d("TAG", "onContactClicked: contact permission");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have already WRITE_CONTACTS permission do stuff", Toast.LENGTH_SHORT).show();
        } else {
            requestRunTimePermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSION_REQUEST_WRITE_CONTACTS);
        }
    }

    public void onCallPhoneClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have already have CALL_PHONE permission do stuff", Toast.LENGTH_SHORT).show();
        } else {
            requestRunTimePermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST);
        }

    }

    public void onSmsClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have already have SEND_SMS permission do stuff", Toast.LENGTH_SHORT).show();
        } else {
            requestRunTimePermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_SEND_SMS_PERMISSITION_REQUEST);
        }
    }

    public void onStorageClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You have already have STORAGE permission do stuff", Toast.LENGTH_SHORT).show();
        } else {
            requestRunTimePermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_STORAGE_PERMISSITION_REQUEST);
        }
    }

    public void onLocationClicked(View view) {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "You have already have Location permission do stuff", Toast.LENGTH_SHORT).show();
        }else{
            requestRunTimePermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_LOCATION_PERMISSITION_REQUEST);
        }
    }


    public void onCameraStorageClicked(View view) {

        if (checkMultiplePermission(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})) {
            Toast.makeText(this, "You have already -- CAMERA and Storage -- permission do stuff", Toast.LENGTH_SHORT).show();
        } else {
            requestRunTimePermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length == 1) {
            if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS || requestCode == MY_PERMISSION_REQUEST_WRITE_CONTACTS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Contact Permission Granted", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == MY_CAMERA_PERMISSITION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == CALL_PHONE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "CALL PHONE Permission Granted", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == MY_SEND_SMS_PERMISSITION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SEND SMS permission granted", Toast.LENGTH_SHORT).show();
            }
            if(requestCode== 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                Toast.makeText(this, "Camera +Storage permission granted", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
