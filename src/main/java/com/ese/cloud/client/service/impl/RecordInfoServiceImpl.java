package com.ese.cloud.client.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ese.cloud.client.dao.RecordInfoDao;
import com.ese.cloud.client.dao.ShortURLDao;
import com.ese.cloud.client.entity.ReasonCount;
import com.ese.cloud.client.entity.RecordInfo;
import com.ese.cloud.client.entity.UnusualMobExcel;
import com.ese.cloud.client.excel.HightInPhoneExcelView;
import com.ese.cloud.client.service.RecordInfoService;
import com.ese.cloud.client.util.DateTimeUtil;
import com.ese.cloud.client.util.EmailServer;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by mengchenyun on 2017/3/28.
 */
@Service
public class RecordInfoServiceImpl implements RecordInfoService {

    Logger logger = LoggerFactory.getLogger(RecordInfoService.class);

    @Autowired
    RecordInfoDao recordInfoDao;//记录数据库操作
    @Autowired
    ShortURLDao shortURLDao;//短链数据库操作


//    @Autowired
//    HightInPhoneExcelView hightInPhoneExcelView;
//    @Value("${excel.usual.file}")
//    private String fileUrl;

    @Override
    public boolean add(RecordInfo recordInfo) {
        return recordInfoDao.add(recordInfo);
    }

