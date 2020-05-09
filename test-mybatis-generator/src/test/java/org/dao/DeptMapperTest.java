package org.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.pojo.Dept;

import java.io.InputStream;

/**
 * @author yu
 * @date 2020/5/9
 */
public class DeptMapperTest {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        // mybatis配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //传入mybatis的配置文件信息 ,创建会话工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    @Test
    public void deleteByPrimaryKey() {
        //创建会话
        SqlSession session = sqlSessionFactory.openSession();
        //创建代理对象
        DeptMapper dao = session.getMapper(DeptMapper.class);
        Dept dept = new Dept();
        dept.setDeptname("teststes");
        dao.deleteByPrimaryKey(1);
        session.commit();
        session.close();
    }
}