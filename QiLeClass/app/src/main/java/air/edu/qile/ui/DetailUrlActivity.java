package air.edu.qile.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
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
import air.edu.qile.model.bean.TokenBean;
import air.edu.qile.tool.CommonTool;

public class DetailUrlActivity extends AppCompatActivity {

    private String osspath;
    private String osscover;
    private RecyclerView detailaty_recycleview;
    private StandardGSYVideoPlayer  detailPlayer;
    private RelativeLayout activity_detail_player;
    private OrientationUtils orientationUtils;
    private GSYVideoOptionBuilder gsyVideoOptionBuilder;
    private boolean isPlay;
    private boolean isPause;
    private boolean cache=false;
    private int currentplayPosition=0;
    private List   listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置一个exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        Log.w("test","DetailUrlActivity onCreate \n\n\n\n\n ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        osspath = getIntent().getStringExtra("osspath");
        osscover= getIntent().getStringExtra("osscover");
        Log.w("test","osspath:  "+osspath+"   osscover:  "+osscover);
        EventBus.getDefault().register(this);
        initview();
        initdata();
    }

    private void initview() {
        detailaty_recycleview = findViewById(R.id.detailaty_recycleview);
        detailaty_recycleview.setLayoutManager(new LinearLayoutManager(this));
        detailPlayer = findViewById(R.id.detail_player);
        activity_detail_player=findViewById(R.id.activity_detail_player);
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        try {
            osscover = CommonTool.encode(osscover,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(osscover).into(imageView);

        gsyVideoOptionBuilder = new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl("")
                .setCacheWithPlay(cache)
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                       // Log.w("test","progress: "+progress+"   currentPosition: "+currentPosition+"  duration: "+duration);
                        if(duration -currentPosition <800 ){
                            //  播放下一个
                            if(currentplayPosition+1< listdata.size()){
                                currentplayPosition=currentplayPosition+1;
                            }else {
                                currentplayPosition=0;  //重头开始
                            }
                            playVideo();
                        }
                    }
                })
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }
                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                });
        if(gsyVideoOptionBuilder!=null){
            gsyVideoOptionBuilder.build(detailPlayer);

        }
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(DetailUrlActivity.this, true, true);
            }
        });
        detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
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
        adapter.setClickListen(new RcycleviewAdapter.adpterClickListen() {
            @Override
            public void click(int position, List mDatas) {
                currentplayPosition=position;
                playVideo(  );
            }
        });
        if( msgEvent.getListdata().size() >0 ){
            BaseData data = (BaseData) msgEvent.getListdata().get(0);
            reSetCover( data.getUrl() , data.getName()   );
            currentplayPosition=0;
        }
        // 顺序播放
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        super.onResume();
        isPause = false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return  detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }

    //重新设置封面的播放地址
    private void reSetCover( String url , String name){
        detailPlayer.release();
        gsyVideoOptionBuilder.setUrl(url)
                .setCacheWithPlay(cache)
                .setVideoTitle(name)
                .build(detailPlayer);
        gsyVideoOptionBuilder.build(detailPlayer);
    }


    private void playVideo(    ) {
        String url=((BaseData)listdata.get(currentplayPosition)).getUrl() ;
        String name=  ((BaseData)listdata.get(currentplayPosition)).getName();
        detailPlayer.release();

        gsyVideoOptionBuilder
                .setUrl(url)
                .setVideoTitle(name)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setCacheWithPlay(cache)
                .build(detailPlayer);

       detailPlayer.startPlayLogic();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (isPlay) {
            getCurPlay().release();
        }
         if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

}
