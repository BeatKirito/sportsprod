package com.csb.sports.common.pojo;

import java.util.Date;

/**
 * 运动用户关系表Do
 * @author 陈少波
 * @version $Id: SportUserRelationDo, v0.1 2017年04月30日 14:34 Exp $
 */
public class SportUserRelationDo {

    /** 主键 */
    private Integer id;

    /** 用户id */
    private String  uid;

    /** 好友id */
    private String  friendId;

    /** 数据创建时间 */
    private Date    gmtCreate;

    /** 数据更新时间 */
    private Date    gmtModified;

    /** 好友关系状态 */
    private Integer status;

    /** 扩展信息 */
    private String  extInfo;

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
     * Getter method for property <tt>friendId</tt>.
     *
     * @return property value of friendId
     */
    public String getFriendId() {
        return friendId;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param friendId value to be assigned to property friendId
     */
    public void setFriendId(String friendId) {
        this.friendId = friendId;
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
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>extInfo</tt>.
     *
     * @return property value of extInfo
     */
    public String getExtInfo() {
        return extInfo;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param extInfo value to be assigned to property extInfo
     */
    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
