package cn.edu.nuc.androidlab.eventbusdemo

import cn.edu.nuc.androidlab.eventbusdemo.bean.EventType
import org.junit.Test

/**
 * EventType 相等性测试
 *
 * Created by MurphySL on 2017/9/4.
 */
class EqualsTest{
    @Test
    fun equalsTest(){
        val activity = MainActivity::javaClass

        // val subscriberMap = ConcurrentHashMap<EventType, CopyOnWriteArrayList<Subscription>>()
        //  val hunter = SubscriberMethodHunter(subscriberMap)

        var clz : Class<*>? =activity.javaClass

        var eventType1 : EventType? = null
        var eventType2 : EventType? = null

        while(clz != null){
            val methods = clz.declaredMethods

            methods.forEach {
                val params = it.parameterTypes
                if(params != null && params.size == 1){
                    val annotation = it.getAnnotation(Subscriber::class.java)
                    if(annotation != null){
                        val tag = annotation.tag
                        eventType1 = EventType(params[0], tag)
                    }
                }
            }
            clz = clz.superclass
        }

        while(clz != null){
            val methods = clz.declaredMethods

            methods.forEach {
                val params = it.parameterTypes
                if(params != null && params.size == 1){
                    val annotation = it.getAnnotation(Subscriber::class.java)
                    if(annotation != null){
                        val tag = annotation.tag
                        eventType2 = EventType(params[0], tag)
                    }
                }
            }
            clz = clz.superclass
        }

        println(eventType1 == eventType2)
        println(eventType1 === eventType2)

    }

    @Test
    fun stringTest(){
        val s1 = "123"
        val s3 = StringBuilder("123")
        val s2 = StringBuilder("123")
        println(s3.toString() == s2.toString())
        println(s2.toString() == s1)
        println(s2.toString() === s1)
    }

    @Test
    fun classEqualsTest(){
        val clz1 = String::class.java
        val clz2 = String::class.java
        val clz3 = StringBuilder::class.java

        println(clz1 == clz2)
        println(clz1 == clz3)
        println(clz1 === clz2)

        println(clz1.name)
        val clzclz = clz1::class.java
        println(clzclz.name)
    }
}
