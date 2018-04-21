package air.edu.qile.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import air.edu.qile.R;
import air.edu.qile.model.bean.ModuleData;

/**
 * Created by Administrator on 2018/4/17.
 */

public class FragmentRcycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected List<ModuleData> mDatas;

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
        ModuleData data = mDatas.get(position);
        if (holder instanceof Holder_Card1) {
            Holder_Card1 card1= (Holder_Card1) holder;
            card1.title.setText( data.getConfig().getName() );

            Picasso.with(mContext)
                    .load(data.getCover().getUrl() )
                    .into(card1.cover);
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
