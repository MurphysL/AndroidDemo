package cn.edu.nuc.androidlab.eventbusdemo.handler

import android.os.Handler
import android.os.Looper
import android.util.Log
import cn.edu.nuc.androidlab.eventbusdemo.bean.Subscription

/**
 * UIThreadEventHandler
 *
 * Created by MurphySL on 2017/9/2.
 */
class UIThreadEventHandler : EventHandler{
    private val TAG : String = this.javaClass.name

    private val handler = Handler(Looper.getMainLooper())

    private val default = DefaultEventHandler()

    override fun handleEvent(subscription: Subscription, event: Any) {
        Log.i(TAG, event.toString())
        handler.post {
            default.handleEvent(subscription, event)
        }
    }

}