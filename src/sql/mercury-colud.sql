/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 60011
 Source Host           : localhost:3306
 Source Schema         : test-colud

 Target Server Type    : MySQL
 Target Server Version : 60011
 File Encoding         : 65001

 Date: 19/02/2021 22:28:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `my_file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `my_file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_store_id` int(11) NULL DEFAULT NULL COMMENT '文件仓库ID',
  `my_file_md5_id` int(11) NULL DEFAULT NULL COMMENT '文件的md5ID',
  `download_time` int(11) NULL DEFAULT 0 COMMENT '下载次数',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `parent_folder_id` int(11) NULL DEFAULT NULL COMMENT '文件夹ID',
  `size` int(11) NULL DEFAULT NULL COMMENT '文件大小',
  `type` int(11) NULL DEFAULT NULL COMMENT '文件类型',
  `postfix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  PRIMARY KEY (`my_file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for file_folder
-- ----------------------------
DROP TABLE IF EXISTS `file_folder`;
CREATE TABLE `file_folder`  (
  `file_folder_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件夹ID',
  `file_folder_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件夹名称',
  `parent_folder_id` int(11) NULL DEFAULT 0 COMMENT '父文件夹ID',
  `file_store_id` int(11) NULL DEFAULT NULL COMMENT '所属文件仓库ID',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`file_folder_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for file_md5
-- ----------------------------
DROP TABLE IF EXISTS `file_md5`;
CREATE TABLE `file_md5`  (
  `file_md5_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件md5 ID',
  `file_md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `upload_count` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`file_md5_id`, `file_md5`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for file_store
-- ----------------------------
DROP TABLE IF EXISTS `file_store`;
CREATE TABLE `file_store`  (
  `file_store_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件仓库ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '主人ID',
  `current_size` int(11) NULL DEFAULT 0 COMMENT '当前容量（单位KB）',
  `max_size` int(11) NULL DEFAULT 1048576 COMMENT '最大容量（单位KB）',
  `permission` int(11) NULL DEFAULT 0 COMMENT '仓库权限，0可上传下载、1不允许上传可以下载、2不可以上传下载',
  PRIMARY KEY (`file_store_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for my_file
-- ----------------------------
DROP TABLE IF EXISTS `my_file`;
CREATE TABLE `my_file`  (
  `my_file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `my_file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_store_id` int(11) NULL DEFAULT NULL COMMENT '文件仓库ID',
  `my_file_md5_id` int(11) NULL DEFAULT NULL COMMENT '文件MD5ID',
  `download_time` int(11) NULL DEFAULT 0 COMMENT '下载次数',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `parent_folder_id` int(11) NULL DEFAULT NULL COMMENT '父文件夹ID',
  `size` int(11) NULL DEFAULT NULL COMMENT '文件大小',
  `type` int(11) NULL DEFAULT NULL COMMENT '文件类型',
  `postfix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  PRIMARY KEY (`my_file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for temp_file
-- ----------------------------
DROP TABLE IF EXISTS `temp_file`;
CREATE TABLE `temp_file`  (
  `file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '临时文件ID',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件大小',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间：4小时后删除',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件在FTP上的存放路径',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的openid，用于qq登陆使用',
  `file_store_id` int(11) NULL DEFAULT NULL COMMENT '文件仓库ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '	0000@qq.com' COMMENT '用户邮箱',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `image_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `role` int(11) NULL DEFAULT 1 COMMENT '用户角色,0管理员，1普通用户',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
