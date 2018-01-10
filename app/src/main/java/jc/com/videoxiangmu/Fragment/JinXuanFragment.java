package jc.com.videoxiangmu.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class JinXuanFragment extends Fragment implements IHomeView {


    RecyclerView homeRv;
    private View view;
    List<HomeBean.RetBean.ListBean.ChildListBean> hlist = new ArrayList<>();

    private int height = 640;// 滑动开始变色的高,真实项目中此高度是由广告轮播或其他首页view高度决定
    private int overallXScroll = 0;
    HomeAdapter adapter;
    TextView tv_search;
    private Banner bn;
    private SwipeRefreshLayout sw;
    private RelativeLayout jx_rl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_jinxuan, null);
        initData();
        showData();
        return view;
    }

    private void initData() {
        jx_rl = view.findViewById(R.id.jx_rl);
        homeRv = view.findViewById(R.id.home_rv);
        tv_search = view.findViewById(R.id.tv_search);
        HomePresenter homePresenter = new HomePresenter(getActivity(), this);
        homePresenter.showHData();
        homeRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                overallXScroll = overallXScroll + dy;// 累加y值 解决滑动一半y值为0
                if (overallXScroll <= 0) {   //设置标题的背景颜色
                    jx_rl.setBackgroundColor(Color.argb((int) 0, 41, 193, 246));
                } else if (overallXScroll > 0 && overallXScroll <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) overallXScroll / height;
                    float alpha = (255 * scale);
                    jx_rl.setBackgroundColor(Color.argb((int) alpha, 41, 193, 246));
                } else {
                    jx_rl.setBackgroundColor(Color.argb((int) 255, 41, 193, 246));
                }
            }
        });

    }

    private void showData() {
        sw = view.findViewById(R.id.jx_sw);
        homeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeAdapter(getActivity());
        homeRv.setAdapter(adapter);
        setHeader(homeRv);
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sw.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        adapter.notifyDataSetChanged();
                        sw.setRefreshing(false);
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                },3000);
            }
        });
    }


    @Override
    public void showShouData(List<HomeBean.RetBean.ListBean.ChildListBean> list) {
        this.hlist = list;
        adapter.serRefresh(list);
    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.banner_layout, view, false);
        adapter.setHeaderView(header);
    }
    @Override
    public void showBData(HomeBean bbean) {
            bn = view.findViewById(R.id.home_banner);
            ArrayList<String> banlist = new ArrayList<>();
//            Log.i("++++mlist.size+++++", "showBanData: "+hlist.size());
            for (int i = 0; i < bbean.getRet().getList().get(0).getChildList().size(); i++) {
                banlist.add(bbean.getRet().getList().get(0).getChildList().get(i).getPic());
            }
            bn.setImageLoader(new imageloader());
            bn.setImages(banlist);
            bn.start();
            bn.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(getActivity(), MovieActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("loadUrl",hlist.get(position).getLoadURL());
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
    }
}
