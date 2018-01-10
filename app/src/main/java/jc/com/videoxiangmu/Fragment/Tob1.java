package jc.com.videoxiangmu.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jc.com.videoxiangmu.R;

/**
 * Created by 56553 on 2017/11/24.
 */

public class Tob1 extends Fragment{

    Unbinder unbinder;
    @BindView(R.id.tv_daoyan)
    TextView tvDaoyan;
    @BindView(R.id.tv_yanyuan)
    TextView tvYanyuan;
    @BindView(R.id.h_rv)
    RecyclerView hRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentone, null);
        unbinder = ButterKnife.bind(this, view);
        showData();
        initData();
        return view;
    }

    private void showData() {
        Bundle bundle = getArguments();
        String daoyan = (String) bundle.get("daoyan");
        String yanyuan = (String) bundle.get("yanyuan");
        tvDaoyan.setText("导演:" + daoyan);
        tvYanyuan.setText("主演:" + yanyuan);
    }

    private void initData() {
        hRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
