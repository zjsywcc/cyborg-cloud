package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.APIInfo;

import java.util.List;

/**
 * api信息服务
 * Created by rencong on 17/1/24.
 */
public interface APIInfoService {

    /**
     * 添加外包人员信息
     * @param apiInfo
     * @return
     */
    public boolean add(APIInfo apiInfo);

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
    public List<APIInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 修改
     * @param apiInfo
     * @return
     */
    public boolean update(APIInfo apiInfo);

    /**
     * 根据id查询api信息
     * @param id
     * @return
     */
    public APIInfo findById(String id);

}
