create database shuttle;

create table user(
	id bigint not null auto_increment,
	password varchar(255) not null comment '加密密码',
	phone varchar(255) not null comment '手机',
	address varchar(255) comment '地址',
	score int(255) unsigned default 0 comment '点数',
	admin tinyint(1) default 0 comment '管理员',
	name varchar(255) not null comment '昵称',
	primary key(id),
	unique(phone),
	unique(name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table service(
	id bigint not null auto_increment,
	name varchar(255) not null comment '服务名',
	color varchar(255) not null comment '服务颜色',
	icon varchar(255) not null comment '服务图标',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table category(
	id bigint not null auto_increment,
	name varchar(255) not null comment '类别名',
	serviceId bigint not null comment '商店id',
	primary key(id),
	FOREIGN KEY (serviceId) REFERENCES service(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table store(
	id bigint not null auto_increment,
	name varchar(255) not null comment '商店名',
	serviceId bigint not null comment '服务id',
	categoryId bigint not null comment '类别id',
	image varchar(255) not null comment  '商店图片',
	rate int(2) not null comment '商店评分',
	sales int(3) not null comment  '商店销量',
	primary key(id),
	FOREIGN KEY (serviceId) REFERENCES service(id),
	FOREIGN KEY (categoryId) REFERENCES category(id),
	unique(name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table product(
	id bigint not null auto_increment,
	name varchar(255) not null comment '产品名',
	price int(10) not null comment '产品价格',
	image varchar(255) not null comment '产品图片',
	quantity int(10) not null comment '数量',
	sales int(10) default 0,
	rate int(10) default 5 comment '评分',
	storeId bigint not null comment '商店id',
	primary key(id),
	foreign key(storeId) references store(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table orders(
	id bigint not null auto_increment,
	cid bigint not null comment '消费者用户id',
	sid bigint comment '生产者用户id',
	pid bigint not null comment '产品id',
	date datetime not null comment '创建时间',
	address varchar(255) not null comment '地址',
	note varchar(255) comment '备注',
	file varchar(255) comment '附件',
	status int(10) not null default -1 comment '订单状态',
	FOREIGN KEY (pid) REFERENCES product(id),
	FOREIGN KEY (cid) REFERENCES user(id),
	FOREIGN KEY (sid) REFERENCES user(id),
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table comments(
    id bigint not null auto_increment,
    content varchar(255) not null comment '内容',
    name varchar(255) not null comment '评论者昵称',
    storeId bigint not null comment '商店id',
    date datetime not null comment '时间',
    userId bigint not null comment '用户id',
    primary key(id)
)