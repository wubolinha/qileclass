package air.edu.qile;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.baidu.mobstat.StatService;
import com.orhanobut.hawk.Hawk;

/**
 * Created by Administrator on 2018/4/14.
 */

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        AppContext = this;
        startService(new Intent(this, BackService.class));


        // 百度⾃自动埋点，建议在Application中调⽤用。否则可能造成部分⻚页⾯面遗漏，⽆无法完整统计。
        // @param autoTrace：如果设置为true，打开⾃自动埋点；反之关闭
        // @param autoTrackWebview：
        // 如果设置为true，则⾃自动track所有webview，如果有对webview绑定WebChromeClient，
        // 为避免影响APP本⾝身回调，请调⽤用trackWebView接⼝口；
        // 如果设置为false，则不⾃自动track webview，如需对特定webview进⾏行统计，需要对特定
        // webview调⽤用trackWebView()即可。
        StatService.autoTrace(getApplicationContext(), true, true);


    }

    public static Context AppContext;


}
