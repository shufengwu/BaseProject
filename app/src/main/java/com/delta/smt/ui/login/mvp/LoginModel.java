package com.delta.smt.ui.login.mvp;


import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.User;

import rx.Observable;


/**
 * Created by V.Wenju.Tian on 2016/9/2.
 */
public class LoginModel extends BaseModel<ApiService> implements LoginContract.Model {


    public LoginModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<LoginResult> login(String name, String password) {

        User user = new User();
        user.setName(name);
        user.setPwd(password);
        // TODO: 2016/10/19 如果先从本地读取在从网络获取怎么写

        return getService().login(user).compose(RxsRxSchedulers.<LoginResult>io_main());
    }
}


