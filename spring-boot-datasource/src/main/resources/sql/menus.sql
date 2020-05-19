/*
Navicat MySQL Data Transfer

Source Server         : su
Source Server Version : 50644
Source Host           : localhost:3306
Source Database       : halodb

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2020-05-19 14:11:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `icon` varchar(50) DEFAULT '',
  `name` varchar(50) NOT NULL,
  `parent_id` int(11) DEFAULT '0',
  `priority` int(11) DEFAULT '0',
  `target` varchar(20) DEFAULT '_self',
  `team` varchar(255) DEFAULT '',
  `url` varchar(1023) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES ('1', '2019-10-08 18:35:18', '0', '2019-10-08 18:35:18', '', '首页', '0', '1', '_self', '', '/');
INSERT INTO `menus` VALUES ('2', '2019-10-08 18:35:18', '0', '2019-10-08 18:35:18', '', '归档', '0', '2', '_self', '', '/archives');
