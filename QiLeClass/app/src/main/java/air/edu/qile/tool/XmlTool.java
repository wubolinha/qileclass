package air.edu.qile.tool;

import com.thoughtworks.xstream.XStream;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import air.edu.qile.model.bean.DataConfig;

/**
 * Created by Administrator on 2018/4/19 0019.
 */

public class XmlTool {


    private static  XmlTool instance;
    private XStream xStream=null;
    public XmlTool() {
        xStream=new XStream();

        xStream.alias("Folder", DataConfig.class);

        xStream.useAttributeFor(DataConfig.class, "show");
        xStream.useAttributeFor(DataConfig.class, "name");
        xStream.useAttributeFor(DataConfig.class, "cover");
        xStream.useAttributeFor(DataConfig.class, "cardtype");
        xStream.useAttributeFor(DataConfig.class, "describe");
        xStream.useAttributeFor(DataConfig.class, "title");

        xStream.alias("DataConfig", List.class);
    }

    public static XmlTool getInstance(){
        if( instance==null ){
            instance=new XmlTool();
        }
        return  instance;
    }


    // 读取
    public List<DataConfig>  ReadFromXML(String xmlnetpath){
        List<DataConfig> configlist=new ArrayList<DataConfig>();
        try {
            URL url = new URL(xmlnetpath);
            InputStreamReader in = new InputStreamReader (new BufferedInputStream(url.openStream()),"UTF-8");
            configlist= (List<DataConfig>) xStream.fromXML( in );
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configlist;
    }



    //  写入  xml
    public boolean WriteToXML(List<DataConfig> configlist,String SavePath){

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
