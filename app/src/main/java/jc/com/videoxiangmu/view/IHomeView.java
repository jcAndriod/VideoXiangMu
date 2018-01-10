package jc.com.videoxiangmu.view;

import java.util.List;

import jc.com.videoxiangmu.bean.HomeBean;

/**
 * Created by 56553 on 2017/11/28.
 */

public interface IHomeView {
    public void showShouData(List<HomeBean.RetBean.ListBean.ChildListBean> list);
    public void showBData(HomeBean bbean);
}
