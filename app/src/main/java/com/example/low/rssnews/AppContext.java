package com.example.low.rssnews;

import android.app.Application;

/**
 * Created by Low on 2016/6/20.
 */
public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtil.initOkHttp();
    }
}
