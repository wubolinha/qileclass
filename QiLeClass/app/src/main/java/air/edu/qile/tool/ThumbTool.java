package air.edu.qile.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import air.edu.qile.model.bean.VideoInfo;
import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by Administrator on 2018/5/3 0003.
 */

public class ThumbTool {


    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);



    public   VideoInfo  getVideoInfo(  String url){
        Log.w("test","mUri:"+url.toString());
        FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
        mmr.setDataSource(  url);
        Bitmap bitmap= mmr.getScaledFrameAtTime( 3000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC,250,180 );
        // Bitmap bitmap = mmr.getFrameAtTime(2000000, FFmpegMediaMetadataRetriever.OPTION_NEXT_SYNC); // frame at 2 seconds
        String strDuration = mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION);
        VideoInfo info=new VideoInfo();
        info.setDuration(strDuration);
        info.setThumbitmap(bitmap);
        mmr.release();
        return   info;
    }




    // 加入处理队列
    public   void addToHanderQueue(final String   url){

        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {

                getVideoInfo(url);
            }
        });


    }


}
