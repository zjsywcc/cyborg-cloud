package com.ese.cloud.client.service.unicom;

import com.ese.cloud.client.entity.hunanUnicom.AreaInfo;

/**
 * 区域信息服务
 * Created by rencong on 17/1/24.
 */
public interface AreaInfoService {

    /**
     * 添加外包人员信息
     * @param areaInfo
     * @return
     */
    public boolean add(AreaInfo areaInfo);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public boolean delete(String id);



    /**
     * 修改
     * @param areaInfo
     * @return
     */
    public boolean update(AreaInfo areaInfo);

    /**
     * 根据id查询区域信息
     * @param id id信息
     * @return 区域信息
     */
    public AreaInfo getInfoById(String id);

    /**
     * 根据区域编码查询区域信息
     * @param code 区域编码
     * @return
     */
    public AreaInfo getInfoByCode(String code);


}
