/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.17-log : Database - river
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`river` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `river`;

/*Table structure for table `user_table` */

DROP TABLE IF EXISTS `user_table`;

CREATE TABLE `user_table` (
  `id` varchar(32) NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) NOT NULL COMMENT '登录密码',
  `alias_name` varchar(255) NOT NULL COMMENT '用户别名用于显示',
  `phone` varchar(255) DEFAULT NULL COMMENT '用户电话',
  `roles` varchar(255) DEFAULT NULL COMMENT '用户角色多个以逗号分隔',
  `organization_id` varchar(255) DEFAULT NULL,
  `enable` int(11) DEFAULT '1' COMMENT '是否可用',
  `login_ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user_id` varchar(255) DEFAULT NULL COMMENT '创建人id',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `update_user_id` varchar(255) DEFAULT NULL COMMENT '最后一次更新人id',
  `del_flag` int(11) DEFAULT '0' COMMENT '删除标志位 0正常 1删除',
  `edit` int(11) DEFAULT '0' COMMENT '是否可编辑，0可以，1不可以',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_table` */

insert  into `user_table`(`id`,`user_name`,`password`,`alias_name`,`phone`,`roles`,`organization_id`,`enable`,`login_ip`,`login_date`,`description`,`remarks`,`create_user_id`,`create_at`,`update_at`,`update_user_id`,`del_flag`,`edit`) values ('0001','Admin','00be4484780c980e71b6fc74796f772f','管理员','18205082233','admin','015fe2ae4cc6014876a45fe2ac3b0005',1,NULL,NULL,NULL,NULL,NULL,NULL,'2017-11-30 16:08:05','0001',0,0),('015fe80d834401480ed25fe8000f000d','shuiwuju','7655f172bad5eb0f895448e63943ac6e','水务局','18355412311','','015fe2ac3c00014876a45fe2ac3b0001',1,NULL,NULL,NULL,NULL,'0001','2017-11-23 16:45:21','2017-11-23 17:12:25','0001',0,0),('015fe82872ac01480ed25fe8000f0010','guanlichu','ed76950f0b2f0a7a468b0dac5a6eef57','管理处','13588445566','','015fe2ae4cc6014876a45fe2ac3b0005',1,NULL,NULL,NULL,NULL,'0001','2017-11-23 17:14:46','2017-12-01 11:05:49','0001',0,0),('015fe829266c01480ed25fe8000f0011','gongguanke','e6929d9b6babb8c632815c83113eea11','工管科','18455661122','hdglccz,ggkkz','015fe2af237b014876a45fe2ac3b0006',1,NULL,NULL,NULL,NULL,'0001','2017-11-23 17:15:32',NULL,NULL,0,0),('015fe82a4fdd01480ed25fe8000f0012','jiangnansuo','8aa3cfd5abdc5bcfaf1ca2c74ea7de1b','江南所','18266552233','xcy','015fe2b09827014876a45fe2ac3b0009',1,NULL,NULL,NULL,NULL,'0001','2017-11-23 17:16:48','2017-12-04 16:57:02','0001',0,0),('015fe82b0bae01480ed25fe8000f0013','qitakeshi','5729c1eabe83d3702190b2ccd722e37f','其他科室','18377882211','qtks','015fe2afda32014876a45fe2ac3b0007',1,NULL,NULL,NULL,NULL,'0001','2017-11-23 17:17:36',NULL,NULL,0,0),('0160100ad1ef014829a660100ad10001','zyc','cd9ae35f5fa942cf13d8a020eee63ef1','张有才','13544554477','admin,ggkkz','015fe2ae4cc6014876a45fe2ac3b0005',1,NULL,NULL,NULL,NULL,'0001','2017-12-01 11:07:13',NULL,NULL,0,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
