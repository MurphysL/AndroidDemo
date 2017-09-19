package cn.edu.nuc.androidlab.opencvdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test8, options);
        Bitmap bitmap2 = zoomImage(bitmap, 300, 300);


        ImageView im2 = (ImageView) findViewById(R.id.new_img);
        JNIUtils utils = new JNIUtils();
        int[] result = utils.getEdge(bitmap2);
        Log.i(TAG, result.length+" ");
        for(int i : result){
            Log.i(TAG, "result : "+ i);
        }

        for(int i = 0 ;i < result.length;i +=4){
            Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.test8, options);
            Bitmap bitmap3 = zoomImage(bitmap4, 300, 300);
            Bitmap tmep = Bitmap.createBitmap(bitmap3, result[i], result[i+1], Math.abs(result[i+2]), Math.abs(result[i+3]));
            switch (i){
                case 0:
                    ImageView i1 = (ImageView) findViewById(R.id.img1);
                    i1.setImageBitmap(tmep);
                    break;
                case 4:
                    ImageView i2 = (ImageView) findViewById(R.id.img2);
                    i2.setImageBitmap(tmep);
                    break;
                case 8:
                    ImageView i3 = (ImageView) findViewById(R.id.img3);
                    i3.setImageBitmap(tmep);
                    break;
                case 12:
                    ImageView i4 = (ImageView) findViewById(R.id.img4);
                    i4.setImageBitmap(tmep);
                    break;
                case 16:
                    ImageView i5 = (ImageView) findViewById(R.id.img5);
                    i5.setImageBitmap(tmep);
                    break;
            }
        }

        im2.setImageBitmap(bitmap2);
    }

    private Bitmap zoomImage(Bitmap bitmap, int newWidth, int newHeight){
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Matrix matrix = new Matrix();
        float scaleWidth = (float)newWidth/(float)width;
        float scaleHeight = (float)newHeight/(float)height;
        matrix.preScale(scaleWidth, scaleHeight);
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0 ,width, height, matrix, true);
        return bitmap1;
    }

}
