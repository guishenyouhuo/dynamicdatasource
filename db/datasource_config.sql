/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : etl

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2018-11-13 17:50:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `datasource_config`
-- ----------------------------
DROP TABLE IF EXISTS `datasource_config`;
CREATE TABLE `datasource_config` (
  `bean_id` varchar(64) NOT NULL COMMENT 'beanid',
  `url` varchar(64) NOT NULL COMMENT '连接地址',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `maxActive` varchar(64) NOT NULL COMMENT '最大活动连接数',
  `type` varchar(64) NOT NULL COMMENT '数据源类型',
  `ds_key` varchar(64) NOT NULL COMMENT '数据源键值',
  PRIMARY KEY (`bean_id`),
  UNIQUE KEY `IDX_DATASOURCE_CONFIG` (`ds_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据源配置表';

-- ----------------------------
-- Records of datasource_config
-- ----------------------------
