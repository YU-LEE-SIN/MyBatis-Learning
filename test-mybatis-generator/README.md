## mybatis-generator 
####使用Maven插件
1. 在 pom.xml 中导入 generator 依赖
2. 在resources下新建generatorConfig.xml
3. 在Maven中 点击 Plugins\mybatis-generator\mybatis-generator:generate(双击运行)；

#### 使用 java
1. 添加依赖
pom.xml
```xml
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.3.5</version>
</dependency>
```
2.建立 generatorConfig.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
<!-- 数据库驱动jar包 -->
  <classPathEntry location="D:/repository/mysql/mysql-connector-java/5.1.25/mysql-connector-java-5.1.25.jar" />
  <context id="DBTables" targetRuntime="MyBatis3">
    <!-- jdbc连接配置 -->
    <jdbcConnection driverClass="com.mysql.jdbc.Driver"
        connectionURL="jdbc:mysql://localhost:3306/shopping?characterEncoding=utf8"
        userId="root"
        password="123456">
    </jdbcConnection>
    <!-- false: JDBC DECIMAL 和 NUMERIC 类型解析为 Integer（默认）
         true: JDBC DECIMAL 和   NUMERIC 类型解析为java.math.BigDecimal -->
    <javaTypeResolver >
      <property name="forceBigDecimals" value="false" />
    </javaTypeResolver>
    <!-- targePackage 指定生成的PO类所在的包 
         targetProject 指定包所在的项目路径(项目名/src/main/java或者./src/main/java)根据实际效果选择 -->
    <javaModelGenerator targetPackage="com.mybatis.po" targetProject="./src/main/java">
    <!-- 是否使用子包 ，即schema（表空间）作为包名后缀-->
      <property name="enableSubPackages" value="false" />
      <!-- 意味着任何字符串属性的setter方法将调用trim方法，去除空白符-->
      <property name="trimStrings" value="true" />
    </javaModelGenerator>
    <!-- targetPackage 指定生成的XML配置文件所在包 -->
    <sqlMapGenerator targetPackage="com.mybatis.mapper"  targetProject="./src/main/java">
      <property name="enableSubPackages" value="true" />
    </sqlMapGenerator>
    <!-- targetPackage 指定生成的mapper接口所在包 -->
    <javaClientGenerator type="XMLMAPPER" targetPackage="test.dao"  targetProject=""./src/main/java"">
      <property name="enableSubPackages" value="true" />
    </javaClientGenerator>
    <!-- 指定数据库表 此处还有很多自定义配置，根据个人需求进行设置即可 -->  
            <table tableName="user"></table>  
            <table tableName="order"></table>  
            <table tableName="detail"></table>  
            <table tableName="item"></table>  
    <!-- <table schema="DB2ADMIN" tableName="ALLTYPES" domainObjectName="Customer" >
      <property name="useActualColumnNames" value="true"/>
      <generatedKey column="ID" sqlStatement="DB2" identity="true" />
      <columnOverride column="DATE_FIELD" property="startDate" />
      <ignoreColumn column="FRED" />
      <columnOverride column="LONG_VARCHAR_FIELD" jdbcType="VARCHAR" />
    </table> -->
  </context>
</generatorConfiguration>
```
3. 运行Java代码 ，（MyBatis Generator 官网  : https://mybatis.org/generator/running/runningWithJava.html）
```java
public class MybatisGenerator{
    public static void main(String[] args){
       List<String> warnings = new ArrayList<String>();
             boolean overwrite = true;
              //指定逆向工程配置文件
             File configFile = new File("generatorConfig.xml");
             ConfigurationParser cp = new ConfigurationParser(warnings);
             Configuration config = cp.parseConfiguration(configFile);
             DefaultShellCallback callback = new DefaultShellCallback(overwrite);
             MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
             myBatisGenerator.generate(null);
    }
}
```
#### 使用 idea 插件 ？？？
