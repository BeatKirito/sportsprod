package com.csb.sports.common.dao;

import java.util.List;

import com.csb.sports.common.pojo.SportRouteDo;

/**
 * @author 陈少波
 * @version $Id: SportRouteDao, v0.1 2017年04月30日 16:15 Exp $
 */
public interface SportRouteDao {

    /**
     * 插入一条数据
     * @param sportRouteDo 需要插入的数据Do
     * @return 影响条数
     */
    int insert(SportRouteDo sportRouteDo);

    /**
     * 查询所有数据
     * @return 数据集合
     */
    List<SportRouteDo> selectAll();

    /**
     * 根据uid删除一条数据
     * @param uid 用户id
     * @return  影响条数
     */
    int deleteByUid(String uid);

    /**
     * 根据uid更新一条数据
     * @param uid 用户id
     * @return 影响条数
     */
    int update(String uid);
}
