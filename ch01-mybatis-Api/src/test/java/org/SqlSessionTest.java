package org;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yu
 * @date 2020/4/9
 */
public class SqlSessionTest {
    @Test
    public void testSqlSession() throws IOException {
        // mybatis配置文件
        String resource = "mybatis-config.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 创建会话工厂，传入mybatis的配置文件信息
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        //通过SqlSessionFactory创建SqlSession
        SqlSession session = null;
        try {
            session = factory.openSession();
            System.out.println(session);
        } finally {
            session.close();
        }
    }
    @Test
    public void testSqlSession2() throws IOException {
        // mybatis配置文件
        String resource = "mybatis-config.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 创建会话工厂，传入mybatis的配置文件信息
        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(inputStream);
        //下面这种写法是jdk7才有的, 等价于try finally的写法
        // 推荐的写法,这种写法,括号里面的类型必须实现了closable接口
        //SqlSession实现了closable接口， 这个接口有close()方法
        try(SqlSession session = factory.openSession()){
            System.out.println(session);
        }
    }
}
