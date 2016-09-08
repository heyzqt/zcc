package com.zcc.entity;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 地址对象
 */
public class Address {

    /**
     * 地址Id
     */
    private int id;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 地址
     */
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "Id=" + id +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
