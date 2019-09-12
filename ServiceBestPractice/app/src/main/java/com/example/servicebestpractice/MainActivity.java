package com.example.servicebestpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyDownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyDownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startDownload = findViewById(R.id.start_download);
        startDownload.setOnClickListener(this);
        Button pauseDownload = findViewById(R.id.pause_download);
        pauseDownload.setOnClickListener(this);
        Button cancelDownload = findViewById(R.id.cancel_download);
        cancelDownload.setOnClickListener(this);

        //启动并绑定服务
        Intent intent = new Intent(this, MyDownloadService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View view) {
        if (downloadBinder == null)
            return;

        switch (view.getId()){
            case R.id.start_download:
                String url = "http://download.qt.io/official_releases/qt/5.12/5.12.0/qt-opensource-windows-x86-5.12.0.exe";
                downloadBinder.startDownload(url);
                break;
            case R.id.pause_download:
                downloadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }
    }
}
