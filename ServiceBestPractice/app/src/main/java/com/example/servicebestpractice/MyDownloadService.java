package com.example.servicebestpractice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;

public class MyDownloadService extends Service {
    private DownloadAsyncTask downloadAsyncTask;
    private String downloadUrl;

    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            downloadAsyncTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.makeText(MyDownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadAsyncTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download failed", -1));
            Toast.makeText(MyDownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPaused() {
            downloadAsyncTask = null;
            Toast.makeText(MyDownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadAsyncTask = null;
            stopForeground(true);
            Toast.makeText(MyDownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    public MyDownloadService() {
    }

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (downloadAsyncTask == null) {
                downloadUrl = url;
                downloadAsyncTask = new DownloadAsyncTask(downloadListener);
                downloadAsyncTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(MyDownloadService.this, "Download ...",
                        Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (downloadAsyncTask != null) {
                downloadAsyncTask.pauseDownload();
                Toast.makeText(MyDownloadService.this, "Pausing...", Toast.LENGTH_SHORT).show();
            }
        }

        public void cancelDownload() {
            if (downloadAsyncTask != null) {
                downloadAsyncTask.cancelDownload();
                //取消下载需将文件删除
                if (downloadUrl != null) {
                    //取到文件，包括保存的路径
                    String filename = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory, filename);
                    if (file.exists()) {
                        file.delete();
                    }
                    Toast.makeText(MyDownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //通知
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private String notificationId = "channelId";
    private String notificationName = "channelName";

    private Notification getNotification(String title, int progress) {
        //create Notification channel
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);
            getNotificationManager().createNotificationChannel(channel);
        }
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        Notification notification = builder.build();
        return notification;
    }
}
