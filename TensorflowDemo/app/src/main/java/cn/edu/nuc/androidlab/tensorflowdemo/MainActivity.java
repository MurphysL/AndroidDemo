package cn.edu.nuc.androidlab.tensorflowdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String MODEL_FILE = "file:///android_asset/mnist.pd"; //asserts目录下的pb文件名字
    //public static final String MODEL_FILE = "mnist.pb"; //asserts目录下的pb文件名字
    public static final String PIC_FILE = "file:///android_asset/test.png";
    public static final String INPUT_NODE = "x";       //输入节点的名称
    public static final String OUTPUT_NODE = "softmax";  //输出节点的名称
    public static final String KEEP_PROB_NODE = "y_"; // keep_prob节点的名称

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
                Bitmap bimap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);
                float[] result = bitmapToFloatArray(bimap);

                Trace.beginSection("feed");
                inferenceInterface.feed(INPUT_NODE, result, HEIGHT, WIDTH);

                Trace.beginSection("run");
                String[] outputNames = new String[]{OUTPUT_NODE};
                inferenceInterface.run(outputNames);

                float[] outputs = new float[NUM_CLASSES];
                Trace.beginSection("fetch");
                inferenceInterface.fetch(OUTPUT_NODE, outputs);

                handler.sendEmptyMessage(argmax(outputs));


            }
        }).start();



    }

    private void initView() {
        pic = (ImageView) findViewById(R.id.img);
        result_tv = (TextView) findViewById(R.id.result);
    }

    public static int argmax(float[] prob){
        int result = 0;
        for(int i=1;i<prob.length;i++) {
            if (prob[result] < prob[i]) {
                result = i;
            }
        }
        return result;
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

//                Log.i(TAG,i+","+j+" : argb = "+argb+", a="+a+", r="+r+", g="+g+", b="+b);
                result[k++] = r / 255.0f;
            }
        }
        Log.i(TAG, result.length+"");

        return result;
    }






















}
