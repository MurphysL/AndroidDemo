package cn.edu.nuc.androidlab.eventbusdemo.handler

import android.os.Handler
import android.os.HandlerThread
import cn.edu.nuc.androidlab.eventbusdemo.bean.Subscription

/**
 * AsyncEventHandler
 *
 * ThreadMode : Async
 *
 * Created by MurphySL on 2017/9/2.
 */
class AsyncEventHandler : EventHandler{

    private val dispatcherThread : DispatcherThread = DispatcherThread(AsyncEventHandler::class.java.simpleName)

    private val default = DefaultEventHandler()

    init {
        dispatcherThread.start()
    }

    override fun handleEvent(subscription: Subscription, event: Any) {
        dispatcherThread.post(Runnable {
            default.handleEvent(subscription, event)
        })
    }

    class DispatcherThread(name : String) : HandlerThread(name){

        private var asyncHandler : Handler? = null

        fun post(runnable: Runnable){
            asyncHandler?.let {
                post(runnable)
            }
        }

        @Synchronized
        override fun start() {
            super.start()
            asyncHandler = Handler(this.looper)
        }
    }

}