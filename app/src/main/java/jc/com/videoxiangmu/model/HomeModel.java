package jc.com.videoxiangmu.model;

import jc.com.videoxiangmu.serices.HomeServices;
import jc.com.videoxiangmu.utils.Constant;
import jc.com.videoxiangmu.utils.RetrofitManager;
import okhttp3.OkHttpClient;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 56553 on 2017/11/28.
 */

public class HomeModel implements IHomeModel {
    @Override
    public void HomeOb(Observer observer) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        RetrofitManager.getInstance(Constant.HOME_URL,okHttpClient)
                .setCreate(HomeServices.class)
                .sendHData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
