### 这是一个jdbc的Demo

#### jdbc编程步骤
1. 加载数据库驱动
2. 创建并获取数据库链接
3. 创建jdbc statement对象
4. 设置sql语句
5. 设置sql语句中的参数(使用preparedStatement)
6. 通过statement执行sql并获取结果
7. 对sql执行结果进行解析处理
8. 释放资源(resultSet、prepareStatement、connection)
#### 使用原生jdbc查询数据存在的问题
1. 数据库连接，使用时就创建，不使用立即释放，对数据库进行频繁连接开启和关闭，造成数据库资源浪费，影响 数据库性能。
解决方法：使用数据库连接池管理数据库连接。

2. 将sql语句硬编码到Java代码中，如果sql 语句修改，需要重新编译java代码，不利于系统维护。
解决方法：将sql语句配置在xml配置文件中，即使sql变化，不需要对java代码进行重新编译。

3. 向preparedStatement中设置参数，对占位符号位置和设置参数值，硬编码在java代码中，不利于系统维护。
解决方法：将sql语句及占位符号和参数全部配置在xml中。

4. 从resultSet中遍历结果集数据时，存在硬编码，将获取表的字段进行硬编码，，不利于系统维护。
解决方法：将查询的结果集，自动映射成java对象。