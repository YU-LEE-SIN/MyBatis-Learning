package org.service;

import org.apache.ibatis.session.RowBounds;
import org.dao.EmployDao;
import org.pojo.Employee;
import org.util.MapperFactory;

import java.util.List;

/**
 * @author yu
 * @date 2020/5/8
 */
public class EmployService {
    private EmployDao employDao = MapperFactory.generateMapperOne(EmployDao.class);

    /**
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Employee> getEmpByCustom(int pageNum, int pageSize){

        int offset= (pageNum-1)*pageSize;

        return employDao.getEmpByCustom(offset, pageSize);
    }

    /**
     * 总页数
     * @return
     */
    public int getEmpCount(){
        return employDao.getEmpCount();
    }

    /**
     *  内存分页:
     * @param rowBounds
     * @return
     */
    public List<Employee> getEmpByRowBounds(RowBounds rowBounds){
        return employDao.getEmpByRowBounds(rowBounds);
    }

    /**
     *  mybatis 分页插件
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Employee> getEmpByPageHelper(int pageNum,int pageSize){
        return employDao.getEmpByPageHelper(pageNum, pageSize);
    }
}
