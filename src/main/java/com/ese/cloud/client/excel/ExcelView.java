package com.ese.cloud.client.excel;

import com.ese.cloud.client.entity.hunanUnicom.NetworkReport;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * excel类
 * Created by rencong on 16/12/27.
 */
public class ExcelView extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<NetworkReport> data = (List<NetworkReport>) map.get("data");//故障数据
        String start = map.get("start").toString();//开始时间
        String end = map.get("end").toString();//结束时间

        HSSFSheet sheet = workbook.createSheet("list");
        sheet.setDefaultColumnWidth((short) 12);
        HSSFCell cell = getCell(sheet, 0, 0);
        setText(cell, start+"-"+end+"故障统计");
        HSSFCellStyle dateStyle = workbook.createCellStyle();
        //dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));
        cell = getCell(sheet, 1, 0);
        cell.setCellValue("日期：2008-10-23");
        //cell.setCellStyle(dateStyle);
        getCell(sheet, 2, 0).setCellValue("");
        getCell(sheet, 2, 1).setCellValue("");
        getCell(sheet, 2, 2).setCellValue("");
        getCell(sheet, 2, 3).setCellValue("");
        getCell(sheet, 2, 4).setCellValue("");
        getCell(sheet, 2, 5).setCellValue("");
        getCell(sheet, 2, 6).setCellValue("");



        HSSFRow sheetRow = sheet.createRow(data.size()+3);
        for (short i = 0; i < data.size(); i++) {
            sheetRow.createCell(i).setCellValue(data.get(i).getId());
            sheetRow.createCell(i).setCellValue(data.get(i).getId());
            sheetRow.createCell(i).setCellValue(data.get(i).getId());
            sheetRow.createCell(i).setCellValue(data.get(i).getId());
            sheetRow.createCell(i).setCellValue(data.get(i).getId());
            sheetRow.createCell(i).setCellValue(data.get(i).getId());
            sheetRow.createCell(i).setCellValue(data.get(i).getId());
            sheetRow.createCell(i).setCellValue(data.get(i).getId());
            sheetRow.createCell(i).setCellValue(data.get(i).getId());

        }



        String filename = "故障统计.xls";//设置下载时客户端Excel的名称
        filename = encodeFilename(filename, request);//处理中文文件名
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }


    /**
     * 设置下载文件中文件的名称
     *
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFilename(String filename, HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息
         * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }


}
