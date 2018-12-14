package com.qhsd.library.helper.thread;

/**
 * @author Doris
 * @date 2018/12/14
 **/
public class PrioriRunnable implements Runnable {

    public final Priority priority;
    private final Runnable runnable;
    long SEQ;

    /**
     * 构造函数
     *
     * @param priority 优先级
     * @param runnable 任务对象
     */
    public PrioriRunnable(Priority priority, Runnable runnable) {
        this.priority = priority == null ? Priority.NORMAL : priority;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        this.runnable.run();

    }
}
