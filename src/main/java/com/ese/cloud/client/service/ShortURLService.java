package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.ShortURL;

import java.util.List;

/**
 * Created by mengchenyun on 2017/3/30.
 */
public interface ShortURLService {

    public boolean add(ShortURL shortURL);

    public ShortURL findByShort(String shortURL);

    public ShortURL findByReal(String realURL);

    /**
     * 查询
     * @param id
     * @return
     */
    public ShortURL findById(String id);

    /**
     *
     * @param mob
     * @return
     */
    public boolean mobIsExist(String mob);


    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<ShortURL> pageFind(int level,String userid,int pageIndex, int pageSize);

    public List<ShortURL> pageFindFilterIndividual(int pageIndex, int pageSize, String phone, String status, String date, String driverType);

    public Long countFilterIndividual(String phone, String status, String date, String driverType);
    /**
     * 查询全部信息
     * @return
     */
    public List<ShortURL> all();

    /**
     * 查询总数
     * @return
     */
    public Long count(int level,String userid);

    /**
     * 查询某个业务线的短信发送总数
     * @param driverType
     * @return
     */
    public Long countByDriverType(String driverType);

    /**
     * 查询已发送短信
     * @return
     */
    public Long countSent();

    /**
     * 查询某个业务线已发送短信
     * @param driverType
     * @return
     */
    public Long countSentByDriverType(String driverType);

    public Long countSMSDaily(String date);

    /**
     * 查询某个业务线日短信总量
     * @param date
     * @param driverType
     * @return
     */
    public Long countSMSDailyByDriverType(String date, String driverType);

    /**
     * 查询日发送短信
     * @param date
     * @return
     */
    public Long countSentDaily(String date);

    /**
     * 查询某个业务线日短信已发送的量
     * @param date
     * @param driverType
     * @return
     */
    public Long countSentDailyByDriverType(String date, String driverType);

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(String id);

    public boolean deleteByShortURL(String shortURL);

    /**
     * 修改
     * @param shortURL
     * @return
     */
    public boolean update(ShortURL shortURL);

    /**
     * 通过手机号查询短连接信息
     * @param mob
     * @return
     */
    public ShortURL findByMob(String mob);

    /**
     * 根据时间范围查询短发下发总数,group by mob
     * @param start 开始时间戳
     * @param end   结束时间戳
     * @return 总数
     */
    public long countByUser(long start,long end);
}
