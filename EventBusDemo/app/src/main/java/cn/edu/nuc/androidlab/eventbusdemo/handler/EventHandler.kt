package cn.edu.nuc.androidlab.eventbusdemo.handler

import cn.edu.nuc.androidlab.eventbusdemo.bean.Subscription

/**
 * EventHandler
 *
 * Created by MurphySL on 2017/9/2.
 */
interface EventHandler {
    fun handleEvent(subscription : Subscription, event : Any)
}