package cn.com.jcgroup.admin.task;

/**
 * 定时任务基类
 * @author LiuYong on 17/6/3 下午3:20.
 */
public abstract class SynTask {
    
    /**
     * 开始定时任务
     */
    public void startTask(){};

    /**
     * 设置定时任务表达式
     */
    public abstract void schedule();

    /**
     * 执行业务逻辑
     */
    public abstract void doTask();

    /**
     * 结束定时任务
     */
    public void endTask(){};

    /**
     * 执行定时任务
     */
    public void synTask(){
        startTask();
        doTask();
        endTask();
    }
}
