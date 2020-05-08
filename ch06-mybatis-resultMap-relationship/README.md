### 映射输出
#### 一对一（association）一对多（collection）
```xml
<resultMap id="UserOrderMap"  type="org.pojo.Orders">
        <id column="pk_id" property="id"/>
        <result column="user_id" property="userId"/>
        <result column="order_number" property="orderNumber"/>
        <result column="order_price" property="orderPrice"/>
        <result column="create_time" property="createTime"/>
        <result column="status" property="status"/>
        <result column="address" property="address"/>

        <!--一个订单对应多个订单项，使用collection映射-->
        <collection property="orderItems" ofType="org.pojo.OrderItem">
            <result column="pk_id" property="id"/>
            <result column="order_id" property="orderId"/>
            <result column="product_id" property="productId"/>
            <result column="item_num" property="itemNum"/>

            <!--一个订单项对应一个商品,关联(association)查询-->
            <association property="product" javaType="org.pojo.Product">
                <id column="pk_id" property="id"/>
                <result column="p_name" property="itemName"/>
                <result column="price" property="price"/>
                <result column="detail" property="detail"/>
                <result column="p_image" property="image"/>
                <result column="crate_time" property="createTime"/>
            </association>

        </collection>
    </resultMap>
```