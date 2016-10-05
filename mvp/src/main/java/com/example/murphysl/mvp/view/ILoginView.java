package com.example.murphysl.mvp.view;

/**
 * ILoginView
 *
 * @author: MurphySL
 * @time: 2016/10/5 16:53
 */

public interface ILoginView {
    void onClear();
    void onLoginResult(Boolean flag);
    void onSetProgressBarVisibility(int visibility);
}
