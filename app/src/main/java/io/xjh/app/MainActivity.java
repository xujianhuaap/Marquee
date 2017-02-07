package io.xjh.app;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.xjh.app.data.HomeService;
import io.xjh.app.utils.RetrofitUtil;
import retrofit2.Call;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        ButterKnife.bind(this);
        Observable.just(1,2,3).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i(MainActivity.class.getName(),"onNext"+integer);
            }
        });
//        HomeService homeService= RetrofitUtil.create(HomeService.class);
//        Call<String> call=homeService.getAuto();
//
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1111");
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String drawable) {
                Log.i(MainActivity.class.getName(),"onNext"+drawable);
            }
        });
//
//
//        Observable.just("").map(new Func1<String, Drawable>() {
//            @Override
//            public Drawable call(String s) {
//                return null;
//            }
//        }).subscribe(new Action1<Drawable>() {
//            @Override
//            public void call(Drawable drawable) {
//
//            }
//        });

    }
}
