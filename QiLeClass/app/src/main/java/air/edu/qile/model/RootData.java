package air.edu.qile.model;

import java.util.List;

import air.edu.qile.model.bean.ModuleConfig;
import air.edu.qile.tool.XmlTool;

/**
 * Created by Administrator on 2018/5/8.
 *
 *  一级界面数据，一级界面数据，为了尽快展示内容，一级界面的数据是不加密的
 *  http://shareworld.oss-cn-shenzhen.aliyuncs.com/%E5%A5%87%E4%B9%90%E8%AF%BE%E5%A0%82/DataConfig.txt
 *
 */

public class RootData {

    public static String  rooturl="http://shareworld.oss-cn-shenzhen.aliyuncs.com/%E5%A5%87%E4%B9%90%E8%AF%BE%E5%A0%82/DataConfig.txt";

    public  static List<ModuleConfig> getData(){
        List<ModuleConfig>   lists= XmlTool.getInstance().ReadFromXML( rooturl );
        return lists;
    }

}
