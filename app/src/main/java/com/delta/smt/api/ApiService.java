package com.delta.smt.api;


import com.delta.smt.entity.Brands;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.Update;
import com.delta.smt.entity.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


/**
 * Created by Administrator on 2016/3/23.
 */
public interface ApiService {
    @POST("ams/library/user/login2")
    Observable<LoginResult> login(@Body User user);

    @GET("v2/items")
    Observable<Brands> getBrands(@QueryMap Map<String, String> map);

    //Shufeng.Wu Update
    @GET("http://172.22.35.177:8081/update.json")
    Observable<Update> getUpdate();


}
