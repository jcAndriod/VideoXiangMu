package jc.com.videoxiangmu.presenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jc.com.videoxiangmu.bean.HomeBean;
import jc.com.videoxiangmu.model.HomeModel;
import jc.com.videoxiangmu.view.IHomeView;
import rx.Observer;

/**
 * Created by 56553 on 2017/11/28.
 */

public class HomePresenter {
    Context context;
    IHomeView view;
    HomeModel modle;
    List<HomeBean.RetBean.ListBean.ChildListBean> list = new ArrayList<>();

    public HomePresenter(FragmentActivity context, IHomeView view) {
        this.context = context;
        this.view = view;
        modle = new HomeModel();
    }
    public void showHData(){
        modle.HomeOb(new Observer<HomeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("+++onError+++", "onError: "+e.toString());
            }

            @Override
            public void onNext(HomeBean bean) {
                for (int i = 0; i < bean.getRet().getList().size(); i++) {
                    list.addAll(bean.getRet().getList().get(i).getChildList());
                }
                view.showShouData(list);
                view.showBData(bean);
            }
        });
    }
}
