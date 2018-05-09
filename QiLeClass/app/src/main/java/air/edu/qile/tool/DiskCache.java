package air.edu.qile.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import java.io.ByteArrayOutputStream;

import air.edu.qile.model.bean.VideoInfo;

/**
 * Created by Administrator on 2018/5/3.
 */

public class DiskCache {

    private static DiskCache instance;
    public  static DiskCache getInstance(){
        if( instance == null){
            instance=new DiskCache();
        }
        return instance;
    }

    public void  putVideoInfo(String tag ,VideoInfo info ){
        Log.w("test","DiskCache  put :  "+tag);
        Hawk.put(tag,info);
    }

    public VideoInfo  getVideoInfo(String url){
        Log.w("test","DiskCache  get :  "+url);
        if(Hawk.contains(url)){
            return Hawk.get(url);
        }
        return null;
    }


    public void  putImage(String tag ,Bitmap bitmap ){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datas = baos.toByteArray();
        Hawk.put(tag,datas);
        Log.w("test","putImage  to cache "+tag);
    }

    public Bitmap  getImage(String tag){
        if(  Hawk.contains( tag ) ){
            byte[] datas = Hawk.get( tag );
            Bitmap bitmap = BitmapFactory.decodeByteArray(datas, 0,datas.length);
            Log.w("test","getImage  from cache "+tag);
            return bitmap;
        }
        return null;
    }


}
