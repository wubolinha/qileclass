package air.edu.qile.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import air.edu.qile.R;

/**
 * Created by Administrator on 2018/4/17.
 */

public class FragmentRcycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected List mDatas;

    public FragmentRcycleAdapter(Context mContext, List mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.card1, parent, false);
        Holder_Card1 card1 = new Holder_Card1(view);
        return card1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder_Card1) {


        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class Holder_Card1 extends RecyclerView.ViewHolder {
        public ImageView cover;
        public TextView title;

        //实现的方法
        public Holder_Card1(View itemView) {
            super(itemView);
            cover =  itemView.findViewById(R.id.cover);
            title =  itemView.findViewById(R.id.title);
        }
    }


}
