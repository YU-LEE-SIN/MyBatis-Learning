package org.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.pojo.Dept;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * @author yu
 * @date 2020/4/10
 */
public class DeptDaoTest {
    @Test
    public void getDeptAll() throws IOException {
        // mybatis配置文件
        String resource = "mybatis-config.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 创建会话工厂，传入mybatis的配置文件信息
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过SqlSession操作数据库
        // 第一个参数：等于Mapper文件的namespace+"."+statement的id
        // 第二个参数：指定和映射文件中所匹配的parameterType类型的参数
        // selectOne查询出一条记录
        try(SqlSession session = factory.openSession(true)){
            //getMapper的方式是利用动态代理技术,帮我们把DeptDao
            //这个接口生成一个实现类,它会找对应的mapper文件中的namespace + id
            //id是方法名确定
            DeptDao deptImpl = session.getMapper(DeptDao.class);
            List<Dept> deptList=deptImpl.getDeptAll();
            System.out.println(deptList);
        }
    }
}