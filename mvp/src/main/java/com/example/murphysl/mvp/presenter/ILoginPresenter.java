package com.example.murphysl.mvp.presenter;

/**
 * ILoginPresenter
 *
 * @author: MurphySL
 * @time: 2016/10/5 17:11
 */

public interface ILoginPresenter {
    void clear();
    void doLogin(String name , String password);
    void setProgressBarVisibility(int visibility);
}
