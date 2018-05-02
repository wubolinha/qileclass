package air.edu.qile.model.bean;

/**
 * Created by Administrator on 2018/4/28 0028.
 */

public class TaskContent {
    private  String taskname;
    private  String taskparas;
    private   boolean  isfinish;  //任务是否完成


    public TaskContent(String taskname, String taskparas, boolean isfinish) {
        this.taskname = taskname;
        this.taskparas = taskparas;
        this.isfinish = isfinish;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTaskparas() {
        return taskparas;
    }

    public void setTaskparas(String taskparas) {
        this.taskparas = taskparas;
    }

    public boolean isIsfinish() {
        return isfinish;
    }

    public void setIsfinish(boolean isfinish) {
        this.isfinish = isfinish;
    }
}
