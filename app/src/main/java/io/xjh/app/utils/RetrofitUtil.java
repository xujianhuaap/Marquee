package io.xjh.app.utils;

import retrofit2.Retrofit;

/**
 * Created by xujianhua on 2017/2/7.
 */

public class RetrofitUtil {
    public static Retrofit retrofit;
    public static RetrofitUtil retrofitUtil;
    public static RetrofitUtil getInstance(){
        if(retrofitUtil==null){
            retrofitUtil=new RetrofitUtil();
        }
        return retrofitUtil;
    }

    private RetrofitUtil() {
        retrofit=new Retrofit.Builder()
                .baseUrl("https://www.android.com/")
                .build();
    }

    public static  <T>T create(Class<T> clazz){
        if(retrofitUtil==null){
            getInstance();
        }
        return retrofit.create(clazz);
    }
}
