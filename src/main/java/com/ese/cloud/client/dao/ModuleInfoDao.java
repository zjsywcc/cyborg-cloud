package com.ese.cloud.client.dao;

import com.ese.cloud.client.entity.ModuleInfo;

import java.util.List;
import java.util.Set;

/**
 * 功能模块dao
 * Created by rencong on 16/10/10.
 */
public interface ModuleInfoDao {

    /**
     * 添加
     * @param moduleInfo 功能模块
     * @return 添加成功true,否则false
     */
    public boolean add(ModuleInfo moduleInfo);

    /**
     * 删除
     * @param id id
     * @return 删除成功true,否则false
     */
    public boolean delete(String id);

    /**
     * 更新
     * @param moduleInfo
     * @return 更新后的对象信息
     */
    public boolean update(ModuleInfo moduleInfo);

    /**
     * 根据id查找
     * @param id
     * @return 功能模块对象
     */
    public ModuleInfo findById(String id);


    /**
     * 批量查询
     * @param ids
     * @return
     */
    public List<ModuleInfo> findByIds(Set<String> ids);

    /**
     * 检查key是否已经存在
     * @param key
     * @return true:存在,false:不存在
     */
    public boolean isExistKey(String key);


    public List<ModuleInfo> getAll();

    public List<ModuleInfo> getByLevel(Integer level);

}
