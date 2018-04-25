package air.edu.qile.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import air.edu.qile.R;
import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.bean.BaseData;
import air.edu.qile.model.bean.ModuleData;

public class DetailActivity extends AppCompatActivity {

    private String osspath;
    private   RecyclerView detailaty_recycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        osspath = getIntent().getStringExtra("osspath");
        EventBus.getDefault().register(this);
        initview();
        initdata();
    }

    private  void initview(){
        detailaty_recycleview= findViewById(R.id.detailaty_recycleview);
        detailaty_recycleview.setLayoutManager(new LinearLayoutManager(this));

    }


    private void initdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OssBrowser.getInstance().ShowFileinModule(osspath);
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusEvent(Event_BaseData event_baseData) {
        Log.w("test", "baseDataList: " + event_baseData.baseDataList.size());

        RcycleviewAdapter adapter=new RcycleviewAdapter(this,event_baseData.baseDataList,R.layout.card2);
        detailaty_recycleview.setAdapter( adapter );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public static class Event_BaseData {
        public String cmd;
        public List<BaseData> baseDataList;

    }

}
