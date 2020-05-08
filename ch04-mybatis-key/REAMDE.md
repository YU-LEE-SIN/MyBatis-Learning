### 获取自动生成的值
#### Mybatis使用useGeneratedKeys获得自动生成id
插入记录时的一个通常的案例是:插入的同时获取自动生成的值
    并把获取的值赋值给实体类的某一个属性
```xml
    <!--
       设置方法就是:
       1. useGeneratedKeys = true
       2. keyProperty = 实体类的某一个属性的名字
 - keyProperty：将查询到主键值设置到parameterType指定的对象的哪个属性，
       -->
    <insert id="getGeneratedKeys" useGeneratedKeys="true" keyProperty="id">
        insert into dept(deptname) values(#{deptName})
    </insert>
```
####  使用select last_insert_id()得到自增长的id
```xml
  <insert id="insert"  >
        <!--
               将插入数据的主键返回，返回到Dept对象中
              SELECT LAST_INSERT_ID()：得到刚insert进去记录的主键值，只适用与自增主键
              keyProperty：将查询到主键值设置到parameterType指定的对象的哪个属性，keyProperty = 实体类的某一个属性的名字
              resultType：指定SELECT LAST_INSERT_ID()的结果类型
        -->
        <selectKey resultType="integer" order="AFTER"  keyProperty="id">
            select last_insert_id()
        </selectKey>
        insert into dept(deptname) values(#{deptName})
    </insert>
```
#### 使用mysql的uuid（）生成主键
```xml
 <!--
         执行过程：
         先利用selectKey 查询数据库,sql语句是select uuid(),得到数据库生成的uuid值
        并赋值给dept实体类的id字段。

        - resultType : 表明selectKey元素里面的sql语句执行的结果类型
        - order : 它有2个值,一个是before一个是after,因为现在的案例,是先要从
                     数据库获取值赋值给test实体类,所以是before,另外一个含义就是before表明
                        selectKey是在insert语句之前运行。
        - keyProperty：将查询的结果赋值到实体的一个字段上。
         这3点的总目标就是利用selectKey的查询语句的结果(resultType)赋值给实体类的属性(keyProperty)

          此时此刻的mybatis版本的默认行为是这样的: 只取第一行的第一列的值 ，("不"可以有多行记录)。

     -->
    <insert id="insert">
        <selectKey resultType="string" order="BEFORE" keyProperty="id" >
            select  uuid() as id
        </selectKey>
        insert into test(id,testname) values(#{id},#{deptName})
    </insert>
```

#### 多属性赋值
```xml
 <!--
     keyColumn官方文档:
    官方文档:https://mybatis.org/mybatis-3/sqlmap-xml.html
    关于keyColumn属性的作用可以查看mybatis源代码中的
    SelectKeyGenerator类的processGeneratedKeys方法(主要在内部调用的handleMultiplleProperties)

    keyColumn作用:
    1. 在keyProperty属性设置超过1的情况下起作用
    2. 目的是把selectKey中的sql语句的结果赋值给keyProperty的各个属性,赋值方法是keyColumn与keyProperty顺序对应赋值
    也就是把keyColumn第一列的值赋值给keyProperty设置的第一个属性,以此类推.

    注意:这样设计的考虑是:因为insert里面只能有一个selectKey,那如何给insert传递的实体类的多个属性赋值呢?
    就是利用keyProperty与keyColumn结合来达成-->
    <insert id="insert">
        <selectKey resultType="org.pojo.Dept"
            order="BEFORE"
            keyProperty="idxx,namexx"
            keyColumn="id,deptname">
            select id ,deptname form dept  limit 1;
        </selectKey>
        insert into dept(id,deptname) values(#{idxx},#{namexx})
    </insert>
```
