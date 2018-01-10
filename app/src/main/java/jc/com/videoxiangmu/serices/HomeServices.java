package jc.com.videoxiangmu.serices;

import jc.com.videoxiangmu.bean.HomeBean;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by 56553 on 2017/11/28.
 */

public interface HomeServices {
    @GET("front/homePageApi/homePage.do")
    Observable<HomeBean> sendHData();
}
