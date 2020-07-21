package com.example.rainboot.common.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * 钉钉通知工具类
 *
 * @Author 小熊
 * @Created 2019-07-13 12:29 AM
 */
@Slf4j
public class DingTalkUtil {

    /**
     * 日志机器人地址
     */
    private static final String LOG_ROBOT_URL = "https://oapi.dingtalk.com/robot/send?access_token=466631aaef21dc35ad0305a1f63447f05eba27d55cb38fdd79e9807d4ef237b7";
    private static DingTalkClient client = new DefaultDingTalkClient(LOG_ROBOT_URL);

    private DingTalkUtil() {

    }

    /**
     * 发送Log日志text格式
     *
     * @param content 内容
     * @param atPhone at的用户手机号
     */
    public static void sendLogText(String content, String atPhone) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        getText(request, content, atPhone);
        try {
            client.execute(request);
        } catch (ApiException e) {
            log.error("日志机器人发送text格式消息失败：", e);
        }
    }

    /**
     * 发送Log日志link格式
     *
     * @param messageUrl 内容链接
     * @param picUrl     图片链接
     * @param title      标题
     * @param text       文本
     */
    public static void sendLogLink(String messageUrl, String picUrl, String title, String text) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        getLink(request, messageUrl, picUrl, title, text);
        try {
            client.execute(request);
        } catch (ApiException e) {
            log.error("日志机器人发送link格式消息失败：", e);
        }
    }

    /**
     * 发送Log日志markdown格式
     *
     * @param title 标题
     * @param text  内容
     */
    public static void sendLogMarkdown(String title, String text) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        getMarkdown(request, title, text);
        try {
            client.execute(request);
        } catch (ApiException e) {
            log.error("日志机器人发送markdown格式消息失败：", e);
        }
    }

    /**
     * 生成文本格式OapiRobotSendRequest
     *
     * @param content 文本内容
     * @param atPhone at的用户手机号
     */
    public static OapiRobotSendRequest getText(OapiRobotSendRequest request, String content, String atPhone) {
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Collections.singletonList(atPhone));
        request.setAt(at);
        return request;
    }

    /**
     * 生成link格式OapiRobotSendRequest
     *
     * @param messageUrl 内容链接
     * @param picUrl     图片链接
     * @param title      标题
     * @param text       文本
     */
    public static OapiRobotSendRequest getLink(OapiRobotSendRequest request, String messageUrl, String picUrl, String title, String text) {
        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(messageUrl);
        link.setPicUrl(picUrl);
        link.setTitle(title);
        link.setText(text);
        request.setLink(link);
        return request;
    }

    /**
     * 生成markdown格式OapiRobotSendRequest
     *
     * @param title 标题
     * @param text  内容
     */
    public static OapiRobotSendRequest getMarkdown(OapiRobotSendRequest request, String title, String text) {
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(text);
        request.setMarkdown(markdown);
        return request;
    }
}
