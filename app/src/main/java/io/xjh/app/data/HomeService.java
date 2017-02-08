package io.xjh.app.data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by xujianhua on 2017/2/7.
 */

public interface HomeService {
    @GET("/data/2.5/weather?id=2172797")
    Call<String> getAuto();

    @FormUrlEncoded
    @POST("/user")
    Call<String> submitUserInfo(@Field("UserName")String userName);
}
