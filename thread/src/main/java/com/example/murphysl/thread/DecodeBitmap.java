package com.example.murphysl.thread;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * DecodeBitmap
 *
 * @author: MurphySL
 * @time: 2016/10/18 10:10
 */

public class DecodeBitmap {

    public static Bitmap decodeBitmapFromResource(Resources res , int resId , int resHeight , int resWeight){

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res , resId , options);

        options.inSampleSize = calculateInSampleSize(options , resHeight , resWeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res , resId , options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int resHeight , int resWeight){

        final  int weight = options.outWidth;
        final  int height = options.outHeight;
        int inSampleSize = 1;

        if(weight > resWeight || height > resHeight){
            int halfWeight = weight / 2;
            int halfHeight = height / 2;

            while((halfHeight / inSampleSize >= resHeight) && (halfWeight / inSampleSize >= resWeight)){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
