package cn.edu.nuc.androidlab.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.nuc.androidlab.eventbusdemo.event.MessageEvent;
import cn.edu.nuc.androidlab.eventbusdemo.event.MsgEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView skip;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        cn.edu.nuc.androidlab.eventbusdemo.EventBus.register(this);

        content = (TextView) findViewById(R.id.content);
        skip = (TextView) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, SecondActivity.class));
                cn.edu.nuc.androidlab.eventbusdemo.EventBus.post("123", "1");
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event){
        Log.i(TAG, "onEvent");
        content.setText(event.getMsg());
        Toast.makeText(MainActivity.this, event.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode =  ThreadMode.MAIN)
    public void onMsgEvent(MessageEvent event){
        Log.i(TAG, "onMsgEvent: ");
        content.setText(event.getMsg());
    }

    @Subscriber(tag="1")
    public void testEvent(String msg){
        content.setText(msg);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
