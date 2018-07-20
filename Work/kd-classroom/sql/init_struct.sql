/*
 Navicat MySQL Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 47.96.108.32:3306
 Source Schema         : kd-classroom

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 30/06/2018 23:52:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员账号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e' COMMENT '管理员密码',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建账号时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改账号时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for catalog_resources
-- ----------------------------
DROP TABLE IF EXISTS `catalog_resources`;
CREATE TABLE `catalog_resources`  (
  `id` bigint(20) NOT NULL,
  `catalog_id` bigint(20) UNSIGNED NOT NULL COMMENT '分类id',
  `resource_id` bigint(20) UNSIGNED NOT NULL,
  `relation` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '类目跟资源之间的关系，1-该资源是在该类目下被创建，2-该资源从别的类目分享来的',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_catalog_id`(`catalog_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `school_id` bigint(20) UNSIGNED NOT NULL,
  `grade` tinyint(3) UNSIGNED NOT NULL COMMENT '年级',
  `class_no` tinyint(3) UNSIGNED NOT NULL COMMENT '班级号',
  `student_num` smallint(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '学生数量',
  `term_start_time` timestamp(0) NULL DEFAULT NULL COMMENT '学期开始时间',
  `term_end_time` timestamp(0) NULL DEFAULT NULL COMMENT '学期结束时间',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_school_id`(`school_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000121 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_notes
-- ----------------------------
DROP TABLE IF EXISTS `file_notes`;
CREATE TABLE `file_notes`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `child_resource_id` bigint(20) UNSIGNED NOT NULL COMMENT '一个子资源可以生成多个笔记资源',
  `note_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '笔记文件名',
  `note_type` tinyint(3) UNSIGNED NOT NULL DEFAULT 2 COMMENT '笔记文件类型，2-图片',
  `note_content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_child_resource_id`(`child_resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000007 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_resource_child
-- ----------------------------
DROP TABLE IF EXISTS `file_resource_child`;
CREATE TABLE `file_resource_child`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `resource_id` bigint(20) UNSIGNED NOT NULL COMMENT '父资源id',
  `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源内容，一般是资源文件的url',
  `order_no` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '序号，比如ppt的页码',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000971 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_resources
-- ----------------------------
DROP TABLE IF EXISTS `file_resources`;
CREATE TABLE `file_resources`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) UNSIGNED NOT NULL COMMENT '教师id',
  `resource_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源的名称',
  `resource_type` tinyint(3) UNSIGNED NOT NULL COMMENT '资源类型，1-ppt，2-图片',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000342 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for package_class
-- ----------------------------
DROP TABLE IF EXISTS `package_class`;
CREATE TABLE `package_class`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `package_id` bigint(20) UNSIGNED NOT NULL,
  `teacher_class_id` bigint(20) UNSIGNED NOT NULL COMMENT '老师班级关系表的id',
  `status` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '资源包在班级里的状态，0-未上课，1-已上课',
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key_tcid_pid`(`teacher_class_id`, `package_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000214 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for package_info
-- ----------------------------
DROP TABLE IF EXISTS `package_info`;
CREATE TABLE `package_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) UNSIGNED NOT NULL,
  `package_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '包名',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `teacher_class_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '教师班级关系id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_teacher_id`(`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000131 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for package_resource
-- ----------------------------
DROP TABLE IF EXISTS `package_resource`;
CREATE TABLE `package_resource`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `package_class_id` bigint(20) UNSIGNED NOT NULL COMMENT 'package_class表id',
  `resource_id` bigint(20) UNSIGNED NOT NULL,
  `status` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '该资源在该班级的状态，0-未开始，1-已结束，2-未结束(进行中)',
  `current_no` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '该资源在该班级进行到的当前页码',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留一个备注字段',
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key_pcid_rid`(`package_class_id`, `resource_id`) USING BTREE,
  INDEX `key_rid`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000646 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for package_resource_note
-- ----------------------------
DROP TABLE IF EXISTS `package_resource_note`;
CREATE TABLE `package_resource_note`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `package_class_id` bigint(20) UNSIGNED NOT NULL,
  `resource_id` bigint(20) UNSIGNED NOT NULL,
  `child_resource_id` bigint(20) UNSIGNED NOT NULL,
  `note_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '笔记文件名',
  `note_type` tinyint(3) UNSIGNED NULL DEFAULT 2 COMMENT '笔记文件类型，2-图片',
  `note_content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '笔记内容',
  `del_flag` bit(1) NULL DEFAULT b'0',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key_prid_rid_cid`(`package_class_id`, `resource_id`, `child_resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000623 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for resource_class
-- ----------------------------
DROP TABLE IF EXISTS `resource_class`;
CREATE TABLE `resource_class`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `resource_id` bigint(20) UNSIGNED NOT NULL COMMENT '资源id',
  `teacher_class_id` bigint(20) UNSIGNED NOT NULL COMMENT '老师班级关系表的id',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key_tcid_rid`(`teacher_class_id`, `resource_id`) USING BTREE,
  INDEX `key_rid`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000371 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '所属父学校id',
  `school_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `area` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `education_length` tinyint(10) UNSIGNED NULL DEFAULT NULL COMMENT '学制，1-九年制，2-八年制',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1000051 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `class_id` bigint(20) UNSIGNED NOT NULL COMMENT '班级id',
  `school_id` bigint(20) UNSIGNED NOT NULL COMMENT '学校id',
  `student_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生号',
  `student_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生姓名',
  `head_img` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'http://kd-classroom-public.oss-cn-hangzhou.aliyuncs.com/default-avatar.png?Expires=4685752424&OSSAccessKeyId=LTAIHR1bcULwhxpg&Signature=dGX45xT0X6f6M2u1WqNeucPanBw%3D' COMMENT '头像地址',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e',
  `parent_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家长手机号',
  `begin_time` timestamp(0) NULL DEFAULT NULL COMMENT '入学时间',
  `age` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT ' 年龄',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `parent_phone`(`parent_phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000202 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student_collect_notes
-- ----------------------------
DROP TABLE IF EXISTS `student_collect_notes`;
CREATE TABLE `student_collect_notes`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` bigint(20) UNSIGNED NOT NULL,
  `package_class_id` bigint(20) UNSIGNED NOT NULL,
  `resource_id` bigint(20) UNSIGNED NOT NULL,
  `subject_id` bigint(20) UNSIGNED NOT NULL,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_stuid_subid`(`student_id`, `subject_id`) USING BTREE,
  INDEX `key_sid_pcid`(`student_id`, `package_class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000276 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学科名',
  `cover_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '封面图片',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100054 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_menu_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名',
  `menu_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `school_id` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `subject_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学科id，多个用逗号分隔',
  `teacher_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mobile_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e',
  `age` tinyint(4) NULL DEFAULT NULL,
  `head_img` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'http://kd-classroom-public.oss-cn-hangzhou.aliyuncs.com/default-avatar.png?Expires=4685752424&OSSAccessKeyId=LTAIHR1bcULwhxpg&Signature=dGX45xT0X6f6M2u1WqNeucPanBw%3D' COMMENT '头像地址',
  `job_title` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '职称，1-班主任',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key_mobile_no`(`mobile_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000169 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher_catalog
-- ----------------------------
DROP TABLE IF EXISTS `teacher_catalog`;
CREATE TABLE `teacher_catalog`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) NOT NULL,
  `parent_catalog_id` bigint(20) NULL DEFAULT NULL COMMENT '父目录id',
  `catalog_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目录名',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_teacher_id`(`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000000 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher_class
-- ----------------------------
DROP TABLE IF EXISTS `teacher_class`;
CREATE TABLE `teacher_class`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) UNSIGNED NOT NULL,
  `class_id` bigint(20) UNSIGNED NOT NULL,
  `subject_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '老师教的这个班级的学科, 多门课英文逗号隔开',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` bit(1) NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_cid_subid`(`class_id`, `subject_id`) USING BTREE,
  INDEX `key_tid_cid`(`teacher_id`, `class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000406 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher_resource
-- ----------------------------
DROP TABLE IF EXISTS `teacher_resource`;
CREATE TABLE `teacher_resource`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) UNSIGNED NOT NULL,
  `resource_id` bigint(20) UNSIGNED NOT NULL,
  `relationship` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '1-创建，2-被分享',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ukey_tid_rid`(`teacher_id`, `resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000359 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_action_data
-- ----------------------------
DROP TABLE IF EXISTS `user_action_data`;
CREATE TABLE `user_action_data`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `user_type` tinyint(3) UNSIGNED NOT NULL COMMENT '用户类型，1-教师，2-学生',
  `plat_form` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '操作平台，1-pc，2-移动',
  `last_login_time` timestamp(0) NULL DEFAULT NULL COMMENT '最近一次登录时间',
  `last_update_pwd_time` timestamp(0) NULL DEFAULT NULL COMMENT '最近一次修改密码时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_uid_type`(`user_id`, `user_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
