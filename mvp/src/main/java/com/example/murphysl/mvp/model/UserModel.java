package com.example.murphysl.mvp.model;

import android.app.usage.UsageStats;

/**
 * UserModel
 *
 * @author: MurphySL
 * @time: 2016/10/5 16:54
 */

public class UserModel implements IUser{
    private String name;
    private String password;

    public UserModel(String name , String password){
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String name , String password){
        if(name == null || password == null || !name.equals(getName()) || !password.equals(getPassword()))
            return false;
        return true;
    }

}
