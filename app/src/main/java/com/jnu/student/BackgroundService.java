//package com.jnu.student;
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//
//import androidx.core.app.NotificationCompat;
//
//public class BackgroundService extends Service {
//
//    private static final int NOTIFICATION_ID = 1;
//    private static final String CHANNEL_ID = "MyChannel";
//
//    public BackgroundService() {
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // 在服务启动时显示通知
//        startForeground(1, new Notification());
//        showNotification();
//
//        return START_STICKY;
//    }
//
//    private void showNotification() {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("My App")
//                .setContentText("App is running in the background.")
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setContentIntent(pendingIntent)
//                .build();
//
//        startForeground(NOTIFICATION_ID, notification);
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // 不需要绑定服务，返回 null
//        return null;
//    }
//}
