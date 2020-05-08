### 输入映射
mybatis的输入映射。
####简单类型
三种简单类型输入

mapper（Dao）类如下:
```java
public interface EmployeeDao {
    
    int insertEmp(Employee employee);

    Employee getById(int id);

    Employee getByAgeAndDept(String name, int id);

    Employee getByAgeAndDeptTwo(@Param("empName") String name, @Param("id") int deptId);

    Employee getByAgeAndDeptThree(Employee employee);

    List<Employee> getAllByCondition(Map<String, Object> conditions);
    
}
```
简单类型的映射文件
```xml
 <!--如果方法只有一个参数,下面的参数名称不需要跟方法的参数名一样
    可以任意填写-->
    <select id="getById" resultType="emp">
        select id,name,age,address,deptId from employee
        where id=#{xx}
    </select>

    <!-- 在非调试编译java类的过程中,默认方法的参数
       名字是消失的,这个消失主要是针对反射的api

       导致的后果是通过反射取参数名的时候,得到参数名
       不是你声明的参数名,而是arg0,arg1,arg2...的形式

       为了解决这个问题,mybatis提供了一套方案,
       除了保留arg0,arg1,这一套jdk自带的方案
       它自己实现了一套param1,param2 的方案
       param1固定代表着第一个参数-->

    <select id="getByAgeAndDept" resultType="emp">
        select id,name,age,address,deptId from employee
        where name like #{param1} and id=#{param2}
    </select>

    <!--在sql中指定参数名,依据这个名字,去反射找到mapper接口对应名字的值

    sql语句中指定的参数有3种形式
    1.arg0,arg1...
    2. param1,param2
    3.在mapper接口中通过Param注解来指定名字,与sql语句的参数名一样-->
    <select id="getByAgeAndDeptTwo" resultType="emp">
        select id,name,age,address,deptId from employee
        where name like #{empName} and id=#{id}
    </select>
```
####pojo包装类型
```xml
  <!--参数用#,大括号里面值,在insert这个案例中,就是
    Employee实体类的属性的名字.
    #是利用jdbc的参数化查询方式,没有注入式漏洞的问题
     parameterType：指定输入 参数类型是pojo（包括 用户信息）
        #{}中指定pojo的属性名，接收到pojo对象的属性值，
        mybatis通过OGNL(对象图导航语言)获取对象的属性值
    -->
    <insert id="insertEmp" parameterType="org.pojo.Employee">
        insert into employee( name , age, address, deptId)
        values (#{name},#{age},#{address},#{deptId})
    </insert>
    <!--通过实体类映射输入参数，sql参数和实体类的字段一致-->
    <select id="getByAgeAndDeptThree" resultType="emp" parameterType="emp">
        select id,name,age,address,deptId from employee
         where name like '%'#{name}'%' and id=#{id}
    </select>
```
####HashMap
```xml
 <!--参数为map-->
    <select id="getAllByCondition" resultType="emp">
         select id,name,age,address,deptId from employee
        where age > #{age}
        order by #{sortField} #{sortType}
    </select>
```
####测试
```java
public class EmployeeDaoTest {
    //pojo包装类型
    @Test
    public void insertEmp() {
        Employee employee = new Employee("name", 22,"CQ", 1);
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        employeeDao.insertEmp(employee);
    }
    
    //简单类型
    @Test
    public void getById() {
        SqlSession session = SqlSessionFactoryUtil.getSession();
        EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);
        //EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Employee employee = employeeDao.getById(1);
        System.out.println(employee);
    }

    @Test
    public void getByAgeAndDept() {
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Employee result = employeeDao.getByAgeAndDept("yu", 1);
        System.out.println(result);
    }

    @Test
    public void getByAgeAndDeptTwo() {
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Employee result = employeeDao.getByAgeAndDeptTwo("yu", 1);
        System.out.println(result);
    }
    //pojo包装类型
    @Test
    public void getByAgeAndDeptThree() {
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Employee employee = new Employee("三", 1);
        Employee result = employeeDao.getByAgeAndDeptThree(employee);
        System.out.println(result);
    }
    //HashMap
    @Test
    public void getAllByCondition() {
        EmployeeDao employeeDao = MapperFactory.generateMapper(EmployeeDao.class);
        Map<String, Object> map = new HashMap<>();
        map.put("age", 24);
        map.put("sortField", "age");
        map.put("sortType", "desc");
        List<Employee> employeeList = employeeDao.getAllByCondition(map);
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }
    }
}
```
#### xml特殊符号转义写法
    &lt;          <   小于
    
    &lt;=         <=   小于或等于

    &gt;          >    大于

    &gt;=        >=    大于或等于

    &lt;&gt;   <>   不等于

    &amp;      &

    &apos;   '
    
    &quot;      "