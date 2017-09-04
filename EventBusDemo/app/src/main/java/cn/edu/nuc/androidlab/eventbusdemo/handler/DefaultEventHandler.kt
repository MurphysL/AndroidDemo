package cn.edu.nuc.androidlab.eventbusdemo.handler

import android.util.Log
import cn.edu.nuc.androidlab.eventbusdemo.bean.Subscription

/**
 * DefaultEventHandler
 *
 * ThreadMod : post
 *
 * Created by MurphySL on 2017/9/2.
 */
class DefaultEventHandler : EventHandler{
    private val TAG = "DefaultEventHandler"

    override fun handleEvent(subscription: Subscription, event: Any) {
        Log.i(TAG, subscription.target.method.name)

        subscription.target.method.invoke(subscription.subscriber, event)
    }
}