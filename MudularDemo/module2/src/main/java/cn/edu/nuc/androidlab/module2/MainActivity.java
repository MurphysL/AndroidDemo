package cn.edu.nuc.androidlab.module2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.edu.nuc.androidlab.router.RouterConstants;

@Route(path = RouterConstants.MODULE2_MAIN_ACTIVITY)
public class MainActivity extends AppCompatActivity {

    private TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module2_activity_main);

        tv2 = (TextView) findViewById(R.id.module2_tv);
        tv2.setText("this is module 2");
    }
}
