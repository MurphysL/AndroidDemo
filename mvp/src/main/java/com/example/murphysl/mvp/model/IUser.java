package com.example.murphysl.mvp.model;

/**
 * IUser
 *
 * @author: MurphySL
 * @time: 2016/10/5 16:58
 */

public interface IUser {
    String getName();
    String getPassword();
    boolean checkPassword(String name , String password);
}
