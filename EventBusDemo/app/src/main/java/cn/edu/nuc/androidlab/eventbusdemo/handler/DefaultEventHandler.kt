package cn.edu.nuc.androidlab.eventbusdemo.handler

import cn.edu.nuc.androidlab.eventbusdemo.bean.Subscription

/**
 * Created by MurphySL on 2017/9/2.
 */
class DefaultEventHandler : EventHandler{
    override fun handleEvent(subscription: Subscription, event: Any) {
        subscription.method.method.invoke(subscription.subscriber, event)
    }

}