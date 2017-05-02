package com.csb.sports.common.pojo;

import java.util.Date;

/**
 * 运动轨迹表Do
 * @author 陈少波
 * @version $Id: SportRouteDo, v0.1 2017年04月30日 14:41 Exp $
 */
public class SportRouteDo {

    /** 主键 */
    private Integer id;

    /** 用户id */
    private String  uid;

    /** 数据创建时间 */
    private Date    gmtCreate;

    /** 数据更新时间 */
    private Date    gmtModified;

    /** 路线结束时间 */
    private Date    gmtFinished;

    /** 步数 */
    private Integer stepCount;

    /** 路线长度 */
    private Integer routeLength;

    /** 路线信息 */
    private String  routeInfo;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>uid</tt>.
     *
     * @return property value of uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param uid value to be assigned to property uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param gmtCreate value to be assigned to property gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Getter method for property <tt>gmtModified</tt>.
     *
     * @return property value of gmtModified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param gmtModified value to be assigned to property gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * Getter method for property <tt>gmtFinished</tt>.
     *
     * @return property value of gmtFinished
     */
    public Date getGmtFinished() {
        return gmtFinished;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param gmtFinished value to be assigned to property gmtFinished
     */
    public void setGmtFinished(Date gmtFinished) {
        this.gmtFinished = gmtFinished;
    }

    /**
     * Getter method for property <tt>stepCount</tt>.
     *
     * @return property value of stepCount
     */
    public Integer getStepCount() {
        return stepCount;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param stepCount value to be assigned to property stepCount
     */
    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    /**
     * Getter method for property <tt>routeLength</tt>.
     *
     * @return property value of routeLength
     */
    public Integer getRouteLength() {
        return routeLength;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param routeLength value to be assigned to property routeLength
     */
    public void setRouteLength(Integer routeLength) {
        this.routeLength = routeLength;
    }

    /**
     * Getter method for property <tt>routeInfo</tt>.
     *
     * @return property value of routeInfo
     */
    public String getRouteInfo() {
        return routeInfo;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param routeInfo value to be assigned to property routeInfo
     */
    public void setRouteInfo(String routeInfo) {
        this.routeInfo = routeInfo;
    }
}
