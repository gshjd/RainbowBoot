package com.example.rainboot.config.timedtask;

import com.example.rainboot.common.util.DingTalkUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务
 *
 * @version 1.0
 * @Author 小熊
 * @Created 2018/11/16 16:34
 */
@Configuration
public class CustomerRecovery {

    @Scheduled(cron = "0 30 20 * * ?")
    public void overtimeNotice() {
        String markdownStr = "#### 加班打卡通知\n8点30到了\n请记得打卡！";
        DingTalkUtil.sendLogMarkdown("打卡通知", markdownStr);
        DingTalkUtil.sendLogText("熊爸爸请打卡", "15170840217");
    }

    /*@Scheduled(cron = "0 30 18 * * ?")
    public void offDutyNotice() {
        String markdownStr = "#### 下班打卡通知\n6点30到了\n请记得打卡！";
        DingTalkUtil.sendLogMarkdown("打卡通知", markdownStr);
        DingTalkUtil.sendLogText("熊爸爸请打卡", "15170840217");
    }*/

    @Scheduled(cron = "0 20 17 * * ?")
    public void eatFood() {
        String markdownStr = "#### 晚饭点饭通知\n5点20到了\n请记得点饭！";
        DingTalkUtil.sendLogMarkdown("晚饭点饭通知", markdownStr);
        DingTalkUtil.sendLogText("熊爸爸请点晚饭", "15170840217");
    }
}
