package air.edu.qile.ui;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import air.edu.qile.R;
import air.edu.qile.databinding.ActivityMainBinding;
import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.OssTokenGet;
import air.edu.qile.model.bean.TokenBean;
import air.edu.qile.ui.ListFragmentPagerAdapter;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, OnClickListener {

    private ImageView[] iconlist=new ImageView[4];
    private int[]  backgroundlist={R.drawable.item1_anim,R.drawable.item2_anim,R.drawable.item3_anim,R.drawable.item4_anim};
    private  ViewPager viewpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

        iconlist[0].setOnClickListener(this);
        iconlist[1].setOnClickListener(this);
        iconlist[2].setOnClickListener(this);
        iconlist[3].setOnClickListener(this);

        viewpage.setCurrentItem(0);
        iconReset(0);
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

    private void iconReset(int index) {
        // Toast.makeText(this,"index:"+index,Toast.LENGTH_SHORT).show();
        iconlist[0] .setBackgroundResource(R.drawable.shuxue_nor);
        iconlist[1].setBackgroundResource(R.drawable.xuexitiandi_nor);
        iconlist[2].setBackgroundResource(R.drawable.yingyu_nor);
        iconlist[3].setBackgroundResource(R.drawable.yuwen_nor);
        iconlist[index] .setBackgroundResource(backgroundlist[index]);
        AnimationDrawable anim = (AnimationDrawable) iconlist[index] .getBackground();
        anim.start();
        viewpage.setCurrentItem(index);
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
         iconReset(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
