package com.example.murphysl.imtest.model.i;

import com.example.murphysl.imtest.bean.UserBean;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobListener;

public abstract class QueryUserListener extends BmobListener<UserBean> {

    public abstract void done(UserBean s, BmobException e);

    @Override
    protected void postDone(UserBean o, BmobException e) {
        done(o, e);
    }
}
