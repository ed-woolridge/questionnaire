-- ----------------------------
CREATE DATABASE  IF NOT EXISTS questionnaire_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE questionnaire_db;
-- Chat2DB export data , export time: 2025-06-25 18:48:38
-- ----------------------------
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for table admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码（加密存储）',
  `real_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '管理员状态：ACTIVE(正常)、INACTIVE(禁用)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` (`id`,`username`,`password`,`real_name`,`phone`,`email`,`status`,`create_time`,`update_time`,`last_login_time`,`is_deleted`)  VALUES ('1','admin','e10adc3949ba59abbe56e057f20f883e','系统管理员','13800138000','admin@example.com','ACTIVE','2025-06-25 08:24:59','2025-06-25 08:28:19','2025-06-25 17:31:30',0);
-- ----------------------------
-- Table structure for table answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `answer_sheet_id` bigint NOT NULL COMMENT '答卷ID',
  `question_id` bigint NOT NULL COMMENT '问题ID',
  `option_id` bigint DEFAULT NULL COMMENT '选项ID（选择题）',
  `text_answer` text COLLATE utf8mb4_unicode_ci COMMENT '文本答案（填空题）',
  `number_answer` decimal(10,2) DEFAULT NULL COMMENT '数字答案',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_answer_sheet_id` (`answer_sheet_id`),
  KEY `idx_question_id` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='答案表';

-- ----------------------------
-- Records of answer
-- ----------------------------
-- ----------------------------
-- Table structure for table answer_sheet
-- ----------------------------
DROP TABLE IF EXISTS `answer_sheet`;
CREATE TABLE `answer_sheet` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `questionnaire_id` bigint NOT NULL COMMENT '问卷ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'IN_PROGRESS' COMMENT '答卷状态：IN_PROGRESS(填写中)、COMPLETED(已完成)、TERMINATED(已终止)',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始填写时间',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `duration` int DEFAULT NULL COMMENT '填写时长（分钟）',
  `surveyor_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查员姓名',
  `surveyor_phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查员电话',
  `survey_province` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查地点省份',
  `survey_city` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查地点城市',
  `survey_district` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查地点县区',
  `survey_location` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调查地点具体位置',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_questionnaire_id` (`questionnaire_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='答卷表';

-- ----------------------------
-- Records of answer_sheet
-- ----------------------------
INSERT INTO `answer_sheet` (`id`,`questionnaire_id`,`user_id`,`status`,`start_time`,`submit_time`,`duration`,`surveyor_name`,`surveyor_phone`,`survey_province`,`survey_city`,`survey_district`,`survey_location`,`is_deleted`)  VALUES ('1','1',NULL,'IN_PROGRESS','2025-06-25 17:13:32','2025-06-25 17:13:32',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
INSERT INTO `answer_sheet` (`id`,`questionnaire_id`,`user_id`,`status`,`start_time`,`submit_time`,`duration`,`surveyor_name`,`surveyor_phone`,`survey_province`,`survey_city`,`survey_district`,`survey_location`,`is_deleted`)  VALUES ('2','1',NULL,'IN_PROGRESS','2025-06-25 17:23:22','2025-06-25 17:23:23',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
INSERT INTO `answer_sheet` (`id`,`questionnaire_id`,`user_id`,`status`,`start_time`,`submit_time`,`duration`,`surveyor_name`,`surveyor_phone`,`survey_province`,`survey_city`,`survey_district`,`survey_location`,`is_deleted`)  VALUES ('3','1',NULL,'IN_PROGRESS','2025-06-25 17:25:10','2025-06-25 17:25:11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
INSERT INTO `answer_sheet` (`id`,`questionnaire_id`,`user_id`,`status`,`start_time`,`submit_time`,`duration`,`surveyor_name`,`surveyor_phone`,`survey_province`,`survey_city`,`survey_district`,`survey_location`,`is_deleted`)  VALUES ('4','1',NULL,'IN_PROGRESS','2025-06-25 17:29:28','2025-06-25 17:29:28',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
INSERT INTO `answer_sheet` (`id`,`questionnaire_id`,`user_id`,`status`,`start_time`,`submit_time`,`duration`,`surveyor_name`,`surveyor_phone`,`survey_province`,`survey_city`,`survey_district`,`survey_location`,`is_deleted`)  VALUES ('5','1',NULL,'IN_PROGRESS','2025-06-25 18:23:07','2025-06-25 18:23:08',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
INSERT INTO `answer_sheet` (`id`,`questionnaire_id`,`user_id`,`status`,`start_time`,`submit_time`,`duration`,`surveyor_name`,`surveyor_phone`,`survey_province`,`survey_city`,`survey_district`,`survey_location`,`is_deleted`)  VALUES ('6','1',NULL,'IN_PROGRESS','2025-06-25 18:46:25','2025-06-25 18:46:26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
-- ----------------------------
-- Table structure for table question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `questionnaire_id` bigint NOT NULL COMMENT '问卷ID',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '问题内容',
  `type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '问题类型：SINGLE_CHOICE(单选)、MULTIPLE_CHOICE(多选)、TEXT(填空)、NUMBER(数字)、SCREENING(筛选题)',
  `sort_order` int DEFAULT '0' COMMENT '排序号',
  `is_required` tinyint DEFAULT '1' COMMENT '是否必填：0-否，1-是',
  `is_screening` tinyint DEFAULT '0' COMMENT '是否为筛选题：0-否，1-是',
  `screening_condition` text COLLATE utf8mb4_unicode_ci COMMENT '筛选题终止条件（JSON格式）',
  `description` text COLLATE utf8mb4_unicode_ci COMMENT '问题说明',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_questionnaire_id` (`questionnaire_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问题表';

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('1','1','1. 您本次出游的时间，是否在6小时以上','SINGLE_CHOICE','1',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('2','1','2. 您本次游览的地点，离您日常居住（生活）以及工作（上学）的社区（村庄），距离是否在10公里以上','SINGLE_CHOICE','2',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('3','1','3. 您本次出游过程中，是否已经在我市其他县（区、市）或景区（点）等区域参与了本项调查','SINGLE_CHOICE','3',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('4','1','4. 您目前的常住地-省','TEXT','4',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('5','1','4. 您目前的常住地-市','TEXT','5',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('6','1','4. 您目前的常住地-县/区','TEXT','6',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('7','1','5. 您的性别是','SINGLE_CHOICE','7',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('8','1','6. 您的年龄','SINGLE_CHOICE','8',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('9','1','7. 您日常居住生活的地方','SINGLE_CHOICE','9',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('10','1','8. 您本次出游主要目的','SINGLE_CHOICE','10',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('11','1','8.1 其他目的说明','TEXT','11',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('12','1','9. 您本次出游是否住宿过夜','SINGLE_CHOICE','12',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('13','1','9.1 住宿夜数','NUMBER','13',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('14','1','9.2 主要住宿类型','SINGLE_CHOICE','14',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('15','1','10. 本次游览的景区类型','MULTIPLE_CHOICE','15',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('16','1','11. 来我市游览的方式','SINGLE_CHOICE','16',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('17','1','11.1 其他方式说明','TEXT','17',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('18','1','12. 交通出行方式','MULTIPLE_CHOICE','18',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('19','1','13. 计划在本市游览天数','NUMBER','19',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('20','1','14. 今天是来本市的第几天','NUMBER','20',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('21','1','15. 交给旅行社或单位支付的费用','NUMBER','21',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('22','1','16. 交通费','NUMBER','22',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('23','1','16.1 飞机费用','NUMBER','23',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('24','1','16.2 火车费用','NUMBER','24',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('25','1','16.3 长途汽车费用','NUMBER','25',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('26','1','16.4 轮船费用','NUMBER','26',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('27','1','16.5 自驾/租车费用','NUMBER','27',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('28','1','17. 其他费用','NUMBER','28',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('29','1','18. 餐饮费用','NUMBER','29',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('30','1','19. 住宿费用','NUMBER','30',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('31','1','20. 门票费用','NUMBER','31',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('32','1','21. 购物费用','NUMBER','32',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('33','1','21.1 文创产品费用','NUMBER','33',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('34','1','22. 文化艺术/娱乐费用','NUMBER','34',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('35','1','23. 医疗康养费用','NUMBER','35',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('36','1','24. 市内交通费用','NUMBER','36',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('37','1','25. 其他费用','NUMBER','37',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('38','1','26. 本次出游在本省共游览了几个市','NUMBER','38',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('39','1','27. 市1','TEXT','39',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('40','1','28. 市2','TEXT','40',1,0,NULL,NULL,0);
INSERT INTO `question` (`id`,`questionnaire_id`,`content`,`type`,`sort_order`,`is_required`,`is_screening`,`screening_condition`,`description`,`is_deleted`)  VALUES ('41','1','29. 市3','TEXT','41',1,0,NULL,NULL,0);
-- ----------------------------
-- Table structure for table questionnaire
-- ----------------------------
DROP TABLE IF EXISTS `questionnaire`;
CREATE TABLE `questionnaire` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '问卷标题',
  `description` text COLLATE utf8mb4_unicode_ci COMMENT '问卷描述',
  `type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT 'TOURISM_SURVEY' COMMENT '问卷类型：TOURISM_SURVEY(旅游调查问卷)',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'DRAFT' COMMENT '问卷状态：DRAFT(草稿)、PUBLISHED(已发布)、CLOSED(已关闭)',
  `valid_start_time` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `valid_end_time` datetime DEFAULT NULL COMMENT '有效期结束时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='问卷表';

-- ----------------------------
-- Records of questionnaire
-- ----------------------------
INSERT INTO `questionnaire` (`id`,`title`,`description`,`type`,`status`,`valid_start_time`,`valid_end_time`,`create_time`,`update_time`,`create_by`,`is_deleted`)  VALUES ('1','地方接待国内游客抽样调查问卷','为了不断提高我们的旅游接待水平，使您得到质价相符的服务，请您帮助填写这份调查问卷。','TOURISM_SURVEY','PUBLISHED','2025-06-25 08:24:59','2026-06-25 08:24:59','2025-06-25 08:24:59','2025-06-25 08:24:59','1',0);
INSERT INTO `questionnaire` (`id`,`title`,`description`,`type`,`status`,`valid_start_time`,`valid_end_time`,`create_time`,`update_time`,`create_by`,`is_deleted`)  VALUES ('2','地方接待国内游客抽样调查问卷','为了不断提高我们的旅游接待水平，使您得到质价相符的服务，请您帮助填写这份调查问卷。','TOURISM_SURVEY','PUBLISHED','2025-06-25 08:28:19','2026-06-25 08:28:19','2025-06-25 08:28:19','2025-06-25 08:28:19','1',0);
-- ----------------------------
-- Table structure for table sms_code
-- ----------------------------
DROP TABLE IF EXISTS `sms_code`;
CREATE TABLE `sms_code` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `code` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码',
  `type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码类型：LOGIN(登录)、REGISTER(注册)、QUESTIONNAIRE(问卷验证)',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'UNUSED' COMMENT '验证码状态：UNUSED(未使用)、USED(已使用)、EXPIRED(已过期)',
  `send_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_phone_type` (`phone`,`type`),
  KEY `idx_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信验证码表';

-- ----------------------------
-- Records of sms_code
-- ----------------------------
-- ----------------------------
-- Table structure for table user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `role` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'USER' COMMENT '用户角色：ADMIN(管理员)、USER(普通用户)',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'UNVERIFIED' COMMENT '用户状态：UNVERIFIED(未认证)、VERIFIED(已认证)、BLOCKED(已封禁)',
  `province` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '常驻地省份',
  `city` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '常驻地城市',
  `district` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '常驻地县区',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
