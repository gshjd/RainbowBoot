package com.example.rainboot.system.util.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

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
        list.add(map);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<Map<Integer, String>> getList() {
        return list;
    }

}
