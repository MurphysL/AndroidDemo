package com.xbei.murphysl.rxjavasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
                //ObservableEmitter 事件发射器
                Log.i(TAG, "onNext(1): ");
                e.onNext(1);
                Log.i(TAG, "onNext(2): ");
                e.onNext(2);

                //e.onComplete();//仍可发送,但不接收
                //e.onError(new Throwable());
                Log.i(TAG, "onNext(3): ");
                e.onNext(3);
                Log.i(TAG, "onNext(4): ");
                e.onNext(4);

                e.onComplete();
            }
        });
        //Observable<Integer> observable = Observable.just(1 , 2 , 3);

        Integer[] a = {1 ,2 , 3};
        //Observable<Integer> observable = Observable.fromArray(a);


        Observer<Integer> observer = new Observer<Integer>() {
            Disposable disposable;
            int i = 0;
            @Override
            public void onSubscribe(Disposable d) {
                //Disposable 截断
                /*Log.i(TAG, "onSubscribe: ");
                disposable = d;*/
            }

            @Override
            public void onNext(Integer value) {
                Log.i(TAG, "onNext: " + Thread.currentThread().getName());
                Log.i(TAG, "onNext: " + value);
                /*if(i == 2){
                    Log.d(TAG, "dispose");
                    disposable.dispose();
                }
                i ++;*/
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())//版本对应
                .subscribe(observer);
    }
}
