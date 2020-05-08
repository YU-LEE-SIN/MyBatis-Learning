package org.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yu
 * @date 2020/4/19
 */
public class Product {
    private Integer id;
    private String itemName;
    private String detail;
    private String image;
    private BigDecimal price;
    private Date createTime;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", detail='" + detail + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", createTime=" + createTime +
                '}';
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
