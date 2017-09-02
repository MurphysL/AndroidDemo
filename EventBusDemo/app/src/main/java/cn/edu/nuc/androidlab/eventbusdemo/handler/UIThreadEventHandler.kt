package cn.edu.nuc.androidlab.eventbusdemo.handler

import android.os.Handler
import android.os.Looper
import cn.edu.nuc.androidlab.eventbusdemo.bean.Subscription

/**
 * UIThreadEventHandler
 *
 * Created by MurphySL on 2017/9/2.
 */
class UIThreadEventHandler : EventHandler{

    private val handler = Handler(Looper.getMainLooper())

    private val default = DefaultEventHandler()

    override fun handleEvent(subscription: Subscription, event: Any) {
        handler.post {
            default.handleEvent(subscription, event)
        }
    }

}