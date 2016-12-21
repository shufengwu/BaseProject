package com.delta.smt.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.login.di.DaggerLoginComponent;
import com.delta.smt.ui.login.di.LoginModule;
import com.delta.smt.ui.login.mvp.LoginContract;
import com.delta.smt.ui.login.mvp.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/12 16:13
 */


public class LoginActivity extends BaseActiviy<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.userName)
    EditText et_userName;
    @BindView(R.id.password)
    EditText et_password;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }


    @OnClick(R.id.bt_login)
    public void onClick() {
        String name = et_userName.getText().toString();
        String passWord = et_password.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(passWord)) {

            ToastUtils.showMessage(this, "用户名不能为空");

        } else {
            getPresenter().login(name, passWord);
        }
    }
    @Override
    public void loginSucess() {

        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void loginFailed() {

        et_password.setFocusable(true);
        ToastUtils.showMessage(this, "登录失败请重试！");

    }
}
