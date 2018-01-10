package jc.com.videoxiangmu.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import jc.com.videoxiangmu.R;
import jc.com.videoxiangmu.bean.HomeBean;

/**
 * Created by 56553 on 2017/11/28.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    Context context;
    List<HomeBean.RetBean.ListBean.ChildListBean> list = new ArrayList<>();
    private View mHeaderView;

    public HomeAdapter(Context context) {
        this.context = context;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void serRefresh(List<HomeBean.RetBean.ListBean.ChildListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new MyViewHolder(mHeaderView);
        View view = View.inflate(context, R.layout.home_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        holder.tv.setText(list.get(position).getTitle());
        holder.iv.setImageURI(Uri.parse(list.get(position).getPic()));
    }
    @Override
    public int getItemCount() {
        return mHeaderView == null ? list.size() : list.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView iv;
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
