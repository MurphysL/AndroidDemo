package cn.edu.nuc.androidlab.eventbusdemo

import android.util.Log
import cn.edu.nuc.androidlab.eventbusdemo.bean.EventType
import cn.edu.nuc.androidlab.eventbusdemo.bean.Subscription
import cn.edu.nuc.androidlab.eventbusdemo.bean.TargetMethod
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * SubscriberMethodHunter
 *
 * Created by MurphySL on 2017/9/2.
 */
class SubscriberMethodHunter(private val subscriberMap : ConcurrentHashMap<EventType, CopyOnWriteArrayList<Subscription>>){

    private val TAG : String = this.javaClass.simpleName

    fun findSubscriberMethod(subscriber : Any){
        var clz : Class<*>? = subscriber.javaClass

        while(clz != null){
            val method = clz.declaredMethods

            method.forEach {
                val annotation  = it.getAnnotation(Subscriber::class.java)
                if(annotation != null){
                    val params = it.parameterTypes
                    if(params != null && params.size == 1 ){
                        // 只支持参数为 1 的方法
                        val paramType : Class<*>  = params[0]::class.java
                        val tag = annotation.tag
                        val eventType = EventType(paramType, tag)
                        val target = TargetMethod(it, paramType)

                        subscribe(eventType, target, subscriber)
                        Log.i(TAG, "method name : ${it.name}")
                    }
                }

            }
            Log.i(TAG, clz.simpleName)
            clz = clz.superclass
        }

    }

    private fun subscribe(eventType: EventType, target: TargetMethod, subscriber: Any) {
        var subscriptionList = subscriberMap[eventType]
        if(subscriptionList == null)
            subscriptionList = CopyOnWriteArrayList()

        val subscription = Subscription(subscriber, target)

        if(subscriptionList.contains(subscription)){
            return
        }

        subscriptionList.add(subscription)
        subscriberMap.put(eventType, subscriptionList)

    }

}