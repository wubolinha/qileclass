package air.edu.qile.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import air.edu.qile.MyApp;
import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.OssTokenGet;
import air.edu.qile.model.RootOssHttp;
import air.edu.qile.model.bean.ModuleData;
import air.edu.qile.model.bean.MsgEvent;
import air.edu.qile.model.bean.TokenBean;

/**
 * Created by Administrator on 2018/4/17.
 */

public class BaseFragment  extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w("test", getClass().getSimpleName()+ "   Register EventBus ...");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void EventBusEvent(List<ModuleData> moduleDataList) {


    }
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void EventBusEvent( MsgEvent msgEvent ) {
        Log.w("test",getClass().getSimpleName()+" 收到 EventBusEvent:  "+ msgEvent.getCmd());
    }

    public void  getOpenOssConfigData(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RootOssHttp.getInstance().getOpenOssModuleList(url);
            }
        }).start();

    }


}
