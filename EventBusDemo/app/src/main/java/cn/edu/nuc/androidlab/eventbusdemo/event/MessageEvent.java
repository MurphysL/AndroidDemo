package cn.edu.nuc.androidlab.eventbusdemo.event;

/**
 * Created by MurphySL on 2017/9/2.
 */

public class MessageEvent {

    private String msg;

    public MessageEvent(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
}
