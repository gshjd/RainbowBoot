package com.example.rainboot.config.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.example.rainboot.common.util.DingTalkUtil;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * error级别日志捕捉并发送至钉钉
 * @Created 2019-07-25
 * @author 小熊
 */
public class SendErrorMsgAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent event) {
        if (event.getLevel() == Level.ERROR) {
            FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
            String markdownStr = "#### **异常详情**" +
                    "\n\n1.出错时间：\n\n>" + fastDateFormat.format(event.getTimeStamp()) +
                    "\n\n2.错误描述：\n\n>" + event.getFormattedMessage() +
                    "\n\n3.tranceId：\n\n>" + event.getMDCPropertyMap() +
                    "\n\n4.错误路径：\n\n>" + event.getLoggerName();
            DingTalkUtil.sendLogMarkdown("error通知", markdownStr);
        }
    }
}
