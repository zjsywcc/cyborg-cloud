package com.ese.cloud.client.controller.mobile;


import com.alibaba.fastjson.JSON;
import com.ese.cloud.client.dao.RecordInfoDao;
import com.ese.cloud.client.entity.RecordInfo;
import com.ese.cloud.client.entity.ShortURL;
import com.ese.cloud.client.service.PassportService;
import com.ese.cloud.client.service.RecordInfoService;
import com.ese.cloud.client.service.ShortURLService;
import com.ese.cloud.client.util.DateTimeUtil;
import com.ese.cloud.client.util.ReturnData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by rencong on 2017/3/3.
 */
@Controller
@RequestMapping("mobile")
public class MobileController {

    Logger logger = LoggerFactory.getLogger(MobileController.class);

    @Autowired
    ShortURLService shortURLService;//短链接服务
    @Autowired
    RecordInfoService recordInfoService;//记录
    @Autowired
    PassportService passportService;

    @Autowired
    RecordInfoDao recordInfoDao;

    @Value("${short-url.expired-time}")
    long expireTime;

    /**
     * 验证短链接,并显示首页
     * @param sURL 短链接
     * @return
     */
    @RequestMapping(value="/{sURL}",method= RequestMethod.GET)
    public ModelAndView redirectToRealURL(@PathVariable("sURL") String sURL) {
        ShortURL decode = shortURLService.findByShort(sURL);


        Date now = new Date();
        int diffMinute = 0;
        try {
            diffMinute = DateTimeUtil.diffMinutes(now, DateTimeUtil.parse(decode.getCreateTime()));
        } catch (Exception e) {
            logger.error("解析createTime出错", e);
        }



        ModelAndView mvn = new ModelAndView();

        if(decode == null || diffMinute> expireTime) {
            //显示链接已经失效
            logger.info("url:{},链接已经失效!",sURL);
            mvn.setViewName("mobile/403");
        } else {
            mvn.addObject("short", sURL);
            mvn.setViewName("mobile/index");
        }
        return mvn;
    }


    /**
     * photo页面
     * @param id
     * @return
     */
    @RequestMapping(value="/photo",method= RequestMethod.GET)
    public ModelAndView photo(@RequestParam("id") String id) {

        ModelAndView mvn = new ModelAndView();
        mvn.addObject("id", id);
        mvn.setViewName("mobile/photo_info");

        return mvn;
    }


    @RequestMapping(value="/403",method= RequestMethod.GET)
    public ModelAndView error(@RequestParam("id") String id) {

        ModelAndView mvn = new ModelAndView();
        mvn.addObject("id", id);
        mvn.setViewName("mobile/403");

        return mvn;
    }

    @RequestMapping(value="/success",method= RequestMethod.GET)
    public ModelAndView success() {

        ModelAndView mvn = new ModelAndView();

        mvn.setViewName("mobile/success");

        return mvn;
    }


    /**
     * 显示关于
     * @return
     */
    @RequestMapping(value="/about",method= RequestMethod.GET)
    public ModelAndView about() {

        ModelAndView mvn = new ModelAndView();

        mvn.setViewName("mobile/about");

        return mvn;
    }







    /**
     * 验证手机号是否存在
     * @param mob
     * @return
     */
    @RequestMapping(value="/check_mob",method= RequestMethod.POST)
    @ResponseBody
    public  String  checkMob(@RequestParam String  mob,@RequestParam String  shortUrl){

        logger.info("mob is {},short url:{}",mob,shortUrl);

        ShortURL shortURL = shortURLService.findByShort(shortUrl);

        //查询表中是否存在
        if (shortURL!=null && StringUtils.equals(shortURL.getRealURL(),mob.trim())) {
            //查询是否已经提交过
            if(recordInfoService.isExistSubmit(mob)){
                return ReturnData.result(201, "该手机号已经提交过", "");
            }else {
                //检查手机号是否修改成功
                if(recordInfoDao.checkUpdateMobSuccess(mob)){
                    return ReturnData.result(202,"","");
                }else {
                    return ReturnData.result(200,"","");
                }
            }
        }else {
            return ReturnData.result(-1,"","");
        }

    }



    /**
     * 保存信息
     * @param code
     * @param name
     * @param regmob
     * @param car
     * @param identity
     * @param bank
     * @param bind
     * @return
     */
    @RequestMapping(value="/save_info",method= RequestMethod.POST)
    @ResponseBody
    public  String  saveBaseInfo(
            @RequestParam String  code,//短信编码
            @RequestParam String  name,//姓名
            @RequestParam String  basecity,//地点
            @RequestParam String  regmob,//注册手机号
           // @RequestParam String  newmob,//新手机号
            @RequestParam String  car,//车牌
            @RequestParam String  identity,//身份证
            @RequestParam(required = false) String  bank,//银行卡后6位
            //@RequestParam(required = false) String  role,//业务线
            //@RequestParam String  relogin,//重新登录方式
            @RequestParam(required = false) String  reloginTime,//重新登录时间
            @RequestParam(required = false) String  firstUserTime,//首次使用时间
            @RequestParam(required = false) String  lastUserTime,//最后使用时间
            //@RequestParam String  loseTime,//原号失效时间
            //@RequestParam String  loseCity,//原号失效地
            @RequestParam(required = false) String  loseReason,//原号失效原因
            @RequestParam(required = false) Boolean  bind){



        ShortURL shortURL = shortURLService.findByShort(code);


        RecordInfo obj = new RecordInfo();
        obj.setLoginMob(shortURL.getRealURL());
        obj.setCreateTime(new Date().getTime());
        obj.setUpdateTime(new Date().getTime());
        obj.setName(name);
        obj.setCity(basecity);
        obj.setOldPhone(regmob);
        obj.setNewPhone(shortURL.getRealURL());
        obj.setPlateNo(car.toUpperCase());
        obj.setIdCardNo(identity);
        obj.setBankCard(bank);
        obj.setDriverType(shortURL.getDriverType());
        obj.setReLoginTime(reloginTime);
        /*if(StringUtils.equals(relogin,"重新登录成功")) {
            obj.setLoginStatus(0);
        }else {
            obj.setLoginStatus(1);
        }*/
        obj.setStartTime(firstUserTime);
        obj.setEndTime(lastUserTime);
       /* obj.setPhoneDisableTime(loseTime);
        obj.setPhoneDisableLoc(loseCity);*/
        obj.setPhoneDisableReason(loseReason);

        obj.setSubmitStatus(0);



        if(bind != null) {
            obj.setBindStatus(0);
        }

        //检查信息是否完整
        String checkResutl = checkBaseInfo(obj);
        if(StringUtils.isNotEmpty(checkResutl)){
            return ReturnData.result(400,checkResutl,"");
        }



        if(recordInfoService.add(obj)){
            return ReturnData.result(200,"",obj.getId());
        }else {
            return ReturnData.result(-1,"","");
        }


    }







