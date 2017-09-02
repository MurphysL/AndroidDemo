package cn.edu.nuc.androidlab.eventbusdemo

import android.app.usage.UsageEvents
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.edu.nuc.androidlab.eventbusdemo.event.MessageEvent
import cn.edu.nuc.androidlab.eventbusdemo.event.MsgEvent
import kotlinx.android.synthetic.main.activity_second.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by MurphySL on 2017/9/2.
 */
class SecondActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        send.setOnClickListener {
            EventBus.getDefault().post(MsgEvent("New message"))
        }

        send2.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("New Message"))
        }
    }


}