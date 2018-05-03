package air.edu.qile.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 * <p>
 * 通用事件类
 */

public class MsgEvent {

    private String cmd;
    private Object content;
    private Object  extradata;
    private List   listdata;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }


    public List getListdata() {
        return listdata;
    }

    public void setListdata(List listdata) {
        this.listdata = listdata;
    }


    public Object getExtradata() {
        return extradata;
    }

    public void setExtradata(Object extradata) {
        this.extradata = extradata;
    }
}
