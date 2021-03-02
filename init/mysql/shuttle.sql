create database shuttle;

use shuttle;

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

INSERT INTO `user` VALUES (1,'084e0343a0486ff05530df6c705c8bb4','18800000000','Earth',0,0,'guest');

create table service(
	id bigint not null auto_increment,
	name varchar(255) not null comment '服务名',
	color varchar(255) not null comment '服务颜色',
	icon varchar(255) not null comment '服务图标',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `service` VALUES (1,'外卖','#51A8DD','el-icon-shopping-bag-2'),(2,'打印','#86C166','el-icon-printer'),(3,'超市','#8B81C3','el-icon-shopping-cart-2'),(4,'快递','#F56C6C','el-icon-box'),(5,'跑腿','#F9BF45','el-icon-position'),(6,'其他','#DC9FB4','el-icon-user');

create table category(
	id bigint not null auto_increment,
	name varchar(255) not null comment '类别名',
	serviceId bigint not null comment '商店id',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `category` VALUES (1,'一食堂',1),(2,'寝室',2),(3,'校内',3),(4,'二食堂',1),(5,'食品',3),(6,'校内快递',4);

create table store(
	id bigint not null auto_increment,
	name varchar(255) not null comment '商店名',
	serviceId bigint not null comment '服务id',
	categoryId bigint not null comment '类别id',
	image varchar(255) not null comment  '商店图片',
	rate int(2) not null comment '商店评分',
	sales int(3) not null comment  '商店销量',
	primary key(id),
	unique(name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `store` VALUES (1,'咖啡店',1,1,'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-b1ebbd3c-ca49-405b-957b-effe60782276/5a703942-ff8e-4e9b-b371-d55328366c75.webp',5,31),(2,'打印店',2,2,'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-b1ebbd3c-ca49-405b-957b-effe60782276/0f42a92a-bdd0-4330-bae7-a4c9ee762a03.webp',3,2),(3,'小卖部',3,3,'https://images.pexels.com/photos/64613/pexels-photo-64613.jpeg?auto=compress&amp;cs=tinysrgb&amp;h=650&amp;w=940',1,1),(4,'餐馆一',1,1,'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-b1ebbd3c-ca49-405b-957b-effe60782276/8d8345a5-f35e-44cd-b90d-781c59338d25.jpg',0,0),(5,'餐馆二',1,4,'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-b1ebbd3c-ca49-405b-957b-effe60782276/b1120bf4-c9aa-4ef9-8fe3-fa8db861f8d8.jpg',1,1),(6,'快递',4,6,'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-b1ebbd3c-ca49-405b-957b-effe60782276/28fbda62-830a-4b23-84f3-4227a959b286.jpg',0,0);

create table product(
	id bigint not null auto_increment,
	name varchar(255) not null comment '产品名',
	price int(10) not null comment '产品价格',
	image varchar(255) not null comment '产品图片',
	quantity int(10) not null comment '数量',
	sales int(10) default 0,
	rate int(10) default 5 comment '评分',
	storeId bigint not null comment '商店id',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `product` VALUES (1,'咖啡',1000,'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-b1ebbd3c-ca49-405b-957b-effe60782276/cf5dc339-6ad8-4928-95ec-c580f0b7d9c2.webp',0,30,3,1),(2,'普通打印',100,'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-b1ebbd3c-ca49-405b-957b-effe60782276/388cc99b-394c-4955-8ae1-7d4388306e58.jpg',0,2,3,2),(3,'爆米花',500,'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-b1ebbd3c-ca49-405b-957b-effe60782276/f24ccd07-6755-4f32-bcf4-ed7e50cadc83.jpg',0,1,0,3),(4,'快递1',0,'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-b1ebbd3c-ca49-405b-957b-effe60782276/5ad2e3ef-0e18-4a8a-af30-6d18c35f9eb7.jpg',0,0,0,6);

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
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table ads(
    id bigint not null auto_increment,
    image varchar(255) not null comment 'ad图片',
    storeId bigint not null comment '商店id',
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ads` VALUES (1,'https://www.foodiesfeed.com/wp-content/uploads/2019/02/messy-pizza-on-a-black-table-768x512.jpg',2),(2,'https://www.foodiesfeed.com/wp-content/uploads/2017/05/juicy-burger-in-a-vibrant-interior.jpg',3);