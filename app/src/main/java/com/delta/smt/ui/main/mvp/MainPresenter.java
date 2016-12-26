package com.delta.smt.ui.main.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Update;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 9:42
 */

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View mView) {
        super(model, mView);
    }

    public void checkUpdate(){
        getModel().getUpdate().subscribe(new Action1<Update>() {

            @Override
            public void call(Update update) {
                getView().showExistUpdateDialog(update);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
