#mybatis概述
##mybatis介绍
MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射，
MyBatis 封装了JDBC代码以及设置参数和获取结果集的工作。mybatis让程序员将主要精力放在sql上，通过mybatis提供的映射方式，
将接口和 Java 的 POJOs 映射成数据库中的记录。mybatis可以将向 PreparedStatement中的输入参数自动进行输入映射，将查询结果集灵活映射成java对象。（输出映射）
##SqlSessionFactory 与 SqlSessionFactoryBuilder
SqlSessionFactory是MyBatis的核心对象，它是创建SqlSession的工厂类。

SqlSessionFactory对象的实例可以通过SqlSessionFactoryBuilder对象类获得，
而SqlSessionFactoryBuilder则可以从XML配置文件或一个预先定制的Configuration的实例构建出SqlSessionFactory的实例。

每一个MyBatis的应用程序都是以一个SqlSessionFactory实例为核心。同时，SqlSessionFactory是线程安全的，SqlSessionFactory一旦被创建，
应该在应用执行期间都存在。在应用运行期间不要重复创建多次，建议使用单例模式。

##SqlSession

SqlSession是MyBatis的关键对象，类似于JDBC中的Connection，它是应用程序与持久层之间执行交互操作的一个单线程对象。

SqlSession的底层封装了JDBC连接，可以用SqlSession实例来直接执行被映射的SQL语句。

SqlSession是线程不安全的，每个线程都应该有它自己的SqlSession实例，SqlSession的实例不能被共享。不能将SqlSession实例的引用当做一个类的静态属性。

##框架原理
![框架原理图](https://img-blog.csdn.net/20141028140852531?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbHVhbmxvdWlz/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
##mybatis执行过程

1、配置mybatis的配置文件，Mybatis-Config.xml（名称不固定）

2、通过配置文件，加载mybatis运行环境，创建SqlSessionFactory会话工厂,SqlSessionFactory是线程安全的，SqlSessionFactory一旦被创建，应该在应用执行期间都存在。
在应用运行(SqlSessionFactory在实际使用时按单例方式)

3、通过SqlSessionFactory创建SqlSession。SqlSession是一个面向用户接口（提供操作数据库方法），实现对象是线程不安全的，建议sqlSession应用场合在方法体内。

4、调用sqlSession的方法去操作数据。如果需要提交事务，需要执行SqlSession的commit()方法。

5、释放资源，关闭SqlSession

