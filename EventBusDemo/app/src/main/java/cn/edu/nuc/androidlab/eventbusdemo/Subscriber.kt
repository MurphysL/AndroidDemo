package cn.edu.nuc.androidlab.eventbusdemo

/**
 * Subscriber
 *
 * Created by MurphySL on 2017/9/2.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Subscriber(val tag: String, val mode : ThreadMode = ThreadMode.MAIN)