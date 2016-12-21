package com.delta.smt.ui.login.mvp;


import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.LoginResult;

import rx.Observable;

/**
 * Created by V.Wenju.Tian on 2016/9/2.
 */
public interface LoginContract {

    interface Model extends IModel {

        Observable<LoginResult> login(String name, String password);

    }

    interface View extends IView {

        void loginSucess();
        void loginFailed();

    }

}
