package com.delta.smt.ui.login.di;


import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.login.mvp.LoginContract;
import com.delta.smt.ui.login.mvp.LoginModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by V.Wenju.Tian on 2016/11/29.
 */

@Module
public class LoginModule {


    LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LoginContract.View providerView() {
        return view;
    }

    @ActivityScope
    @Provides
    LoginContract.Model providerModel(ApiService service) {
        return new LoginModel(service);
    }

}
