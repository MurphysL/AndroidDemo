package cn.edu.nuc.androidlab.eventbusdemo

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

    fun findSubscriberMethod(subscriber : Any){
        var clz : Class<*>? = subscriber::class.java

        while(clz != null){
            val method = clz.declaredMethods

            method.forEach {
                val annotation  = it.getAnnotation(Subscriber::class.java)
                if(annotation != null){
                    val params = it.parameterTypes
                    if(params != null && params.size == 1 ){
                        // 只支持参数为 1 的方法
                        val paramType  = params[0]
                        val tag = annotation.tag
                        val threadMode = annotation.mode
                        val eventType = EventType(paramType, tag)
                        val target = TargetMethod(it, paramType)

                        subscribe(eventType, target, subscriber, threadMode)
                    }
                }

            }
            clz = clz.superclass
        }

    }

    fun removeSubscriberMethod(subscriber: Any){
        var clz : Class<*>? = subscriber::class.java

        while(clz != null){
            val methods = clz.declaredMethods
            if(methods != null && methods.isNotEmpty()){
                methods.forEach {
                    val annotation = it.getAnnotation(Subscriber::class.java)
                    if(annotation != null){
                        val params = it.parameterTypes
                        if(params != null && params.size ==  1){
                            val tag = annotation.tag
                            val eventType = EventType(params[0], tag)
                            subscriberMap.remove(eventType)
                        }
                    }
                }
            }
            clz = clz.superclass
        }

    }

    private fun subscribe(eventType: EventType, target: TargetMethod, subscriber: Any, threadMode: ThreadMode) {
        var subscriptionList = subscriberMap[eventType]
        if(subscriptionList == null)
            subscriptionList = CopyOnWriteArrayList()

        val subscription = Subscription(subscriber, target, threadMode)

        if(!subscriptionList.contains(subscription)){
            subscriptionList.add(subscription)
            subscriberMap.put(eventType, subscriptionList)
        }

    }

}