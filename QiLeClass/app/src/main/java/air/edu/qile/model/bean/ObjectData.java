package air.edu.qile.model.bean;

import java.io.File;

/**
 * Created by Administrator on 2018/4/20.
 *
 *  oss 上的 数据对象
 *
 */

public class ObjectData {

    private String fullpath;
    private String url;
    private String etag;
    private long  objsize;


    // 文件夹特有属性，   类型数据设置 ，对应 DataConfig.txt
    private DataConfig  folderConfig;
    //封面 url
    private String      folderCover;
    //封面图片etag
    private String      folderCover_etag;



    public String getFullpath() {
        return fullpath;
    }
    public String getSimplName() {
        return new File( fullpath ).getName();
    }
    public String getParentPath() {
        return ( new File( fullpath ).getParentFile().getAbsolutePath()+"/").substring(1);
    }


    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public long getObjsize() {
        return objsize;
    }

    public void setObjsize(long objsize) {
        this.objsize = objsize;
    }

    public DataConfig getFolderConfig() {
        return folderConfig;
    }

    public void setFolderConfig(DataConfig folderConfig) {
        this.folderConfig = folderConfig;
    }

    public String getFolderCover() {
        return folderCover;
    }

    public void setFolderCover(String folderCover) {
        this.folderCover = folderCover;
    }

    public String getFolderCover_etag() {
        return folderCover_etag;
    }

    public void setFolderCover_etag(String folderCover_etag) {
        this.folderCover_etag = folderCover_etag;
    }

    @Override
    public String toString() {
        return "ObjectData{" +
                "fullpath='" + fullpath + '\'' +
                ", etag='" + etag + '\'' +
                ", objsize=" + objsize +
                ", folderConfig=" + folderConfig +
                ", folderCover='" + folderCover + '\'' +
                ", folderCover_etag='" + folderCover_etag + '\'' +
                '}';
    }
}
