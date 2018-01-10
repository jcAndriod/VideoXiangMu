package jc.com.videoxiangmu;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by 56553 on 2017/11/23.
 */

public class App extends Application {
    private static App mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mApplication = this;
    }

    public static App getInstance(){
        return mApplication;
    }
}
