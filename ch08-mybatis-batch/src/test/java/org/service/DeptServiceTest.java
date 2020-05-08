package org.service;

import org.junit.Test;
import org.pojo.Dept;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yu
 * @date 2020/5/8
 */
public class DeptServiceTest {

    @Test
    public void updateBatch() {
        List<Dept> depts = new ArrayList<>();
        for(int i=11;i<16;i++){
            Dept dept = new Dept();
            dept.setId(i);
            dept.setDeptName("one " + i);
            depts.add(dept);
        }

        DeptService deptService = new DeptService();

        deptService.updateBatch(depts);


    }

    @Test
    public void updateBatch2() {
        List<Dept> depts = new ArrayList<>();
        for(int i=11;i<16;i++){
            Dept dept = new Dept();
            dept.setId(i);
            dept.setDeptName("two " + i);
            depts.add(dept);
        }

        DeptService deptService = new DeptService();
        deptService.updateBatch2(depts);

    }
}