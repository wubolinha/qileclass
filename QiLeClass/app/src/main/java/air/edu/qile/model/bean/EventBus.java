package air.edu.qile.model.bean;

/**
 * Created by Administrator on 2018/4/26 0026.
 * <p>
 * 通用事件类
 */

public class EventBus {

    private String cmd;
    private Object content;


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
}
