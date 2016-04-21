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
  UNIQUE KEY `id` (`class_id`,`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='班级关联教师';

/*Data for the table `class_teacher` */

insert  into `class_teacher`(`id`,`class_id`,`teacher_id`) values (1,1,1),(2,1,2);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='班级';

/*Data for the table `classes` */

insert  into `classes`(`id`,`class_name`,`student_count`,`school_id`) values (1,'社会学',37,1),(2,'计算机科学与技术',100,1),(3,'生物工程',100,2),(4,'3',3,1);

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
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  UNIQUE KEY `uniq_school_name` (`school_name`),
  UNIQUE KEY `u_c` (`class_count`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='学校';

/*Data for the table `school` */

insert  into `school`(`id`,`school_name`,`head_img`,`class_count`,`adress`,`school_type`,`open`,`create_time`,`school_desc`,`update_time`) values (1,'安徽大学','upload/school/20160419181833_156001.jpg',10,'合肥市经济技术开发区九龙路111号',1,'1','2016-03-17 14:14:28','<span style=\"color:#333333;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\">新闻</span><span style=\"color:#CC0000;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\">安大</span><span style=\"color:#333333;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\"> 学术</span><span style=\"color:#CC0000;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\">安大</span><span style=\"color:#333333;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\"> 活力</span><span style=\"color:#CC0000;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\">安大</span><span style=\"color:#333333;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\"> 校友</span><span style=\"color:#CC0000;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\">安大</span><span style=\"color:#333333;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\"> 数字</span><span style=\"color:#CC0000;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\">安大</span><span style=\"color:#333333;font-family:arial;font-size:13px;line-height:20.02px;background-color:#FFFFFF;\"> Copyright . 2015 All rights reserved 皖ICP备020547号 联系地址 磬苑校区:合肥市经济技术开发区九龙路111号 邮编...</span>','2016-03-23 11:32:30'),(2,'浙江大学','upload/school/20160415140213_942953.jpg',20,'',1,'0','2016-04-15 14:03:18','<span style=\"color:#262626;font-family:微软雅黑, \'Microsoft YaHei\';font-size:14px;line-height:22.4px;background-color:#F5EFE5;\">浙江大学是一所历史悠久、声誉卓著的高等学府，坐落于中国历史文化名城、风景旅游胜地杭州。浙江大学的前身求是书院创立于1897年，为中国人自己最早创办的新式高等学校之一。1928年，定名国立浙江大学。抗战期间，浙大举校西迁，在贵州遵义、湄潭等地办学七年，1946年秋回迁杭州。1952年全国高等学校院系调整时，浙江大学部分系科转入兄弟高校和中国科学院，留在杭州的主体部分被分为多所单科性院校，后分别发展为原浙江大学、杭州大学、浙江农业大学和浙江医科大学。1998年，同根同源的四校实现合并，组建了新浙江大学，迈上了创建世界一流大学的新征程。在一百一十多年的办学历程中，浙江大学始终以造就卓越人才、推动科技进步、服务社会发展、弘扬先进文化为己任，逐渐形成了以“求是创新”为校训的优良传统。 </span>','2016-04-15 14:03:16'),(3,'测试学校','upload/school/20160419174902_647821.png',11,'',0,'0',NULL,'',NULL);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `class_id` bigint(20) NOT NULL COMMENT '所属班级',
  `sex` tinyint(2) NOT NULL DEFAULT '0' COMMENT '性别',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '学生名称',
  `identy_key` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键值',
  PRIMARY KEY (`name`),
  KEY `identy_key` (`identy_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='学生';

/*Data for the table `student` */

insert  into `student`(`class_id`,`sex`,`name`,`identy_key`) values (2,2,'2',1),(3,3,'3',2),(4,4,'4',3),(3,0,'一年级',4);

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '老师主键',
  `teacher_name` varchar(50) DEFAULT '' COMMENT '老师名称',
  `age` int(4) DEFAULT '0' COMMENT '老师年龄',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id` (`id`),
  UNIQUE KEY `u_t` (`teacher_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='教师';

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`teacher_name`,`age`) values (1,'Mr He',30),(2,'Mr Z',40);

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
