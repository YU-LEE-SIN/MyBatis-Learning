package org.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.pojo.OrderItem;
import org.pojo.Orders;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yu
 * @date 2020/4/22
 */
public class OrderMapperDaoTest {
    private SqlSessionFactory sqlSessionFactory;
    //此方法是在执行findUserOrders之前执行
    @Before
    public void setUp() throws Exception {
        // mybatis配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //传入mybatis的配置文件信息 ,创建会话工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void findUserOrders() {
        //创建会话
        SqlSession session = sqlSessionFactory.openSession();
        //创建代理对象
        OrderMapperDao dao = session.getMapper(OrderMapperDao.class);
        List<Orders> ordersList = dao.findUserOrders(1);
        System.out.println("debug====:"+ordersList);
        session.close();
    }
}