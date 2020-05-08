## 批量处理
#### 批量添加
```xml
<!--    进行批量操作的时候,有可能产生错误,原因是因为有些数据库
           默认允许传递的数据量是有限制的,mysql默认是1M,
           如果是这种情况,需要在mysql的配置文件(my.ini)中的
           mysqld这个组里面添加max_allowed_packet=10M-->
    <insert id="batchInsert" parameterType="org.pojo.Dept">
        insert into dept(deptname) values
        <foreach collection="list" item="dept" separator=",">
            (#{dept.deptName})
        </foreach>
    </insert>
```
#### 批量删除
```xml
    <delete id="batchDelete">
        delete from dept where
        <foreach collection="deptId" item="id" separator="or">
            id=#{id}
        </foreach>
    </delete>
```
#### 批量修改
两种批量修改方式

第一种方式一定要在jdbc的连接url中添加allowMultiQueries=true这个选项
因为配置的 mysql jdbc链接字符串 默认不支持一次性执行多个sql语句；但是在我们的 update map中需要执行多个 update语句。最后加上参数 "allowMultiQueries" 设置为true 如下：
```properties
<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true" />
```

```xml
  <!--利用分号分隔多个sql语句来实现,
   需要在jdbc的连接url中添加allowMultiQueries=true这个选项即可-->
    <!--sql语句相当于这个造型 ： update dept set deptname=xxx where id=xx；
                   update dept set deptname=xxx where id=xx；
                   update dept set deptname=xxx where id=xx；
     但是这种操作,mysql与sqlserver是支持,oracle数据"好像"是不支持的
     -->
    <update id="batchUpdate">
        <foreach collection="list" item="dept" separator=";">
            update dept set
            deptname=#{dept.deptName}
            where id=#{dept.id}
        </foreach>
    </update>
```
方式二：
```xml
    <!--https://developpaper.com/mybatis-batch-updates-in-three-ways/
    利用case when的方式批量更新(update)-->
    <update id="batchUpdateOne">
        update dept set
        <trim prefix="deptname = case" suffix="end">
            <foreach collection="list" item="dept">
                when id=#{dept.id} then #{dept.deptName}
            </foreach>
        </trim>
        <where>
            <foreach collection="list" item="dept" separator="or">
                id = #{dept.id}
            </foreach>
        </where>
    </update>
```
#### batch方式
batch的方式sql语句简单,但java代码写起来麻烦一些
    jdbc的批量处理是利用PrepareStatement的
    addBatch()
    executeBatch()
    这两个方法来完成的
   而且性能较低
```java
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
```

####总结
    1. batch的方式sql语句简单,但java代码写起来麻烦一些
    而且性能较低
    2. 拼接一个完整sql语句的优势是性能高,缺点是
    拼接sql语句时复杂一些,而且还会某种程度上依赖数据库的sql语法

    3. allowMultiQueries这个连接字符串的参数
    是让mysql数据库支持用分号分隔的多个sql语句的执行

    4. max_allowed_packet 这个mysql数据库服务器参数
    的调整是为了允许网络通信传递的数据量的大小.
