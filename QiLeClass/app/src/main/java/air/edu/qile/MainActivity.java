package air.edu.qile;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import air.edu.qile.databinding.ActivityMainBinding;
import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.OssTokenGet;
import air.edu.qile.model.bean.TokenBean;
import air.edu.qile.ui.ListFragmentPagerAdapter;


public class MainActivity extends AppCompatActivity implements  ViewPager.OnPageChangeListener {


    private ListFragmentPagerAdapter mAdapter;
    private ActivityMainBinding  dataBinding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
       // setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initview();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        new OssTokenGet( ).getAccessToken();
                    }
                }
        ).start();
    }

    private void  initview(){
         mAdapter= new ListFragmentPagerAdapter(getSupportFragmentManager());
        dataBinding.viewpage.setOffscreenPageLimit(3);
        dataBinding.viewpage.setAdapter(mAdapter);
        dataBinding.viewpage.setCurrentItem(0);
        dataBinding.viewpage.addOnPageChangeListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

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
