package air.edu.qile;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import air.edu.qile.databinding.ActivityMainBinding;
import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.OssTokenGet;
import air.edu.qile.model.TokenBean;
import air.edu.qile.ui.ListFragmentPagerAdapter;


public class MainActivity extends AppCompatActivity implements  ViewPager.OnPageChangeListener {


    private ListFragmentPagerAdapter mAdapter;
    private ActivityMainBinding  dataBinding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  bug: 这里为什么要同时使用 2 个 setContentView ？
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
       // setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        threadStart();
                    }
                }
        ).start();
        initview();
    }

    private void  initview(){
         mAdapter= new ListFragmentPagerAdapter(getSupportFragmentManager());
        dataBinding.viewpage.setAdapter(mAdapter);
        dataBinding.viewpage.setCurrentItem(0);
        dataBinding.viewpage.addOnPageChangeListener(this);
    }

    public void threadStart ( ){

        OssTokenGet.getInstance().getAccessToken();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusEvent(TokenBean bean) {

        OssBrowser osshelp=new OssBrowser( this ,  bean );
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

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
