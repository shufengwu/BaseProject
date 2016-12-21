package com.delta.smt.app;


import com.delta.smt.api.API;
import com.delta.smt.base.BaseApplication;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.di.component.DaggerAppComponent;

/**
 * Created by V.Wenju.Tian on 2016/11/29.
 */

public class App extends BaseApplication {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().clientModule(getClientModule()).appModule(getAppModule()).serviceModule(getServiceModule()).build();
    }

    @Override
    protected String getBaseUrl() {
        return API.BASE_URL;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
