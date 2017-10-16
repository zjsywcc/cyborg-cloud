package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.AppInfo;

import java.util.List;

/**
 * appinfo服务
 * Created by rencong on 17/2/15.
 */
public interface AppInfoService {

    /**
     * 添加
     * @param appInfo
     * @return
     */
    public boolean add(AppInfo appInfo);

    /**
     * 查询
     * @param id
     * @return
     */
    public AppInfo findById(String id);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<AppInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部信息
     * @return
     */
    public List<AppInfo> all();

    /**
     * 查询总数
     * @param query
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
     * @param appInfo
     * @return
     */
    public boolean update(AppInfo appInfo);

    /**
     * 验证AppInfo信息
     * @param id app id,非必须
     * @param type 类型:
     * @param value 值
     * @return
     */
    public boolean validateAppInfo(String id,String type,String value);

}
