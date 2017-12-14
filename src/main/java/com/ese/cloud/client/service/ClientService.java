package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.ClientInfo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 客户信息操作
 * Created by rencong on 16/12/27.
 */
public interface ClientService {

    /**
     * 添加客户信息
     * @param name 客户名称
     * @param type 客户级别
     * @param address 联系地址
     * @param mobphone 手机
     * @param telephone 电话
     * @param contact 联系人
     * @return success-true,failed-false
     */
    public boolean add(String name,Integer type,String address,String mobphone,String telephone,String contact,String OLT,String ip,String accessNo,String remark,Double lat,Double lng);

    /**
     * 添加客户对象
     * @param clientInfo
     * @return
     */
    public boolean add(ClientInfo clientInfo);

    /**
     * 修改客户信息
     * @param id
     * @param name
     * @param type
     * @param address
     * @param mobphone
     * @param telephone
     * @param contact
     * @return
     */
    public boolean update(String id,String name,Integer type,String address,String mobphone,String telephone,String contact,String OLT,String ip,String accessNo,String remark,Double lat,Double lng);

    /**
     * 根据id删除客户信息
     * @param id
     * @return
     */
    public boolean delById(String id);

    /**
     * 查询全部客户信息
     * @return
     */
    public List<ClientInfo> findAll();

    /**
     * 根据id查询客户信息
     * @param id
     * @return
     */
    public ClientInfo findById(String id);

    /**
     * 批量查询客户信息
     * @param id
     * @return
     */
    //public List<ClientInfo> findByIds(Set<String> id);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<ClientInfo> pageFind(int pageIndex, int pageSize,Query query);

    /**
     * 计算总数
     * @return
     */
    public Long count();


    /**
     * 根据name模糊查询
     * @param name
     * @return
     */
    public List<ClientInfo> likeByName(String name);





}
