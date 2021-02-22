# shuttle

shuttle是一个综合性C2C校园服务平台，主要服务于移动端的单页面应用，功能包括用户注册、浏览商品、下单、接单、搜索、充值等基本功能，项目分为[后端](https://github.com/TouwaErioer/shuttle-interconnected)、[前端](https://github.com/TouwaErioer/shuttle-interconnected-web)、[后台管理系统](https://github.com/TouwaErioer/shuttle-interconnected-admin)。   

平台集用户和服务者为一体，在该平台注册为用户后，可以自行根据相关需求，成为客户或服务者；客户以普通用户的身份对商品下单，获得便利的校园服务；服务者则可以选择性的接受订单获取佣金，在此过程中平台只作为数据中介，不参与与商家的金钱对接，一切金钱交易由客户与服务者线下完成，实现对财务的去中心化，减少系统的复杂性。

## 依赖

* 构建工具：Mamen

* web框架：Spring Boot

* 缓存：Redis

* 数据库：Mysql

* 数据库连接池：Druid

* ORM: MyBatis

* 日志：log4j2

* 消息队列：rabbitMQ

* API管理：Swagger

* 权限控制：JWT

* 分页插件：PageHelper

## 构建

```sh
git clone https://github.com/TouwaErioer/shuttle-interconnected

# 因为项目涉及到文件的读写，要开放相应的权限
sudo chmod 777 /shuttle-interconnected

cd /shuttle-interconnected

sudo mvn clean package
```

### 必要参数（环境变量）

```sh
# 数据库相关
dbHost
dbName
dbUser
dbPasswd

# jwt密钥
key

# 支付宝沙盒相关
appId
gateway
notifyUrl
redirectUrl
returnUrl

# redis相关
redisPassword
redisHost

# rabbitMQ相关
mqHost
mqUser
mqPasswd

# druid相关
druidUser
druidPasswd

# 端口
port
```

### 必要文件（请放在项目根目录下）

```
# 支付宝沙盒私钥
private.txt

# 支付宝沙盒公钥
public.txt
```