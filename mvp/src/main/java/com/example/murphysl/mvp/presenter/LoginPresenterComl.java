package com.example.murphysl.mvp.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.murphysl.mvp.model.UserModel;
import com.example.murphysl.mvp.view.ILoginView;

/**
 * LoginPresenterComl
 *
 * @author: MurphySL
 * @time: 2016/10/5 17:13
 */

public class LoginPresenterComl implements ILoginPresenter {

    private ILoginView view;
    private UserModel userModel;
    private Handler handler;

    public LoginPresenterComl(ILoginView view){
        this.view = view;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    private void initUser(){
        userModel = new UserModel("Android" , "1234");
    }

    @Override
    public void clear() {
        view.onClear();
    }

    @Override
    public void doLogin(String name, String password) {
        final boolean flag = userModel.checkPassword(name,password);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.onLoginResult(flag);
            }
        }, 3000);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        view.onSetProgressBarVisibility(visibility);
    }
}
