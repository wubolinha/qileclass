package air.edu.qile.model;

/**
 * Created by Administrator on 2018/4/18.
 *
 *   模块数据，对应oss上的  ModuleSet.xml

 <DataConfig>
 <!-- show:是否显示 ， true：在app中显示 ， false：在app中不显示-->
 <show>true</show>
 <!-- card:显示的卡片类型 -->
 <card>1</card>
 <!-- folder:读取的数据文件夹-->
 <folder>嘟啦古诗</folder>
 <!--image：封面图片 -->
 <image>嘟啦古诗.jpg</image>
 <!--title：标题 -->
 <title>嘟啦古诗</title>
 <!--description：简单描述 -->
 <description>熟读古诗300首，不会作诗也会吟</description>
 </DataConfig>


 *
 *
 */

public class ModuleData {


    private  boolean show;
    private  int card;
    private  String folder;
    private  String image;
    private  String title;
    private  String description;


}
