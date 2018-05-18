package air.edu.qile.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import air.edu.qile.R;
import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.bean.BaseData;
import air.edu.qile.model.bean.MsgEvent;
import air.edu.qile.tool.CommonTool;

public class ListPlayActivity  extends GSYBaseActivityDetail<ListGSYVideoPlayer> {

    private ListGSYVideoPlayer detailPlayer;
    private RecyclerView detailaty_recycleview;
    private String osspath;
    private String osscover;
    private int currentplayPosition=0;
    private List   listdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_play);
        detailPlayer =findViewById(R.id.detail_player);
        detailaty_recycleview = findViewById(R.id.detailaty_recycleview);
        detailaty_recycleview.setLayoutManager(new LinearLayoutManager(this));

        EventBus.getDefault().register(this);
        //普通模式
        initVideo();
        osspath = getIntent().getStringExtra("osspath");
        osscover= getIntent().getStringExtra("osscover");
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);

        detailPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        detailPlayer.setRotateViewAuto(false);
        detailPlayer.setLockLand(false);
        detailPlayer.setShowFullAnimation(false);
        detailPlayer.setNeedLockFull(true);
        detailPlayer.setVideoAllCallBack(this);

        detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
        initdata();
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        try {
            osscover = CommonTool.encode(osscover,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(osscover).into(imageView);
        detailPlayer.setThumbImageView(imageView);
    }


    private void initdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] pathary  = osspath.split(".com/");
                String newpath =pathary[1].replace("奇乐课堂/","");
                OssBrowser.getInstance().disPatchTask("ShowFileinModule",newpath);
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusEvent(final MsgEvent msgEvent) {
        if(!msgEvent.getCmd().equals("BaseDataList")){
            return;
        }
        Log.w("test", "DetailUrlActivity baseDataList: " +msgEvent.getListdata().size());
        RcycleviewAdapter adapter = new RcycleviewAdapter(this, msgEvent.getListdata(), R.layout.card2);
        detailaty_recycleview.setAdapter(adapter);

        listdata=msgEvent.getListdata();

        final List<GSYVideoModel> urls = new ArrayList<>();
        for(Object obj :listdata){
            BaseData  data= (BaseData) obj;
            urls.add(new GSYVideoModel( data.getUrl() ,data.getName() ));
        }

        adapter.setClickListen(new RcycleviewAdapter.adpterClickListen() {
            @Override
            public void click(int position, List mDatas) {
                // Logger.w("click: " + position);
                currentplayPosition=position;
                detailPlayer.setUp(urls, true, currentplayPosition);

            }
        });
        if( msgEvent.getListdata().size() >0 ){
            currentplayPosition=0;
            detailPlayer.setUp(urls, true, currentplayPosition);
        }

    }

    @Override
    public ListGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        return null;
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        super.onEnterFullscreen(url, objects);
        //隐藏调全屏对象的返回按键
        GSYVideoPlayer gsyVideoPlayer = (GSYVideoPlayer)objects[1];
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
    }



    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return  detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


}
