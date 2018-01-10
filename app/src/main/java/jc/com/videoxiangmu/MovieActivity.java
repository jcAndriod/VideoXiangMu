package jc.com.videoxiangmu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.superplayer.library.SuperPlayer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jc.com.videoxiangmu.Fragment.Tob1;
import jc.com.videoxiangmu.Fragment.Tob2;
import jc.com.videoxiangmu.adapter.MyAdapter;
import jc.com.videoxiangmu.bean.MovieBean;
import jc.com.videoxiangmu.utils.OkHttpUtils;
import jc.com.videoxiangmu.utils.OnUiCallback;
import okhttp3.Call;

public class MovieActivity extends FragmentActivity implements SuperPlayer.OnNetChangeListener {

    @BindView(R.id.tv_fi)
    TextView tvFi;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.view_super_player)
    SuperPlayer viewSuperPlayer;
    @BindView(R.id.tv_super_player_complete)
    TextView tvSuperPlayerComplete;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();
    private SuperPlayer player;
    private boolean isLive;

    List<MovieBean.RetBean> hlist = new ArrayList<>();
    private String url;


    /**
     * 测试地址
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String loadurl = bundle.getString("loadUrl");
        OkHttpUtils.getInstance().doGet(loadurl, new OnUiCallback() {
            @Override
            public void onFailed(Call call, IOException e) {
                Log.i("+++failed+++", "onFailed: " + "失败");
            }

            @Override
            public void onSuccess(String result) throws IOException {
                Toast.makeText(MovieActivity.this, "走了", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                MovieBean movieBean = gson.fromJson(result, MovieBean.class);
                hlist.add(movieBean.getRet());
                Log.i("++hlist+++", "onSuccess: " + hlist.toString());
                tvTitle.setText(hlist.get(0).getTitle());
                url = hlist.get(0).getHDURL();
                Log.i("++++++url++++++", "onSuccess: " + url);
                initData();
                initPlayer();
                initMData();
            }
        });
    }

    /**
     * 初始化相关的信息
     */
    private void initData() {
        isLive = getIntent().getBooleanExtra("isLive", false);
    }

    /**
     * 初始化播放器
     */
    private void initPlayer() {
        player = (SuperPlayer) findViewById(R.id.view_super_player);
        if (isLive) {
            player.setLive(true);//设置该地址是直播的地址
        }
        player.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .onPrepared(new SuperPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                    }
                }).onComplete(new Runnable() {
            @Override
            public void run() {
                /**
                 * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                 */
            }
        }).onInfo(new SuperPlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                /**
                 * 监听视频的相关信息。
                 */

            }
        }).onError(new SuperPlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                /**
                 * 监听视频播放失败的回调
                 */

            }
        }).setTitle(url)//设置视频的titleName
                .play(url);//开始播放视频
        player.setScaleType(SuperPlayer.SCALETYPE_FITXY);
        player.setPlayerWH(0, player.getMeasuredHeight());//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置
    }

    /**
     * 网络链接监听类
     */
    @Override
    public void onWifi() {
        Toast.makeText(this, "当前网络环境是WIFI", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMobile() {
        Toast.makeText(this, "当前网络环境是手机网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisConnect() {
        Toast.makeText(this, "网络链接断开", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoAvailable() {
        Toast.makeText(this, "无网络链接", Toast.LENGTH_SHORT).show();
    }


    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
    private void initMData() {
        mTitle.add("简介");
        mTitle.add("评论");
        Bundle bundle = new Bundle();
        bundle.putString("daoyan", hlist.get(0).getDirector());
        bundle.putString("yanyuan", hlist.get(0).getActors());
        Tob1 tob1 = new Tob1();
        tob1.setArguments(bundle);
        mFragment.add(tob1);
        mFragment.add(new Tob2());
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), mTitle, mFragment);
        viewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabs.setupWithViewPager(viewPager);
        //使用ViewPager的适配器
        tabs.setTabsFromPagerAdapter(adapter);
        reflex(tabs);
    }
    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);
                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);
                    int dp10 = dip2px(tabLayout.getContext(), 0);//字之间的距离，0表示没有
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        //线的宽度
                        int width = 300;
                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public static int dip2px(Context context, float dipValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
