package air.edu.qile;

import android.app.Application;
import android.content.Context;

import com.orhanobut.hawk.Hawk;

/**
 * Created by Administrator on 2018/4/14.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        AppContext=this;
    }

    public  static Context AppContext;


}
