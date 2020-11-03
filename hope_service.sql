-- 创建数据库
create database hope_service;


create table user(
	id bigint not null auto_increment,
	password varchar(255) not null comment '加密密码',
	email varchar(255) not null comment '邮箱',
	address varchar(255) comment '地址',
	type int(10) not null default 0 comment '用户类型',
	score int(255) unsigned comment '点数',
	primary key(id),
	unique(email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 添加管理员
insert into user(email,password,address,type,score) values('admin@hope.com','hope','hope','2',0)

-- 服务表
create table service(
	id bigint not null auto_increment,
	service_name varchar(255) not null comment '服务名',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 类别表
create table category(
	id bigint not null auto_increment,
	name varchar(255) not null,
	service_id bigint not null comment '类型',
	primary key(id),
	unique(name),
	FOREIGN KEY (service_id) REFERENCES service(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 产品表
create table product(
	id bigint not null auto_increment,
	product_name varchar(255) not null comment '产品名',
	price int(10) not null comment '产品价格',
	image varchar(255) not null comment '产品图片',
	service_type bigint not null comment '类型',
	quantity int(10) not null comment '数量',
	category_id bigint not null,
	sales int(10) default 0,
	primary key(id),
	FOREIGN KEY (service_type) REFERENCES service(id),
	foreign key(category_id) references category(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 订单表
create table orders(
	id bigint not null auto_increment,
	cid bigint not null comment '消费者用户id',
	uid bigint comment '生产者用户id',
	pid bigint not null comment '产品id',
	create_time datetime not null comment '创建时间',
	address varchar(255) not null comment '地址',
	note varchar(255) not null comment '备注',
	file_url varchar(255) comment '附件',
	complete int(10) not null default -1 comment '是否完成订单',
	FOREIGN KEY (pid) REFERENCES product(id),
	FOREIGN KEY (cid) REFERENCES user(id),
	FOREIGN KEY (uid) REFERENCES user(id),
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;