### 简单的输出映射
mybatis 的两种 输出映射
- resultType
- resultMap 
#### ResultType
使用resultType进行输出映射，只有查询出来的列名和pojo中的属性名一致，该列才可以映射成功。
如果查询出来的列名和pojo中的属性名全部不一致，没有创建pojo对象。
只要查询出来的列名和pojo中的属性有一个一致，就会创建pojo对象。
##### 输出简单类型
```xml
<!--
    利用resultType这种形式,要求数据库的字段
    与实体类的属性名是一样的.

    如果不一样,只能通过改变sql语句的别名来达成
    例：select PK_id as id .....

    查询出来的结果集只有一行且一列，可以使用简单类型进行输出映射。
    -->
    <select id="getById" resultType="org.pojo.Employee">
        select pk_id as id,user_name as name,
        age, address , dept_Id as deptId from employee
        where pk_id=#{id}
    </select>
```
#### resultMap
mybatis中使用resultMap完成高级输出结果映射。(一对多，多对多) ,这里只有简单映射

resultMap使用场景：

如果查询出来的列名和pojo的属性名不一致，通过定义一个resultMap对列名和pojo属性名之间作一个映射关系。

1.定义resultMap
2.使用resultMap作为statement的输出映射类型
##### 定义resultMap
```xml
<!--定义resultMap-->
   <resultMap id="empResultMap" type="org.pojo.Employee">
          <!--
          id表示查询结果集中唯一标识
          column：查询出来的列名 (sql 字段)
  	 	property：type指定的pojo类型中的属性名 (实体类字段)
          如果是主键字段就用id元素来配置,其它的就用result元素来配置
          其它没有配置的项,靠自动映射完成映射处理
        -->
          <id property="id" column="pk_id"></id>
          <result property="name" column="user_name"/>
      </resultMap>

    <!--使用继承方式-->
    <resultMap id="child" type="org.pojo.Employee" extends="empResultMap">
        <result property="deptId" column="dept_Id"/>
    </resultMap>
```
##### 使用resultMap
```xml
<!-- 使用resultMap进行输出映射-->
   <!--resultMap：指定定义的resultMap的id，如果这个resultMap在其它的mapper文件，前边需要加namespace-->
    <select id="getAll" resultMap="child">
        select pk_id ,user_name ,
        age, address , dept_Id
        from employee
    </select>
```

##### 用来演示构造函数映射的案例
```xml
<!--用来演示构造函数映射的案例-->
    <resultMap id="employeeResultMap2" type="org.pojo.Employee">

        <!--constructor元素中有两个子元素,idArg和arg,其作用类似于id,result


        原理:通过你配置的arg或idArg的个数,判断构造函数参数的个数
        然后每一个配置项对的javaType确定参数的类型
        -->
        <constructor>
            <arg column="pk_id" javaType="Integer"></arg>
            <arg column="user_name" javaType="string"></arg>
        </constructor>
        <id property="id" column="pk_id"></id>
        <result property="name" column="user_name" />
    </resultMap>
```
