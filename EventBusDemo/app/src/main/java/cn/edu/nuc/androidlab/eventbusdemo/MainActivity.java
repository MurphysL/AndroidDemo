package cn.edu.nuc.androidlab.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView skip;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.register(this);

        content = (TextView) findViewById(R.id.content);
        skip = (TextView) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, SecondActivity.class));
                EventBus.post("123", "1");
            }
        });

    }

    @Subscriber(tag="1")
    public void testEvent(String msg){
        content.setText(msg);
    }

    @Override
    protected void onDestroy() {
        EventBus.unregister(this);
        super.onDestroy();
    }

}
