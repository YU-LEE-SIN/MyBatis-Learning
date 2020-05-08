use demo;
create table dept(
    id int auto_increment primary key,
    deptname varchar(15)
) ENGINE =InnoDB comment '部门表';

create table employee(
   pk_id int auto_increment primary key,
   user_name varchar(15) ,
   age tinyint ,
   address varchar(64),
   dept_Id int not null
)ENGINE =InnoDB comment '员工表';

insert into dept(deptname)
VALUES ('人事'),
       ('财务'),
       ('后勤'),
       ('采购'),
       ('研发');


insert into employee(user_name, age, address, dept_Id)
VALUES ('yu', '20', 'CQ', 1),
       ('yu2', '21', 'RC', 1),
       ('张三', '25', 'GZ', 3),
       ('yu3', '22', 'GZ', 2),
       ('yu4', '23', 'ZH', 5),
       ('yu5', '25', 'xx', 5),
       ('李某', '23', 'CQ', 1),
       ('蟹蟹', '21', 'RC', 5),
       ('张莫', '25', 'GZ', 3);


select *from dept;
select * from employee;

create table user(
                     pk_id int not null auto_increment primary key,
                     username varchar(15) not null,
                     address varchar(255) default ''
);
insert into  user( username, address) values
('admin','地球'),('tes2','地球'),('tes3','地球');
# drop table user;
create table product(
                        pk_id int not null auto_increment primary key,
                        p_name varchar(32) not null comment '商品名称',
                        price decimal(10,2) default '0' comment '商品定价',
                        detail text comment '商品描述',
                        p_image varchar(64) default '' comment '图片',
                        create_time datetime default current_timestamp comment '添加时间'
)comment '商品表';
insert into product(p_name, price) values
('P30','3999'),('mate30','4999'),('P20','2999'),('mate50','7999');

create table orders(
                       pk_id int not null auto_increment primary key,
                       user_id int not null comment '用户id',
                       order_number varchar(300)not null COMMENT '订单编号',
                       price decimal(10,2) not null comment '商品总价,付款金额',
                       create_time datetime default current_timestamp COMMENT '订单生成的时间',
                       status tinyint(4) not null default '0' COMMENT '订单的状态，默认0未支付未发货，
    1则表示支付未发货，2表示已发货未收货，3 表示已完成交易，4表示订单取消，5表示删除订单',
                       address varchar(300) default 'xxx'COMMENT '订单的收获地址'
)comment '订单表';
insert into orders(user_id, order_number, price) values
('1','123456','19996'),
('1','1234567','19996');

create table order_item(
                           pk_id int primary key auto_increment not null comment'id',
                           order_id int not null comment'订单id',
                           product_id int(11) NULL DEFAULT NULL COMMENT '商品id',
                           num int(11) NULL DEFAULT NULL COMMENT '商品数量',
                           price decimal(10,2)  NULL DEFAULT NULL COMMENT '商品小计'
)comment '订单详情表';
insert into order_item (order_id, product_id, num, price) values
#订单一123456
('1','1','1','3999.00'),
('1','2','1','4999.00'),
('1','3','1','2999.00'),
('1','4','1','7999.00'),
#订单二1234567
('2','1','1','3999.00'),
('2','2','1','4999.00'),
('2','3','1','2999.00'),
('2','4','1','7999.00');