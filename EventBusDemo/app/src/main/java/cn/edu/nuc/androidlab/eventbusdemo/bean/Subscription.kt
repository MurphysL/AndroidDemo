package cn.edu.nuc.androidlab.eventbusdemo.bean

import cn.edu.nuc.androidlab.eventbusdemo.ThreadMode

/**
 * Subscription
 *
 * Created by MurphySL on 2017/9/2.
 */
data class Subscription(val subscriber : Any, val target: TargetMethod, val thread : ThreadMode)