package cn.edu.nuc.androidlab.router;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * RouterUtils
 *
 * Created by MurphySL on 2017/8/11.
 */

public class RouterUtils {

    public static Object navigation(String path){
        return ARouter.getInstance().build(path).navigation();
    }

}
