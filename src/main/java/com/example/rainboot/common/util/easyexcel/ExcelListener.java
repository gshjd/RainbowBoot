package com.example.rainboot.common.util.easyexcel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lijunhao
 * @date: 2019/12/25/025 10:34
 * @version: 1.0
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener<Map<Integer, String>> {
    private List<Map<Integer, String>> list = new ArrayList<>();

    @Override
    public void invoke(Map<Integer, String> map, AnalysisContext analysisContext) {
//        log.info("解析到一条数据:{}", JSON.toJSONString(o));
        list.add(map);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<Map<Integer, String>> getList() {
        return list;
    }

}
