package air.edu.qile.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import air.edu.qile.R;
import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.RootOssHttp;
import air.edu.qile.model.bean.ModuleConfig;
import air.edu.qile.model.bean.MsgEvent;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, OnClickListener {

    private ImageView[] iconlist=new ImageView[4];
    private TextView[]  tvlist=new TextView[4];
    private String[] folderarray=new String[4];
    private  ViewPager viewpage;
    private int currentIndex;
    private boolean hadGetIndex=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        EventBus.getDefault().register(this);

        OssBrowser.getInstance().disPatchTask("init","");
        Log.w("test","MainActivity onCreate \n\n\n\n\n ");
        initview();

    }

    private void initview() {

        ListFragmentPagerAdapter  mAdapter = new ListFragmentPagerAdapter(getSupportFragmentManager());
        viewpage = findViewById(R.id.viewpage);
        viewpage.setOffscreenPageLimit(3);
        viewpage.setAdapter(mAdapter);
        viewpage.addOnPageChangeListener(this);

        iconlist[0] = findViewById(R.id.icon1);
        iconlist[1] = findViewById(R.id.icon2);
        iconlist[2] = findViewById(R.id.icon3);
        iconlist[3] = findViewById(R.id.icon4);
        tvlist[0] = findViewById(R.id.textv1);
        tvlist[1] = findViewById(R.id.textv2);
        tvlist[2] = findViewById(R.id.textv3);
        tvlist[3] = findViewById(R.id.textv4);

        iconlist[0].setOnClickListener(this);
        iconlist[1].setOnClickListener(this);
        iconlist[2].setOnClickListener(this);
        iconlist[3].setOnClickListener(this);

        viewpage.setCurrentItem(0);
        iconReset(0);

        getOpenOssConfigData( RootOssHttp.rootConfig );
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.icon1:
                iconReset(0);
                break;
            case R.id.icon2:
                iconReset(1);
                break;
            case R.id.icon3:
                iconReset(2);
                break;
            case R.id.icon4:
                iconReset(3);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(hadGetIndex){
            iconReset(currentIndex);
        }
     }

    private void iconReset(int index) {
        currentIndex = index;
        hadGetIndex=true;
        // Toast.makeText(this,"index:"+index,Toast.LENGTH_SHORT).show();
        for(int i=0;i<iconlist.length;i++){
            ImageView   iv=iconlist[i];
            AnimationDrawable temp_anim = (AnimationDrawable) iv .getBackground();
            if(i==index){
                temp_anim.start();
            }else {
                temp_anim.stop();
                temp_anim.selectDrawable(0);
            }
        }
        viewpage.setCurrentItem(index);
        for(TextView tv:tvlist){
            tv.setTextColor(Color.BLACK);
            tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        tvlist[index].setTextColor(Color.WHITE);
        tvlist[index] .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusEvent( MsgEvent msgEvent ) {
       if( msgEvent.getCmd().equals("class_root") ){
           List<ModuleConfig> configlist = msgEvent.getListdata();
           for(int i=0;i<configlist.size();i++){
               ModuleConfig config = configlist.get(i);
               if(i<4){
                   Log.w("test","获取 oss 上的 目录 :"+config.getName());
                   tvlist[i].setText(""+config.getDescribe());
                   folderarray[i]= config.getName();
                   //  获取每个分类下的配置数据
                   switch (i){
                       case 0:
                           Fragment_1.fg_tag=folderarray[0];
                           break;
                       case 1:
                           Fragment_2.fg_tag=folderarray[1];
                           break;
                       case 2:
                           Fragment_3.fg_tag=folderarray[2];
                           break;
                   }
                   getOpenOssConfigData(RootOssHttp.rootUrl+config.getName()+"/"+RootOssHttp.configName);
               }
           }
       }
    }
    private void  getOpenOssConfigData(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                RootOssHttp.getInstance().getOpenOssModuleList(url);
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /***********    滑动监听 *******************************/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
         iconReset(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
