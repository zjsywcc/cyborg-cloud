package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.ModuleInfo;

import java.util.List;
import java.util.Set;

/**
 * 模块信息服务
 * Created by rencong on 16/10/10.
 */
public interface ModuleInfoService {

    /**
     * 添加
     * @param name
     * @param url
     * @param key
     * @param ico
     * @param remark
     * @return
     */
    public boolean add(Integer level,String father,String name,String url,String key,String ico,String remark,Integer sort);

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(String id);

    /**
     * 修改
     * @param id
     * @param name
     * @param url
     * @param key
     * @param ico
     * @param remark
     * @return
     */
    public boolean update(String id,Integer level,String father,String name,String url,String key,String ico,String remark,Integer sort);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ModuleInfo findById(String id);

    /**
     * 查询全部模块信息
     * @return
     */
    public List<ModuleInfo> findAll();

    /**
     * 根据级别查询模块信息
     * @param level
     * @return
     */
    public List<ModuleInfo> findByLevel(Integer level);


    /**
     * 批量查询
     * @param ids
     * @return
     */
    public List<ModuleInfo> findByIds(Set<String> ids);

}
