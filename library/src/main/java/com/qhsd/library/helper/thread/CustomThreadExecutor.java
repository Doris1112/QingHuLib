package com.qhsd.library.helper.thread;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Doris
 * @date 2018/12/14
 **/
public class CustomThreadExecutor extends ThreadPoolExecutor {

    /**
     * 核心线程池大小（默认大小为5）
     */
    private static final int CORE_POOL_SIZE = 5;
    /**
     * 最大线程池队列大小
     */
    private static final int MAXIMUM_POOL_SIZE = 128;
    /**
     * 保持存活时间，当线程数大于corePoolSize的空闲线程能保持的最大时间。
     */
    private static final int KEEP_ALIVE = 1;
    /**
     * 主要获取添加任务
     */
    private static final AtomicLong SEQ_SEED = new AtomicLong(0);


    private static final ThreadFactory FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "线程" + String.valueOf(mCount.getAndDecrement()));
        }
    };

    /**
     * 线程队列方式 先进先出
     */
    private static final Comparator<Runnable> FIFO = new Comparator<Runnable>() {
        @Override
        public int compare(Runnable lhs, Runnable rhs) {
            if (lhs instanceof PrioriRunnable && rhs instanceof PrioriRunnable) {
                PrioriRunnable lpr = ((PrioriRunnable) lhs);
                PrioriRunnable rpr = ((PrioriRunnable) rhs);
                int result = lpr.priority.ordinal() - rpr.priority.ordinal();
                return result == 0 ? (int) (lpr.SEQ - rpr.SEQ) : result;
            } else {
                return 0;
            }
        }
    };

    /**
     * 线程队列方式 后进先出
     */
    private static final Comparator<Runnable> LIFO = new Comparator<Runnable>() {
        @Override
        public int compare(Runnable lhs, Runnable rhs) {
            if (lhs instanceof PrioriRunnable && rhs instanceof PrioriRunnable) {
                PrioriRunnable lpr = ((PrioriRunnable) lhs);
                PrioriRunnable rpr = ((PrioriRunnable) rhs);
                int result = lpr.priority.ordinal() - rpr.priority.ordinal();
                return result == 0 ? (int) (rpr.SEQ - lpr.SEQ) : result;
            } else {
                return 0;
            }
        }
    };

    /**
     * 默认工作线程数5
     *
     * @param fifo 优先级相同时, 等待队列的是否优先执行先加入的任务.
     */
    public CustomThreadExecutor(boolean fifo) {
        this(CORE_POOL_SIZE, fifo);
    }

    /**
     * @param poolSize 工作线程数
     * @param fifo     优先级相同时, 等待队列的是否优先执行先加入的任务.
     */
    private CustomThreadExecutor(int poolSize, boolean fifo) {
        this(poolSize, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new PriorityBlockingQueue<>(MAXIMUM_POOL_SIZE, fifo ? FIFO : LIFO), FACTORY);
    }

    private CustomThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    /**
     * 提交任务
     * @param runnable 需要运行的任务
     */
    @Override
    public void execute(Runnable runnable) {
        if (runnable instanceof PrioriRunnable) {
            ((PrioriRunnable) runnable).SEQ = SEQ_SEED.getAndIncrement();
        }
        super.execute(runnable);
    }

}
