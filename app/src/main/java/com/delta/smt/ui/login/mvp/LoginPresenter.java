package com.delta.smt.ui.login.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.LoginResult;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by V.Wenju.Tian on 2016/9/2.
 */

@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View mView) {
        super(model, mView);
    }

    public void login(String name, String password) {


        getModel().login(name, password).subscribe(new Action1<LoginResult>() {
            @Override
            public void call(LoginResult loginResult) {

                Log.e(TAG, "call() called with: loginResult = [" + loginResult + "]");
                getView().loginSucess();

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "call() called with: throwable = [" + throwable + "]");
                getView().loginFailed();
            }
        });

    }
}
