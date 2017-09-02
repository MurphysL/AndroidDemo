package cn.edu.nuc.androidlab.eventbusdemo.matchPolicy

import cn.edu.nuc.androidlab.eventbusdemo.bean.EventType

/**
 * MatchPolicy
 *
 * Created by MurphySL on 2017/9/2.
 */
interface MatchPolicy{
    fun findMatchEventTypes(type : EventType, event : Any) : List<EventType>
}