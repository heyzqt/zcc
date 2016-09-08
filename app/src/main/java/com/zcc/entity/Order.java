package com.zcc.entity;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 订单详情对象
 */
public class Order {

    /**
     * 订单Id
     */
    private int id;

    /**
     * 商品Id
     */
    private String businessId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 创单时间
     */
    private String createTime;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 商品数量
     */
    private String count;

    /**
     * 商品单价
     */
    private String price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Id=" + id +
                ", businessId='" + businessId + '\'' +
                ", userId='" + userId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", address='" + address + '\'' +
                ", count='" + count + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
