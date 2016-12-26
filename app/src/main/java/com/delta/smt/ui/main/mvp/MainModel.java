package com.delta.smt.ui.main.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Update;

import rx.Observable;

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
}
