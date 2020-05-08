package org.dao;

import org.pojo.Employee;

import java.util.List;

/**
 * @author yu
 * @date 2020/4/19
 */
public interface EmpDao {
    Employee getById(Integer id);
    List<Employee> getAll();
}
