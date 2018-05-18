package air.edu.qile.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import air.edu.qile.model.bean.VideoInfo;
import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by Administrator on 2018/5/3 0003.
 */

public class ImageCacheTool {

    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);


    public static   VideoInfo  getVideoInfo( String etag, String url){

        VideoInfo info =DiskCache.getInstance().getVideoInfo(etag);
        if(info == null){
            Log.w("test","网络获取：  mUri:"+url.toString());

            FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
            mmr.setDataSource(  url);
            Bitmap bitmap= mmr.getScaledFrameAtTime( 5000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC,250,180 );
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


    // 异步显示图片
    public static void syncPutImageToView(final String etag, final String imageurl, final ImageView thumbview,  final TextView numtext){
        if(fixedThreadPool==null){
            fixedThreadPool = Executors.newFixedThreadPool(8);
        }
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                String urltag= (String) thumbview.getTag();
                String img_url = imageurl;
                if(urltag.equals( img_url )){
                    // 如果activity已经退出了，就不在继续执行

                    final VideoInfo info =  ImageCacheTool.getVideoInfo(etag, img_url);
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(info.getThumbitmap(), 0, info.getThumbitmap().length);

                    SimpleDateFormat formatter = new SimpleDateFormat("mm分ss秒");//这里想要只保留分秒可以写成"mm:ss"
                    formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
                    final String hms = formatter.format(Long.parseLong( info.getDuration() ));


                    thumbview.post(new Runnable() {
                        @Override
                        public void run() {
                            thumbview.setImageBitmap(bitmap);
                            numtext.setText(hms );
                        }
                    });

                }
            }
        });


    }

    public static void stopGetImage(){
        Log.w("test","停止获取图片 ...  fixedThreadPool.shutdownNow");
        fixedThreadPool.shutdownNow();
        fixedThreadPool=null;

    }


}