    @Override
    public RecordInfo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return recordInfoDao.findOneByQuery(query);
    }

    @Override
    public boolean isExistNewPhone(String newPhone) {
        Query query = new Query();
        query.addCriteria(Criteria.where("newPhone").is(newPhone));
        RecordInfo recordInfo =  recordInfoDao.findOneByQuery(query);
        if(recordInfo == null){
            return false;
        }else {
            return true;
        }
    }


    @Override
    public boolean isExistLoginPhone(String loginMob) {
        Query query = new Query();
        query.addCriteria(Criteria.where("loginMob").is(loginMob));
        RecordInfo recordInfo =  recordInfoDao.findOneByQuery(query);
        if(recordInfo == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean isExistSubmit(String loginMob) {
        Query query = new Query();
        query.addCriteria(Criteria.where("loginMob").is(loginMob).and("submitStatus").is(1).and("status").is(0));
        RecordInfo recordInfo =  recordInfoDao.findOneByQuery(query);
        if(recordInfo == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public RecordInfo findByLoginMob(String loginMob) {
        Query query = new Query();
        query.addCriteria(Criteria.where("loginMob").is(loginMob));
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        return  recordInfoDao.findOneByQuery(query);
    }

    @Override
    public RecordInfo findByNewMob(String newMob) {
        Query query = new Query();
        query.addCriteria(Criteria.where("newPhone").is(newMob));
        return  recordInfoDao.findOneByQuery(query);
    }

    @Override
    public List<RecordInfo> pageFind(int pageIndex, int pageSize) {
        Query query = new Query();
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return recordInfoDao.findListByQuery(query);
    }

    @Override
    public List<RecordInfo> pageFindFilter(int pageIndex, int pageSize, String pattern) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(Criteria.where("name").regex(pattern),
                                                    Criteria.where("oldPhone").regex(pattern),
                                                    Criteria.where("idCardNo").regex(pattern),
                                                    Criteria.where("city").regex(pattern),
                                                    Criteria.where("plateNo").regex(pattern)));
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return recordInfoDao.findListByQuery(query);
    }

    /**
     * 根据搜索项对表格list进行过滤取得对应的rows
     * @param pageIndex
     * @param pageSize
     * @param name
     * @param phone
     * @param loginMob
     * @param idCard
     * @param driverType
     * @param sendStatus
     * @param date
     * @return
     */
    @Override
    public List<RecordInfo> pageFindFilterIndividual(int pageIndex, int pageSize,
                                                     String name,
                                                     String phone,
                                                     String loginMob,
                                                     String idCard,
                                                     String driverType,
                                                     String sendStatus,
                                                     String date,
                                                     String strategyStatus,
                                                     String result,
                                                     String newPhoneProof) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        name = name.trim();
        phone = phone.trim();
        loginMob = loginMob.trim();
        idCard = idCard.trim();
        driverType = driverType.trim();
        sendStatus = sendStatus.trim();
        date = date.trim();
        strategyStatus = strategyStatus.trim();
        result = result.trim();
        Criteria criteria;
        List<Criteria> andCriteriaList = new ArrayList<Criteria>();
        List<Criteria> orCriteriaList = new ArrayList<Criteria>();
        andCriteriaList.add(Criteria.where("submitStatus").is(1));
        if(!name.isEmpty()) {
            andCriteriaList.add(Criteria.where("name").regex(name));
        }
        if(!phone.isEmpty()) {
            andCriteriaList.add(Criteria.where("oldPhone").regex(phone));
        }
        if(!loginMob.isEmpty()) {
            andCriteriaList.add(Criteria.where("loginMob").regex(loginMob));
        }
        if(!idCard.isEmpty()) {
            andCriteriaList.add(Criteria.where("idCardNo").regex(idCard));
        }
        if(!driverType.isEmpty()) {
            andCriteriaList.add(Criteria.where("driverType").regex(driverType));
        }
        if(!sendStatus.isEmpty()) {
            andCriteriaList.add(Criteria.where("sendMsg").is(Integer.parseInt(sendStatus)));
        }
        if (!date.isEmpty()) {
            Date day = null;
            try {
                day = DateTimeUtil.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(day != null) {
                Date nextDay = DateTimeUtil.nextDay(day, 1);
                andCriteriaList.add(Criteria.where("createTime").gte(day.getTime()).lte(nextDay.getTime()));
            }
        }
        if(!strategyStatus.isEmpty()) {
            andCriteriaList.add(Criteria.where("status").is(Integer.parseInt(strategyStatus)));
        }
        if(!result.isEmpty()) {
            andCriteriaList.add(Criteria.where("result").is(Integer.parseInt(result)));
        }
        if(!newPhoneProof.isEmpty()) {
            int type = Integer.parseInt(newPhoneProof);
            if(type == 1) {
                andCriteriaList.add(Criteria.where("picNewPhoneProof").exists(true));
                andCriteriaList.add(Criteria.where("picNewPhoneProof").elemMatch(new Criteria().ne(null)));
            } else {
                andCriteriaList.add(Criteria.where("picNewPhoneProof").size(0));
            }
        }
        if(andCriteriaList.isEmpty()) {
            criteria = new Criteria();
        } else {
            criteria = new Criteria().andOperator(andCriteriaList.toArray(new Criteria[andCriteriaList.size()]));
        }
        query.addCriteria(criteria);
        query.skip(pageIndex);// skip相当于从那条记录开始
        query.limit(pageSize);// 从skip开始,取多少条记录
        return recordInfoDao.findListByQuery(query);
    }

    @Override
    public List<RecordInfo> all() {
        Query query = new Query();
        return recordInfoDao.findListByQuery(query);
    }

    @Override
    public List<RecordInfo> findByCreateTime(long start, long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("status").is(1));


        return recordInfoDao.findListByQuery(query);
    }

    @Override
    public List<RecordInfo> findUnSendMsgByCreateTime(long start, long end) {

        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(start).lte(end));
        query.addCriteria(Criteria.where("status").is(1));
        query.addCriteria(Criteria.where("sendMsg").is(0));

        return recordInfoDao.findListByQuery(query);
    }

    @Override
    public Long count() {
        Query query = new Query();
        return recordInfoDao.count(query);
    }

    @Override
    public Long countDistinctByLoginMob() {
        Query query = new Query();
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    /**
     * 查询对登录手机作去重之后每个业务线的记录数
     * @return
     */
    public Long countDistinctByDriverType(String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    @Override
    public Long countSubmit() {
        Query query = new Query();
        query.addCriteria(Criteria.where("submitStatus").is(1));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    /**
     * 查询某个业务线的提交数
     * @return
     */
    public Long countSubmitByDriverType(String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("submitStatus").is(1));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    @Override
    public Long countPermit() {
        Query query = new Query();
        query.addCriteria(Criteria.where("result").is(1));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    /**
     * 查询某个业务线的通过数
     * @return
     */
    public Long countPermitByDriverType(String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("result").is(1));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    @Override
    public Long countDaily(long start, long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    /**
     * 查询某个业务线的日总量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countDailyByDriverType(long start, long end, String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    @Override
    public Long countSubmitDaily(long start, long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("submitStatus").is(1));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    /**
     * 查询某个业务线的日提交量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countSubmitDailyByDriverType(long start, long end, String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("submitStatus").is(1));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    @Override
    public Long countStrategyDaily(long start, long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("status").is(1));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    /**
     * 查询某个业务线的日跑策略量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countStrategyDailyByDriverType(long start, long end, String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("status").is(1));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    @Override
    public Long countPassDaily(long start, long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("result").is(1));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    /**
     * 查询某个业务线的日通过量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countPassDailyByDriverType(long start, long end, String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("result").is(1));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.countDistinctByLoginMob(query);
    }

    /**
     * 根据搜索项对表格list进行过滤取得对应的总数
     * @param name
     * @param phone
     * @param loginMob
     * @param idCard
     * @param driverType
     * @param sendStatus
     * @param date
     * @return
     */
    @Override
    public Long countFilterIndividual(String name, String phone, String loginMob, String idCard, String driverType, String sendStatus, String date, String strategyStatus, String result, String newPhoneProof) {
        Query query = new Query();
        name = name.trim();
        phone = phone.trim();
        loginMob = loginMob.trim();
        idCard = idCard.trim();
        driverType = driverType.trim();
        sendStatus = sendStatus.trim();
        date = date.trim();
        strategyStatus = strategyStatus.trim();
        result = result.trim();
        Criteria criteria;
        List<Criteria> andCriteriaList = new ArrayList<Criteria>();
        andCriteriaList.add(Criteria.where("submitStatus").is(1));
        if(!name.isEmpty()) {
            andCriteriaList.add(Criteria.where("name").regex(name));
        }
        if(!phone.isEmpty()) {
            andCriteriaList.add(Criteria.where("oldPhone").regex(phone));
        }
        if(!loginMob.isEmpty()) {
            andCriteriaList.add(Criteria.where("loginMob").regex(loginMob));
        }
        if(!idCard.isEmpty()) {
            andCriteriaList.add(Criteria.where("idCardNo").regex(idCard));
        }
        if(!driverType.isEmpty()) {
            andCriteriaList.add(Criteria.where("driverType").regex(driverType));
        }
        if(!sendStatus.isEmpty()) {
            andCriteriaList.add(Criteria.where("sendMsg").is(Integer.parseInt(sendStatus)));
        }
        if (!date.isEmpty()) {
            Date day = null;
            try {
                day = DateTimeUtil.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(day != null) {
                Date nextDay = DateTimeUtil.nextDay(day, 1);
                andCriteriaList.add(Criteria.where("createTime").gte(day.getTime()).lte(nextDay.getTime()));
            }
        }
        if(!strategyStatus.isEmpty()) {
            andCriteriaList.add(Criteria.where("status").is(Integer.parseInt(strategyStatus)));
        }
        if(!result.isEmpty()) {
            andCriteriaList.add(Criteria.where("result").is(Integer.parseInt(result)));
        }
        if(!newPhoneProof.isEmpty()) {
            int type = Integer.parseInt(newPhoneProof);
            if(type == 1) {
                andCriteriaList.add(Criteria.where("picNewPhoneProof").exists(true));
                andCriteriaList.add(Criteria.where("picNewPhoneProof").elemMatch(new Criteria().ne(null)));
            } else {
                andCriteriaList.add(Criteria.where("picNewPhoneProof").size(0));
            }
        }
        if(andCriteriaList.isEmpty()) {
            criteria = new Criteria();
        } else {
            criteria = new Criteria().andOperator(andCriteriaList.toArray(new Criteria[andCriteriaList.size()]));
        }
        query.addCriteria(criteria);
        return recordInfoDao.count(query);
    }

    @Override
    public Long countSent() {
        Query query = new Query();
        query.addCriteria(Criteria.where("sendMsg").is(1));
        return recordInfoDao.count(query);
    }

    /**
     * 查询某个业务线的短信发送量
     * @param driverType
     * @return
     */
    public Long countSentByDriverType(String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sendMsg").is(1));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.count(query);
    }

    @Override
    public Long countSentDaily(long start, long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sendMsg").is(1));
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        return recordInfoDao.count(query);
    }

    /**
     * 查询某个业务线的日短信发送量
     * @param start
     * @param end
     * @param driverType
     * @return
     */
    public Long countSentDailyByDriverType(long start, long end, String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sendMsg").is(1));
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.count(query);
    }

    @Override
    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return recordInfoDao.delete(query);
    }

    @Override
    public boolean update(RecordInfo recordInfo) {
        return recordInfoDao.update(recordInfo);
    }

    @Override
    public List<RecordInfo> resultSendMsg() {

        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(1).orOperator(Criteria.where("sendMsg").is("0"),Criteria.where("sendMsg").exists(false)));


        List<RecordInfo> list = recordInfoDao.findListByQuery(query);

        return list;
    }

    @Override
    public boolean updateSendMsg(String id) {
        Update update = new Update();

        update.set("sendMsg",1);

        return recordInfoDao.update(id,update);

    }

    @Override
    public List<ReasonCount> getReasonCount(long start, long end) {
        return recordInfoDao.getReasonCount(start, end);
    }

    @Override
    public long countByReason(String reason, long start, long end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reason").regex(reason));
        query.addCriteria(Criteria.where("result").is(0));
        query.addCriteria(Criteria.where("status").is(1));
        query.addCriteria(Criteria.where("submitStatus").is(1));
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        return recordInfoDao.count(query);
    }

    /**
     * 查询某个业务线某天无法通过原因的量
     * @param reason
     * @param start
     * @param end
     * @return
     */
    public long countByReasonByDriverType(String reason, long start, long end, String driverType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reason").regex(reason));
        query.addCriteria(Criteria.where("result").is(0));
        query.addCriteria(Criteria.where("status").is(1));
        query.addCriteria(Criteria.where("submitStatus").is(1));
        query.addCriteria(Criteria.where("createTime").lte(end).gte(start));
        query.addCriteria(Criteria.where("driverType").regex(driverType));
        return recordInfoDao.count(query);
    }

    @Override
    public JSONArray sendSmsAndUnSubmit(int num) {

        JSONArray result = new JSONArray();

        List<BasicDBObject> list = shortURLDao.getDataNum(num);

        for(BasicDBObject obj:list){
            Query query1 = new Query();
            query1.addCriteria(Criteria.where("loginMob").is(obj.getString("_id")));
            if(recordInfoDao.count(query1)==0){
                JSONObject b = new JSONObject();
                b.put("phone",obj.getString("_id"));
                b.put("total",obj.getInt("total"));
                result.add(b);
            }
        }

        return result;
    }

    @Override
    public JSONArray submitNumAndUnUpdateMob(int num) {
        JSONArray result = new JSONArray();

        List<BasicDBObject> list = recordInfoDao.getDataNum(num);

        for(BasicDBObject obj:list){
            Query query1 = new Query();
            query1.addCriteria(Criteria.where("loginMob").is(obj.getString("_id")));
            query1.addCriteria(Criteria.where("status").is(1));
            query1.addCriteria(Criteria.where("result").is(0));
            query1.addCriteria(Criteria.where("submitStatus").is(1));


            Query query2 = new Query();
            query2.addCriteria(Criteria.where("loginMob").is(obj.getString("_id")));
            query2.addCriteria(Criteria.where("status").is(1));
            query2.addCriteria(Criteria.where("result").is(1));
            query2.addCriteria(Criteria.where("submitStatus").is(1));


            if((recordInfoDao.count(query1)!= 0) && (recordInfoDao.count(query2)== 0)){
                JSONObject b = new JSONObject();
                b.put("phone",obj.getString("_id"));
                b.put("total",obj.getInt("total"));
                result.add(b);
            }
        }

        return result;
    }

    @Override
    public void reportUnusualMob() {

        JSONArray data1 = sendSmsAndUnSubmit(3);
        JSONArray data2 = submitNumAndUnUpdateMob(3);


        List<UnusualMobExcel> dataList1 = jsonDataToList(data1);
        List<UnusualMobExcel> dataList2 = jsonDataToList(data2);

        Map<String, Object> dataMap= new HashMap<String ,Object>();
        dataMap.put("data1",dataList1);
        dataMap.put("data2",dataList2);
        //生成excel文件
        try {
//            hightInPhoneExcelView.buildExcelDocument(dataMap);
        }catch (Exception e){
            logger.error("",e);
        }


    }


    private List<UnusualMobExcel> jsonDataToList(JSONArray data){

        List<UnusualMobExcel> result = new ArrayList<>();

        for(int i=0;i<data.size();i++){
            UnusualMobExcel obj = new UnusualMobExcel();
            obj.setMob(data.getJSONObject(i).getString("phone"));
            obj.setNum(data.getJSONObject(i).getIntValue("total"));

            result.add(obj);
        }

        return result;
    }
}
