package air.edu.qile.model;

import android.util.Log;

import air.edu.qile.MyApp;
import air.edu.qile.model.bean.TokenBean;

/**
 * Created by Administrator on 2018/4/19 0019.
 * <p>
 * <p>
 * 数据获取统一通道
 * <p>
 * 先从 oss 中 获取 数据 的 etag ，如果 在缓存中的 etag 相等，说明数据没有发生改变，那么就读取缓存中的数据
 * 如果 缓存中没有数据，或者 etag 不一致， 那么就以 oss 上 的数据为准
 */

public class DataAccess implements  OssTokenGet.OssTokenListen{


    private  OssTokenGet  tokenGet;
    private OssBrowser ossBrowser;
    private  boolean ossEnable=false;

    private static DataAccess instance;
    public static DataAccess getInstance() {
        if( instance ==null ){
            instance=new DataAccess();
        }
        return instance;
    }
    public DataAccess() {
         tokenGet =new OssTokenGet(this);
         tokenGet.getAccessToken();
    }

    // 对应的路径
    public String getData(final String  path   ){
        Log.w("test","getData:"+path);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while ( ! ossEnable ){

                }
                ossBrowser.ShowFolderFile(path);
            }
        }).start();

        return null;
    }


    @Override
    public void GetToken(TokenBean token) {

        ossBrowser=new OssBrowser(MyApp.AppContext , token );

        ossEnable=true;

    }



}
