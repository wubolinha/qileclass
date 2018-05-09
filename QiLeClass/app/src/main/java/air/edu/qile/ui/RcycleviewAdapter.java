package air.edu.qile.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import air.edu.qile.R;
import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.bean.BaseData;
import air.edu.qile.model.bean.ModuleData;
import air.edu.qile.model.bean.VideoInfo;
import air.edu.qile.tool.DiskCache;
import air.edu.qile.tool.ThumbTool;

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

    public List getmDatas() {
        return mDatas;
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
            final ModuleData data = (ModuleData) objdata;
            if (holder instanceof Holder_Card) {
                final Holder_Card card1= (Holder_Card) holder;
                card1.card1rlt.setTag( position );
                card1.number.setText( data.getNumInModule() +"" );
                try {
                    card1.title.setText( data.getConfig().getName() );
                    final Bitmap bitmap= DiskCache.getInstance().getImage( data.getCover().getEtag() );
                    if( bitmap==null){
                        // 首先获取缓存，如果缓存不存在，
                        // Picasso.with(mContext).load(data.getCover().getUrl() ).into(card1.cover);
                        Picasso.with(mContext).load(data.getCover().getUrl() ).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap netbitmap, Picasso.LoadedFrom from) {
                                card1.cover.setImageBitmap( netbitmap );
                                DiskCache.getInstance().putImage( data.getCover().getEtag() , netbitmap);
                            }
                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                            }
                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                            }
                        });
                    }else {
                        card1.cover.setImageBitmap( bitmap );
                    }

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

                    VideoInfo info= new ThumbTool ().getVideoInfo( data.getEtag(),  data.getUrl() );
                    Bitmap bitmap = BitmapFactory.decodeByteArray( info.getThumbitmap(), 0, info.getThumbitmap().length);
                    card1.cover.setImageBitmap( bitmap );

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
        public TextView title,number;
        public RelativeLayout card1rlt;
        //实现的方法
        public Holder_Card(View itemView) {
            super(itemView);
            cover =  itemView.findViewById(R.id.cover);
            title =  itemView.findViewById(R.id.title);

            card1rlt= itemView.findViewById(R.id.card1rlt);
            card1rlt.setOnClickListener( this);

            number= itemView.findViewById(R.id.number);

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
                        intent.putExtra("osscover",data.getCover().getUrl());
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
