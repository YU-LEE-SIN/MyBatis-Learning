### Mybatis原生API+mapper文件
## 从 XML 中构建 SqlSessionFactory
每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得。

SqlSessionFactoryBuilder则可以通过从XML配置文件或一个预先定制的Configuration的实例构建出SqlSessionFactory的实例

从 XML 文件中构建 SqlSessionFactory 配置文件中包含了影响mybatis行为的设置(settings)和属性(properties)信息
``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
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

        <!--模拟生产环境,主要是改变数据库服务器的地址,假定为10.10.10.10-->
        <environment id="production">
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://10.10.10.10:3306/demo"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 加载映射文件-->
    <mappers>
        <mapper resource="basicMapper.xml"></mapper>
    </mappers>
</configuration>
```
 **下面是大致文件结构**：
```xml
<configuration>：声明在标签里面的信息是配置信息
<typeAliases>：声明在该标签里面的信息是一个个的别名
<typealias>：声明要使用别名的对象（全路径）用java注解的话可以使用@Alias注解声明
<environments>：声明在该标签内的环境变量，default表示默认的环境变量，一个environment表示一个jdbc连接数据库，如果有很多数据库的话我们要用到不同的环境变量
<environment>：声明环境变量<br/><transactionManager>：声明事务管理器，它的类型（type）有：JDBC（基于jdbc的事务） 还有 MANAGED（托管的事务）
<dataSource>：声明数据源，数据源的类型有NOPOOLED ，POOLED ，还有JIDN，在数据量少的话用ONPOOLED，测试和开发过程一般用POOLED，实际运行使用JIDN
<property>：jdbc连接的一些属性
<mappers>：声明我们定义的一个个Mapper类，或者说是关联
<mapper>：声明Mapper的路径
```
##从 SqlSessionFactory 中获取 SqlSession
获取sqlSession可以通过SqlSessionFactory。可以通过这个接口来执行命令，获取映射器和管理事务
```java
public class SqlSessionTest{
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
```
##xml映射文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="test">
    <select id="getDeptById" resultType="org.pojo.Dept">
        select  id , deptname from dept where id=#{id}
    </select>
    <select id="getDeptAll" resultType="org.pojo.Dept">
        select  id , deptname from dept
    </select>
    <delete id="delDeptById" parameterType="integer">
        delete from dept where id = #{id}
    </delete>
    <update id="editDept" parameterType="org.pojo.Dept">
        update dept set deptname=#{deptName} where id=#{id}
    </update>
    <insert id="insertDept">
        insert into dept(deptname) values(#{deptName})
    </insert>
</mapper>
```
## 测试代码
```java
public class BasicCRUDTest {
    //根据id查询部门信息，得到一条记录结果
    @Test
    public void getDeptByIdTest() throws IOException {
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
            Dept dept =  session.selectOne("test.getDeptById",1);
            System.out.println(dept);
        }
    }
    //查询所有部门信息
    @Test
    public void getDeptAllTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过SqlSession操作数据库
        // selectList查询出所有的数据，与映射文件中resultType所指定的类型一致
        try(SqlSession session = factory.openSession(true)){
            List<Dept> dept =  session.selectList("test.getDeptAll");
            System.out.println(dept);
        }
    }
    //添加部门
    @Test
    public void insertDeptTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
       // true为自动提交事务 ,false则相反(用于是否开启事务,默认为true)
        try(SqlSession sqlSession = factory.openSession(false)){
            int flag =  sqlSession.insert("insertDept","xx");
            // 提交事务
            sqlSession.commit();
            System.out.println(flag);
        }
    }
    //根据id删除部门
    @Test
    public void delDeptTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        try(SqlSession session = factory.openSession(true)){
            int flag =  session.delete("test.delDeptById",7);
            System.out.println(flag);
        }
    }
    //根据id修改部门信息
    @Test
    public void editDeptTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 插入部门对象
        Dept dept=new Dept();
        dept.setId(6);
        dept.setDeptName("管理");
        try(SqlSession session = factory.openSession(true)){
            int flag =  session.update("test.editDept",dept);
            System.out.println(flag);
        }
    }
}
```
