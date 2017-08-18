package cn.edu.nuc.androidlab.mudulardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.edu.nuc.androidlab.router.RouterConstants;
import cn.edu.nuc.androidlab.router.RouterUtils;

public class MainActivity extends AppCompatActivity {

    private Button module1;
    private Button module2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        module1 = (Button) findViewById(R.id.module1);
        module1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterUtils.navigation("/module1/test");
            }
        });


        module2 = (Button) findViewById(R.id.module2);
        module2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterUtils.navigation(RouterConstants.MODULE2_MAIN_ACTIVITY);
            }
        });

    }
}
