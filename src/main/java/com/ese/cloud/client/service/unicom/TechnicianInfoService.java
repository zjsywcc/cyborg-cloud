package com.ese.cloud.client.service.unicom;

import com.ese.cloud.client.entity.hunanUnicom.TechnicianInfo;
import java.util.List;

/**
 * 维修人员信息服务
 * Created by rencong on 17/1/23.
 */
public interface TechnicianInfoService {

    /**
     * 添加外包人员信息
     * @param technicianInfo
     * @return
     */
    public boolean add(TechnicianInfo technicianInfo);

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
    public List<TechnicianInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 获取全部接口人信息
     * @return
     */
    public List<TechnicianInfo> allInterfaceList();


    /**
     * 根据id查询
     * @param id
     * @return
     */
    public TechnicianInfo findOneById(String id);

    /**
     * 修改
     * @param technicianInfo
     * @return
     */
    public boolean update(TechnicianInfo technicianInfo);

}
