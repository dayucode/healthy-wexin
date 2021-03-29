/*
 Navicat MySQL Data Transfer

 Source Server         : 118.126.110.110
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 118.126.110.110:3306
 Source Schema         : healthy

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 10/04/2019 20:29:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for h_admin
-- ----------------------------
DROP TABLE IF EXISTS `h_admin`;
CREATE TABLE `h_admin`  (
  `admin_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员ID',
  `username` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `admin_name` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员姓名',
  `admin_sex` tinyint(3) NOT NULL COMMENT '管理员性别',
  `admin_phone` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员手机号',
  `avatar` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员头像',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for h_coronary
-- ----------------------------
DROP TABLE IF EXISTS `h_coronary`;
CREATE TABLE `h_coronary`  (
  `coronary_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '冠心病评估ID',
  `openid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户微信openid',
  `coronary_sex` tinyint(1) NOT NULL COMMENT '性别',
  `coronary_age` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '年龄',
  `tc` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '总胆固醇',
  `coronary_smoke` tinyint(1) NOT NULL COMMENT '是否吸烟',
  `hdl_c` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '高密度脂蛋白',
  `sbp` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收缩压',
  `coronary_value` double NOT NULL COMMENT '风险值',
  `coronary_status` tinyint(3) NOT NULL COMMENT '风险等级',
  `location` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户地理位置',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`coronary_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '冠心病评估表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for h_family
-- ----------------------------
DROP TABLE IF EXISTS `h_family`;
CREATE TABLE `h_family`  (
  `openid` char(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '用户微信openid',
  `phone` char(16) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '家属电话号码',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`openid`, `phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '用户家属表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for h_obesity
-- ----------------------------
DROP TABLE IF EXISTS `h_obesity`;
CREATE TABLE `h_obesity`  (
  `obesity_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '体重测量ID',
  `openid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户微信openid',
  `obesity_datetime` datetime(0) NULL DEFAULT NULL COMMENT '体重测量时间',
  `obesity_value` double NOT NULL COMMENT '体重值',
  `bmi` double NOT NULL COMMENT 'BMI指数',
  `obesity_type` tinyint(3) NOT NULL COMMENT '体重状态',
  `location` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户地理位置',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`obesity_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体重表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for h_pressure
-- ----------------------------
DROP TABLE IF EXISTS `h_pressure`;
CREATE TABLE `h_pressure`  (
  `pressure_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '血压测量ID',
  `openid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户微信openid',
  `pressure_datetime` datetime(0) NULL DEFAULT NULL COMMENT '血压测量时间',
  `high_pressure` double NOT NULL COMMENT '收缩压',
  `low_pressure` double NOT NULL COMMENT '舒张压',
  `pressure_type` tinyint(3) NOT NULL COMMENT '血压类型',
  `location` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户地理位置',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`pressure_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '血压表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for h_sugar
-- ----------------------------
DROP TABLE IF EXISTS `h_sugar`;
CREATE TABLE `h_sugar`  (
  `sugar_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '血糖测量ID',
  `openid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户微信openid',
  `sugar_datetime` datetime(0) NULL DEFAULT NULL COMMENT '血糖测量时间',
  `sugar_value` double NOT NULL COMMENT '血糖指数',
  `sugar_type` tinyint(3) NOT NULL COMMENT '血糖类型',
  `location` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户地理位置',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`sugar_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '血糖表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for h_user
-- ----------------------------
DROP TABLE IF EXISTS `h_user`;
CREATE TABLE `h_user`  (
  `user_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `openid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户微信openid',
  `user_sex` tinyint(3) NULL DEFAULT NULL COMMENT '用户性别',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '用户生日',
  `height` double NULL DEFAULT NULL COMMENT '用户身高',
  `weight` double NULL DEFAULT NULL COMMENT '用户体重',
  `user_phone` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `mail` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `nickname` char(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信昵称',
  `head_img_url` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信头像',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
