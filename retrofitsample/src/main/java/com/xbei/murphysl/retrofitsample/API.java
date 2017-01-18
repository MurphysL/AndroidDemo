package com.xbei.murphysl.retrofitsample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * API
 *
 * @author: MurphySL
 * @time: 2017/1/18 11:38
 */


public interface API {
    /**
     *  https://api.github.com/users/baiiu
     */
    @GET("users/{user}")
    Call<User> userInfo(@Path("user") String user);
}
