package com.guagua.moses.servicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.guagua.moses.servicetest.services.MyService;

/**
 * Created by Moses on 2015
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private Button startServiceBtn, stopServiceBtn, bindServiceBtn, unbindServiceBtn;
    private IMyAidlInterface aidlInterface;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlInterface = IMyAidlInterface.Stub.asInterface(service);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (i < 101) {
                        try {
                            aidlInterface.download(i);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(80);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        i++;
                    }
                }
            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    protected void initView() {
        startServiceBtn = (Button) findViewById(R.id.start_service_btn);
        stopServiceBtn = (Button) findViewById(R.id.stop_service_btn);
        bindServiceBtn = (Button) findViewById(R.id.bind_service_btn);
        unbindServiceBtn = (Button) findViewById(R.id.unbind_service_btn);
        startServiceBtn.setOnClickListener(this);
        stopServiceBtn.setOnClickListener(this);
        bindServiceBtn.setOnClickListener(this);
        unbindServiceBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service_btn:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service_btn:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service_btn:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service_btn:
                unbindService(serviceConnection);
                break;
            default:
                break;

        }
    }
}
