package cn.edu.nuc.androidlab.tensorflowdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;


/**
 * HandWritingActivity
 *
 * Created by MurphySL on 2017/9/11.
 */

public class HandwritingActivity extends AppCompatActivity {
    private static final String TAG = "HandwritingActivity";

    public static final String MODEL_FILE = "file:///android_asset/handwriting.pd"; //asserts目录下的pb文件名字
    public static final String INPUT_NODE = "image_batch";       //输入节点的名称
    public static final String OUTPUT_NODE = "predicted";  //输出节点的名称
    public static final String KEEP_PROB = "keep_prob"; // 下降速率

    public static final int NUM_CLASSES = 10;   //输出节点的个数，即总的类别数。
    public static final int HEIGHT = 1;       //输入图片的像素高
    public static final int WIDTH = 784;        //输入图片的像素宽

    private ImageView pic;
    private TextView result_tv;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            result_tv.setText(msg.what + "");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                TensorFlowInferenceInterface inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                Bitmap origin = BitmapFactory.decodeResource(getResources(), R.drawable.test4, options);
                Bitmap bitmap = zoomImage(origin, 64, 64);

                float[]result = bitmapToFloatArray(bitmap);

                Trace.beginSection("feed");
                inferenceInterface.feed(INPUT_NODE, result, 1, 64, 64, 1);

                Trace.beginSection("feed");
                float[] keep_prob = {1.0f};
                inferenceInterface.feed(KEEP_PROB, keep_prob, 1, 1);

                Trace.beginSection("run");
                String[] outputNames = new String[]{OUTPUT_NODE};
                inferenceInterface.run(outputNames);

                float[] outputs = new float[50];
                Trace.beginSection("fetch");
                inferenceInterface.fetch(OUTPUT_NODE, outputs);

                Log.i(TAG, argmax(outputs) + " ");

                //handler.sendEmptyMessage(argmax(outputs));


            }
        }).start();

    }

    private void initView() {
        pic = (ImageView) findViewById(R.id.img);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test3, options);
        Bitmap bitmap2 = zoomImage(bitmap, 64, 64);
        pic.setImageBitmap(bitmap2);
        result_tv = (TextView) findViewById(R.id.result);
    }

    public static int argmax(float[] prob){
        int result = 0;
        for(int i=0;i<prob.length;i++) {
            if (prob[result] < prob[i]) {
                result = i;
            }
        }
        return result;
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

    /**
     * 将bitmap转为（按行优先）一个float数组。其中的每个像素点都归一化到0~1之间。
     * @param bitmap 灰度图，r,g,b分量都相等。
     * @return
     */
    public static float[] bitmapToFloatArray(Bitmap bitmap){
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        float[] result = new float[height*width];
        //float[][] result = new float[height][width];
       // float[][][][] conv = new float[1][64][64][1];
        Log.i(TAG,"bitmap width:"+width+",height:"+height);
        Log.i(TAG,"bitmap.getConfig():"+bitmap.getConfig());

        int k = 0;

        //行优先
        for(int j = 0;j < height;j++){
            for (int i = 0;i < width;i++){
                int argb = bitmap.getPixel(i,j);

                int r = Color.red(argb);
                int g = Color.green(argb);
                int b = Color.blue(argb);
                int a = Color.alpha(argb);

                //由于是灰度图，所以r,g,b分量是相等的。
                assert(r==g && g==b);

                //result[j][i] = r/255.0f;
                result[k ++] = r/255.0f;
            }
        }
        return result;

      /*  for(int i = 0 ;i < result.length ;i ++){
            for(int j = 0 ;j < result[0].length ;j ++){
                conv[0][i][j][0] = result[i][j];
            }
        }

        for(int i = 0 ;i < conv.length ;i ++){
            for(int j = 0 ;j < conv[0].length ;j ++){
                for(int n = 0 ;n < conv[0][0].length;n ++){
                    for(int m = 0 ;m < conv[0][0][0].length ;m ++){
                        System.out.println(conv[i][j][n][m] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
            System.out.println();
        }

        return conv;*/
    }
}
