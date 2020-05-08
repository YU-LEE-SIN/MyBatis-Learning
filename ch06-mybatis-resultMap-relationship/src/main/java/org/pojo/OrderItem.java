package org.pojo;

import java.util.List;

/**
 * @author yu
 * @date 2020/4/19
 */
public class OrderItem {
    private Integer id;
    private String orderId;
    private Integer productId;
    private Integer itemNum;
    /**明细对应的商品信息*/
    private Product product;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", productId=" + productId +
                ", itemNum=" + itemNum +
                ", product=" + product +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getItemNum(){
        return itemNum;
    }
    public void setItemNum(Integer itemNum){
        this.itemNum = itemNum;
    }
}
