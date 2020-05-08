package org.dao;

import org.apache.ibatis.annotations.Param;
import org.pojo.Employee;

import java.util.List;

/**
 * @author yu
 * @date 2020/4/23
 */
public interface EmpDao {
    List<Employee> getEmpByAge(Integer age);

    List<Employee> getEmpByAgeAndDeptId(@Param("axx") Integer age, @Param("deptId") Integer deptId);

    List<Employee> getEmpChoose(@Param("age") Integer age, @Param("deptId") Integer deptId);

    boolean insertEmp(Employee employee);

    boolean updateEmp(Employee employee);
}
