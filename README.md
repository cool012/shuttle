# shuttle

shuttle是一个综合性C2C校园服务平台，主要服务于移动端的单页面应用，功能包括用户注册、浏览商品、下单、接单、搜索、充值等基本功能，项目分为[后端](https://github.com/TouwaErioer/shuttle-interconnected)、[前端](https://github.com/TouwaErioer/shuttle-interconnected-web)、[后台管理系统](https://github.com/TouwaErioer/shuttle-interconnected-admin)。   

平台集用户和服务者为一体，在该平台注册为用户后，可以自行根据相关需求，成为客户或服务者；客户以普通用户的身份对商品下单，获得便利的校园服务；服务者则可以选择性的接受订单获取佣金，在此过程中平台只作为数据中介，不参与与商家的金钱对接，一切金钱交易由客户与服务者线下完成，实现对财务的去中心化，减少系统的复杂性。

API:[ShowDoc](https://www.showdoc.com.cn/1290360650186390)

## 依赖

| Description | Version|
|  :----: | :----: |
| Spring Boot | 2.3.0 |
| Redis | 4.0.9 |
| Mysql | 5.5 |
| Druid | 1.1.10 |
| MyBatis | 2.1.0 |
| log4j2 | 2.13.2 |
| RabbitMQ | latest |
| Swagger | 2.9.2 |
| JWT | 3.4.0 |
| PageHelper | 1.2.5 |
| ElasticSearch | 7.6.2 |
| MongoDB | latest |

## 必要参数

请在项目根目录 `.env` 文件里完成参数填写

```sh
dbUser= # mysql用户
dbName= # mysql数据库名称
dbPassword= # mysql密码
redisPassword= # redis密码
mqUser= # rabbitmq用户
mqPassword= # rabbitmq密码
key= # jwt密钥
appId= # 支付宝沙盒应用id
gateway= # 支付宝沙盒网关
redirectUrl= # 前端服务器ip，如果前端部署在同一台服务器上可以不填
druidUser= # druid用户
druidPassword= # druid密码
port= # http端口
elasticsearchUrl= # elasticsearch地址
mongoUser= # mongodb用户
mongoPassword= # mongodb密码
```

## 必要文件

请在根目录添加如下文件

```
# 支付宝沙盒私钥
private.txt

# 支付宝沙盒公钥
public.txt
```

## 环境依赖

* Docker
* Docler-compose
* 为 `ElasticSearch` 赋予相应权限

```sh
# 创建elasticsearch数据文件目录
mkdir /elasticsearch/data

# 赋予相应权限
sudo chmod 777 /elasticsearch/data
```

## 分支

[master](https://github.com/TouwaErioer/shuttle) 主分支，使用 `log4j2` 作为日志框架，日志写入到项目根 目录 `logs` 文件夹

[KEL](https://github.com/TouwaErioer/shuttle) 使用 `Kibana、ElasticSearch、Logstash` 作为日志系统

## 部署

```sh
# 克隆项目
git clone https://github.com/TouwaErioer/shuttle

# 因为项目涉及到文件的读写，要开放相应的权限
sudo chmod 777 shuttle

# 切换到项目
cd shuttle

# 在后台启动
sudo docker-compose up -d

# 停止并删除容器
sudo docker-compose down

# 停止
sudo docker-compose stop
```

