/*
SQLyog Ultimate v9.62 
MySQL - 5.5.43-0+deb7u1-log : Database - youyamvc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`youyamvc` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `youyamvc`;

/*Table structure for table `a_admin_user` */

DROP TABLE IF EXISTS `a_admin_user`;

CREATE TABLE `a_admin_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT '' COMMENT '用户名',
  `password` varchar(50) DEFAULT '',
  `real_name` varchar(25) DEFAULT '' COMMENT '真名',
  `email` varchar(30) DEFAULT '' COMMENT '邮箱',
  `telephone` varchar(20) DEFAULT '' COMMENT '座机号',
  `mobile_phone` varchar(20) DEFAULT '' COMMENT '手机号',
  `address` varchar(100) DEFAULT '' COMMENT '手机号',
  `create_time_ymd` int(4) DEFAULT '0',
  `create_time_hms` int(4) DEFAULT '0',
  `modified_time_ymd` int(4) DEFAULT '0',
  `modified_time_hms` int(4) DEFAULT '0',
  `super_admin` tinyint(2) DEFAULT '0' COMMENT '是否超级管理员',
  PRIMARY KEY (`id`),
  KEY `idx_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `a_admin_user` */

insert  into `a_admin_user`(`id`,`user_name`,`password`,`real_name`,`email`,`telephone`,`mobile_phone`,`address`,`create_time_ymd`,`create_time_hms`,`modified_time_ymd`,`modified_time_hms`,`super_admin`) values (1,'admin','admin','好的','','','','',0,0,0,0,0);

/*Table structure for table `class_teacher` */

DROP TABLE IF EXISTS `class_teacher`;

CREATE TABLE `class_teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级关联老师表主键',
  `class_id` bigint(20) DEFAULT '0' COMMENT '班级表主键',
  `teacher_id` bigint(20) DEFAULT '0' COMMENT '老师表主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `class_teacher` */

insert  into `class_teacher`(`id`,`class_id`,`teacher_id`) values (1,14,1),(2,3,1),(3,1,1),(5,1,2),(6,1,1),(7,1,1),(8,1,1),(9,2,1);

/*Table structure for table `classes` */

DROP TABLE IF EXISTS `classes`;

CREATE TABLE `classes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级主键',
  `class_name` varchar(50) DEFAULT '' COMMENT '班级名称',
  `student_count` int(4) DEFAULT '0' COMMENT '班级学生人数',
  `school_id` bigint(20) DEFAULT '0' COMMENT '学校id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `classes` */

insert  into `classes`(`id`,`class_name`,`student_count`,`school_id`) values (1,'高中1班',11,2),(2,'初中2班',12,1),(3,'初三1班',13,1),(4,'4班',1,1),(5,'5班',1,3),(6,'6班',12,3),(7,'7班',12,1),(8,'8班',12,2),(9,'9班',11,3),(10,'10班',11,2),(11,'11班',12,2),(12,'12班',12,1),(13,'13班',13,1),(14,'14班',14,1),(15,'15班',15,1),(16,'16班',16,1),(17,'17班',17,1),(18,'18班',18,3),(19,'19班',19,1),(20,'20班',20,1),(21,'21班',21,1);

/*Table structure for table `dict` */

DROP TABLE IF EXISTS `dict`;

CREATE TABLE `dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_key` varchar(50) DEFAULT '',
  `dict_value` varchar(1000) DEFAULT '',
  `dict_type` int(2) DEFAULT '0',
  `dict_desc` varchar(50) DEFAULT '',
  `dict_order` int(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dict` */

/*Table structure for table `school` */

DROP TABLE IF EXISTS `school`;

CREATE TABLE `school` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学校主键',
  `school_name` varchar(50) DEFAULT '' COMMENT '学校名称',
  `head_img` varchar(50) DEFAULT '' COMMENT '学校头像',
  `class_count` int(4) DEFAULT '0' COMMENT '班级个数',
  `adress` text COMMENT '学校地址',
  `school_type` tinyint(2) DEFAULT '0' COMMENT '学校类型',
  `open` char(1) DEFAULT '0' COMMENT '是否开学',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `school_desc` longtext COMMENT '学校描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `school` */

insert  into `school`(`id`,`school_name`,`head_img`,`class_count`,`adress`,`school_type`,`open`,`create_time`,`school_desc`) values (1,'安徽大学','',0,'',0,'0','2016-03-17 14:14:28',''),(2,'合肥工业大学','',0,'',0,'0','2016-03-17 13:15:03',''),(3,'清华大学','',0,'',0,'0',NULL,'');

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '老师主键',
  `teacher_name` varchar(50) DEFAULT '' COMMENT '老师名称',
  `age` int(4) DEFAULT '0' COMMENT '老师年龄',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`teacher_name`,`age`) values (1,'何老师',31),(2,'王老师',30);

/*Table structure for table `user_web` */

DROP TABLE IF EXISTS `user_web`;

CREATE TABLE `user_web` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `user_name` varchar(20) DEFAULT '' COMMENT '登录名称',
  `user_password` varchar(100) DEFAULT '' COMMENT '登录密码存储加密后的值',
  `real_name` varchar(20) DEFAULT '' COMMENT '用户真名',
  `score_amount` decimal(12,2) DEFAULT '0.00' COMMENT '积分余额',
  `money_amount` decimal(12,2) DEFAULT '0.00' COMMENT '现金余额',
  `regist_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `account_status` tinyint(2) DEFAULT '0' COMMENT '账号状态0无效1有效',
  `sex` tinyint(1) DEFAULT '0' COMMENT '性别1男0女',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `head_img_src` varchar(100) DEFAULT '' COMMENT '头像地址',
  `account_level` tinyint(2) DEFAULT '0' COMMENT '账号等级',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机号',
  `nickname` varchar(30) DEFAULT '' COMMENT '昵称',
  `two_code_img_src` varchar(50) DEFAULT '' COMMENT '二维码图片',
  `baby_sex` tinyint(1) DEFAULT '0' COMMENT '1',
  `baby_birthday` datetime DEFAULT NULL COMMENT '2',
  `baby_two_sex` tinyint(1) DEFAULT '0' COMMENT '3',
  `baby_two_birthday` datetime DEFAULT NULL COMMENT '4',
  `baby_three_sex` tinyint(1) DEFAULT '0' COMMENT '5',
  `baby_three_birthday` datetime DEFAULT NULL COMMENT '6',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user_web` */

insert  into `user_web`(`id`,`user_name`,`user_password`,`real_name`,`score_amount`,`money_amount`,`regist_time`,`last_login_time`,`account_status`,`sex`,`birthday`,`head_img_src`,`account_level`,`mobile`,`nickname`,`two_code_img_src`,`baby_sex`,`baby_birthday`,`baby_two_sex`,`baby_two_birthday`,`baby_three_sex`,`baby_three_birthday`) values (1,'11','','','0.00','0.00',NULL,NULL,0,0,NULL,'11',1,'1','','',0,NULL,0,NULL,0,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
