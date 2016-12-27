package com.delta.smt.ui.main.mvp;

import android.os.Environment;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.api.DownloadService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Update;
import com.delta.smt.ui.main.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 12:49
 */


public class MainModel extends BaseModel<ApiService> implements MainContract.Model {

    public MainModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Update> getUpdate() {
        return getService().getUpdate().compose(RxsRxSchedulers.<Update>io_main());
    }

    public void download(String urlStr, Subscriber subscriber /*,Action1<ResponseBody> action1*/){
        System.out.println("download");
        final File file = new File(Environment.getExternalStorageDirectory(), "update.apk");
        if (file.exists()) {
            file.delete();
        }
        OkHttpClient client = new OkHttpClient.Builder()
                //.addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getHostName("http://172.22.35.177:8081/app-debug.apk"))
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        /*getService().*/
        retrofit.create(DownloadService.class).download(urlStr)
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
                .subscribe(subscriber);
    }

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
