package com.guagua.moses.servicetest.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.guagua.moses.servicetest.IMyAidlInterface;
import com.guagua.moses.servicetest.MainActivity;
import com.guagua.moses.servicetest.R;

/**
 * Created by Moses on 2015
 */
public class MyService extends Service {
    private static final String TAG = "MyService";
    private NotificationCompat.Builder builder;

    @Override
    public void onCreate() {
        Log.e(TAG, "MyService onCreate()>>>>>>>>");
        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("通知标题")
                .setContentText("通知内容")
                .setContentIntent(pendingIntent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "MyService onStartCommand()>>>>>>>>");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开始执行后台任务
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "MyService onDestroy()>>>>>>>>");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub() {
        @Override
        public void download(int process) throws RemoteException {
            builder.setProgress(100, process, false);
            startForeground(1, builder.build());
        }
    };

}