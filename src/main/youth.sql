/*
 Navicat Premium Data Transfer

 Source Server         : 111.229.107.179
 Source Server Type    : MySQL
 Source Server Version : 80200
 Source Host           : 111.229.107.179:3306
 Source Schema         : youth

 Target Server Type    : MySQL
 Target Server Version : 80200
 File Encoding         : 65001

 Date: 10/05/2024 09:24:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `role` int NULL DEFAULT NULL COMMENT '角色：（0管理员）（1普通用户）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'lisi', NULL, '1111', NULL, 1);
INSERT INTO `user` VALUES (7, 'admin', NULL, '1111', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
