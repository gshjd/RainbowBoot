package com.example.rainboot.common.util.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.cache.MapCache;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Excel处理
 *
 * @author: lijunhao
 * @date: 2019/12/25/025 18:23
 * @version: 1.0
 */
public class EasyExcelUtil {

    /**
     * 读取Excel
     *
     * @param file     文件
     * @param password 文件密码
     * @return 文件列表
     */
    public static List<Map<Integer, String>> readExcel(File file, String password) {
        ExcelListener listener = new ExcelListener();
        readExcel(file, listener, password);
        return listener.getList();
    }

    /**
     * 读取文件
     *
     * @param file     文件
     * @param listener 映射监听器
     * @param password 文件密码
     */
    public static void readExcel(File file, AnalysisEventListener<?> listener, String password) {
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file, listener);
        if (StringUtils.isNotBlank(password)) {
            excelReaderBuilder.password(password);
        }
        excelReaderBuilder.readCache(new MapCache()).sheet().doRead();
    }
}