    /**
     * 提交审核信息
     * @return
     */
    @RequestMapping(value="/submit_info",method= RequestMethod.POST)
    @ResponseBody
    public String submitPhoto(@RequestParam String id,
                              @RequestParam(required = false) ArrayList<String> idcard,
                              @RequestParam(required = false) ArrayList<String> other,
                              @RequestParam(required = false) ArrayList<String> mob,
                              @RequestParam(required = false) ArrayList<String> driver){

        logger.info("id:{},idcard:{},other:{},mob:{},driver:{}",id, JSON.toJSON(idcard),JSON.toJSON(other),JSON.toJSON(mob),JSON.toJSON(driver));

        RecordInfo recordInfo = recordInfoService.findById(id);

        recordInfo.setSubmitStatus(1);//已经提交
        recordInfo.setPicWithIDCard(idcard);
        recordInfo.setPicOtherProof(other);
        recordInfo.setPicDriveLicense(driver);
        recordInfo.setPicNewPhoneProof(mob);
        recordInfo.setCreateTime(new Date().getTime());//更新时间

        if(recordInfoService.update(recordInfo)){
            //跳转到提交成功界面
            return ReturnData.result(200,"","");
        }else {
            return  ReturnData.result(-1,"","");
        }
    }


    /**
     * 检查基础信息是否完整
     * @param recordInfo
     * @return 不完整的结果信息
     */
     private String checkBaseInfo(RecordInfo recordInfo){

         String result="";

         if(StringUtils.isEmpty(recordInfo.getName())){
             result = result+",姓名不能为空";
         }
         if(StringUtils.isEmpty(recordInfo.getCity())){
             result = result+",城市不能为空";
         }
         if(StringUtils.isEmpty(recordInfo.getOldPhone())){
             result = result+",注册手机号不能为空";
         }
         if(StringUtils.isEmpty(recordInfo.getNewPhone())){
             result = result+",新手机号不能为空";
         }
         if(StringUtils.isEmpty(recordInfo.getPlateNo())){
             result = result+",车牌号不能为空";
         }
         if(StringUtils.isEmpty(recordInfo.getIdCardNo())){
             result = result+",身份证号不能为空";
         }
         /*if(StringUtils.isEmpty(recordInfo.getBankCard())){
             result = result+",银行卡后六位不能为空";
         }*/
         /*if(StringUtils.isEmpty(recordInfo.getDriverType())){
             result = result+",业务线不能为空";
         }*/
         if(StringUtils.isEmpty(recordInfo.getLoginMob())){
             result = result+",登录手机号不能为空";
         }



         /*if(StringUtils.isEmpty(recordInfo.getReLoginTime())){
             result = result+",请选择重新登录时间";
         }
         if(StringUtils.isEmpty(recordInfo.getStartTime())){
             result = result+",首次使用时间不能为空";
         }
         if(StringUtils.isEmpty(recordInfo.getEndTime())){
             result = result+",最后使用时间不能为空";
         }*/


         /*if(StringUtils.isEmpty(recordInfo.getPhoneDisableLoc())){
             result = result+",原号失效地不能为空";
         }
         if(StringUtils.isEmpty(recordInfo.getPhoneDisableTime())){
             result = result+",原号失效时间不能为空";
         }*/
         if(StringUtils.isEmpty(recordInfo.getPhoneDisableReason())){
             result = result+",原号失效原因不能为空";
         }

         return result;
     }


    /**
     * 根据手机号查询是否已经登录过
     * @param mob
     * @return true已经登录过,false未登录过
     */
    @RequestMapping(value="/check_login_status",method= RequestMethod.POST)
    @ResponseBody
    public String submitPhoto(@RequestParam String mob){


        boolean status = passportService.checkDriverLoginSuccess(mob);

        return ReturnData.result(200,"",String.valueOf(status));


    }

    /**
     * 检查新手机号是不是发送短信的手机号
     * @param mob
     * @return
     */
    @RequestMapping(value="/check_new_mob",method= RequestMethod.POST)
    @ResponseBody
    public String checkNewMob(@RequestParam String  mob,
                              @RequestParam String  shortUrl){

        boolean status = passportService.checkDriverLoginSuccess(mob);

        return ReturnData.result(200,"",String.valueOf(status));


    }






}
