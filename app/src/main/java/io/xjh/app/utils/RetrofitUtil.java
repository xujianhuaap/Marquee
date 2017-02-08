package io.xjh.app.utils;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xujianhua on 2017/2/7.
 */

public class RetrofitUtil {
    public static final int READ_TIME_OUT=30;
    public static final int CONNECT_TIME_OUT=60;
    public static final String BASE_URL="http://api.openweathermap.org";


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
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(initOkHttpClient())
                .build();
    }

    /***
     *
     * @return
     */
    private OkHttpClient initOkHttpClient(){
        return new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT,TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request=chain.request();
                        Request newRequest = request.newBuilder()
                                .addHeader("User-Agent", "MarqueeView")
                                .build();
                        Response response=chain.proceed(newRequest);
                        Log.d("request","Request--->"+newRequest.toString());
                        Log.d("request","Request Header--->"+newRequest.headers().toString());
                        Log.d("response","Response--->"+response.toString());
                        return response;
                    }
                })
                .build();
    }
    public static  <T>T create(Class<T> clazz){
        if(retrofitUtil==null){
            getInstance();
        }
        return retrofit.create(clazz);
    }
}
