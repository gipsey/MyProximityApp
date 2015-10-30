package com.example.daviddebre.myproximityapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by daviddebre on 30/10/15.
 */
public class ProximityIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String key = LocationManager.KEY_PROXIMITY_ENTERING;

        Boolean entering = intent.getBooleanExtra(key, false);

        if (entering) {
            Toast.makeText(context, "entering", Toast.LENGTH_SHORT).show();
            Log.d(getClass().getSimpleName(), "entering");
        } else {
            Toast.makeText(context, "exiting", Toast.LENGTH_SHORT).show();
            Log.d(getClass().getSimpleName(), "exiting");
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setWhen(System.currentTimeMillis())
                .setLights(Color.WHITE, 1500, 1500)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle("Proximity Alert!")
                .setContentText("You are near your point of interest.")
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        notificationManager.notify(0, notification);
    }

}
