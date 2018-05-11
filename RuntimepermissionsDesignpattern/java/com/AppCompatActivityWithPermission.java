package com.example.admin.runtimepermissionsdesignpattern;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AppCompatActivityWithPermission extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected int MY_PERMISSIONS_REQUEST_READ_CONTACTS=20;
    protected int MY_PERMISSION_REQUEST_WRITE_CONTACTS=30;
    protected int MY_PHOTO_TAGGING_PERMISSIONS =40;
    protected int CALL_PHONE_REQUEST =50;
    protected int MY_CAMERA_PERMISSITION_REQUEST =60;
    protected int MY_SEND_SMS_PERMISSITION_REQUEST =70;
    protected int MY_LOCATION_PERMISSITION_REQUEST =80;
    protected int MY_STORAGE_PERMISSITION_REQUEST =90;

    protected final String [] permissionsNeededForPhotoTagging =new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION};


    protected void requestRunTimePermissions(final Activity activity, final String [] permissions, final int customPermissionConstant){
        if(permissions.length==1){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0])){

                Snackbar.make(findViewById(android.R.id.content),"App needs permission to work",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(activity,permissions,customPermissionConstant);
                            }
                        }).show();
            }else {
                ActivityCompat.requestPermissions(this,new String[]{permissions[0]},customPermissionConstant);
            }
//        }else if(permissions.length>1 && customPermissionConstant== MY_PHOTO_TAGGING_PERMISSIONS){
        }else if(permissions.length>1 ){
            final List<String> deniedPermissions=new ArrayList<String>();

            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_DENIED){
                    deniedPermissions.add(permission);
                }
            }
            if(deniedPermissions.size()==1){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,deniedPermissions.get(0))){

                    Snackbar.make(findViewById(android.R.id.content),"App needs permission to work",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String [] deniedArray=deniedPermissions.toArray(new String[deniedPermissions.size()]);
                                    ActivityCompat.requestPermissions(activity,deniedArray,customPermissionConstant);
                                }
                            }).show();
                }else {

                    String [] deniedArray=deniedPermissions.toArray(new String[deniedPermissions.size()]);
                    ActivityCompat.requestPermissions(activity,deniedArray,customPermissionConstant);
                }
            }else if(deniedPermissions.size()>1){
                final String [] temp=deniedPermissions.toArray(new String[deniedPermissions.size()]);
                if(isFirstTimeAskForPermission(String.valueOf(customPermissionConstant))){     // so tht no need show rationale Else we need to check should rationle for all permission request
                    ActivityCompat.requestPermissions(activity,temp,customPermissionConstant);
                }else{
                    Snackbar.make(findViewById(android.R.id.content),"This functionality needs multiple app permissions",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(activity,temp,customPermissionConstant);
                                }
                            }).show();
                }

            }
        }
    }

    protected boolean isFirstTimeAskForPermission(String KEY_customPermissionRequestCode){
        SharedPreferences sharedPreferences=getSharedPreferences(KEY_customPermissionRequestCode,MODE_PRIVATE);
        boolean isFirstTime=sharedPreferences.getBoolean(KEY_customPermissionRequestCode,true);
        if(isFirstTime){
            SharedPreferences.Editor editor=sharedPreferences.edit();

            editor.putBoolean(KEY_customPermissionRequestCode,false);
            editor.commit();
        }
        return isFirstTime;
    }



    protected boolean checkWhetherAllPermissionsPresentForPhotoTagging(){
        for(String permission: permissionsNeededForPhotoTagging){
            if(ActivityCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }

    protected String[] getDeniedPermissionsAmongPhototaggingPermissions(){
        String [] deniedPermissionsArray;
        final List<String> deniedPermissions=new ArrayList<String>();
        for(String permission: permissionsNeededForPhotoTagging){
            if(ActivityCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(permission);
            }
        }
        deniedPermissionsArray=deniedPermissions.toArray(new String[deniedPermissions.size()]);
        return deniedPermissionsArray;
    }


    protected boolean checkMultiplePermission(final String [] permissionsToCheck){
        for(String permission: permissionsToCheck){
            if(ActivityCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }
}