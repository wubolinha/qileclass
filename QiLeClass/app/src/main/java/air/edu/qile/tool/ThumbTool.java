package air.edu.qile.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import air.edu.qile.model.bean.VideoInfo;
import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by Administrator on 2018/5/3 0003.
 */

public class ThumbTool {


    public   VideoInfo  getVideoInfo( String etag, String url){


        VideoInfo info =DiskCache.getInstance().getVideoInfo(etag);
        if(info == null){
            Log.w("test","网络获取：  mUri:"+url.toString());

            FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
            mmr.setDataSource(  url);
            Bitmap bitmap= mmr.getScaledFrameAtTime( 3000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC,250,180 );
            // Bitmap bitmap = mmr.getFrameAtTime(2000000, FFmpegMediaMetadataRetriever.OPTION_NEXT_SYNC); // frame at 2 seconds
            String strDuration = mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION);
            info=new VideoInfo();
            info.setDuration(strDuration);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] datas = baos.toByteArray();
            info.setThumbitmap(datas);

            // 加入缓存
            DiskCache.getInstance().putVideoInfo(etag, info );

            mmr.release();
        }else {
            Log.w("test","使用 缓存  mUri:"+url.toString());
        }
        return   info;
    }






}
