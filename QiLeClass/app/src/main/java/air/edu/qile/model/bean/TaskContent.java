package air.edu.qile.model.bean;

/**
 * Created by Administrator on 2018/4/28 0028.
 */

public class TaskContent {
    private  String taskname;
    private  String taskparas;


    public TaskContent(String taskname, String taskparas) {
        this.taskname = taskname;
        this.taskparas = taskparas;
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
}
