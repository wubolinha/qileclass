package air.edu.qile.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import air.edu.qile.R;
import air.edu.qile.model.bean.BaseData;
import air.edu.qile.model.bean.ModuleData;

/**
 * Created by Administrator on 2018/4/17.
 */

public class RcycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected List mDatas;
    private  int cardId;
    private  adpterClickListen clickListen;


    public RcycleviewAdapter(Context mContext, List mDatas, int cardId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.cardId = cardId;
    }

    public void setClickListen(adpterClickListen clickListen) {
        this.clickListen = clickListen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(cardId, parent, false);
        Holder_Card card = new Holder_Card(view);
        return card;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object objdata = mDatas.get(position);
        if(objdata  instanceof  ModuleData){
            ModuleData data = (ModuleData) objdata;
            if (holder instanceof Holder_Card) {
                Holder_Card card1= (Holder_Card) holder;
                card1.card1rlt.setTag( position );
                try {
                    card1.title.setText( data.getConfig().getName() );
                    Picasso.with(mContext)
                            .load(data.getCover().getUrl() )
                            .into(card1.cover);
                }catch (NullPointerException  ex){
                    ex.printStackTrace();
                }

            }
        }
        else  if(objdata  instanceof  BaseData){
            BaseData data = (BaseData) objdata;
            if (holder instanceof Holder_Card) {
                Holder_Card card1= (Holder_Card) holder;
                card1.card1rlt.setTag( position );
                try {
                    card1.title.setText( data.getName());
//                    Picasso.with(mContext)
//                            .load(data.getCover().getUrl() )
//                            .into(card1.cover);
                }catch (NullPointerException  ex){
                    ex.printStackTrace();
                }

            }
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class Holder_Card extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView cover;
        public TextView title;
        public RelativeLayout card1rlt;
        //实现的方法
        public Holder_Card(View itemView) {
            super(itemView);
            cover =  itemView.findViewById(R.id.cover);
            title =  itemView.findViewById(R.id.title);
            card1rlt= itemView.findViewById(R.id.card1rlt);
            card1rlt.setOnClickListener( this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.card1rlt:
                    int position = (int) view.getTag();
                    Object objdata = mDatas.get(position);
                    if(objdata  instanceof  ModuleData){
                        ModuleData data= (ModuleData) objdata;
                        String osspath=data.getFolder().getFullpath();
                          Intent intent=new Intent(mContext, DetailUrlActivity.class);
                      //  Intent intent=new Intent(mContext, DetailActivity.class);
                        intent.putExtra("osspath",osspath);
                        mContext.startActivity( intent );
                    }
                    if(objdata  instanceof  BaseData){
                        BaseData data= (BaseData) objdata;
                        if(clickListen!=null){
                            clickListen.click(position , mDatas);
                        }
                    }
                    break;
            }
        }
    }


    public interface  adpterClickListen{

        public void  click(int position  ,List mDatas);

    }


}
