package com.example.sun_safe_app.broadcast;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.MyApplication;
import com.example.sun_safe_app.R;

public class MyBroadcastReceiver2 extends BroadcastReceiver {
    private static Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.intent = intent;
        if (intent.getAction().equals("great")) {
            scheduleAlarms(context);
            //Do your work


            // Set the Toast
//        Toast.makeText(context, "It is time to record today's pain data!", Toast.LENGTH_LONG).show();


            // Set the Notification
            NotificationManager mNotificationManager;
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context.getApplicationContext(), "notify_002");
//        Intent ii = new Intent(context.getApplicationContext(), LoginActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//        bigText.bigText("high");
//        bigText.setBigContentTitle("Today's Bible Verse");
//        bigText.setSummaryText("Text in detail");
//        mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.drawable.ic_4_blue);
            mBuilder.setContentTitle("Sun Safe");
            mBuilder.setContentText("You need to top-up your sunscreen now");
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigText);
            // Create an explicit intent for an Activity in your app
//        Intent intent2 = new Intent(MyApplication.getInstance(),MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance(), 0, intent2, 0);
//        mBuilder.setContentIntent(pendingIntent);
//        mBuilder.setAutoCancel(true);
            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);

            Intent intent2 = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setAutoCancel(true);

            mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // === Removed some obsoletes
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "Your_channel_id";
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(channel);
                mBuilder.setChannelId(channelId);
            }
            mNotificationManager.notify(0, mBuilder.build());
        }

    }
    static void scheduleAlarms(Context ctxt) {
        AlarmManager mgr = (AlarmManager) ctxt.getSystemService(Context.ALARM_SERVICE);
//        Intent i = new Intent(ctxt, MyBroadcastReceiver2.class);
        Intent i = intent;
        i.setAction("great");
        PendingIntent pi = PendingIntent.getBroadcast(ctxt, 1, i, 0);
        mgr.cancel(pi);
        Log.e("sunsreen", "onetime");
        int min = i.getIntExtra("time",0);
        mgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+ 6000*min, pi);
    }


}
