package io.xjh.app.data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by xujianhua on 2017/2/7.
 */

public interface HomeService {
    ///PPDMobileBorrow/AccountService/GetCurrentUserAmountV2
    @POST("/PPDMobileBorrow/AppDataCollectService/UpdateAppUserContact")
    Call<String> getAuto(@Query("AppId")String AppId,@Query("idfa")String idfa,@Query("Deviceid")String Deviceid);

    @POST("PPDMobileBorrow/AccountService/GetCurrentUserAmountV2")
    Call<String> getAuto();

    @FormUrlEncoded
    @POST("/user")
    Call<String> submitUserInfo(@Query("user_name") String userName,@Field("user_info")String userInfo);
}
