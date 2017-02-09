package io.xjh.app.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
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
    public static final String BASE_URL="http://192.168.211.177:7777";


    public static Retrofit retrofit;//
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
     * IgIyitf5N4QYyBCGcCLjX2UZbnRHWaYesBBUSQCt2ipqJYFGW2HDyXTxCS28S5HGuM/iCG8ze2TNQsnGFf+/EP5xHuGjm24EU7YSsf17PEsIP8Le8eup8anJPn9QcjwF4LxWXva9llPxQ4hK5n/Nrldy2U0B8MBtBtALOMF+ZhI=
     *{"X-PPD-KEY":"tc-002",
     * "X-PPD-APPID":"10080004",
     * "X-PPD-SIGN":"IgIyitf5N4QYyBCGcCLjX2UZbnRHWaYesBBUSQCt2ipqJYFGW2HDyXTxCS28S5HGuM/iCG8ze2TNQsnGFf+/EP5xHuGjm24EU7YSsf17PEsIP8Le8eup8anJPn9QcjwF4LxWXva9llPxQ4hK5n/Nrldy2U0B8MBtBtALOMF+ZhI=",
     * "X-PPD-TOKEN":"41cf8343-de92-4e94-a75f-b4ae8bd6ce0f",41cf8343-de92-4e94-a75f-b4ae8bd6ce0f
     * "X-PPD-APPOS":"2",
     * "X-PPD-DEVICEID":"354224061858781",
     * "X-PPD-TIMESTAMP":"1486670142",
     * "X-PPD-APPVERSION":"4.5.0",
     * "X-PPD-SIGNVERSION":"1","
     * X-PPD-KEYVERSION":"1"}
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
                        HttpUrl httpUrl=request.url();
                        httpUrl = addSign(httpUrl);
                        Request newRequest = request.newBuilder()
                                .addHeader("User-Agent", "MarqueeView")
                                .addHeader("X-PPD-KEY","tc-002")
                                .addHeader("X-PPD-SIGN","IgIyitf5N4QYyBCGcCLjX2UZbnRHWaYesBBUSQCt2ipqJYFGW2HDyXTxCS28S5HGuM/iCG8ze2TNQsnGFf+/EP5xHuGjm24EU7YSsf17PEsIP8Le8eup8anJPn9QcjwF4LxWXva9llPxQ4hK5n/Nrldy2U0B8MBtBtALOMF+ZhI=")
                                .addHeader("X-PPD-APPID","10080004")
                                .addHeader("X-PPD-TOKEN","41cf8343-de92-4e94-a75f-b4ae8bd6ce0f")
                                .addHeader("X-PPD-APPOS","2")
                                .addHeader("X-PPD-DEVICEID","354224061858781")
                                .addHeader("X-PPD-TIMESTAMP",System.currentTimeMillis()/1000+"")
                                .addHeader("X-PPD-APPVERSION","4.5.0")
                                .addHeader("X-PPD-SIGNVERSION","1")
                                .addHeader("X-PPD-KEYVERSION","1")

                                .url(httpUrl)
                                .build();
                        Response response=chain.proceed(newRequest);
                        Log.d("retrofit","Request--->"+newRequest.toString());
                        Log.d("retrofit","Request Header--->"+newRequest.headers().toString());
                        Log.d("retrofit","Response--->"+response.toString());
                        Log.d("retrofit","Response Body--->"+new String(response.body().bytes()));
                        Log.d("retrofit","Response Header--->"+response.headers().toString());
                        return response;
                    }
                })
                .build();
    }

    @NonNull
    private HttpUrl addSign(HttpUrl httpUrl) {
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        Collections.sort(nameList);

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < nameList.size(); i++) {
            buffer.append("&").append(nameList.get(i)).append("=").append(httpUrl.queryParameterValues(nameList.get(i)) != null &&
                    httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "");
        }
        httpUrl = httpUrl.newBuilder()
//                .addQueryParameter("sign", MD5Util.encode(buffer.toString()))
                .build();
        Log.d("retrofit","sign "+buffer);
        return httpUrl;
    }

    public static  <T>T create(Class<T> clazz){
        if(retrofitUtil==null){
            getInstance();
        }
        return retrofit.create(clazz);
    }
}
