package cn.edu.nuc.androidlab.opencvdemo;

/**
 * Created by MurphySL on 2017/9/18.
 */

public class JNIUtils {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    native int[] getEdge(Object bitmap);
}
