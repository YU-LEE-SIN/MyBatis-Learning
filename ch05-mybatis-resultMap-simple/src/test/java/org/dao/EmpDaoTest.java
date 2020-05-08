package org.dao;

import org.junit.Test;
import org.pojo.Employee;
import org.util.MapperFactory;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yu
 * @date 2020/4/19
 */
public class EmpDaoTest {

    @Test
    public void getById() {
        EmpDao dao = MapperFactory.generateMapper(EmpDao.class);
        Employee emp = dao.getById(1);
        System.out.println("---debug : employee:" + emp);
    }

    @Test
    public void getAll() {
        EmpDao dao = MapperFactory.generateMapper(EmpDao.class);
        List<Employee> employees = dao.getAll();
        for (Employee employee : employees) {
            System.out.println("-----debug: employee = " + employee);
        }
    }
}