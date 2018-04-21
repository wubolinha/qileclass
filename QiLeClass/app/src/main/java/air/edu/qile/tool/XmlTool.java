package air.edu.qile.tool;

import com.thoughtworks.xstream.XStream;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import air.edu.qile.model.bean.ModuleConfig;

/**
 * Created by Administrator on 2018/4/19 0019.
 */

public class XmlTool {


    private static  XmlTool instance;
    private XStream xStream=null;
    public XmlTool() {
        xStream=new XStream();

        xStream.alias("Folder", ModuleConfig.class);

        xStream.useAttributeFor(ModuleConfig.class, "show");
        xStream.useAttributeFor(ModuleConfig.class, "name");
        xStream.useAttributeFor(ModuleConfig.class, "cover");
        xStream.useAttributeFor(ModuleConfig.class, "cardtype");
        xStream.useAttributeFor(ModuleConfig.class, "describe");
        xStream.useAttributeFor(ModuleConfig.class, "title");

        xStream.alias("ModuleConfig", List.class);
    }

    public static XmlTool getInstance(){
        if( instance==null ){
            instance=new XmlTool();
        }
        return  instance;
    }


    // 读取
    public List<ModuleConfig>  ReadFromXML(String xmlnetpath){
        List<ModuleConfig> configlist=new ArrayList<ModuleConfig>();
        try {
            URL url = new URL(xmlnetpath);
            InputStreamReader in = new InputStreamReader (new BufferedInputStream(url.openStream()),"UTF-8");
            configlist= (List<ModuleConfig>) xStream.fromXML( in );
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configlist;
    }



    //  写入  xml
    public boolean WriteToXML(List<ModuleConfig> configlist, String SavePath){

        try {
            OutputStreamWriter out=new OutputStreamWriter( new FileOutputStream(SavePath),"UTF-8"  );
            xStream.toXML(configlist, out);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;

    }



}
