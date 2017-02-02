package com.xbei.murphysl.rxjavasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxjavaActivity
 *
 * @author: MurphySL
 * @time: 2017/1/17 11:09
 */


public class RxjavaActivity extends AppCompatActivity {
    private static final String TAG = "RxjavaActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 创建Observable
         */
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
//                //ObservableEmitter 事件发射器
//                Log.i(TAG, "onNext(1): ");
//                e.onNext(1);
//                Log.i(TAG, "onNext(2): ");
//                e.onNext(2);
//
//                //e.onComplete();//仍可发送,但不接收
//                //e.onError(new Throwable());
//                Log.i(TAG, "onNext(3): ");
//                e.onNext(3);
//                Log.i(TAG, "onNext(4): ");
//                e.onNext(4);
//
//                e.onComplete();
//            }
//        });
//        //Observable<Integer> observable = Observable.just(1 , 2 , 3);
//
//        Integer[] a = {1, 2, 3};
//        //Observable<Integer> observable = Observable.fromArray(a);
//
//
//        Observer<Integer> observer = new Observer<Integer>() {
//            Disposable disposable;
//            int i = 0;
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                //Disposable 截断
//                /*Log.i(TAG, "onSubscribe: ");
//                disposable = d;*/
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                Log.i(TAG, "onNext: " + Thread.currentThread().getName());
//                Log.i(TAG, "onNext: " + value);
//                /*if(i == 2){
//                    Log.d(TAG, "dispose");
//                    disposable.dispose();
//                }
//                i ++;*/
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "onError: ");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete: ");
//            }
//        };
//
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())//版本对应
//                .subscribe(observer);


        /**
         * 变换操作符 map
         */
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onNext(4);
//            }
//        }).map(new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return "this is" + integer;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.i(TAG, "accept: " + s);
//            }
//        });

        /**
         * zip
         */
//        Observable<Integer> o = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onNext(4);
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable<String> os = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("hello");
//                e.onNext("world");
//                e.onNext("hello");
//                e.onNext("Android");
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable.zip(o, os, new BiFunction<Integer, String , String>() {
//            @Override
//            public String apply(Integer integer, String s) throws Exception {
//                return integer + s;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.i(TAG, "accept: " + s);
//            }
//        });

        /**
         * Sample 采样
         */
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                for(int i = 0 ; ;i ++){
//                    e.onNext(i);
//                }
//            }
//        }).subscribeOn(Schedulers.io())
//                .sample(2 , TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.i(TAG, "accept: " + integer);
//                    }
//                });

        /**
         * BackpressureStrategy.ERROR 流量不均衡时抛出异常
         */
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for(int i = 0 ;i < 1000 ;i ++){
                    e.onNext(i);
                }
                e.onComplete();
            }
        } , BackpressureStrategy.ERROR)
        .subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(20);
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.i(TAG, "onError: " + t.toString());
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
