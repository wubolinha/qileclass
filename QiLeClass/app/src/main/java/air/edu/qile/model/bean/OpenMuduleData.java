package air.edu.qile.model.bean;

/**
 * Created by Administrator on 2018/5/12.
 */

public class OpenMuduleData {

    private String fatherurl;
    private  ModuleConfig config;  //模块配置
    private  String  NumInModule="0";  //模块内的文件数目

    public String getFatherurl() {
        return fatherurl;
    }

    public void setFatherurl(String fatherurl) {
        this.fatherurl = fatherurl;
    }

    public ModuleConfig getConfig() {
        return config;
    }

    public void setConfig(ModuleConfig config) {
        this.config = config;
    }


    public String getNumInModule() {
        return NumInModule;
    }

    public void setNumInModule(String numInModule) {
        NumInModule = numInModule;
    }
}
