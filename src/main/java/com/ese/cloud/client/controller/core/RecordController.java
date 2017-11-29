package com.ese.cloud.client.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.dao.SysSettingInfoDao;
import com.ese.cloud.client.entity.RecordInfo;
import com.ese.cloud.client.entity.SysSettingInfo;
import com.ese.cloud.client.service.RecordInfoService;
import com.ese.cloud.client.service.SMSService;
import com.ese.cloud.client.service.ShortURLService;
import com.ese.cloud.client.util.GiftURLUtil;
import com.ese.cloud.client.util.ReturnData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mengchenyun on 2017/3/28.
 */
@Controller
@RequestMapping("record")
public class RecordController {

    Logger logger = LoggerFactory.getLogger(RecordController.class);



//    @Value("${gift.query.url}")
//    String giftQueryURL;



    @Autowired
    RecordInfoService recordInfoService;
    @Autowired
    SMSService smsService;

    @Autowired
    SysSettingInfoDao sysSettingInfoDao;
    @Autowired
    ShortURLService shortURLService;

    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index() {
        return "record/index";
    }

    @RequestMapping(value="/main",method= RequestMethod.GET)
    public String main() {
        return "record/main";
    }

    /***
     * 列表
     * @param sSearch
     * @param sEcho
     * @param iDisplayStart
     * @param iDisplayLength
     * @param iColumns
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(
            @RequestParam("sSearch") String sSearch,//查询
            @RequestParam("sSearch_0") String name,//查询
            @RequestParam("sSearch_1") String phone,//查询
            @RequestParam("sSearch_2") String loginMob,//查询
            @RequestParam("sSearch_3") String idCardNo,//查询
            @RequestParam("sSearch_4") String driverType,//查询
            @RequestParam("sSearch_5") String sendStatus,//查询
            @RequestParam("sSearch_6") String date,//查询
            @RequestParam("sSearch_7") String strategyStatus,//查询
            @RequestParam("sSearch_8") String result,//查询
            @RequestParam("sSearch_9") String newPhoneProof,//查询
            @RequestParam("sEcho") String sEcho,//标识
            @RequestParam("iDisplayStart") int iDisplayStart,//开始位置
            @RequestParam("iDisplayLength") int iDisplayLength,//显示长度
            @RequestParam("iColumns") String iColumns){
//        List<RecordInfo> list = recordInfoService.pageFindFilter(iDisplayStart, iDisplayLength, sSearch);
        List<RecordInfo> list = recordInfoService.pageFindFilterIndividual(iDisplayStart, iDisplayLength, name, phone,loginMob,idCardNo, driverType, sendStatus, date, strategyStatus, result, newPhoneProof);
        long count = recordInfoService.countFilterIndividual(name, phone, loginMob, idCardNo, driverType, sendStatus, date, strategyStatus, result, newPhoneProof);
        JSONObject json=new JSONObject();
        json.put("sEcho", sEcho);
        json.put("iTotalRecords",count);//总条数
        json.put("iTotalDisplayRecords",count);//显示总条数
        json.put("aaData", JSONArray.toJSON(list));
        return json.toString();
    }


    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public  String  update(@RequestParam String id,
                           @RequestParam String name,
                           @RequestParam String oldPhone,
                           @RequestParam String idCardNo,
                           @RequestParam String city,
                           @RequestParam String plateNo,
                           @RequestParam String newPhone,
                           @RequestParam String bankCard,
                           @RequestParam String bindStatus,
                           @RequestParam String loginStatus,
                           @RequestParam String startTime,
                           @RequestParam String endTime,
                           @RequestParam String phoneDisableTime,
                           @RequestParam String phoneDisableLoc,
                           @RequestParam String phoneDisableReason,
                           @RequestParam String picWithIDCard,
                           @RequestParam String picDriveLicense,
                           @RequestParam String picNewPhoneProof,
                           @RequestParam String picOtherProof,
                           @RequestParam String status){

        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setName(name);
        recordInfo.setOldPhone(oldPhone);
        recordInfo.setIdCardNo(idCardNo);
        recordInfo.setPlateNo(plateNo);
        recordInfo.setBankCard(bankCard);
        recordInfo.setNewPhone(newPhone);
        recordInfo.setStartTime(startTime);
        recordInfo.setEndTime(endTime);
        recordInfo.setPhoneDisableTime(phoneDisableTime);
        recordInfo.setPhoneDisableLoc(phoneDisableLoc);
        recordInfo.setPhoneDisableReason(phoneDisableReason);
        recordInfo.setStatus(0);
        recordInfo.setChecked(0);

        if(recordInfoService.update(recordInfo)){
            return ReturnData.result(0,"修改申请记录成功", null);
        }else{
            return ReturnData.result(-1,"修改申请记录失败", null);
        }

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method= RequestMethod.POST)
    @ResponseBody
    public  String  delete(@RequestParam String id){
        if(recordInfoService.delete(id)){
            return ReturnData.result(0,"", null);
        }else{
            return ReturnData.result(-1,"", null);
        }
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public String updateStatus(@RequestParam String id, @RequestParam boolean flag) {

        try {
            RecordInfo recordInfo = recordInfoService.findById(id);
            if(recordInfo == null) {
                return ReturnData.result(-2, "查找记录失败", null);
            }
            if(flag) {
                recordInfo.setResult(1);
                recordInfo.setTransactionStatus(0);
                recordInfo.setSendMsg(0);
                recordInfoService.update(recordInfo);
                return ReturnData.result(0, "设置状态为“允许更改手机号”成功", null);
            } else {
                recordInfo.setResult(0);
                recordInfo.setTransactionStatus(0);
                recordInfo.setSendMsg(0);
                recordInfoService.update(recordInfo);
                return ReturnData.result(0, "设置状态为“禁止更改手机号”成功", null);
            }
        } catch (Exception e) {
            logger.error("update send text msg status error: ", e);
            return ReturnData.result(-1, "发送短信失败", null);
        }
    }

    /**
     * 根据id查询指标信息
     * @param id
     * @return
     */
    @RequestMapping(value="/findById",method= RequestMethod.POST)
    @ResponseBody
    public  String  findById(@RequestParam String  id){
        RecordInfo recordInfo = recordInfoService.findById(id);

       //下载链接
        List<String> downCards= new ArrayList<>();
        List<String> downDrivers= new ArrayList<>();
        List<String> downPhones= new ArrayList<>();
        List<String> downOthers= new ArrayList<>();

        //上传链接
        List<String> cards = recordInfo.getPicWithIDCard() != null ? recordInfo.getPicWithIDCard() : new ArrayList<String>();
        List<String> drivers = recordInfo.getPicDriveLicense() != null ? recordInfo.getPicDriveLicense() : new ArrayList<String>();
        List<String> newphone = recordInfo.getPicNewPhoneProof() != null ? recordInfo.getPicNewPhoneProof() : new ArrayList<String>();
        List<String> others = recordInfo.getPicOtherProof() != null ? recordInfo.getPicOtherProof() : new ArrayList<String>();

        for(String key:cards){

            if(StringUtils.isNotEmpty(key)) {
//                downCards.add(GiftURLUtil.getInstance().queryDownloadUrl(giftQueryURL, key));
            }
        }

        for(String key:drivers){

            if(StringUtils.isNotEmpty(key)) {
//                downDrivers.add(GiftURLUtil.getInstance().queryDownloadUrl(giftQueryURL, key));
            }
        }

        for(String key:newphone){

            if(StringUtils.isNotEmpty(key)) {
//                downPhones.add(GiftURLUtil.getInstance().queryDownloadUrl(giftQueryURL, key));
            }
        }

        for(String key:others){

            if(StringUtils.isNotEmpty(key)) {
//                downOthers.add(GiftURLUtil.getInstance().queryDownloadUrl(giftQueryURL, key));
            }
        }

        recordInfo.setPicDriveLicense(downDrivers);
        recordInfo.setPicNewPhoneProof(downPhones);
        recordInfo.setPicOtherProof(downOthers);
        recordInfo.setPicWithIDCard(downCards);

        if(recordInfo != null){
            return ReturnData.result(0,"", JSON.toJSONString(recordInfo));
        }else{
            return ReturnData.result(-1,"", null);
        }
    }



