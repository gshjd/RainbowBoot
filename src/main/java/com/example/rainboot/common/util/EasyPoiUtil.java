package com.example.rainboot.common.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * easyPOI操作工具
 *
 * @Author 小熊
 * @Created 2019-07-10 03:39 PM
 * @Version 1.0
 */
@Slf4j
public class EasyPoiUtil {

    /**
     * 导出excel
     *
     * @param response  response
     * @param tableName 文件名称
     * @param list      文件内容
     * @param clazz     文件模板对象
     * @param title     文件标题
     * @param sheetName 表名称
     */
    public static void downExcel(HttpServletResponse response, String tableName, List<?> list, Class<?> clazz, String title, String sheetName) {
        //指定列表标题和工作表名称
        ExportParams params = new ExportParams(title, sheetName);
        Workbook workbook = ExcelExportUtil.exportExcel(params, clazz, list);
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 文件名称转码，否则出现乱码
        tableName = new String(tableName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + tableName + ".xls");
        response.setCharacterEncoding("UTF-8");
        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            log.error("导出失败：", e);
        }
    }

    /**
     * 导入Excel
     *
     * @param titleRows 标题行数
     * @param headRows  头行
     * @param file      excel文件
     * @param clazz     文件模板对象
     * @return excel数据
     */
    public static List<?> importExcel(int titleRows, int headRows, MultipartFile file, Class<?> clazz) {
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headRows);
        List<?> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), clazz, params);
        } catch (Exception e) {
            log.error("导入失败：", e);
        }
        return list;
    }
}
