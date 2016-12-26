package com.delta.smt.ui.main.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Update;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 9:41
 */


public interface MainContract {
    interface View extends IView {
        //Shufeng.Wu Update
        void showExistUpdateDialog(Update update);
    }

    interface Model extends IModel {
        //Shufeng.Wu Update
        Observable<Update> getUpdate();
    }

}
