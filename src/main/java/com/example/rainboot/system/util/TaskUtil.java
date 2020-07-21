package com.example.rainboot.system.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务工具类（采用多线程线程池）
 *
 * @author 小熊
 * @version 1.2 增加指定时间执行定时任务
 */
@Slf4j
public class TaskUtil {

    /**
     * StoreInfo
     */
    private static ScheduledExecutorService taskService = null;

    /**
     * 创建线程池
     *
     * @param threadNum 创建线程池中的线程数
     */
    public TaskUtil(int threadNum) {
        taskService = (ScheduledExecutorService) ThreadPoolExecutorUtil.getThreadPool();
    }

    /**
     * 启动定时任务线程
     *
     * @param command      需要执行的线程
     * @param initialDelay 执行延迟时间
     * @param period       间隔时间
     * @param unit         时间单位
     * @return
     */
    public boolean start(Runnable command, long initialDelay, long period, TimeUnit unit) {
        try {
            taskService.scheduleAtFixedRate(command, initialDelay, period, unit);
        } catch (Exception e) {
            log.error("启动失败：", e);
            return false;
        }
        return true;
    }

    /**
     * 指定时间执行任务
     *
     * @param command 需要执行的线程
     * @param time    指定的时间 24小时制 格式:HH:mm:ss
     * @return 是否执行成功
     */
    public boolean start(Runnable command, String time) {
        try {
            long oneDay = 24 * 60 * 60 * 1000;
            long initDelay = getTimeMillis(time) - System.currentTimeMillis();
            taskService.scheduleAtFixedRate(command, initDelay, oneDay, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("执行失败：", e);
            return false;
        }
        return true;
    }

    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            log.error("获取失败：", e);
        }
        return 0;
    }

    /**
     * 关闭定时任务线程
     *
     * @return
     */
    public boolean stop() {
        try {
            taskService.shutdown();
        } catch (Exception e) {
            log.error("关闭失败：", e);
            return false;
        }
        return true;
    }

    /**
     * 关闭线程池
     */
    public void close() {
        taskService = null;
    }

    /**
     * 强制中断定时任务
     *
     * @return
     */
    public boolean stopNow() {
        try {
            taskService.shutdownNow();
        } catch (Exception e) {
            log.error("中断失败：", e);
            return false;
        }
        return true;
    }
}
