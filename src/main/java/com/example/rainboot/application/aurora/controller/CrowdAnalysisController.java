package com.example.rainboot.application.aurora.controller;

import com.example.rainboot.application.aurora.pojo.vo.UploadNumberPackageVO;
import com.example.rainboot.system.config.runner.loadurl.AuroraUrl;
import com.example.rainboot.system.util.ApiResult;
import com.example.rainboot.system.util.BeanUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 号码包人群分析
 *
 * @author 小熊
 */
@RestController
@RequestMapping("crowdAnalysis")
public class CrowdAnalysisController {

    /**
     * 上传号码包
     *
     * @param vo
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("uploadNumberPackage")
    public ApiResult<?> uploadNumberPackage(@RequestBody UploadNumberPackageVO vo) throws JsonProcessingException {
        Map<String, Object> params = BeanUtils.transBeanToMap(vo);
        RestTemplate restTemplate = new RestTemplate();
        String url = AuroraUrl.PACKAGE;
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, params, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<?, ?> map = objectMapper.readValue(responseEntity.getBody(), Map.class);
        String remark = map.get("remark").toString();

        if (responseEntity.getStatusCodeValue() < 400) {
            return ApiResult.success(map.get("result"), remark);
        }

        return ApiResult.error(responseEntity.getStatusCodeValue(), responseEntity.getBody(), remark);
    }
}
