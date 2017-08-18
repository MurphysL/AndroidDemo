/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.edu.nuc.androidlab.ocrdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nuc.androidlab.ocrdemo.bean.Book;
import cn.edu.nuc.androidlab.ocrdemo.bean.OCRResult;
import cn.edu.nuc.androidlab.ocrdemo.bean.SearchResult;
import cn.edu.nuc.androidlab.ocrdemo.net.Service;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.EmptyCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE_GENERAL = 105;

    private boolean hasGotToken = false;

    private AlertDialog.Builder alertDialog;

    private List<String> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertDialog = new AlertDialog.Builder(this);

        findViewById(R.id.general_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL);
            }
        });

        // 请选择您的初始化方式
        // initAccessToken();
        initAccessTokenWithAkSk();
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void initAccessToken() {

        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getApplicationContext());
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getApplicationContext(), "5YlS7oqWQM9WXVWz4WDYlytv", "19BPaGpmzpjEk6QD4Zhd77aigppXKd1N");
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    private void infoPopText(final String result) {
        alertText("", result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken();
        } else {
            Toast.makeText(getApplicationContext(), "需要android.permission.READ_PHONE_STATE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GENERAL && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneral(FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            Log.i(TAG, "onResult: \n" + result);
                            distinguishWord(result);
                            infoPopText(result);
                        }
                    });
        }

    }

    private void distinguishWord(String result){
        Gson gson = new Gson();
        OCRResult object = gson.fromJson(result, OCRResult.class);

        Observable
                .fromIterable(object.getWords_result())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<OCRResult.WordsResult, Observable<List<Book.Tags>>>() {
                    @Override
                    public Observable<List<Book.Tags>> apply(OCRResult.WordsResult wordsResult) throws Exception {
                        if(wordsResult != null && !wordsResult.getWords().isEmpty()){
                            return Service.INSTANCE
                                    .getApi_douban()
                                    .searchBookInfo(wordsResult.getWords(), 1)
                                    .map(new Function<SearchResult, List<Book.Tags>>() {
                                        @Override
                                        public List<Book.Tags> apply(SearchResult searchResult) throws Exception {
                                            if(searchResult != null && !searchResult.getBooks().isEmpty()){
                                                return searchResult.getBooks().get(0).getTags();
                                            }
                                            return null;
                                        }
                                    });
                        }
                        return null;
                    }
                })
                .subscribe(new Consumer<Observable<List<Book.Tags>>>() {
                    @Override
                    public void accept(Observable<List<Book.Tags>> listObservable) throws Exception {
                        if(listObservable != null){
                            listObservable
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(Schedulers.io())
                                    .subscribe(new Consumer<List<Book.Tags>>() {
                                        @Override
                                        public void accept(List<Book.Tags> tags) throws Exception {
                                            for(Book.Tags tag : tags){
                                                Log.i(TAG, tag.getName());
                                            }
                                        }
                                    });
                        }
                    }

                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance().release();
    }
}
