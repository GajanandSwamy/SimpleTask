package com.example.gajanand.simpletask;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {

    List<UserListResponse> userListResponse;

    boolean datadownloded = false;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FetchFromApi();


            }
        });

        thread.start();


        return START_STICKY;
    }

    private void sendNotification() {

        this.stopSelf();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext(), RecyclerViewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, ii, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Downloaded");
        bigText.setBigContentTitle("Download");
        bigText.setSummaryText("Status");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setStyle(bigText).setDefaults(Notification.DEFAULT_ALL);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());


    }

    private void FetchFromApi() {


        (Api.getClient().getUsersList()).enqueue(new Callback<List<UserListResponse>>() {
            @Override
            public void onResponse(Call<List<UserListResponse>> call, Response<List<UserListResponse>> response) {
                userListResponse = response.body();
//                setDataInRecyclerView();
                MyDb myDb = MyDb.getInstance(MyService.this);


                datadownloded = myDb.insertUserDetails(userListResponse);

                Log.d("Gajanand", "onResponse: " + datadownloded);

                if (datadownloded) {
                    sendNotification();
                }


            }

            @Override
            public void onFailure(Call<List<UserListResponse>> call, Throwable t) {
                // if error occurs in network transaction then we can get the error in this method.
//                Toast.makeText(GetActivity.this, t.toString(), Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.stopSelf();
    }
}
