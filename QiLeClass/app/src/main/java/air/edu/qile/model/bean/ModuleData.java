package air.edu.qile.model.bean;

/**
 * Created by Administrator on 2018/4/21.
 *
 *  模块 数据
 */

public class ModuleData {

    private  String  fatherModule; //父目录
    private  BaseData folder;  //文件夹
    private  BaseData cover;  //封面
    private  ModuleConfig config;  //模块配置

    public BaseData getFolder() {
        return folder;
    }

    public void setFolder(BaseData folder) {
        this.folder = folder;
    }

    public BaseData getCover() {
        return cover;
    }

    public void setCover(BaseData cover) {
        this.cover = cover;
    }

    public ModuleConfig getConfig() {
        return config;
    }

    public void setConfig(ModuleConfig config) {
        this.config = config;
    }

    public String getFatherModule() {
        return fatherModule;
    }

    public void setFatherModule(String fatherModule) {
        this.fatherModule = fatherModule;
    }

    @Override
    public String toString() {
        return "ModuleData{" +
                "fatherModule='" + fatherModule + '\'' +
                ", folder=" + folder +
                ", cover=" + cover +
                ", config=" + config +
                '}';
    }
}
