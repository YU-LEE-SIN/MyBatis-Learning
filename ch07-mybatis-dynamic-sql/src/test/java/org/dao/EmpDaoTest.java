package org.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.junit.Before;
import org.junit.Test;
import org.pojo.Employee;

import java.io.InputStream;
import java.util.List;


/**
 * @author yu
 * @date 2020/4/23
 */
public class EmpDaoTest {
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
    public void getEmpByAge() {
        //创建会话
        SqlSession session = sqlSessionFactory.openSession();
        //创建代理对象
        EmpDao dao = session.getMapper(EmpDao.class);
        List<Employee> employeeList = dao.getEmpByAge(23);
        System.out.println("debug====:"+employeeList);
        session.close();
    }

    @Test
    public void getEmpByAgeAndDeptId() {
        //创建会话
        SqlSession session = sqlSessionFactory.openSession();
        //创建代理对象
        EmpDao dao = session.getMapper(EmpDao.class);
        List<Employee> employeeList = dao.getEmpByAgeAndDeptId(23,1);
        System.out.println("debug====:"+employeeList);
        session.close();
    }

    @Test
    public void getEmpChoose() {
        //创建会话
        SqlSession session = sqlSessionFactory.openSession();
        //创建代理对象
        EmpDao dao = session.getMapper(EmpDao.class);
        List<Employee> employeeList = dao.getEmpChoose(23,1);
        System.out.println("debug====:"+employeeList);
        session.close();
    }

    @Test
    public void insertEmp() {
        SqlSession session = sqlSessionFactory.openSession();
        EmpDao dao = session.getMapper(EmpDao.class);
        Employee employee = new Employee();
        employee.setName("insert");
        employee.setAge(23);
        employee.setAddress("CQ");
        employee.setDeptId(1);
        boolean flag = dao.insertEmp(employee);
        session.commit();
        session.close();
    }

    @Test
    public void updateEmp() {
        SqlSession session = sqlSessionFactory.openSession();
        EmpDao dao = session.getMapper(EmpDao.class);
        Employee employee = new Employee();
        employee.setName("updatexxx");
//        employee.setAge(23);
//        employee.setAddress("CQ");
        employee.setDeptId(2);
        employee.setId(12);
        boolean flag = dao.updateEmp(employee);
        session.commit();
        session.close();
    }
}