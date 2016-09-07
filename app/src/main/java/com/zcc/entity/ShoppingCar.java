package com.zcc.entity;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 购物车对象
 */
public class ShoppingCar {

    /**
     * 购物车Id
     */
    private int Id;

    /**
     * 商品Id
     */
    private String businessId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 加入购物车时间
     */
    private String create_time;

    /**
     * 商品数量
     */
    private String count;

    /**
     * 商品单价
     */
    private String price;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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
        return "ShoppingCarFragment{" +
                "Id=" + Id +
                ", businessId='" + businessId + '\'' +
                ", userId='" + userId + '\'' +
                ", create_time='" + create_time + '\'' +
                ", count='" + count + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
