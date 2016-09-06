package com.zcc.entity;

/**
 * Created by heyzqt on 2016/9/6.
 *
 * 地址对象
 */
public class Address {

    /**
     * 地址Id
     */
    private int Id;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 地址
     */
    private String address;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
                "Id=" + Id +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
