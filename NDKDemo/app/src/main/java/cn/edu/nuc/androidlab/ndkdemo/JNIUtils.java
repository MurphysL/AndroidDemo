package cn.edu.nuc.androidlab.ndkdemo;

/**
 * Created by MurphySL on 2017/9/18.
 */

public class JNIUtils {

    static {
        System.loadLibrary("app");
    }

    public static native int add(int i , int j);
}
