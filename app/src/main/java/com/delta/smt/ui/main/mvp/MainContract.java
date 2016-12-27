package com.delta.smt.ui.main.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Update;

import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 9:41
 */


public interface MainContract {
    interface View extends IView {
        //Shufeng.Wu Update
        void showExistUpdateDialog(Update update);
        void downloadCompleted();
        void setFileSize(long fileSize);
    }

    interface Model extends IModel {
        //Shufeng.Wu Update
        Observable<Update> getUpdate();
        void download(String urlStr,Subscriber subscriber/*,Action1<ResponseBody> action1*/);
    }

}
