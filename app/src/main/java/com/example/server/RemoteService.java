package com.example.server;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {

    private static final String TAG = "RemoteService-";

    private final IRemoteService.Stub binder = new IRemoteService.Stub() {
        @Override
        public int getPid() throws RemoteException {
            Log.d(TAG, "getPid: ");
            return Process.myPid();
        }

        @Override
        public Rect getRect() throws RemoteException {
            Rect rect = new Rect(10, 11, 12, 13);
            Log.d(TAG, "getRect: " + rect);
            Log.d(TAG, "getRect: left: " + rect.left + ", top: " + rect.top
                    + ", right: " + rect.right + ", bottom: " + rect.bottom);
            return rect;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            // Does nothing.
        }
    };

    public RemoteService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();

        createNotificationChannel();
        startForeground(ONGOING_NOTIFICATION_ID, buildNotification());
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return binder;
    }

    // --------- START create notification ---------
    private final int ONGOING_NOTIFICATION_ID = 1;
    private final String CHANNEL_ID = "CHANNEL_ID";
    private final String CHANNEL_NAME = "CHANNEL_NAME";

    private void createNotificationChannel() {
        Log.d(TAG, "---> MyForegroundService $ createNotificationChannel");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                Log.d(TAG, "---> manager.createNotificationChannel(channel)");
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification buildNotification() {
        Log.d(TAG, "---> MyForegroundService $ buildNotification");
        // If the notification supports a direct reply action, use
        // PendingIntent.FLAG_MUTABLE instead.
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID);
            Log.d(TAG, "---> Notification.Builder   >= Build.VERSION_CODES.O");
        } else {
            builder = new Notification.Builder(this);
            Log.d(TAG, "---> Notification.Builder   < Build.VERSION_CODES.O");
        }

        return builder
                .setContentTitle("Foreground Service")
                .setContentText("Running...")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntent)
                .build();
    }
    // --------- END create notification ---------

}