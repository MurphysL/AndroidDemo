package com.example.murphysl.imtest.view;


import com.example.murphysl.imtest.base.ILoader;
import com.example.murphysl.imtest.base.UniversalImageLoader;

public class ImageLoaderFactory {

    private static volatile ILoader sInstance;

    private ImageLoaderFactory() {}

    public static ILoader getLoader() {
        if (sInstance == null) {
            synchronized (ImageLoaderFactory.class) {
                if (sInstance == null) {
                    sInstance = new UniversalImageLoader();
                }
            }
        }
        return sInstance;
    }
}
