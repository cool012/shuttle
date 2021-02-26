# shuttle

shuttle是一个综合性C2C校园服务平台，主要服务于移动端的单页面应用，功能包括用户注册、浏览商品、下单、接单、搜索、充值等基本功能，项目分为[后端](https://github.com/TouwaErioer/shuttle-interconnected)、[前端](https://github.com/TouwaErioer/shuttle-interconnected-web)、[后台管理系统](https://github.com/TouwaErioer/shuttle-interconnected-admin)。   

平台集用户和服务者为一体，在该平台注册为用户后，可以自行根据相关需求，成为客户或服务者；客户以普通用户的身份对商品下单，获得便利的校园服务；服务者则可以选择性的接受订单获取佣金，在此过程中平台只作为数据中介，不参与与商家的金钱对接，一切金钱交易由客户与服务者线下完成，实现对财务的去中心化，减少系统的复杂性。

## 依赖

| Syntax | Description | Version|
|  :----: |  :----: | :----: |
| Web Frame | Spring Boot | 2.3.4 |
| NoSQL | Redis | 4.0.9 |
| Databases | Mysql | 5.5 |
| Pool | Druid | 1.1.10 |
| ORM | MyBatis | 2.1.0 |
| Log | log4j2 | 2.13.2 |
| MQ | RabbitMQ | 3.x |
| API Manager | Swagger | 2.9.2 |
| Security | JWT | 3.4.0 |
| Pagination | PageHelper | 1.2.5 |

## 必要参数

请在项目根目录创建 `.env` 文件，添加下列参数

```sh
# mysql用户
dbUser=
# mysql数据库名称
dbName=
# mysql密码
dbPassword=

# redis密码
redisPassword=

# rabbitmq用户
mqUser=
# rabbitmq密码
mqPassword=

# jwt密钥
key=

# 支付宝沙盒应用id
appId=
# 支付宝沙盒网关
gateway=
# 支付宝沙盒异步通知
notifyUrl=
# 支付宝沙盒同步通知
returnUrl=
# 重定向
redirectUrl=

# druid用户
druidUser=
# druid密码
druidPassword=

# http端口
port=
```

## 必要文件

请在根目录添加如下文件

```
# 支付宝沙盒私钥
private.txt

# 支付宝沙盒公钥
public.txt
```

## 环境搭建

```sh
# 环境debin10

# 添加软件源的 GPG 密钥
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo apt-key add -

# 向 sources.list 中添加 Docker 软件源
add-apt-repository  "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable"

# 安装 Docker
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io

# 下载docker-compose二进制包
sudo curl -L https://github.com/docker/compose/releases/download/1.27.4/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose

# 赋予相应权限
sudo chmod +x /usr/local/bin/docker-compose
```

> 参考：[Docker —— 从入门到实践](https://yeasy.gitbook.io/docker_practice/install/debian)

## 部署

```sh
git clone https://github.com/TouwaErioer/shuttle-interconnected

# 因为项目涉及到文件的读写，要开放相应的权限
sudo chmod 777 /shuttle-interconnected

cd /shuttle-interconnected

sudo docker-compose up -d
```

