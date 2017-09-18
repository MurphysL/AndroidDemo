package cn.edu.nuc.androidlab.opencvdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test8);
        ImageView im = (ImageView) findViewById(R.id.sample_img);
        JNIUtils utils = new JNIUtils();
        utils.getEdge(bitmap);
        im.setImageBitmap(bitmap);
    }

}
