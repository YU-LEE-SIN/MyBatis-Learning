package org.dao;

import org.apache.ibatis.annotations.Param;
import org.pojo.Employee;

import java.util.List;
import java.util.Map;

/**
 * @author yu
 * @date 2020/4/11
 */
public interface EmployeeDao {
    int insertEmp(Employee employee);

    Employee getById(int id);

    Employee getByAgeAndDept(String name, int id);

    Employee getByAgeAndDeptTwo(@Param("empName") String name, @Param("id") int deptId);

    Employee getByAgeAndDeptThree(Employee employee);

    List<Employee> getAllByCondition(Map<String, Object> conditions);

}
