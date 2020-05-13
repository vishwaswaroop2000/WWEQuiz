package com.example.wwequiz;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.wwequiz.QuizNotification.CHANNEL_ID;

public class QuizService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Wwe QUIZ")
                .setContentText("Click here to answer the latest quiz questions!")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
