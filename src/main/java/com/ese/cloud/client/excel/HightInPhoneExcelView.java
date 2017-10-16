package com.ese.cloud.client.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;



import com.ese.cloud.client.entity.UnusualMobExcel;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * 高进线手机号数据统计
 * Created by rencong on 16/12/27.
 */
@Service
public class HightInPhoneExcelView {

//    @Value("${excel.usual.file}")
//    private String fileUrl;


    public   void buildExcelDocument(Map<String, Object> map) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        List<UnusualMobExcel> data1 = (List<UnusualMobExcel>) map.get("data1");
        List<UnusualMobExcel> data2 = (List<UnusualMobExcel>) map.get("data2");

        HSSFSheet sheet1 = workbook.createSheet("发送短信>=3未提交记录");
        sheet1.setDefaultColumnWidth((short) 12);
        HSSFRow row1 = sheet1.createRow(0);
        row1.createCell(0).setCellValue("mob");
        row1.createCell(1).setCellValue("num");
        for (short i = 0; i < data1.size(); i++) {
            HSSFRow sheetRow = sheet1.createRow(i+1);
            sheetRow.createCell(0).setCellValue(data1.get(i).getMob());
            sheetRow.createCell(1).setCellValue(data1.get(i).getNum());
        }



        HSSFSheet sheet2 = workbook.createSheet("提交次数>=3未能修改成功");
        sheet2.setDefaultColumnWidth((short) 12);
        HSSFRow row2 = sheet2.createRow(0);
        row2.createCell(0).setCellValue("mob");
        row2.createCell(1).setCellValue("num");
        for (short i = 0; i < data2.size(); i++) {
            HSSFRow sheetRow2 = sheet2.createRow(i+1);
            sheetRow2.createCell(0).setCellValue(data2.get(i).getMob());
            sheetRow2.createCell(1).setCellValue(data2.get(i).getNum());
        }


//        File file = new File(fileUrl+"/unusualfile.xls");
//        if(file.exists()) {
//            file.delete();
//            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
//        FileOutputStream fos = new FileOutputStream(fileUrl+"/unusualfile.xls");
//        workbook.write(fos);
//        fos.close();
    }


    private HSSFCell getCell(HSSFSheet sheet,int rowNum,int cellNum){
        HSSFRow row = sheet.getRow(rowNum);
        return row.getCell(cellNum);
    }

    private void setText(HSSFCell cell,String value){
        cell.setCellValue(value);
    }





    /*public static  void main(String args[]){

        List<UnusualMobExcel> data1 = new ArrayList<>();
        List<UnusualMobExcel> data2 = new ArrayList<>();

        UnusualMobExcel o1 = new UnusualMobExcel();
        UnusualMobExcel o2 = new UnusualMobExcel();

        o1.setMob("18321905819");
        o1.setNum(10);

        o2.setMob("18321905818");
        o2.setNum(89);

        data1.add(o1);
        data2.add(o2);


        Map<String, Object> dataMap= new HashMap<String ,Object>();
        dataMap.put("data1",data1);
        dataMap.put("data2",data2);
        try {
            HightInPhoneExcelView ds = new HightInPhoneExcelView();
            ds.buildExcelDocument(dataMap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/



}
