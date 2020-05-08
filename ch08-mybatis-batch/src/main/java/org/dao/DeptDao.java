package org.dao;

import org.apache.ibatis.annotations.Param;
import org.pojo.Dept;

import java.util.List;

/**
 * @author yu
 * @date 2020/5/7
 */
public interface DeptDao {
    void batchInsert(List<Dept> deptList);

    void batchDelete(@Param("deptId") List<Integer> deptIds);

    void batchUpdate(List<Dept> deptList);

    void batchUpdateOne(List<Dept> deptList);

    void update(Dept dept);

}
