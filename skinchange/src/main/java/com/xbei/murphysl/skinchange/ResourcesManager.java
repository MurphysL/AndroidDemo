package com.xbei.murphysl.skinchange;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * ResourcesManager
 *
 * @author: MurphySL
 * @time: 2017/1/3 15:35
 */


public class ResourcesManager {
    private static final String TAG= "ResourcesManager";

    private Resources res;
    private String pkgName;

    public ResourcesManager(Resources res , String pkgName){
        this.pkgName = pkgName;
        this.res = res;
    }

    public Drawable getDrawableByRes(String name){
        Drawable drawable = null;
        try {
            if(res != null){
                drawable = res.getDrawable(res.getIdentifier(name , "drawable" , pkgName));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  drawable;
    }
}
