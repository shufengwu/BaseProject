package com.delta.smt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.GridItemDecoration;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.Update;
import com.delta.smt.ui.brands.BrandListActivity;
import com.delta.smt.ui.main.MyDownloadService;
import com.delta.smt.ui.main.di.DaggerMainComponent;
import com.delta.smt.ui.main.di.MainModule;
import com.delta.smt.ui.main.mvp.MainContract;
import com.delta.smt.ui.main.mvp.MainPresenter;
import com.delta.smt.ui.main.utils.PkgInfoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActiviy<MainPresenter> implements CommonBaseAdapter.OnItemClickListener<String>, MainContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<String> fuctionString;

    //Shufeng.Wu Update
    private long fileSize = 0;
    MyHandler myHandler = new MyHandler(this);


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).mainModule(new MainModule(this)).build().inject(this);
    }

    @Override
    protected void initView() {

        CommonBaseAdapter<String> adapter = new CommonBaseAdapter<String>(this, fuctionString) {
            @Override
            protected void convert(CommonViewHolder holder, String item, int position) {

                Log.e(TAG, "convert: " + item);
                holder.setText(R.id.tv_function, item);
            }

            @Override
            protected int getItemViewLayoutId(int position, String item) {
                return R.layout.item_function;
            }
        };
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new GridItemDecoration(this));
        adapter.setOnItemClickListener(this);
        getPresenter().checkUpdate();
    }

    @Override
    protected void initData() {
        fuctionString = new ArrayList<>();
        fuctionString.add("PCB库房");
        fuctionString.add("PCB库房1");
        fuctionString.add("PCB库房2");
        fuctionString.add("PCB库房3");
        fuctionString.add("PCB库房4");
        fuctionString.add("sample");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    public void onItemClick(View view, String item, int position) {
        Log.e(TAG, "onItemClick: " + item + position);
        ToastUtils.showMessage(this, item);
        switch (item) {
            case "PCB库房":
                break;
            case "PCB库房1":
                break;
            case "PCB库房2":
                break;
            case "PCB库房3":
                break;
            case "PCB库房4":
                break;
            case "sample":
                IntentUtils.showIntent(this, BrandListActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void showExistUpdateDialog(final Update update) {

        if (Integer.parseInt(update.getVersionCode()) > PkgInfoUtils.getVersionCode(MainActivity.this)) {
            String message_wifi = "当前版本为:" + PkgInfoUtils.getVersionName(this) + " Code:" + PkgInfoUtils.getVersionCode(this)
                    + "\n发现新版本:" + update.getVersion() + " Code:" + update.getVersionCode()
                    + "\n更新日志:"
                    + "\n" + update.getDescription();
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(message_wifi)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                /*updateCount_pBar = 0;
                                updateCount_nofi = 0;
                                showNotification(context);
                                downNewApk(updateBean.getUrl(), context);*/

                            //getPresenter().download("http://172.22.35.177:8081/app-debug.apk");
                            Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
                            intent.putExtra("urlStr", update.getUrl());
                            startService(intent);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        System.out.println(update.getDescription());
    }

    @Override
    public void downloadCompleted() {
        //Toast.makeText(this,"下载完成",Toast.LENGTH_SHORT).show();
        System.out.println("下载完成");

    }

    @Override
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
        Message.obtain(myHandler,0).sendToTarget();
        //ProgressUiUtils.showProgerssDialog(MainActivity.this);
        System.out.println("setFileSize");
    }

    private static class MyHandler extends Handler {
        private Context context;

        MyHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //wifi下更新提示对话框
                case 0:
                    //
                    System.out.println("ProgerssDialog");
                    break;
                /*case 0:
                    String message_wifi = "当前版本为:" + PkgInfo.getVersionName(context) + " Code:" + PkgInfo.getVersionCode(context)
                            + "\n发现新版本:" + updateBean.getVersion() + " Code:" + updateBean.getVersionCode()
                            + "\n更新日志:"
                            + "\n" + updateBean.getDescription();
                    new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage(message_wifi)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton(POSI_BUTTON_UPDATE_LOG, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    updateCount_pBar = 0;
                                    updateCount_nofi = 0;
                                    showNotification(context);
                                    downNewApk(updateBean.getUrl(), context);
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton(NEGA_BUTTON_CANCEL, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create()
                            .show();


                    break;
                case 1:
                    progressDialog.dismiss();
                    //防止重复显示
                    if (alertDialog_case2 != null && alertDialog_case2.isShowing()) {
                        alertDialog_case2.dismiss();
                    }
                    AlertDialog.Builder updateBuilder = new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("更新下载完成，是否现在安装并替换本应用？")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton(POSI_BUTTON_OTHER, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
                                    installApk(context, file);
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton(NEGA_BUTTON_CANCEL, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    alertDialog_case2 = updateBuilder.create();
                    alertDialog_case2.show();

                    break;
                case 2:
                    int p1 = (int) msg.obj;
                    //至少增加10%再更新
                    if (p1 / (maxProgress / 10) >= updateCount_pBar) {
                        updateCount_pBar += 1;
                        progressDialog.setProgress(p1);
                        progressDialog.setProgressNumberFormat(NumTrans.getChangedContentLength(p1) + "/" + NumTrans.getChangedContentLength(progressDialog.getMax()));
                    }
                    break;
                case 3:
                    //至少增加10%再更新
                    int p2 = (int) msg.obj;
                    if (p2 / (maxProgress / 10) >= updateCount_nofi) {
                        updateCount_nofi += 1;
                        notifyProgress = NumTrans.getPercentProgress(p2, maxProgress);
                        builder.setContentTitle("正在下载更新... " + notifyProgress + "%");
                        builder.setProgress(100, notifyProgress, false);
                        if (notifyProgress == 100) {
                            builder.setContentTitle("下载完成 " + notifyProgress + "%");
                        }
                        mNotificationManager.notify(0, builder.build());
                    }

                    break;
                case 4:
                    new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("当前网络处于非WiFi网络状态下，使用移动网络下载更新？")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton(POSI_BUTTON_OTHER, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    updateCount_pBar = 0;
                                    updateCount_nofi = 0;
                                    showNotification(context);
                                    downNewApk(updateBean.getUrl(), context);
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton(NEGA_BUTTON_CANCEL, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create()
                            .show();
                    break;*/
            }
        }
    }

}
