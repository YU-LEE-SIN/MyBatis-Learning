package org.dao;

import org.util.MapperFactory;
import org.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.pojo.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yu
 * @date 2020/4/11
 */
public class EmployeeDaoTest {

    @Test
    public void insertEmp() {
        Employee employee = new Employee("name", 22,"CQ", 1);
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        employeeDao.insertEmp(employee);
    }

    @Test
    public void getById() {
        SqlSession session = SqlSessionFactoryUtil.getSession();
        EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);
        //EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Employee employee = employeeDao.getById(1);
        System.out.println(employee);
    }

    @Test
    public void getByAgeAndDept() {
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Employee result = employeeDao.getByAgeAndDept("yu", 1);
        System.out.println(result);
    }

    @Test
    public void getByAgeAndDeptTwo() {
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Employee result = employeeDao.getByAgeAndDeptTwo("yu", 1);
        System.out.println(result);
    }

    @Test
    public void getByAgeAndDeptThree() {
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Employee employee = new Employee("三", 1);
        Employee result = employeeDao.getByAgeAndDeptThree(employee);
        System.out.println(result);
    }

    @Test
    public void getAllByCondition() {
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Map<String, Object> map = new HashMap<>();
        map.put("age", 24);
        map.put("sortField", "age");
        map.put("sortType", "desc");
        List<Employee> employeeList = employeeDao.getAllByCondition(map);
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }
    }
}