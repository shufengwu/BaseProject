package com.delta.smt.ui.main.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Shufeng.Wu on 2016/12/27.
 */

public class ProgressUiUtils {
    //private static Context context;

    private static int maxProgress;
    private static ProgressDialog progressDialog = null;
    private static Notification notifyBuilder;
    private static NotificationCompat.Builder builder;
    private static NotificationManager mNotificationManager;
    private static int notifyProgress = 0;
    private static int updateCount_pBar = 0;
    private static int updateCount_nofi = 0;


    //显示ProgerssDialog
    public static void showProgerssDialog(final Context context/*,final int maxProgress*/) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("更新");
        progressDialog.setIcon(android.R.drawable.ic_dialog_info);
        progressDialog.setMessage("正在下载更新...");
        progressDialog.setCancelable(false);
        progressDialog.setMax(maxProgress);
        progressDialog.show();
    }

    //显示Notification
    public static void showNotification(final Context context) {
        /*Intent intentClick = new Intent(context, NotificationBroadcastReceiver.class);
        intentClick.setAction("notification_clicked");
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentCancel = new Intent(context, NotificationBroadcastReceiver.class);
        intentCancel.setAction("notification_cancelled");
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context, 0, intentCancel, PendingIntent.FLAG_UPDATE_CURRENT);*/

        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setAutoCancel(true);
        builder.setOngoing(false);
        builder.setShowWhen(true);
        builder.setContentTitle("正在下载更新... " + notifyProgress + "%");
        builder.setProgress(100, notifyProgress, false);
        /*builder.setContentIntent(pendingIntentClick);
        builder.setDeleteIntent(pendingIntentCancel);*/
        notifyBuilder = builder.build();
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notifyBuilder);

    }

    public static void setDialogProgress(int progerss){
        //至少增加10%再更新
        if (progerss / (progressDialog.getMax() / 10) >= updateCount_pBar) {
            updateCount_pBar += 1;
            progressDialog.setProgress(progerss);
            progressDialog.setProgressNumberFormat(StringUtils.getChangedContentLength(progerss) + "/" + StringUtils.getChangedContentLength(progressDialog.getMax()));
        }
    }

    public static void setNotifyProgress(int progress){
        if (progress / (maxProgress / 10) >= updateCount_nofi) {
            updateCount_nofi += 1;
            notifyProgress = StringUtils.getPercentProgress(progress, maxProgress);
            builder.setContentTitle("正在下载更新... " + notifyProgress + "%");
            builder.setProgress(100, notifyProgress, false);
            if (notifyProgress == 100) {
                builder.setContentTitle("下载完成 " + notifyProgress + "%");
            }
            mNotificationManager.notify(0, builder.build());
        }
    }

    /*class NotificationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals("notification_clicked")) {
                //处理点击事件
                //Message.obtain(NetworkLib.myHandler, NetworkLib.CASE_APP_INSTALL_REPLACE).sendToTarget();

            }else if (action.equals("notification_cancelled")) {
                //处理滑动删除事件
            }
        }
    }*/
}
