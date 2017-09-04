package cn.edu.nuc.androidlab.eventbusdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*


/**
 * Created by MurphySL on 2017/9/2.
 */
class SecondActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        send.setOnClickListener {

        }

        send2.setOnClickListener {

        }
    }


}