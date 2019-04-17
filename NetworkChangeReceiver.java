package placeme.octopusites.com.placeme;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NetworkChangeReceiver extends BroadcastReceiver {


    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        try {
            if (isAppOnForeground(context)) {

                ActivityManager mngr = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);
                if (taskList.size() > 0) {
                    if (taskList.get(0).topActivity.getClassName().equals("placeme.octopusites.com.placeme.NoInternet")) {
                        Log.d("TAG", "NoInternet is Visible ");
                    } else {
                        Log.d("TAG", "Checking.. ");
                        check();
                    }
                }


            } else {
                Log.d("TAG", "kunal la gf nay");
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    public void isNoInternetVisible() {

        ActivityManager mngr = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);
        if (taskList.size() > 0) {
            if (taskList.get(0).topActivity.getClassName().equals("placeme.octopusites.com.placeme.NoInternet")) {
                Log.d("TAG", "NoInternet is Visible: ");
            }
        }

    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public void check() {

        try {

            new Echo().execute().get(5, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    private class Echo extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String echo_number = "" + new Random().nextInt();
            Log.d("TAG", "kill process s:" + echo_number);

            params.add(new BasicNameValuePair("e", echo_number));
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.makeHttpRequest(Z.url_CheckInternet, "GET", params);

            if (json != null) {
                try {
                    String info = json.getString("info");
                    Log.d("TAG", "kill process r:" + info);

                    if (info.equals(echo_number)) {
                        return true;
                    } else
                        return false;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {

            if (aVoid)
                Log.d("TAG", "lov u all");
            else {
                Intent i = new Intent(mContext, NoInternet.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_HISTORY);
                mContext.startActivity(i);
            }

        }
    }


}