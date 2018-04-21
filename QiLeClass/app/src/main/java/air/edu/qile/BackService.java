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



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();



     }




    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
