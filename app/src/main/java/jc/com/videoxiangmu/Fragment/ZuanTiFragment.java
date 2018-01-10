package jc.com.videoxiangmu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import jc.com.videoxiangmu.MovieActivity;
import jc.com.videoxiangmu.R;
import jc.com.videoxiangmu.adapter.HomeAdapter;
import jc.com.videoxiangmu.bean.HomeBean;
import jc.com.videoxiangmu.presenter.HomePresenter;
import jc.com.videoxiangmu.utils.imageloader;
import jc.com.videoxiangmu.view.IHomeView;

/**
 * Created by 56553 on 2017/11/28.
 */

public class ZuanTiFragment extends Fragment{

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_zuanti, null);
        return view;
    }

}
