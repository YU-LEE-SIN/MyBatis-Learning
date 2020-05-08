### 分页
#### 传统分页方式
Mapper 接口
```java
public interface EmployDao {
    /**分页查询*/
    List<Employee> getEmpByCustom(@Param("offset") int offset, @Param("pageSize") int pageSize);
    /**总页数*/
    int getEmpCount();
}
```
xml 文件
```xml
     <resultMap id="emp" type="org.pojo.Employee">
            <id  column="pk_id" property="id"/>
            <result column="user_name" property="name"/>
            <result column="age" property="age"/>
            <result column="address" property="address"/>
        </resultMap>
    
        <select id="getEmpByCustom" resultMap="emp">
            select  pk_id ,user_name,age,address
            from employee order by  pk_id
            limit #{offset} ,#{pageSize}
        </select>
    
        <select id="getEmpCount" resultType="int">
             select count(*) from employee
        </select>
```
service类
```java
public class EmployService {
    private EmployDao employDao = MapperFactory.generateMapperOne(EmployDao.class);

public List<Employee> getEmpByCustom(int pageNum, int pageSize){

        int offset= (pageNum-1)*pageSize;

        return employDao.getEmpByCustom(offset, pageSize);
    }
}
```
#### 利用RowBounds在内存中分页
内存分页:
还是执行你自己的sql,此sql返回多少记录就是多少
接着是利用RowBounds的2个参数,在内存中,排除掉不属于当前这一页的数据
```java
List<Employee> getEmpByRowBounds(RowBounds rowBounds);
```
```xml
    <select id="getEmpByRowBounds"  resultMap="emp">

        select pk_id ,user_name,age,address
        from employee order by pk_id
    </select>

```
service 类
```java
    /**
     *  内存分页:
     * @param rowBounds
     * @return
     */
    public List<Employee> getEmpByRowBounds(RowBounds rowBounds){
        return employDao.getEmpByRowBounds(rowBounds);
    }
```

#### Mybatis 分页插件
1.添加Mybatis分页插件依赖
```xml
<dependency>
   <groupId>com.github.pagehelper</groupId>
   <artifactId>pagehelper</artifactId>
</dependency>         
```
2.设置插件支持方法参数
```xml
<!--设置插件支持方法参数-->
    <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor">
            <property name="supportMethodsArguments" value="true"/>
        </plugin>
    </plugins>
```
mapper接口
     配置了supportMethodArguments=true之后
     表明dao的方法的参数是可以识别的
     然后参数名字必须同时出现两个
     分别是pageNum和pageSize
```java
 List<Employee> getEmpByPageHelper(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
```
