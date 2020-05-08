##Mapper接口+ mapper文件
###mapper代理方法
1.要mapper接口（相当 于dao接口）
2.编写mapper.xml映射文件 ，mybatis可以自动生成mapper接口实现类代理对象。

在mapper.xml 中 namespace = mapper接口地址。
```xml
<mapper namespace="org.dao.DeptDao">
    .....
</mapper>
```
- mapper.java接口中的方法名和mapper.xml 中的statement的 id一致
- mapper.xml中namespace就是mapper.java的类全路径。
- mapper.xml中statement的id和mapper.java中方法名一致。
- mapper.xml中statement的parameterType指定输入参数的类型和mapper.java的方法输入参数类型一致
- mapper.xml中statement的resultType指定输出结果的类型和mapper.java的方法返回值类型一致。

dao接口如下：
```java
public interface DeptDao {
    List<Dept> getDeptAll();
}
```
mybatis中通过typeHandlers完成jdbc类型和java类型的转换
mapper文件如下：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.dao.DeptDao">
    <!-- 通过select执行数据库查询
     id:标识映射文件中的sql，称为statement的id
     将sql语句封装到mappedStatement对象中，所以将id称为statement的id
     parameterType:指定输入参数的类型
     #{}标示一个占位符,
     #{value}其中value表示接收输入参数的名称，如果输入参数是简单类型，那么#{}中的值可以任意。
     resultType：指定sql输出结果的映射的java对象类型，select指定resultType表示将单条记录映射成java对象
     -->
    <select id="getDeptAll" resultType="dept">
        select  id , deptname from dept
    </select>
</mapper>
```
- 自定义别名
  -	单个别名定义 
  -	批量定义别名
  ```xml
  <!-- 批量别名定义 指定包名，mybatis自动扫描包中的po类，
  自动定义别名，别名就是类名（首字母大写或小写都可以）-->
      <package name="org.pojo"/>
  ```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!-- 别名定义 -->
    <typeAliases>
        <!-- 针对单个别名定义 type:类型的路径 alias:别名 -->
        <typeAlias type="org.pojo.Dept" alias="dept"/>
    </typeAliases>

    <!--可以配置多个环境，一般项目配置一个开发、一个生产环境，
    environments 的 default 属性表明使用哪个环境-->
    <environments default="development">
        <environment id="development">
            <!--设定事务管理器，有两种类型，一个是JDBC，一个是Managed(容器事务)-->
            <transactionManager type="JDBC"></transactionManager>
            <!-- 一般固定是POOLED,池化的.确定是使用连接池-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/demo"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 加载映射文件-->
    <mappers>
        <!--通过resource方法一次加载一个映射文件 -->
        <mapper resource="DeptMapper.xml"></mapper>
    </mappers>
</configuration>
```
### SqlSession的创建
1. SqlSessionFactoryBuilder ：
发起读取解析xml文件,解析的结果就变成一个
Configuration对象有了Configuration对象之后，
通过SqlSessionFactoryBuilder创建会话工厂SqlSessionFactory
2. SqlSessionFactory
通过SqlSessionFactory创建SqlSession，这个对象只应该有一个,跟程序对应,也就是说一个应用应该只有一个SqlSessionFactory
3. SqlSession 
 主要用来与数据库打交道,类似原来jdbc的Connection的作用， 通过SqlSession才能操作数据库
SqlSession中提供了很多操作数据库的方法：如：selectOne(返回单个对象)、selectList（返回单个或多个对象）,insert,update。
SqlSession是一个线程不安全的类，SqlSession最佳应用场合是方法作用域，
定义成局部变量使用。意思就是在方法内部临时创建一个新的sqlSession，用完就销毁
测试代码如下：
```java
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
            //statement的id，，id是方法名确定
            DeptDao deptImpl = session.getMapper(DeptDao.class);
            List<Dept> deptList=deptImpl.getDeptAll();
            System.out.println(deptList);
        }
    }
}
```
###mapper代理方法测试总结
使用mapper代理方法，它是利用动态代理技术，接口生成一个实现类,它会找对应的mapper文件中的namespace + id
根据id 代理对象内部调用`selectOne`或`selectList`
- 如果mapper方法返回单个pojo对象（非集合对象），代理对象内部通过selectOne查询数据库。
- 如果mapper方法返回集合对象，代理对象内部通过selectList查询数据库。
或者使用其他方法。
    