    /*public static  void main(String args[]){
        URI  urlObj=  URI.create("http://10.94.105.96:9999/static/anything/do1_2RNpFFjQSZfyQQ63NacD");

        String[] urls = urlObj.getPath().split("/");

        System.out.println();
    }*/


    /**
     * 指定时间段发送短信通知
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value="/sendResultSms",method= RequestMethod.POST)
    @ResponseBody
    public  String  sendResultSms(@RequestParam String  start,@RequestParam String  end){


        List<RecordInfo> list= recordInfoService.findUnSendMsgByCreateTime(convert2long(start),convert2long(end));

        if(smsService.sendResultMsg(list)){
            return ReturnData.result(200,"",null);
        }else {
            return ReturnData.result(-1,"系统异常请联系管理员",null);
        }

    }


    /**
     * 定时短信开关
     * @param status
     * @return
     */
    @RequestMapping(value="/smsSwitch",method= RequestMethod.POST)
    @ResponseBody
    public  String  smsSwitch(@RequestParam boolean  status){
        sysSettingInfoDao.updateSendMsgStatus(status);
        return ReturnData.result(200,"","");
    }

    @RequestMapping(value="/getSmsSwitch",method= RequestMethod.POST)
    @ResponseBody
    public  String  getSmsSwitch(){
        SysSettingInfo info = sysSettingInfoDao.getSysConfig();
        return ReturnData.result(200,"",JSON.toJSONString(info.getSendResultStatus()));
    }



    private long convert2long(String date) {
        try {
            if (StringUtils.isNotBlank(date)) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                return sf.parse(date).getTime();
            }
        } catch (ParseException e) {
           logger.error("conver2long error:",e);
        }
        return 0l;
    }
    
}
