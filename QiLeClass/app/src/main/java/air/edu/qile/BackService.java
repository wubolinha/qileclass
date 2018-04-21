package air.edu.qile;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.OssTokenGet;
import air.edu.qile.model.bean.TokenBean;

public class BackService extends Service {

    private OssTokenGet tokenGet;
    private OssBrowser ossBrowser;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        tokenGet =new OssTokenGet( );
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        tokenGet.getAccessToken();
                    }
                }
        ).start();
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusEvent(TokenBean bean) {

        ossBrowser=new OssBrowser(MyApp.AppContext , bean );

        ossBrowser.ShowFolderFile("奇乐课堂/微课堂/");
        ossBrowser.ShowFolderFile("奇乐课堂/故事汇/");
        ossBrowser.ShowFolderFile("奇乐课堂/歌乐汇/");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
