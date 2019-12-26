package com.example.rainboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * http连接工具
 *
 * @Author 小熊
 * @Created 2018/11/6 9:43
 * @Version 1.0
 */
public class HttpClientUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String post(String url, MultiValueMap<String, String> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.postForEntity(url, requestEntity, String.class);
        return response.getBody();
    }

    public static String get(String url) {
        RestTemplate client = new RestTemplate();
        //  执行HTTP请求
        ResponseEntity<String> response = client.getForEntity(url, String.class);
        return response.getBody();
    }

    public static String get(String url, MediaType mediaType) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        HttpEntity<String> entity = new HttpEntity<>(url, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return responseEntity.getBody();
    }

    public static String post(String url, MultiValueMap<String, Object> params, MediaType mediaType) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //  提交方式
        headers.setContentType(mediaType);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.postForEntity(url, requestEntity, String.class);
        return response.getBody();
    }

}
