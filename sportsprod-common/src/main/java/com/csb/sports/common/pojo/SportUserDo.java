package com.csb.sports.common.pojo;

/**
 * 运动用户表Do
 * @author 陈少波
 * @version $Id: SportUserDo, v0.1 2017年04月30日 14:29 Exp $
 */
public class SportUserDo {

    /** 主键 */
    private Integer id;

    /** 用户id */
    private String  uid;

    /** 用户名 */
    private String  userName;

    /** 用户登录号 */
    private String  loginId;

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
     * Getter method for property <tt>userName</tt>.
     *
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>loginId</tt>.
     *
     * @return property value of loginId
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param loginId value to be assigned to property loginId
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("SportUserDo [");
        builder.append("id=").append(id).append(",uid=").append(uid).append(",userName=")
            .append(userName).append(",loginId=").append(loginId).append(']');
        return builder.toString();
    }
}
