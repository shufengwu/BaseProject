package com.delta.smt.ui.main;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import com.delta.smt.api.DownloadService;
import com.delta.smt.entity.Download;
import com.delta.smt.ui.main.utils.FileUtils;
import com.delta.smt.ui.main.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Shufeng.Wu on 2016/12/27.
 */

public class MyDownloadService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    int downloadCount = 0;
    private String urlStrl;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    public MyDownloadService() {
        super("DownloadService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        urlStrl =intent.getStringExtra("urlStr");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());
        download();
    }

    public void download() {
        final File file = new File(Environment.getExternalStorageDirectory(), "update.apk");
        if (file.exists()) {
            file.delete();
        }

        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                //不频繁发送通知，防止通知栏下拉卡顿
                int progress = (int) ((bytesRead * 100) / contentLength);
                if ((downloadCount == 0) || progress > downloadCount) {
                    Download download = new Download();
                    download.setTotalFileSize(contentLength);
                    download.setCurrentFileSize(bytesRead);
                    download.setProgress(progress);

                    sendNotification(download);
                }
            }
        });

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getHostName(urlStrl))
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        /*getService().*/
        retrofit.create(DownloadService.class).download(urlStrl)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        System.out.println("开始下载");
                        try {
                            FileUtils.writeFile(inputStream, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InputStream>(){
                    @Override
                    public void onCompleted() {
                        downloadCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(InputStream inputStream) {

                    }
                });

    }

    private void downloadCompleted() {
        Download download = new Download();
        download.setProgress(100);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("File Downloaded");
        notificationManager.notify(0, notificationBuilder.build());

        //安装apk
        final File file = new File(Environment.getExternalStorageDirectory(), "update.apk");
        installApk(this,file);
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(outputFile), "application/vnd.android.package-archive");
        startActivity(intent);*/
    }

    //安装apk
    private static void installApk(Context context, File file) {
        if (Build.VERSION.SDK_INT < 24) {
            Intent intents = new Intent();
            intents.setAction("android.intent.action.VIEW");
            intents.addCategory("android.intent.category.DEFAULT");
            intents.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intents);
        } else {
            if (file.exists()) {
                Uri uri = FileProvider.getUriForFile(context, "hehe.fileprovider", file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                context.startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    private void sendNotification(Download download) {

        //sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(
                StringUtils.getDataSize(download.getCurrentFileSize()) + "/" +
                        StringUtils.getDataSize(download.getTotalFileSize()));
        notificationManager.notify(0, notificationBuilder.build());
    }


    /*private void sendIntent(Download download) {

        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }*/
    public static String getHostName(String urlString) {
        String head = "";
        int index = urlString.indexOf("://");
        if (index != -1) {
            head = urlString.substring(0, index + 3);
            urlString = urlString.substring(index + 3);
        }
        index = urlString.indexOf("/");
        if (index != -1) {
            urlString = urlString.substring(0, index + 1);
        }
        return head + urlString;
    }
}
