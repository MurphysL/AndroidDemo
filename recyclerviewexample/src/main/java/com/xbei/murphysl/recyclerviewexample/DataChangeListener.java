package com.xbei.murphysl.recyclerviewexample;

/**
 * DataChangeListener
 *
 * @author: MurphySL
 * @time: 2017/1/6 13:11
 */

public interface DataChangeListener<T> {
    void onSuccess(T data);
    void onError();
}
