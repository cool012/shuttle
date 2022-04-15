/*
 Navicat Premium Data Transfer

 Source Server         : tencent
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : 101.43.52.45:3306
 Source Schema         : shuttle

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 15/04/2022 10:32:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

create database shuttle;

use shuttle;
-- ----------------------------
-- Table structure for ads
-- ----------------------------
DROP TABLE IF EXISTS `ads`;
CREATE TABLE `ads`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ad图片',
  `storeId` bigint(20) NOT NULL COMMENT '商店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ads
-- ----------------------------
INSERT INTO `ads` VALUES (1, 'https://pic.imgdb.cn/item/6257bab7239250f7c5a6719f.jpg', 2);
INSERT INTO `ads` VALUES (2, 'https://pic.imgdb.cn/item/6257baba239250f7c5a67626.jpg', 3);
INSERT INTO `ads` VALUES (3, 'https://pic.imgdb.cn/item/6257babc239250f7c5a67848.jpg', 3);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别名',
  `serviceId` bigint(20) NOT NULL COMMENT '商店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '一食堂', 1);
INSERT INTO `category` VALUES (2, '寝室', 2);
INSERT INTO `category` VALUES (3, '校内', 3);
INSERT INTO `category` VALUES (4, '二食堂', 1);
INSERT INTO `category` VALUES (5, '食品', 3);
INSERT INTO `category` VALUES (6, '校内快递', 4);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cid` bigint(20) NOT NULL COMMENT '消费者用户id',
  `sid` bigint(20) NULL DEFAULT NULL COMMENT '生产者用户id',
  `pid` bigint(20) NOT NULL COMMENT '产品id',
  `date` datetime NOT NULL COMMENT '创建时间',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `file` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件',
  `status` int(10) NOT NULL DEFAULT -1 COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品名',
  `price` int(10) NOT NULL COMMENT '产品价格',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品图片',
  `quantity` int(10) NOT NULL COMMENT '数量',
  `sales` int(10) NULL DEFAULT 0,
  `rate` float NULL DEFAULT 5 COMMENT '评分',
  `storeId` bigint(20) NOT NULL COMMENT '商店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '咖啡', 1000, 'https://pic.imgdb.cn/item/6257ba16239250f7c5a57225.png', 0, 30, 3, 1);
INSERT INTO `product` VALUES (2, '普通打印', 100, 'https://pic.imgdb.cn/item/6257ba16239250f7c5a5736b.jpg', 0, 2, 3, 2);
INSERT INTO `product` VALUES (3, '爆米花', 500, 'https://pic.imgdb.cn/item/6257ba17239250f7c5a57417.jpg', 0, 1, 0, 3);
INSERT INTO `product` VALUES (4, '快递1', 0, 'https://pic.imgdb.cn/item/6257ba17239250f7c5a574ed.jpg', 0, 0, 0, 6);
INSERT INTO `product` VALUES (5, '咖啡 A', 1000, 'https://pic.imgdb.cn/item/6257ba17239250f7c5a5761a.jpg', 0, 0, 3, 1);
INSERT INTO `product` VALUES (6, '咖啡 B', 1000, 'https://pic.imgdb.cn/item/6257ba18239250f7c5a576fc.jpg', 0, 0, 3, 1);
INSERT INTO `product` VALUES (7, '咖啡 C', 1000, 'https://pic.imgdb.cn/item/6257ba19239250f7c5a57853.jpg', 0, 0, 3, 1);
INSERT INTO `product` VALUES (8, '咖啡 C', 1000, 'https://pic.imgdb.cn/item/6257ba19239250f7c5a57917.jpg', 0, 0, 3, 1);
INSERT INTO `product` VALUES (9, '咖啡 D', 1000, 'https://pic.imgdb.cn/item/6257ba1a239250f7c5a57ad9.jpg', 0, 0, 3, 1);
INSERT INTO `product` VALUES (10, '咖啡 E', 1000, 'https://pic.imgdb.cn/item/6257ba1b239250f7c5a57bf2.jpg', 0, 0, 3, 1);
INSERT INTO `product` VALUES (11, '咖啡 F', 1000, 'https://pic.imgdb.cn/item/6257ba1b239250f7c5a57d37.jpg', 0, 0, 3, 1);

-- ----------------------------
-- Table structure for service
-- ----------------------------
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务名',
  `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务颜色',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service
-- ----------------------------
INSERT INTO `service` VALUES (1, '外卖', '#51A8DD', 'el-icon-shopping-bag-2');
INSERT INTO `service` VALUES (2, '打印', '#86C166', 'el-icon-printer');
INSERT INTO `service` VALUES (3, '超市', '#8B81C3', 'el-icon-shopping-cart-2');
INSERT INTO `service` VALUES (4, '快递', '#F56C6C', 'el-icon-box');
INSERT INTO `service` VALUES (5, '跑腿', '#F9BF45', 'el-icon-position');
INSERT INTO `service` VALUES (6, '其他', '#DC9FB4', 'el-icon-user');

-- ----------------------------
-- Table structure for star
-- ----------------------------
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sid` bigint(20) NOT NULL,
  `pid` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `type` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of star
-- ----------------------------

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商店名',
  `serviceId` bigint(20) NOT NULL COMMENT '服务id',
  `categoryId` bigint(20) NOT NULL COMMENT '类别id',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商店图片',
  `rate` float NOT NULL COMMENT '商店评分',
  `sales` int(3) NOT NULL COMMENT '商店销量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES (1, '咖啡店', 1, 1, 'https://pic.imgdb.cn/item/6257b801239250f7c5a19f5c.png', 5, 30);
INSERT INTO `store` VALUES (2, '打印店', 2, 2, 'https://pic.imgdb.cn/item/6257b802239250f7c5a1a173.jpg', 3, 2);
INSERT INTO `store` VALUES (3, '小卖部', 3, 3, 'https://pic.imgdb.cn/item/6257b803239250f7c5a1a262.jpg', 3, 1);
INSERT INTO `store` VALUES (4, '餐馆一', 1, 1, 'https://pic.imgdb.cn/item/6257b803239250f7c5a1a2eb.jpg', 3, 0);
INSERT INTO `store` VALUES (5, '餐馆二', 1, 4, 'https://pic.imgdb.cn/item/6257b803239250f7c5a1a3c5.jpg', 2, 1);
INSERT INTO `store` VALUES (6, '快递', 4, 6, 'https://pic.imgdb.cn/item/6257b804239250f7c5a1a512.jpg', 3, 0);
INSERT INTO `store` VALUES (7, '餐馆 A', 1, 1, 'https://pic.imgdb.cn/item/6257b804239250f7c5a1a5c0.jpg', 3, 0);
INSERT INTO `store` VALUES (8, '餐馆 B', 1, 1, 'https://pic.imgdb.cn/item/6257b805239250f7c5a1a67d.jpg', 3, 0);
INSERT INTO `store` VALUES (9, '餐馆 C', 1, 1, 'https://pic.imgdb.cn/item/6257b805239250f7c5a1a6ff.jpg', 3, 0);
INSERT INTO `store` VALUES (10, '餐馆 D', 1, 4, 'https://pic.imgdb.cn/item/6257b806239250f7c5a1a7b5.jpg', 3, 0);
INSERT INTO `store` VALUES (11, '餐馆 E', 1, 4, 'https://pic.imgdb.cn/item/6257b806239250f7c5a1a8b5.jpg', 3, 0);
INSERT INTO `store` VALUES (12, '餐馆 F', 1, 4, 'https://pic.imgdb.cn/item/6257b807239250f7c5a1a994.jpg', 3, 0);
INSERT INTO `store` VALUES (13, '打印 A', 2, 2, 'https://pic.imgdb.cn/item/6257b807239250f7c5a1aa3e.jpg', 3, 0);
INSERT INTO `store` VALUES (14, '打印 B', 2, 2, 'https://pic.imgdb.cn/item/6257b807239250f7c5a1ab16.jpg', 3, 0);
INSERT INTO `store` VALUES (15, '打印 C', 2, 2, 'https://pic.imgdb.cn/item/6257b808239250f7c5a1abe2.jpg', 3, 0);
INSERT INTO `store` VALUES (16, '打印 D', 2, 2, 'https://pic.imgdb.cn/item/6257b808239250f7c5a1acc8.jpg', 3, 0);
INSERT INTO `store` VALUES (17, '打印 E', 2, 2, 'https://pic.imgdb.cn/item/6257b809239250f7c5a1ada9.jpg', 3, 0);
INSERT INTO `store` VALUES (18, '打印 F', 2, 2, 'https://pic.imgdb.cn/item/6257b809239250f7c5a1aea4.jpg', 3, 0);
INSERT INTO `store` VALUES (19, '打印 G', 2, 2, 'https://pic.imgdb.cn/item/6257b80a239250f7c5a1af53.jpg', 3, 0);
INSERT INTO `store` VALUES (20, '超市 A', 3, 3, 'https://pic.imgdb.cn/item/6257b80a239250f7c5a1afe3.jpg', 3, 0);
INSERT INTO `store` VALUES (21, '超市 B', 3, 3, 'https://pic.imgdb.cn/item/6257b80a239250f7c5a1b078.jpg', 3, 0);
INSERT INTO `store` VALUES (22, '超市 C', 3, 3, 'https://pic.imgdb.cn/item/6257b80b239250f7c5a1b1c2.jpg', 3, 0);
INSERT INTO `store` VALUES (23, '超市 D', 3, 3, 'https://pic.imgdb.cn/item/6257b80c239250f7c5a1b2b8.jpg', 3, 0);
INSERT INTO `store` VALUES (24, '超市 E', 3, 3, 'https://pic.imgdb.cn/item/6257b80c239250f7c5a1b3a5.jpg', 3, 0);
INSERT INTO `store` VALUES (25, '超市 F', 3, 3, 'https://pic.imgdb.cn/item/6257b80d239250f7c5a1b418.jpg', 3, 0);
INSERT INTO `store` VALUES (26, '超市 G', 3, 3, 'https://pic.imgdb.cn/item/6257b80d239250f7c5a1b5ad.jpg', 3, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '加密密码',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `score` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '点数',
  `admin` tinyint(1) NULL DEFAULT 0 COMMENT '管理员',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'null' COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '084e0343a0486ff05530df6c705c8bb4', '18800000000', 'Earth', 0, 1, 'admin', 'admin@shuttle.cf');
INSERT INTO `user` VALUES (2, '084e0343a0486ff05530df6c705c8bb4', '18100000000', 'Earth', 0, 0, 'client', 'client@shuttle.cf');
INSERT INTO `user` VALUES (3, '084e0343a0486ff05530df6c705c8bb4', '18000000000', 'Earth', 0, 0, 'server', 'server@shuttle.cf');

SET FOREIGN_KEY_CHECKS = 1;
