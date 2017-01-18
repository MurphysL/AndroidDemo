package com.xbei.murphysl.retrofitsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private OkHttpClient client = new OkHttpClient();
    private Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            //baseURL必须后缀“/”
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private API api = retrofit.create(API.class);

    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);

        final Call<User> userCall = api.userInfo("baiiu");
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                Log.i(TAG, "onResponse: " + body);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(call.isCanceled()){
                    Log.i(TAG, "onFailure: " + t.toString());
                }else{
                    Log.i(TAG, "onFailure: " + t.toString());
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<User> clone = userCall.clone();

                try {
                    Response<User> response = clone.execute();
                    final User body = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(body.toString());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
