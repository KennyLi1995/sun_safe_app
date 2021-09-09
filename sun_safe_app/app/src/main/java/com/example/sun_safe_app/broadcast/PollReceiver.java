package com.example.sun_safe_app.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class PollReceiver extends BroadcastReceiver {
    private static final int PERIOD = 60000; // 1 minutes

    @Override
    public void onReceive(Context ctxt, Intent i) {
        if (i.getAction().equals("great")) {
            scheduleAlarms(ctxt);
            //Do your work
        }
    }

    static void scheduleAlarms(Context ctxt) {
        AlarmManager mgr = (AlarmManager) ctxt.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(ctxt, PollReceiver.class);
        i.setAction("great");
        PendingIntent pi = PendingIntent.getBroadcast(ctxt, 0, i, 0);
        mgr.cancel(pi);
        mgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+PERIOD, pi);
    }

}