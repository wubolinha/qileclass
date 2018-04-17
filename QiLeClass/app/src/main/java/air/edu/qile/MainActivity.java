package air.edu.qile;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import air.edu.qile.oss.OssHelp;
import air.edu.qile.oss.OssNet;
import air.edu.qile.oss.TokenBean;
import air.edu.qile.ui.ListFragmentPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements  ViewPager.OnPageChangeListener {


    ViewPager viewpage;

    private ListFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        viewpage=findViewById(R.id.viewpage);
        viewpage.setAdapter(mAdapter);
        viewpage.setCurrentItem(0);
        viewpage.addOnPageChangeListener(this);
    }

    public void threadStart ( ){

        OssNet.getInstance().getAccessToken();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusEvent(TokenBean bean) {
        OssHelp osshelp=new OssHelp( this ,  bean );
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
