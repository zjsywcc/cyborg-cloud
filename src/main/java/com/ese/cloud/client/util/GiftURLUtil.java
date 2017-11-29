package com.ese.cloud.client.util;

import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.entity.GiftResponse;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

/**
 * Created by mengchenyun on 2017/3/31.
 */
public class GiftURLUtil {

    static Logger logger = LoggerFactory.getLogger(GiftURLUtil.class);

    private GiftURLUtil() {

    }

//    public static final String baseURL = "http://10.94.105.96:9090/resource/anything/";

    /*public static void main(String[] args) {
        try {
            File file = new File("D:/test.jpg");
            InputStream input = new FileInputStream(file);
            byte[] byt = new byte[input.available()];
            System.out.println(upload(byt, "test.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



    /**
     * 上传文件到gift服务器
     * @param bytes 文件byte
     * @param fileName 文件名
     * @param filePath 路径
     * @return
     * @throws IOException
     */
    public String upload(byte[] bytes, String fileName, String filePath, String baseURL){
        logger.info("file name:{},file path:{}",fileName,filePath);
        OkHttpClient client = getOkHttpClientSingleton();
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("filecontent", fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), bytes))
                .build();
        Request request = new Request.Builder().url(baseURL).post(formBody).build();
        for(int i = 0; i < 3; i++) {
            try {
                Response response = client.newCall(request).execute();
                String giftStr = response.body().string();
                GiftResponse res = JSON.parseObject(giftStr, GiftResponse.class);
                if (response.isSuccessful()) {
                    logger.info("file name:{},file path:{} upload success!",fileName,filePath);
                    return res.getResourceKey();
                } else {
                    logger.info("file name:{},file path:{} upload failed!",fileName,filePath);
                }
            }catch (Exception e){
                logger.error("gift upload file error:",e);
            }
        }
        return "";
    }

    /**
     * 查询下载链接
     * @param queryUrl 查询路径
     * @param key key
     * @return
     */
    public String queryDownloadUrl(String queryUrl,String key){
        logger.info("file url:{},key:{}",queryUrl,key);
        OkHttpClient client = getOkHttpClientSingleton();

        Request request = new Request.Builder().url(queryUrl+"/"+key).build();
        try {
            Response response = client.newCall(request).execute();
            String giftStr = response.body().string();

            GiftResponse res = JSON.parseObject(giftStr, GiftResponse.class);



            if (response.isSuccessful()) {
                logger.info("query key :{}!",key);
                return res.getDownloadUrl();
            } else {
                logger.info("query key :{}!",key);
            }
        }catch (Exception e){
            logger.error("gift upload file error:",e);
        }

        return "";
    }



    /*静态内部类单例 保证okhttp为单例*/
    private static class HolderClass {
        private final static OkHttpClient instance = new OkHttpClient();
    }

    public static OkHttpClient getOkHttpClientSingleton() {
        return HolderClass.instance;
    }

    /*静态内部类单例 方便Util调用*/
    private static class InnerClass {
        private final static GiftURLUtil instance = new GiftURLUtil();
    }

    public static GiftURLUtil getInstance() {
        return InnerClass.instance;
    }
}
