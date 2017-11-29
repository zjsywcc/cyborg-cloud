package com.ese.cloud.client.service;

import com.ese.cloud.client.entity.TopicInfo;

import java.util.List;

/**
 * topic info 服务
 * Created by rencong on 17/2/15.
 */
public interface TopicInfoService {

    /**
     * 添加
     * @param topicInfo
     * @return
     */
    public boolean add(TopicInfo topicInfo);

    /**
     * 查询
     * @param id
     * @return
     */
    public TopicInfo findById(String id);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<TopicInfo> pageFind(int pageIndex, int pageSize);

    /**
     * 查询全部信息
     * @return
     */
    public List<TopicInfo> all();

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
     * @param topicInfo
     * @return
     */
    public boolean update(TopicInfo topicInfo);


}
