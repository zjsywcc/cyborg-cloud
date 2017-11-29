package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.CyborgInfo;

import java.util.List;

/**
 * Created by wangchengcheng on 2017/10/16.
 */
public interface CyborgInfoService {

    /**
     * 添加
     * @param cyborgInfo
     * @return
     */
    public boolean add(CyborgInfo cyborgInfo);

    /**
     * 查询
     * @param id
     * @return
     */
    public CyborgInfo findById(String id);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<CyborgInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部信息
     * @return
     */
    public List<CyborgInfo> all();

    /**
     * 查询总数
     * @return
     */
    public Long count();

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(String id);

    /**
     * 修改
     * @param cyborgInfo
     * @return
     */
    public boolean update(CyborgInfo cyborgInfo);


}
