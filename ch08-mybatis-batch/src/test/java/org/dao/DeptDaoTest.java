package org.dao;

import org.junit.Test;
import org.pojo.Dept;
import org.util.MapperFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yu
 * @date 2020/5/7
 */
public class DeptDaoTest {
    private  DeptDao deptDao = MapperFactory.generateMapper(DeptDao.class);
    @Test
    public void batchInsert() {

        List<Dept> deptList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Dept dept = new Dept();
            dept.setDeptName("batchInsert"+i);
            deptList.add(dept);
        }
        deptDao.batchInsert(deptList);
    }

    @Test
    public void batchDelete() {
        List<Integer> idList = new ArrayList<>();
        for (int i = 7; i < 14; i++) {
            idList.add(i);
        }
        deptDao.batchDelete(idList);
    }

    @Test
    public void batchUpdate() {
        List<Dept> deptList = new ArrayList<>();
        for (int i = 7; i < 14; i++) {
            Dept dept = new Dept();
            dept.setDeptName("batchUpdate"+i);
            dept.setId(i);
            deptList.add(dept);
        }
        deptDao.batchUpdate(deptList);
    }

    @Test
    public void batchUpdateOne() {
        List<Dept> deptList = new ArrayList<>();
        for (int i = 7; i < 10; i++) {
            Dept dept = new Dept();
            dept.setId(i);
            dept.setDeptName("batchUpdateOne"+i);
            deptList.add(dept);
        }
        deptDao.batchUpdateOne(deptList);
    }
}