package org;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author yu
 * @date 2020/4/9
 */
public class BasicCRUDTest {
    //根据id查询部门信息，得到一条记录结果
    @Test
    public void getDeptByIdTest() throws IOException {
        // mybatis配置文件
        String resource = "mybatis-config.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 创建会话工厂，传入mybatis的配置文件信息
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过SqlSession操作数据库
        // 第一个参数：等于Mapper文件的namespace+"."+statement的id
        // 第二个参数：指定和映射文件中所匹配的parameterType类型的参数
        // selectOne查询出一条记录
        try(SqlSession session = factory.openSession(true)){
            Dept dept =  session.selectOne("test.getDeptById",1);
            System.out.println(dept);
        }
    }
    //查询所有部门信息
    @Test
    public void getDeptAllTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过SqlSession操作数据库
        // selectList查询出所有的数据，与映射文件中resultType所指定的类型一致
        try(SqlSession session = factory.openSession(true)){
            List<Dept> dept =  session.selectList("test.getDeptAll");
            System.out.println(dept);
        }
    }
    //添加部门
    @Test
    public void insertDeptTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
       // true为自动提交事务 ,false则相反(用于是否开启事务,默认为true)
        try(SqlSession sqlSession = factory.openSession(false)){
            int flag =  sqlSession.insert("insertDept","xx");
            // 提交事务
            sqlSession.commit();
            System.out.println(flag);
        }
    }
    //根据id删除部门
    @Test
    public void delDeptTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        try(SqlSession session = factory.openSession(true)){
            int flag =  session.delete("test.delDeptById",7);
            System.out.println(flag);
        }
    }
    //根据id修改部门信息
    @Test
    public void editDeptTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 插入部门对象
        Dept dept=new Dept();
        dept.setId(6);
        dept.setDeptName("管理");
        try(SqlSession session = factory.openSession(true)){
            int flag =  session.update("test.editDept",dept);
            System.out.println(flag);
        }
    }
}
