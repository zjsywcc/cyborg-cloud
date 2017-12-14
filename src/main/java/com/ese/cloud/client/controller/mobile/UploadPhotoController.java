package com.ese.cloud.client.controller.mobile;

import com.ese.cloud.client.util.GiftURLUtil;
import com.ese.cloud.client.util.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 照片上传
 * Created by rencong on 17/3/31.
 */
@Controller
@RequestMapping("upload")
public class UploadPhotoController {
    Logger logger = LoggerFactory.getLogger(UploadPhotoController.class);

    @Value("${spring.http.multipart.filedir}")
    String filePath; // 文件上传路径

//    @Value("${gift.upload.url}")
//    String giftUrl; // 文件上传路径
  /*  @Value("${gift.upload.namespace}")
    String gitftNameSpace;
    @Value("${gift.upload.key}")
    String giftKey;*/


    @RequestMapping(value = "identity",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String identity(MultipartFile file) {

        //上传文件
        String url = saveFile(file,filePath+"/identity/");
        String downloadURL = "";
//        try {
//            downloadURL = GiftURLUtil.getInstance().upload(file.getBytes(), file.getOriginalFilename(), url, giftUrl);
//            logger.info("download url:{}",downloadURL);
//        } catch (IOException e) {
//            logger.error("get download url failure from identiy page", e);
//        }
        if(downloadURL.isEmpty()) {
            return ReturnData.result(-1, "上传Gift失败！", "");
        } else {
            removeFile(url);
            return ReturnData.result(200,"上传Gift成功",downloadURL);
        }
    }


    @RequestMapping(value = "driver",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String driver(MultipartFile file) {

        //上传文件
        String url = saveFile(file,filePath+"/driver/");
        String downloadURL = "";
//        try {
//            downloadURL = GiftURLUtil.getInstance().upload(file.getBytes(), file.getOriginalFilename(), url, giftUrl);
//        } catch (IOException e) {
//            logger.error("get download url failure from driver page", e);
//        }
        if(downloadURL.isEmpty()) {
            return ReturnData.result(-1, "上传Gift失败！", "");
        } else {
            removeFile(url);
            return ReturnData.result(200,"上传Gift成功",downloadURL);
        }

    }

    @RequestMapping(value = "mob",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String mob(MultipartFile file) {

        //上传文件
        String url = saveFile(file, filePath+"/mob/");
        String downloadURL = "";
//        try {
//            downloadURL = GiftURLUtil.getInstance().upload(file.getBytes(), file.getOriginalFilename(), url, giftUrl);
//        } catch (IOException e) {
//            logger.error("get download url failure from mobile page", e);
//        }
        if(downloadURL.isEmpty()) {
            return ReturnData.result(-1, "上传Gift失败！", "");
        } else {
            removeFile(url);
            return ReturnData.result(200,"上传Gift成功",downloadURL);
        }

    }

    @RequestMapping(value = "other",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String other(MultipartFile file) {

        //上传文件
        String url = saveFile(file,filePath+"/other/");
        //写入gift

        String downloadURL = "";
//        try {
//            downloadURL = GiftURLUtil.getInstance().upload(file.getBytes(), file.getOriginalFilename(), url, giftUrl);
//        } catch (IOException e) {
//            logger.error("get download url failure from other page", e);
//        }
        if(downloadURL.isEmpty()) {
            return ReturnData.result(-1, "上传Gift失败！", "");
        } else {
            removeFile(url);
            return ReturnData.result(200,"上传Gift成功",downloadURL);
        }

    }


    /**
     * 保存文件
     * @param file
     * @return 返回文件路径,如果失败为空字符串
     */
    private String saveFile(MultipartFile file,String filePath){
        if (file.isEmpty()) {
            return "";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        Random random=new Random();
        //生成服务器文件路径
        String fileUrl = filePath + System.currentTimeMillis()+random.nextLong()+suffixName;
        File dest = new File(fileUrl);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
            return fileUrl;
        } catch (IllegalStateException e) {
           logger.error("upload file error:",e);
        } catch (IOException e) {
            logger.error("upload file error:",e);
        }
        return "";
    }

    private String removeFile(String filePath) {
        if(filePath == null || filePath.isEmpty()) {
            return "";
        }
        File file = new File(filePath);
        if(file.isFile() && file.exists()){
            file.delete();
            return "success";
        }else{
            logger.error("删除单个文件"+filePath+"失败！");
            return "";
        }
    }






}

