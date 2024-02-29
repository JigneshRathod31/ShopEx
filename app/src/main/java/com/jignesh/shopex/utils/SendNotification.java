package com.jignesh.shopex.utils;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.jignesh.shopex.R;

public class SendNotification {
    public static void sendNotification(Context context, String title, String message, Intent intent) {
        try {
            NotificationCompat.Builder nb = new NotificationCompat.Builder(context, "09041516")
                    .setSmallIcon(R.drawable.shopexicon_rounded)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true);
            if(intent!=null){
                PendingIntent pi = PendingIntent.getActivity(context, 0, intent,  PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                nb.setContentIntent(pi);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManagerCompat nm = NotificationManagerCompat.from(context);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    nm.notify(1, nb.build());
                }
            }
        } catch (Exception e) {
            Log.d("dalle", e+"");
        }
    }
}
