package br.im.cursoandroid.util;

import android.app.Application;

/**
 * Created by felipe on 8/6/16.
 */
public class CustoAndroidApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SingletonRequest.initializeQueue(getApplicationContext());
    }
}
