package cn.edu.nuc.androidlab.eventbusdemo

import cn.edu.nuc.androidlab.eventbusdemo.bean.EventType
import cn.edu.nuc.androidlab.eventbusdemo.bean.Subscription
import cn.edu.nuc.androidlab.eventbusdemo.handler.AsyncEventHandler
import cn.edu.nuc.androidlab.eventbusdemo.handler.DefaultEventHandler
import cn.edu.nuc.androidlab.eventbusdemo.handler.UIThreadEventHandler
import cn.edu.nuc.androidlab.eventbusdemo.matchPolicy.DefaultMatchPolicy
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArrayList

/**
 * EventBus
 *
 * Created by MurphySL on 2017/9/2.
 */
class EventBus{

    companion object {
        private val subscriberMap = ConcurrentHashMap<EventType, CopyOnWriteArrayList<Subscription>>()
        private val hunter = SubscriberMethodHunter(subscriberMap)
        private val localEvents = object : ThreadLocal<Queue<EventType>>(){
            override fun initialValue(): Queue<EventType> = ConcurrentLinkedQueue<EventType>()
        }
        private val dispatcher = EventDispatcher()


        @JvmStatic
        fun register(subscriber : Any) = hunter.findSubscriberMethod(subscriber)

        @JvmStatic
        fun post(event : Any, tag : String){
            localEvents.get().offer(EventType(event.javaClass, tag))
            dispatcher.dispatchEvents(event)
        }
    }

    class EventDispatcher{

        val uiHandler = UIThreadEventHandler()
        val postHandler = DefaultEventHandler()
        val asyncHandler = AsyncEventHandler()

        fun dispatchEvents(event : Any){
            val eventsQueue = localEvents.get()
            while (eventsQueue.size > 0){
                deliveryEvent(eventsQueue.poll(), event)
            }
        }

        private fun deliveryEvent(eventType: EventType?, event: Any) {
            val clz = event.javaClass
            val policy = DefaultMatchPolicy()

            eventType?.let {
                val eventTypes = policy.findMatchEventTypes(eventType, event)
                eventTypes.forEach {
                    handleEvent(it, event)
                }
            }
        }

        private fun handleEvent(eventType: EventType, event: Any) {
            val subscriptions = subscriberMap[eventType]
            subscriptions?.let {
                subscriptions.forEach {
                    //mode

                    val eventHandler = postHandler
                    eventHandler.handleEvent(it, event)
                }
            }
        }
    }



}