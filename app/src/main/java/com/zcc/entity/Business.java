package com.zcc.entity;

/**
 * Created by heyzqt on 2016/9/6.
 *
 * 商品详情界面
 */
public class Business {

    /**
     * 商品Id
     */
    private int Id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品类型
     */
    private String type;

    /**
     * 商品价格
     */
    private String price;

    /**
     * 商品图片地址
     */
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Business{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
