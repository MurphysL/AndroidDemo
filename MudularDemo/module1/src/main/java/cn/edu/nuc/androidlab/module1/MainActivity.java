package cn.edu.nuc.androidlab.module1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/module1/test")
public class MainActivity extends AppCompatActivity {

    private TextView tv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modul_activity_main);

        tv = (TextView) findViewById(R.id.module1_tv);

        tv.setText(R.string.module1_hint);
    }
}
