package org.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.pojo.Employee;

import java.util.List;

/**
 * @author yu
 * @date 2020/5/8
 */
public interface EmployDao {

    List<Employee> getEmpByCustom(@Param("offset") int offset, @Param("pageSize") int pageSize);
    /**总页数*/
    int getEmpCount();


    /**
     * 它的实现原理是这样:
     * 内存分页:
     * 还是执行你自己的sql,此sql返回多少记录就是多少
     *
     * 接着是利用RowBounds的2个参数,在内存中,排除掉不属于当前这一页的数据
     *
     * @param rowBounds
     * @return
     */
    List<Employee> getEmpByRowBounds(RowBounds rowBounds);


    /**
     * 配置了supportMethodArguments=true之后
     * 表明dao的方法的参数是可以识别的
     * 然后参数名字必须同时出现两个
     * 分别是pageNum和pageSize
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Employee> getEmpByPageHelper(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);


}
