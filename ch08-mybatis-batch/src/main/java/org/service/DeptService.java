package org.service;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.dao.DeptDao;
import org.pojo.Dept;
import org.util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * @author yu
 * @date 2020/5/8
 */
public class DeptService {
    void updateBatch(List<Dept> depts) {
        SqlSessionFactory sessionFactory = SqlSessionFactoryUtil.getFactory();

        // 默认情况下执行器的类型(ExecutorType)是Simple

        // 执行器在mybatis的语境中,指的是完成sql语句执行功能的组件
        //下面的代码是创建一个支持批处理的SqlSession对象出来,并且不是自动提交的方式

        SqlSession sqlSession = sessionFactory.openSession(ExecutorType.BATCH);
        DeptDao deptDao = sqlSession.getMapper(DeptDao.class);
        try {
            for (Dept dept : depts) {
                //调用这个方法的时候,在批处理的操作语境下,等价于调用
                //prepareStatement.addBatch
                deptDao.update(dept);
            }
            //等价于调用prepareStatement的executeBatch方法
            sqlSession.commit();
        }catch (Exception e){
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }



    void updateBatch2(List<Dept> depts) {
        SqlSessionFactory sessionFactory = SqlSessionFactoryUtil.getFactory();

        SqlSession sqlSession = sessionFactory.openSession(ExecutorType.BATCH);
        DeptDao deptDao = sqlSession.getMapper(DeptDao.class);
        try {
            int size = depts.size();
            for(int i =0 ; i<size; i++){
                Dept dept = depts.get(i);
                deptDao.update(dept);
                //这个if是每隔3条提交一次,避免积累太多的更新的数据
                if(i>0 && i%2==0 || i== size-1){

                    // 调用commit的时候,其内部真正完成批量操作的方法其实是
                    //flushStatements(); .这个方法的返回值可以获取每个更新语句影响的行数这个数据
                    // flushStatements()方法不会自动调用clearCache()方法来清理一级缓存.

                    // 调用commit方法的时候,会自动调用清空内部一级缓存的方法
                    sqlSession.commit();
                }
            }

        }catch (Exception e){
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
    }
}
