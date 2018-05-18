package air.edu.qile.model;

import android.util.Log;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import air.edu.qile.model.bean.ModuleConfig;
import air.edu.qile.model.bean.ModuleData;
import air.edu.qile.model.bean.MsgEvent;
import air.edu.qile.model.bean.OpenMuduleData;
import air.edu.qile.tool.XmlTool;

/**
 * Created by Administrator on 2018/5/12.
 */

public class RootOssHttp {

    public static  String rootUrl="http://shareworld.oss-cn-shenzhen.aliyuncs.com/奇乐课堂/";
    public  static String configName="DataConfig.txt";
    public static  String rootConfig=rootUrl+configName;

    private static RootOssHttp instance;
    public static RootOssHttp getInstance(){
        if(instance==null){
            instance=new RootOssHttp();
        }
        return instance;
    }

    public  void getOpenOssModuleList(String  url){
        Log.w("test","RootOssHttp getOpenOssModuleList : "+url);
        List<ModuleConfig> configlist =XmlTool.getInstance().ReadFromXML(url);
        List<OpenMuduleData> openMuduleDataList=new ArrayList<>();

        String cmd="";
        for(ModuleConfig  config:configlist){
            if(config.getCardtype().equals("root")){
                cmd="class_root";

            }else if(config.getCardtype().equals("1")) {


                String fatherurl=url.substring(0,url.length()-configName.length());
              //  Log.w("bolin:  ",config.toString()+"\nfatherurl:"+fatherurl);
                OpenMuduleData openMuduleData=new OpenMuduleData();
                openMuduleData.setConfig( config );
                openMuduleData.setFatherurl( fatherurl );
                openMuduleDataList.add( openMuduleData );
                cmd="class_"+new File(fatherurl).getName();
            }
        }
        MsgEvent msgEvent =new MsgEvent();
        msgEvent.setCmd(  cmd);
        if(cmd.equals("class_root")){
            msgEvent.setListdata( configlist );
        }else {
            msgEvent.setListdata( openMuduleDataList );
        }
        Log.w("bolin:  ","发送 msgEvent:"+msgEvent.getCmd());
        EventBus.getDefault().post (msgEvent);
    }




}
