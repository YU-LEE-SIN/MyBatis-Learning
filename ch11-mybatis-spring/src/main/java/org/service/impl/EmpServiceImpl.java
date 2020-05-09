package org.service.impl;

import org.dao.EmpDao;
import org.pojo.Employee;
import org.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yu
 * @date 2020/5/9
 */
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpDao dao;

    @Override
    public List<Employee> getAllEmp() {
        return dao.getAllEmp();
    }
}
