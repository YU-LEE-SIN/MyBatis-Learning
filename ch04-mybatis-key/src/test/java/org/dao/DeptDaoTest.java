package org.dao;

import org.junit.Test;
import org.pojo.Dept;
import org.util.MapperFactory;

import static org.junit.Assert.*;

/**
 * @author yu
 * @date 2020/4/19
 */
public class DeptDaoTest {

    @Test
    public void getGeneratedKeys() {
        DeptDao deptDao = MapperFactory.generateMapper(DeptDao.class);
        Dept dept = new Dept();
        dept.setDeptName("测试");
        deptDao.getGeneratedKeys(dept);
        System.out.println("debug: dept="+ dept);
    }
}