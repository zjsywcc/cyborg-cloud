package com.ese.cloud.client.service;

import com.alibaba.fastjson.JSONArray;
import com.ese.cloud.client.entity.ReasonCount;
import com.ese.cloud.client.entity.RecordInfo;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/28.
 */
public interface RecordInfoService {

    /**
     * 添加
     * @param recordInfo
     * @return
     */
    public boolean add(RecordInfo recordInfo);



    /**
     * 查询
     * @param id
     * @return
     */
    public RecordInfo findById(String id);

    /**
     * 验证新手机号是否存在
     * @param newPhone
     * @return 如果不存在false,如果存储true
     */
    public boolean isExistNewPhone(String newPhone);


    /**
     * 判断登录手机号是否存在
     * @param loginMob
     * @return
     */
    public boolean isExistLoginPhone(String loginMob);


    /**
     * 判断是否已经提交
     * @param loginMob
     * @return
     */
    public boolean isExistSubmit(String loginMob);

    /**
     * 通过新手机号查询记录信息
     * @param newMob
     * @return
     */
    public RecordInfo findByNewMob(String newMob);

    /**
     * 通过登录手机号查询记录信息
     * @param loginMob
     * @return
     */
    public RecordInfo findByLoginMob(String loginMob);


    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<RecordInfo> pageFind(int pageIndex, int pageSize);

    public List<RecordInfo> pageFindFilter(int pageIndex, int pageSize, String pattern);

    public List<RecordInfo> pageFindFilterIndividual(int pageIndex, int pageSize, String name, String phone, String loginMob,  String idCard, String driverType, String sendStatus, String date, String strategyStatus, String result, String newPhoneProof);

    /**
     * 查询全部信息
     * @return
     */
    public List<RecordInfo> all();


    /**
     * 查询指定时间段的记录
     * @param start
     * @param end
     * @return
     */
    public List<RecordInfo> findByCreateTime(long start,long end);

    public List<RecordInfo> findUnSendMsgByCreateTime(long start,long end);

    /**
     * 查询总数
     * @return
     */
    public Long count();

    /**
     * 查询对登录手机作去重之后的记录数
     * @return
     */
    public Long countDistinctByLoginMob();

    /**
     * 查询对登录手机作去重之后每个业务线的记录数
     * @return
     */
    public Long countDistinctByDriverType(String driverType);

    /**
     * 查询提交数
     * @return
     */
    public Long countSubmit();

    /**
     * 查询某个业务线的提交数
     * @return
     */
    public Long countSubmitByDriverType(String driverType);

    /**
     * 查询通过数
     * @return
     */
    public Long countPermit();

    /**
     * 查询某个业务线的通过数
     * @return
     */
    public Long countPermitByDriverType(String driverType);


    public Long countDaily(long start, long end);

    /**
     * 查询某个业务线的日总量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countDailyByDriverType(long start, long end, String driverType);


    public Long countSubmitDaily(long start, long end);

    /**
     * 查询某个业务线的日提交量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countSubmitDailyByDriverType(long start, long end, String driverType);


    public Long countStrategyDaily(long start, long end);

    /**
     * 查询某个业务线的日跑策略量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countStrategyDailyByDriverType(long start, long end, String driverType);


    public Long countPassDaily(long start, long end);

    /**
     * 查询某个业务线的日通过量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countPassDailyByDriverType(long start, long end, String driverType);

    /**
     * 查询已发送短信
     * @return
     */
    public Long countSent();

    /**
     * 查询某个业务线的短信发送量
     * @param driverType
     * @return
     */
    public Long countSentByDriverType(String driverType);

    /**
     * 查询日发送短信
     * @param start
     * @param end
     * @return
     */
    public Long countSentDaily(long start, long end);

    /**
     * 查询某个业务线的日短信发送量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countSentDailyByDriverType(long start, long end, String driverType);

    public Long countFilterIndividual(String name, String phone, String loginMob, String idCard, String driverType, String sendStatus, String date , String strategyStatus, String result, String newPhoneProof);

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(String id);

    /**
     * 修改
     * @param recordInfo
     * @return
     */
    public boolean update(RecordInfo recordInfo);

    /**
     * 发送策略跑完的结果
     * @return
     */
    public List<RecordInfo> resultSendMsg();

    /**
     * 更新发送短息标记
     * @param id
     * @return
     */
    public boolean updateSendMsg(String id);

    public List<ReasonCount> getReasonCount(long start, long end);

    public long countByReason(String reason, long start, long end);

    /**
     * 查询某个业务线某天无法通过原因的量
     * @param reason
     * @param start
     * @param end
     * @return
     */
    public long countByReasonByDriverType(String reason, long start, long end, String driverType);


    /**
     * 总下发链接短信数量大于num,但未提交
     * @param num 次数
     * @return
     */
    public JSONArray sendSmsAndUnSubmit(int num);

    /**
     * 提交大于num次,但修改手机号失败
     * @param num 次数
     * @return
     */
    public JSONArray submitNumAndUnUpdateMob(int num);



    public void reportUnusualMob();






}
