package cn.edu.nuc.androidlab.opencvdemo;

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

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String MODEL_FILE = "file:///android_asset/handwriting.pd"; //asserts目录下的pb文件名字
    public static final String INPUT_NODE = "image_batch";       //输入节点的名称
    public static final String OUTPUT_NODE = "predicted";  //输出节点的名称
    public static final String KEEP_PROB = "keep_prob"; // 下降速率

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //result_tv.setText(msg.what + "");
            Log.i(TAG, msg.what + " ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test11, options);
        Bitmap bitmap2 = zoomImage(bitmap, 300, 300);


        ImageView im2 = (ImageView) findViewById(R.id.new_img);
        JNIUtils utils = new JNIUtils();
        int[] result = utils.getEdge(bitmap2);
        Log.i(TAG, result.length+" ");
        for(int i : result){
            Log.i(TAG, "result : "+ i);
        }

        for(int i = 0 ;i < result.length;i +=4){
            Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.test11, options);
            Bitmap bitmap3 = zoomImage(bitmap4, 300, 300);
            final Bitmap tmep = Bitmap.createBitmap(bitmap3, result[i], result[i+1], Math.abs(result[i+2]), Math.abs(result[i+3]));
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
            if(i == 0)
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TensorFlowInferenceInterface inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE);

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inScaled = false;
                        Bitmap origin = tmep;
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

                        handler.sendEmptyMessage(argmax(outputs));
                    }
                }).start();
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

    public static int argmax(float[] prob){
        int result = 0;
        for(int i=0;i<prob.length;i++) {
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
