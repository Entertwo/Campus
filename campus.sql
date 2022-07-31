/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : campus

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 31/07/2022 09:25:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for contacts
-- ----------------------------
DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int(0) NULL DEFAULT NULL,
  `problem_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problem_details` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `contact`(`user_id`) USING BTREE,
  CONSTRAINT `contact` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contacts
-- ----------------------------
INSERT INTO `contacts` VALUES (1, '李贵涛', 17, '修改', '修改一下用户名');
INSERT INTO `contacts` VALUES (2, '李贵涛', 17, '商品取消代送', '没有取消按钮');

-- ----------------------------
-- Table structure for rim
-- ----------------------------
DROP TABLE IF EXISTS `rim`;
CREATE TABLE `rim`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `rim_text_h5` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `rim_text_h3` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `rim_p` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rim
-- ----------------------------
INSERT INTO `rim` VALUES (1, '安师校园互助平台', '让心灵与城市共美，让行动为美的增辉。', 'Let the soul and the city, let the action increase in the United States.');
INSERT INTO `rim` VALUES (2, '安师校园互助平台', '你可以信赖的人。', 'Lorem, ipsum dolor sit amet consectetur adipisicing elit. Nesciunt dolorem labore fuga quasi alias fugit beatae, nulla.');
INSERT INTO `rim` VALUES (3, '安师校园互助平台', '让心灵与城市共美，让行动为美的增辉。', 'Let the soul and the city, let the action increase in the United States.');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `shop_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` double(16, 2) NULL DEFAULT NULL,
  `pic` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int(0) NULL DEFAULT NULL,
  `send_id` int(0) NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `trade_filed` int(0) NULL DEFAULT NULL COMMENT '0为暂未交易，1交易中，2代送人员配送完成，3取消交易问题件，4交易完成，并评分',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop`(`user_id`) USING BTREE,
  CONSTRAINT `shop` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES (1, '快递百世', '菊园六号1楼', '后营1号112222', 2.00, '/assets/images/logo.png', 17, NULL, '2022-02-08 17:04:50', '2022-04-10 16:29:50', 4);
INSERT INTO `shop` VALUES (2, '快递顺丰', '菊园六号', '后营2', 1.00, '/assets/images/logo.png', 18, 17, '2022-03-30 09:45:10', '2022-04-04 21:53:12', 4);
INSERT INTO `shop` VALUES (3, '快递', '菊园六号', '后营3号', 1.50, '/assets/images/logo.png', 19, 17, '2022-04-04 21:51:45', '2022-02-27 15:43:07', 1);
INSERT INTO `shop` VALUES (4, '卫生纸', '菊园六号222', '后营五号', 12.00, '/assets/images/logo.png', 17, NULL, NULL, '2022-02-11 18:45:43', 0);
INSERT INTO `shop` VALUES (7, '百事可乐', '菊园五号', '喝的', 2.00, '/assets/images/logo.png', 18, 17, '2022-04-04 21:47:13', NULL, 3);
INSERT INTO `shop` VALUES (10, '哇哈哈', '后营五排', '喝的', 5.00, 'assets/images/123.jpg', 18, 17, '2022-04-04 21:51:43', NULL, 2);
INSERT INTO `shop` VALUES (13, '巧克力', '后营', '吃的', 5.00, 'assets\\images\\12345.jpg', 17, 41, '2022-04-10 20:46:25', NULL, 2);
INSERT INTO `shop` VALUES (19, '养乐多', '和鸣A401', '养乐多', 5.00, 'assets\\images\\10.jpg', 17, NULL, NULL, NULL, 0);
INSERT INTO `shop` VALUES (20, '娃哈哈', '菊园6号401', '一箱', 100.00, 'assets\\images\\11.jpg', 18, NULL, NULL, NULL, 0);
INSERT INTO `shop` VALUES (21, '卫生纸', '菊园六号', '一提卫生纸', 10.00, 'assets\\images\\11.jpg', 43, 44, '2022-05-06 14:46:00', '2022-05-06 14:46:28', 4);
INSERT INTO `shop` VALUES (22, '百事可乐', '菊园六号', '一瓶', 5.00, 'assets\\images\\11.jpg', 17, 45, '2022-05-06 19:18:05', NULL, 1);
INSERT INTO `shop` VALUES (23, '可口可乐', '菊园6号', '一瓶', 8.00, 'assets\\images\\11.jpg', 45, 17, '2022-05-06 19:19:22', NULL, 1);
INSERT INTO `shop` VALUES (24, '果粒橙', '竹园', '大瓶饮料', 10.00, 'assets\\images\\11.jpg', 17, NULL, NULL, NULL, 0);
INSERT INTO `shop` VALUES (25, '可口可乐', '菊园6号', '一瓶', 10.00, 'assets\\images\\11.jpg', 47, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `manager_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `manger_id`(`manager_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES (1, '1763096', '123456', '管理员');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `score` int(0) NULL DEFAULT NULL,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (17, '17630966258', '123456', 3, '李贵涛5', 1);
INSERT INTO `tb_user` VALUES (18, '17630966259', '123456', 0, '李贵涛1', 1);
INSERT INTO `tb_user` VALUES (19, '17630966260', '123456', 0, '李贵涛2', 1);
INSERT INTO `tb_user` VALUES (21, '17630966261', '123456', 0, '李贵涛6', 0);
INSERT INTO `tb_user` VALUES (26, '17630966262', '123456', 0, '李贵涛6', 0);
INSERT INTO `tb_user` VALUES (39, '17630966264', '123456', 0, '李贵涛7', 0);
INSERT INTO `tb_user` VALUES (41, '17630966263', '123456', 0, '李贵涛7', 1);
INSERT INTO `tb_user` VALUES (43, '17630966265', '123456', 0, '李贵涛8', 0);
INSERT INTO `tb_user` VALUES (44, '17630966266', '123456', 0, '李贵涛9', 1);
INSERT INTO `tb_user` VALUES (45, '17630966267', '123456', 0, '李贵涛9', 1);
INSERT INTO `tb_user` VALUES (47, '18630966258', '123456', 0, '同学A', 0);
INSERT INTO `tb_user` VALUES (48, '18630966259', '123456', 0, '同学B', 0);

SET FOREIGN_KEY_CHECKS = 1;
