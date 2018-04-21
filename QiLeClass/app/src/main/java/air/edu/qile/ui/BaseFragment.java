package air.edu.qile.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import air.edu.qile.model.bean.ModuleData;
import air.edu.qile.model.bean.TokenBean;

/**
 * Created by Administrator on 2018/4/17.
 */

public class BaseFragment  extends Fragment {

    protected OssBrowser ossBrowser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void EventBusEvent(TokenBean bean) {
        ossBrowser=new OssBrowser(MyApp.AppContext , bean );

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void EventBusEvent(List<ModuleData> moduleDataList) {


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
