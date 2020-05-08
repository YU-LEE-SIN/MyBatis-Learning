package org.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 *   SqlSessionFactoryBuilder:
 *  作用:
 *  1,发起读取解析xml文件,解析的结果就变成一个
 *  Configuration对象
 *  2.有了Configuration对象之后,就可以创建
 *  SqlSessionFactory
 *
 *  SqlSessionFactory
 *  作用:
 *  主要用来创建SqlSession,这个对象只应该有一个,跟程序对应,=
 *  也就是说一个应用应该只有一个SqlSessionFactory
 *  SqlSession是一个线程不安全的类,所以不要用在多线程环境下
 *  如果非要用,你必须解决线程不安全的问题
 *
 *  SqlSession最佳应用场合是方法作用域，定义成局部变量使用。意思就是在方法内部临时创建一个
 *  新的sqlSession,用完就销毁
 *
 * SqlSession每次使用时都要创建出来
 *
 * 作用:
 * 主要用来与数据库打交道,类似原来jdbc的Connection的作用
 * 通过SqlSession才能操作数据库
 * 而且
 * @author yu
 * @date 2020/4/10
 */
public class SqlSessionFactoryUtil {
    private static final String CONFIG_FILE_PATH = "mybatis-config.xml";
    private static SqlSessionFactory factory=null;

    static{
        try {
            InputStream inputStream = Resources.getResourceAsStream(CONFIG_FILE_PATH);
            factory = new SqlSessionFactoryBuilder().build(inputStream);
            System.out.println("-----debug: executory type = " + factory.getConfiguration().getDefaultExecutorType());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取文件失败");
        }
    }

    public static SqlSessionFactory getFactory(){
        return factory;
    }
    public static SqlSession getSession(){
        return factory.openSession(true);
    }
}
