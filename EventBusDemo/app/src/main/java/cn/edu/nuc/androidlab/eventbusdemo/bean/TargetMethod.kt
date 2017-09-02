package cn.edu.nuc.androidlab.eventbusdemo.bean

import java.lang.reflect.Method

/**
 * Created by MurphySL on 2017/9/2.
 */
data class TargetMethod(val method : Method, val paramType : Class<*>)