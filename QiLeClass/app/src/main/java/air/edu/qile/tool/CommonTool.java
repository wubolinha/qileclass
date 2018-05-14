package air.edu.qile.tool;

/**
 * Created by Administrator on 2018/5/13.
 */

public class CommonTool {

    //  shareworld  ->   childrenedu  路径转换
    public static  String  pathTrans(String  path){
        String[] pathary  = path.split(".com/");
        String newpath =pathary[1].replace("奇乐课堂/","");
        return newpath;
    }


}
