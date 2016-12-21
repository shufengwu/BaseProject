package com.delta.smt.api;


import com.delta.smt.entity.Brands;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.User;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by Administrator on 2016/3/23.
 */
public interface ApiService {
    @POST("ams/library/user/login2")
    Observable<LoginResult> login(@Body User user);

    @GET("v2/items")
    Observable<Brands> getBrands(@QueryMap Map<String, String> map);

}
