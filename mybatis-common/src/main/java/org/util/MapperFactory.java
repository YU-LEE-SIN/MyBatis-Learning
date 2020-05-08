package org.util;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yu
 * @date 2020/4/11
 */
public class MapperFactory {
     /** 现在的这个写法,是有bug
     * 因为sqlSession没办法关闭,sqlSession是必须关闭的
     * 不建议使用*/
     public static <T> T generateMapperOne(Class<? extends  T> clz) {
         SqlSession session = SqlSessionFactoryUtil.getSession();
         return session.getMapper(clz);
     }
    /**
     *  改进后的代理方法
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T generateMapper(Class<? extends T> clz) {
        SqlSession session = SqlSessionFactoryUtil.getSession();
        T mapper = session.getMapper(clz);
        InvocationHandler handler = new MapperInterceptor(session, mapper);
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz},handler);
    }
    private static class MapperInterceptor implements InvocationHandler{
        private SqlSession sqlSession;
        private Object target;
        public MapperInterceptor(SqlSession sqlSession,Object target) {
            this.sqlSession = sqlSession;
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                return method.invoke(target, args);
            }finally {
                System.out.println("close-----");
                sqlSession.close();
            }
        }
    }
}
