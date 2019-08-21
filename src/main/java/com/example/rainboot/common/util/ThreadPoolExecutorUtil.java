package com.example.rainboot.common.util;

import java.util.concurrent.*;

/**
 * 线程池工具类
 *
 * @Author 小熊
 * @Version 1.0
 */
public class ThreadPoolExecutorUtil {
    private ThreadPoolExecutorUtil() {
    }
    /**
     * 线程池中所存放的线程数
     */
    private static final int CORE_POOL_SIZE = 8;
    /**
     * 线程池中允许的最大线程数
     */
    private static final int MAX_CORE_POOL_SIZE = 200;
    /**
     * 当线程数大于核心时,终止前多余的空闲线程等待时间(单位：毫秒)
     */
    private static final int KEEP_ALIVE_TIME = 3000;
    /**
     * 执行前用于保持任务的队列
     */
    private static final int BLOCKING_QUEUE_SIZE = 10;
    /**
     * 参数的时间单位
     */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_CORE_POOL_SIZE, KEEP_ALIVE_TIME, UNIT, new LinkedBlockingQueue<>(BLOCKING_QUEUE_SIZE), new ThreadPoolExecutor.AbortPolicy());

    public static ThreadPoolExecutor getThreadPool() {
        return THREAD_POOL;
    }

    public static void close() {
        THREAD_POOL.allowCoreThreadTimeOut(true);
    }
}
