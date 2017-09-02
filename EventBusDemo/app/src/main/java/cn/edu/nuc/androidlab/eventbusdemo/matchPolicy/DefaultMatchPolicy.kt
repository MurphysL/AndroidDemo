package cn.edu.nuc.androidlab.eventbusdemo.matchPolicy

import android.util.Log
import cn.edu.nuc.androidlab.eventbusdemo.bean.EventType
import java.util.*

/**
 * DefaultMatchPolicy
 *
 * Created by MurphySL on 2017/9/2.
 */
class DefaultMatchPolicy : MatchPolicy{

    private val TAG : String = this.javaClass.simpleName

    override fun findMatchEventTypes(type: EventType, event: Any): LinkedList<EventType> {
        var eventClass : Class<*>? = event.javaClass
        val result = LinkedList<EventType>()
        while(eventClass != null){
            Log.i(TAG, eventClass.simpleName)
            result.add(EventType(eventClass, type.tag))
            addInterfaces(result, eventClass, type.tag)
            eventClass = eventClass.superclass
        }

        return result
    }

    private fun addInterfaces(eventTypes : LinkedList<EventType>, eventClass : Class<*>, tag : String){
        val interfacesClasses = eventClass.interfaces
        interfacesClasses.forEach {
            Log.i(TAG, it.simpleName)
            val eventType = EventType(it, tag)
            if(!eventTypes.contains(eventType)){
                eventTypes.add(eventType)
                addInterfaces(eventTypes, it, tag)
            }
        }
    }

}