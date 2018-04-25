package air.edu.qile.model.bean;

/**
 * Created by Administrator on 2018/4/21.
 *
 *   oss 文件的基本信息
 *
 */

public class BaseData {

    private String name;      // 文件名
    private String fullpath;  //文件在 oss上的全路径
    private long size;    //  文件大小
    private String etag;    // etag
    private String url;     //文件url
    private boolean isFolder;  //是否是文件夹


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }


    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "name='" + name + '\'' +
                ", fullpath='" + fullpath + '\'' +
                ", size=" + size +
                ", etag='" + etag + '\'' +
                ", isFolder=" + isFolder +
                ", url='" + url + '\'' +
                '}';
    }
}
