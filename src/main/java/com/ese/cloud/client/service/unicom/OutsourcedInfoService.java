package com.ese.cloud.client.service.unicom;

import com.ese.cloud.client.entity.hunanUnicom.OutsourcedInfo;

import java.util.List;

/**
 * 外包公司信息服务
 * Created by rencong on 17/1/23.
 */
public interface OutsourcedInfoService {

    /**
     * 添加外包公司信息
     * @param outsourcedInfo
     * @return
     */
    public boolean add(OutsourcedInfo outsourcedInfo);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public boolean delete(String id);

    /**
     * 查询总数
     * @return
     */
    public long count();

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<OutsourcedInfo> pageFind(int pageIndex, int pageSize);


    /**
     * 获得全部外包公司信息
     * @return
     */
    public List<OutsourcedInfo> all();

    /**
     * 修改
     * @param outsourcedInfo
     * @return
     */
    public boolean update(OutsourcedInfo outsourcedInfo);

    /**
     * 根据id查询外包公司信息
     * @param id
     * @return
     */
    public OutsourcedInfo findById(String id);

}
