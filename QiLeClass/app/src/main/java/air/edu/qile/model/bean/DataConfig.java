package air.edu.qile.model.bean;

/**
 * Created by Administrator on 2018/4/18.
 *
 *   模块数据，对应oss上的  ModuleSet.xml

 <DataConfig>
 <!-- show:是否显示 ， true：在app中显示 ， false：在app中不显示-->

 <!-- cardtype:显示的卡片类型 -->

 <!-- folder:读取的数据文件夹-->

 <!--cover：封面图片 -->

 <!--title：标题 -->

 <!--description：简单描述 -->

 </DataConfig>


 *
 *
 */

public class DataConfig {


    private  String show;
    private  String name;
    private  String cover;
    private  String cardtype;
    private  String title;
    private  String describe;

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "DataConfig{" +
                "show='" + show + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", cardtype='" + cardtype + '\'' +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
