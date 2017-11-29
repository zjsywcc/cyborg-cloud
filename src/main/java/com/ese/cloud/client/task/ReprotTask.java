package com.ese.cloud.client.task;

import com.ese.cloud.client.dao.SysSettingInfoDao;
import com.ese.cloud.client.entity.RecordInfo;
import com.ese.cloud.client.entity.SysSettingInfo;
import com.ese.cloud.client.service.RecordInfoService;
import com.ese.cloud.client.service.SMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 上报定时任务
 * Created by rencong on 16/12/25.
 */
@Component
public class ReprotTask {

    Logger logger = LoggerFactory.getLogger(ReprotTask.class);

    @Autowired
    RecordInfoService recordInfoService;
    @Autowired
    SMSService smsService;
    @Autowired
    SysSettingInfoDao sysSettingInfoDao;


    /**
     * 统计数据
     * 每天0点执行
     */
  /*  @Scheduled(cron="0 0 15 * * ?")*/
/*    @Scheduled(cron="0/30 * * * * ?")*/
    public void statistics(){

       SysSettingInfo config= sysSettingInfoDao.getSysConfig();

        if(config.getSendResultStatus()) {

            logger.info("发送策略结果,定时任务开始执行");
            List<RecordInfo> list = recordInfoService.resultSendMsg();
            String msg = "";
            for (RecordInfo obj : list) {
                try {
                    if (obj.getResult() == 1) {
                        //可以更改
                        msg = "您好：您的修改手机号申请已经通过，滴滴司机手机号已经从" + obj.getOldPhone() + "变更到" + obj.getNewPhone() + ",请用新手机号在滴滴司机端登录,谢谢!";
                    } else {
                        //不能更改
                        if (obj.getTerminology() == null) {
                            msg = "你好：您的修改手机号没有通过安全审核，暂时无法更改，谢谢!";
                        } else {
                            msg = "你好：您的修改手机号申请因" + obj.getTerminology() + "原因不能通过，暂时无法更改，谢谢!";
                        }
                    }
                    smsService.sendMessage(obj.getLoginMob(), msg);
                    //更新数据库发送状态
                    recordInfoService.updateSendMsg(obj.getId());
                    Thread.sleep(1000l);
                } catch (Exception e) {
                    logger.error("发送策略结果通知短信异常:", e);
                }
            }
            logger.info("发送策略结果,定时任务执行结束");
        }
    }


    /**
     * 每天下午5点自动发送通知结果
     */
    @Scheduled(cron="0 0 16 * * ?")
    //@Scheduled(cron="0/30 * * * * ?")
    public void sendSMS(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long start = calendar.getTime().getTime();
        long end = start +24*3600*1000;

        List<RecordInfo> list= recordInfoService.findUnSendMsgByCreateTime(start,end);


        logger.info("send sms start:{},end:{},list={}",start,end,list);

        if(smsService.sendResultMsg(list)){
            logger.info("发送结果通知成功!");
        }else {
            logger.info("发送结果通知失败!");
        }
    }

    /**
     * 每天8点定时报告疑难账号情况
     */
    @Scheduled(cron="0 0 10 * * ?")
    public void mobReport(){
        try {
            recordInfoService.reportUnusualMob();
        }catch (Exception e){
            logger.error("send mail error:",e);
        }
    }








}
