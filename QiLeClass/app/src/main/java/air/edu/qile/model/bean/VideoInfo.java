package air.edu.qile.model.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by Administrator on 2018/5/3 0003.
 *
 *
 *  视频信息： 视频缩略图 和 时长
 */

public class VideoInfo {


    private Bitmap  thumbitmap;  // 缩略图
    private String  duration; //视频时长

    public Bitmap getThumbitmap() {
        return thumbitmap;
    }

    public void setThumbitmap(Bitmap thumbitmap) {
        this.thumbitmap = thumbitmap;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }







}
