package air.edu.qile.model.bean;

/**
 * Created by Administrator on 2018/4/28 0028.
 *
 *   包含时间标签 的 TokenBean
 */

public class TimeTagTokenBean {

    private long time;
    private TokenBean tokenBean;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public TokenBean getTokenBean() {
        return tokenBean;
    }

    public void setTokenBean(TokenBean tokenBean) {
        this.tokenBean = tokenBean;
    }
}
