package air.edu.qile.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;

import air.edu.qile.R;
import air.edu.qile.model.bean.BaseData;
import air.edu.qile.model.bean.OpenMuduleData;
import air.edu.qile.model.bean.VideoInfo;
import air.edu.qile.tool.CommonTool;
import air.edu.qile.tool.ImageCacheTool;

/**
 * Created by Administrator on 2018/4/17.
 */

public class RcycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected List mDatas;
    private int cardId;
    private adpterClickListen clickListen;


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
        if (objdata instanceof OpenMuduleData) {
            final OpenMuduleData data = (OpenMuduleData) objdata;
            if (holder instanceof Holder_Card) {
                final Holder_Card card1 = (Holder_Card) holder;
                card1.card1rlt.setTag(position);
                card1.number.setText(data.getNumInModule() + "");
                try {
                    card1.title.setText(  data.getConfig().getName());
                    String coverurl = data.getFatherurl() + data.getConfig().getCover();
                    try {
                        coverurl = CommonTool.encode(coverurl,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Picasso.with(mContext).load(coverurl).into(card1.cover);


//                    final Bitmap bitmap= DiskCache.getInstance().getImage(  coverurl   );
//                    if( bitmap==null){
//                        // 首先获取缓存，如果缓存不存在，
//                        Log.w("test","网络  coverurl:"+coverurl);
//                        // Picasso.with(mContext).load(data.getCover().getUrl() ).into(card1.cover);
//                        Picasso.with(mContext).load(coverurl ).into(new Target() {
//                            @Override
//                            public void onBitmapLoaded(Bitmap netbitmap, Picasso.LoadedFrom from) {
//                                card1.cover.setImageBitmap( netbitmap );
//                                DiskCache.getInstance().putImage( coverurl , netbitmap);
//                            }
//                            @Override
//                            public void onBitmapFailed(Drawable errorDrawable) {
//                            }
//                            @Override
//                            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                            }
//                        });
//                    }else {
//                        Log.w("test","缓存  coverurl:"+coverurl);
//                        card1.cover.setImageBitmap( bitmap );
//                    }

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

            }
        } else if (objdata instanceof BaseData) {
            BaseData data = (BaseData) objdata;
            if (holder instanceof Holder_Card) {
                Holder_Card card1 = (Holder_Card) holder;
                card1.card1rlt.setTag(position);
                try {
                    card1.title.setText((position+1)+", "+data.getName());
                    card1.cover.setTag(  data.getUrl() );
                    ImageCacheTool.syncPutImageToView(data.getEtag() , data.getUrl() , card1.cover ,card1.number );

                } catch (NullPointerException ex) {
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
        public TextView title, number;
        public RelativeLayout card1rlt;

        //实现的方法
        public Holder_Card(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            title = itemView.findViewById(R.id.title);

            card1rlt = itemView.findViewById(R.id.card1rlt);
            card1rlt.setOnClickListener(this);

            number = itemView.findViewById(R.id.number);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.card1rlt:
                    int position = (int) view.getTag();
                    Object objdata = mDatas.get(position);
                    if (objdata instanceof OpenMuduleData) {
                        OpenMuduleData data = (OpenMuduleData) objdata;

                        String osspath = data.getFatherurl() + data.getConfig().getName();
                        String coverurl = data.getFatherurl() + data.getConfig().getCover();

                        Intent intent = new Intent(mContext, DetailUrlActivity.class);
                   //     Intent intent = new Intent(mContext, ListPlayActivity.class);

                        intent.putExtra("osspath", osspath+"/");
                        intent.putExtra("osscover", coverurl);
                        mContext.startActivity(intent);
                    }
                    if (objdata instanceof BaseData) {
                        BaseData data = (BaseData) objdata;
                        if (clickListen != null) {
                            clickListen.click(position, mDatas);
                        }
                    }
                    break;
            }
        }
    }


    public interface adpterClickListen {

        public void click(int position, List mDatas);

    }


}
