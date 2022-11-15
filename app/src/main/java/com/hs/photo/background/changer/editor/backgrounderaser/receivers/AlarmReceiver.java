package com.hs.photo.background.changer.editor.backgrounderaser.receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.activity.SplashScreen;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.hs.photo.background.changer.editor.backgrounderaser.activity.SplashScreen.mFirebaseAnalytics;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("inMaorningNotification","true");

        SharedPreferences prefs = context.getSharedPreferences("Notifications_Cal_check", MODE_PRIVATE);
        boolean startServ = prefs.getBoolean("STARTSERVICE", false);
        if (startServ){

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "EveningAlarm");
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent("ALARM_SELECTION", bundle);
            }



            RemoteViews remoteViewCollapse = new RemoteViews(context.getPackageName(), R.layout.notification_collapse);

            RemoteViews remoteViewExpand = new RemoteViews(context.getPackageName(), R.layout.notification_expand);

            Intent launchIntent = new Intent(context, SplashScreen.class);
            Random random = new Random();
            int importance = NotificationManager.IMPORTANCE_HIGH;
            String channelId = "channel-9";
            String channelName = "Backgroundchanger frames";
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(remoteViewCollapse)
                    .setCustomBigContentView(remoteViewExpand);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(launchIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            mBuilder.setContentIntent(resultPendingIntent);
            notificationManager.notify(1, mBuilder.build());

        }
        else {
            SharedPreferences.Editor editor = context.getSharedPreferences("Notifications_Cal_check", MODE_PRIVATE).edit();
            editor.putBoolean("STARTSERVICE", true);
            editor.apply();
        }

    }
}
